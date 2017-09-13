/**
 * 
 */
package core.runnable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import beans.ElementStreamBean;
import core.element.IElement;
import core.model.relational.RelationalModel;
import core.network.IProducer;
import core.network.PacketSubmitter;
import core.persistence.IPersistenceConnector;
import core.persistence.JdbcPersistenceManager;
import core.stream.IStream;

/**
 * @author Roland
 *
 */
public class RunnableStreamEmission implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8264272828086522045L;
	private Boolean runFlag;
	private String stateMsg;

	private IStream stream;
	private IProducer producer;
	private Long frequency;
	private HashMap<String, IElement[]> elements;
	private Integer profileSize;
	private Integer transitionSize;
	private Integer profileIndex;
	private Integer transitionIndex;
	private Integer nextProfile;

	/**
	 * 
	 */
	public RunnableStreamEmission(HttpServletRequest req, ElementStreamBean bean, String command) { 
		this.stream = bean.getStream();
		this.producer = bean.getProducer();
		if(command.equalsIgnoreCase("PLAY")){
			this.frequency = Long.parseLong((String) req.getParameter("frequency"));
			this.stream.generateStream(frequency);

			this.elements = this.stream.getElements();
			this.profileSize = stream.getProfiles().size();;
			this.transitionSize = stream.getTransitions().size();
			this.profileIndex = 0;
			this.transitionIndex = 0;
			this.nextProfile = 1;
			this.stateMsg = "Emission of the stream " + bean.getName() + " with variation " + bean.getVariation() + "...";
		}
		if(command.equalsIgnoreCase("RECORD")){
			this.frequency = Long.parseLong((String) req.getParameter("frequency"));
			this.stream.generateStream(frequency);

			this.elements = this.stream.getElements();

			String dbName = (String) req.getParameter("dbname");
			String dbHost = (String) req.getParameter("dbhost");
			String dbUser = (String) req.getParameter("dbuser");
			String dbPwd = (String) req.getParameter("dbpwd");
			try {
				IPersistenceConnector manager = new JdbcPersistenceManager(dbName, dbHost, dbUser, dbPwd);
				manager.persistParameters(bean.getName(), bean.getVariation(), frequency);
				manager.persistStream(bean.getName(), bean.getVariation(), bean.getStream().getModel(), bean.getStream().getElements());
				this.stateMsg = "Stream " + bean.getName() + " recorded successfully on host " + dbHost + "!";
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		if(command.equalsIgnoreCase("REPLAY")){

			String dbName = (String) req.getParameter("dbname");
			String dbHost = (String) req.getParameter("dbhost");
			String dbUser = (String) req.getParameter("dbuser");
			String dbPwd = (String) req.getParameter("dbpwd");

			try{
				IPersistenceConnector manager = new JdbcPersistenceManager(dbName, dbHost, dbUser, dbPwd);

				this.frequency = ((JdbcPersistenceManager) manager).getFrequency(bean.getName());
				this.elements = ((JdbcPersistenceManager) manager).getElements(bean.getName(), bean.getVariation(), ((RelationalModel) this.stream.getModel()).getAttributes());

				this.profileSize = stream.getProfiles().size();
				this.transitionSize = stream.getTransitions().size();
				this.profileIndex = 0;
				this.transitionIndex = 0;
				this.nextProfile = 1;
				this.stateMsg = "Re-emission of the stream " + bean.getName() + " with variation " + bean.getVariation() + "...";
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void startEmission(){
		this.runFlag = true;
	}

	public void stopEmission(){
		this.runFlag = false;
	}

	public String getStateMsg(){
		return this.stateMsg;
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(this.runFlag){
			if(this.profileIndex < this.profileSize){
				stream.setCurrentProfile(stream.getProfiles().get(this.profileIndex));

				long durationP = (long) stream.getCurrentProfile().getDuration();
				int k = 0;
				while(k < durationP && this.runFlag){
					String pPacketHeader = "P" + this.profileIndex + "It" + k;
					IElement[] packet = elements.get(pPacketHeader);
					ExecutorService executorP = Executors.newCachedThreadPool();
					Future<?> futureP = executorP.submit(new PacketSubmitter(this.producer, packet, this.frequency));
					try {
						futureP.get(frequency, TimeUnit.SECONDS);
						k += frequency;
					} catch (TimeoutException e) {
						futureP.cancel(false);
					} catch (ExecutionException e) {
						futureP.cancel(true);
					} catch (InterruptedException e) {
						futureP.cancel(true);
					}
					executorP.shutdownNow();
				}
				if(k == durationP){
					this.profileIndex++;
				}
			}else{
				this.producer.release();
			}

			if(this.transitionIndex < this.transitionSize && this.nextProfile < this.profileSize){
				stream.setTransition(true);
				stream.setCurrentTransition(stream.getTransitions().get(this.transitionIndex));
				stream.setNextProfile(stream.getProfiles().get(this.nextProfile));

				stream.getCurrentTransition().solveTransitionFunc(stream.getCurrentProfile().getNbElementPerTick(), stream.getNextProfile().getNbElementPerTick(), frequency);

				long durationT = (long) stream.getCurrentTransition().getDuration();

				int l = 0;
				while(l < durationT && this.runFlag){

					String tPacketHeader = "T" + this.transitionIndex + "It" + l;
					IElement[] packet = elements.get(tPacketHeader);
					ExecutorService executorT = Executors.newCachedThreadPool();
					Future<?> futureT = executorT.submit(new PacketSubmitter(this.producer, packet, this.frequency));
					try {
						futureT.get(frequency, TimeUnit.SECONDS);
						l += frequency;
					} catch (TimeoutException e) {
						futureT.cancel(false);
					} catch (ExecutionException e) {
						futureT.cancel(true);
					} catch (InterruptedException e) {
						futureT.cancel(true);
					}
					executorT.shutdownNow();
				}

				if(l == durationT){
					stream.setTransition(false);
					this.transitionIndex++;
					this.nextProfile++;
				}
			}
		}
	}

}
