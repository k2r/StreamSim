package core.main.rmi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.config.XmlStreamInitParser;
import core.element.IElement;
import core.jdbc.JdbcStorageManager;
import core.network.ChunckSubmitter;
import core.stream.ElementStream;
import core.stream.IElementStream;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class MainRMI{

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
		XmlStreamInitParser global = new XmlStreamInitParser("parameters.xml");
		global.initParameters();
		String command = global.getCommand();
		String dbHost = global.getDbHost();
		String dbUser = global.getDbUser();
		String dbPwd = global.getDbPwd();
		String streamName = global.getName();
		int port;
		int profile;
		Long tickDelay = 0L;

		IElementStream stream = null;
		HashMap<String, IElement[]> elements = null;
		int pSize = 0;
		int tSize = 0;

		if(command.equalsIgnoreCase("RECORD") || command.equalsIgnoreCase("PLAY")){	
			port = Integer.parseInt(global.getSgPort());
			profile = Integer.parseInt(global.getVariation());
			tickDelay = global.getTick_interval();

			System.out.println("Welcome to the Stream Simulator by Roland 2K");
			if(command.equalsIgnoreCase("RECORD")){
				System.out.println("The stream " + streamName + " will be recorded for a future replay");
			}else{
				System.out.println("The stream " + streamName + " will be emitted on port " + port);
			}
			
			System.out.println("Time interval between each tick : " + tickDelay + " second(s)");
			System.out.println();

			String variation = "";
			switch(profile){
			case(1) : 	variation += "linearIncrease";
			break;
			case(2) : 	variation += "scaleIncrease";
			break;
			case(3) : 	variation += "exponentialIncrease";
			break;
			case(4) : 	variation += "logarithmicIncrease";
			break;
			case(5) : 	variation += "linearDecrease";
			break;
			case(6) : 	variation += "scaleDecrease";
			break;
			case(7) : 	variation += "exponentialDecrease";
			break;
			case(8) : 	variation += "all";
			break;
			case(9) : 	variation += "no";
			break;
			}

			System.out.println("The output rate will vary according to the profile " + profile);

			stream = new ElementStream(port, streamName, variation);

			stream.initializeSchema();
			stream.initializeVariations();
			stream.generateStream(tickDelay);

			elements = stream.getElements();

			pSize = stream.getProfiles().size();
			tSize = stream.getTransitions().size();

			if(command.equalsIgnoreCase("record")){
				try {
					JdbcStorageManager manager = new JdbcStorageManager(dbHost, dbUser, dbPwd);

					System.out.println("Recording parameters for stream " + streamName + "...");
					manager.recordParameters(streamName, port, variation, tickDelay);

					System.out.println("Recording tuples for stream " + streamName + "...");
					manager.recordStream(streamName, stream.getSchema().getAttributes(), elements);
					System.out.println("Stream " + streamName + " recorded successfully!");
					return;
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if(command .equalsIgnoreCase("REPLAY")){
			try {
				JdbcStorageManager manager = new JdbcStorageManager(dbHost, dbUser, dbPwd);
				port = manager.getPort(streamName);
				String variation = manager.getVariation(streamName);
				tickDelay = manager.getTickDelay(streamName);

				System.out.println("Welcome to the Stream Simulator by Roland 2K");
				System.out.println("The stream " + streamName + " will be replayed from database located on host " + dbHost);
				System.out.println("Time interval between each tick : " + tickDelay + " second(s)");
				System.out.println();



				System.out.println("The output rate will vary according to the variation " + variation);

				stream = new ElementStream(port, streamName, variation);

				stream.initializeSchema();
				stream.initializeVariations();

				System.out.println("Loading the stream from the database...");
				elements = manager.getElements(streamName, stream.getSchema().getAttributes());

				pSize = stream.getProfiles().size();
				tSize = stream.getTransitions().size();

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		if(command.equalsIgnoreCase("PLAY") || command.equalsIgnoreCase("REPLAY")){
			int i = 0;
			int j = 0;
			int nextP = 1;
			System.out.println("Starting transmission in a few moment...");
			Thread.sleep(5000);
			while(i < pSize){
				stream.setCurrentProfile(stream.getProfiles().get(i));
				System.out.println("Profile " + i + "....");
				double rateP = stream.getCurrentProfile().getNbElementPerTick();
				long durationP = (long) stream.getCurrentProfile().getDuration();
				int k = 0;
				while(k < durationP){
					String pChunkKey = "P" + i + "It" + k;
					IElement[] pElements = elements.get(pChunkKey);
					ExecutorService executorP = Executors.newCachedThreadPool();
					Future<?> futureP = executorP.submit(new ChunckSubmitter(pElements, rateP, stream, tickDelay));
					try {
						futureP.get(tickDelay, TimeUnit.SECONDS);
						k += tickDelay;
					} catch (TimeoutException e) {
						futureP.cancel(false);
					} catch (ExecutionException e) {
						futureP.cancel(false);
					}
					executorP.shutdownNow();
					System.out.println(pChunkKey);
				}
				i++;

				if(j < tSize && nextP < pSize){
					stream.setTransition(true);
					stream.setCurrentTransition(stream.getTransitions().get(j));
					stream.setNextProfile(stream.getProfiles().get(nextP));

					System.out.println(stream.getCurrentTransition().getType().toString() + " transition " + j + "....");
					stream.getCurrentTransition().solveTransitionFunc(stream.getCurrentProfile().getNbElementPerTick(), stream.getNextProfile().getNbElementPerTick(), tickDelay);

					long durationT = (long) stream.getCurrentTransition().getDuration();
					
					int l = 0;
					while(l < durationT){
						double rateT = stream.getCurrentTransition().getIntermediateValue();

						String tChunkKey = "T" + j + "It" + l;
						IElement[] tElements = elements.get(tChunkKey);
						ExecutorService executorT = Executors.newCachedThreadPool();
						Future<?> futureT = executorT.submit(new ChunckSubmitter(tElements, rateT, stream, tickDelay));
						try {
							futureT.get(tickDelay, TimeUnit.SECONDS);
							l += tickDelay;
						} catch (TimeoutException e) {
							futureT.cancel(false);
						} catch (ExecutionException e) {
							futureT.cancel(false);
						}
						executorT.shutdownNow();
						System.out.println(tChunkKey);
					}
					stream.setTransition(false);
					j++;
					nextP++;
				}
			}
		}
		System.out.println("Closing the stream generator...");
		return;
	}
}