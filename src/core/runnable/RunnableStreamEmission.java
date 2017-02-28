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
import core.jdbc.JdbcStorageManager;
import core.network.ChunckSubmitter;
import core.stream.IElementStream;

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
	
	private IElementStream stream;
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
		if(command.equalsIgnoreCase("PLAY")){
			this.frequency = Long.parseLong((String) req.getParameter("frequency"));
			this.stream.generateStream(frequency);

			this.elements = this.stream.getElements();
			this.profileSize = stream.getProfiles().size();;
			this.transitionSize = stream.getTransitions().size();
			this.profileIndex = 0;
			this.transitionIndex = 0;
			this.nextProfile = 1;
			this.stateMsg = "Emission of the stream " + bean.getName() + " on port " + bean.getPort() + " with variation " + bean.getVariation() + "...";
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
				JdbcStorageManager manager = new JdbcStorageManager(dbName, dbHost, dbUser, dbPwd);
				manager.recordParameters(bean.getName(), bean.getPort(), bean.getVariation(), frequency);
				manager.recordStream(bean.getName(), bean.getVariation(), bean.getStream().getSchema().getAttributes(), bean.getStream().getElements());
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
				JdbcStorageManager manager = new JdbcStorageManager(dbName, dbHost, dbUser, dbPwd);
				
				this.frequency = manager.getTickDelay(bean.getName());
				this.elements = manager.getElements(bean.getName(), bean.getVariation(), this.stream.getSchema().getAttributes());
				
				this.profileSize = stream.getProfiles().size();;
				this.transitionSize = stream.getTransitions().size();
				this.profileIndex = 0;
				this.transitionIndex = 0;
				this.nextProfile = 1;
				this.stateMsg = "Re-emission of the stream " + bean.getName() + " on port " + bean.getPort() + " with variation " + bean.getVariation() + "...";
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

				double rateP = stream.getCurrentProfile().getNbElementPerTick();
				long durationP = (long) stream.getCurrentProfile().getDuration();
				int k = 0;
				while(k < durationP && this.runFlag){
					String pChunkKey = "P" + this.profileIndex + "It" + k;
					IElement[] pElements = elements.get(pChunkKey);
					ExecutorService executorP = Executors.newCachedThreadPool();
					Future<?> futureP = executorP.submit(new ChunckSubmitter(pElements, rateP, stream, frequency));
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

				if(this.transitionIndex < this.transitionSize && this.nextProfile < this.profileSize){
					stream.setTransition(true);
					stream.setCurrentTransition(stream.getTransitions().get(this.transitionIndex));
					stream.setNextProfile(stream.getProfiles().get(this.nextProfile));

					stream.getCurrentTransition().solveTransitionFunc(stream.getCurrentProfile().getNbElementPerTick(), stream.getNextProfile().getNbElementPerTick(), frequency);

					long durationT = (long) stream.getCurrentTransition().getDuration();

					int l = 0;
					while(l < durationT && this.runFlag){
						double rateT = stream.getCurrentTransition().getIntermediateValue();

						String tChunkKey = "T" + this.transitionIndex + "It" + l;
						IElement[] tElements = elements.get(tChunkKey);
						ExecutorService executorT = Executors.newCachedThreadPool();
						Future<?> futureT = executorT.submit(new ChunckSubmitter(tElements, rateT, stream, frequency));
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

}
