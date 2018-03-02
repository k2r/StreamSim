/**
 * 
 */
package core.main;

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

import core.config.global.ConfigParser;
import core.element.IElement;
import core.model.relational.IRelationalModel;
import core.network.MessagingType;
import core.network.IProducer;
import core.network.PacketSubmitter;
import core.network.rmi.producer.RMIStreamProducer;
import core.persistence.IPersistenceConnector;
import core.persistence.JdbcPersistenceManager;
import core.stream.IStream;
import core.stream.StreamModelName;
import core.stream.relational.RelationalStream;

/**
 * @author Roland
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		/*Parse parameters of stream generator*/
		ConfigParser global = new ConfigParser("conf/properties.xml");
		
		global.initParameters();
		String command = global.getCommand();
		
		String streamName = global.getName();
		String streamModel = global.getModel();
		String variation  = global.getVariation();
		Long frequency = global.getFrequency();
		String consumer = global.getConsumer();
		IProducer producer = null;
		
		String dbHost = global.getDbHost();
		String dbUser = global.getDbUser();
		String dbPwd = global.getDbPwd();
		
		/*Initialize stream structures*/

		IStream stream = null;
		HashMap<String, IElement[]> elements = null;
		int pSize = 0;
		int tSize = 0;

		/*Initialize a new stream for record and play command*/
		if(command.equalsIgnoreCase("RECORD") || command.equalsIgnoreCase("PLAY")){	
			frequency = global.getFrequency();

			System.out.println("Welcome to the Stream Simulator by Roland 2K");
			if(command.equalsIgnoreCase("RECORD")){
				System.out.println("The stream " + streamName + " will be recorded for a future replay");
			}else{
				System.out.println("The stream " + streamName + " will be emitted on consumer service " + consumer);
			}

			System.out.println("The output rate will vary according to the variation " + variation);
			
			if(streamModel.equalsIgnoreCase(StreamModelName.REL.toString())){
				stream = new RelationalStream(streamModel, variation);
			}
		
			stream.initializeModel();
			stream.initializeVariations();
			stream.generateStream(frequency);

			elements = stream.getElements();

			pSize = stream.getProfiles().size();
			tSize = stream.getTransitions().size();

			/*Initialize a peristence connector for command record and persist parameters and stream elements*/
			if(command.equalsIgnoreCase("RECORD")){
				try {
					IPersistenceConnector manager = new JdbcPersistenceManager("streamsim", dbHost, dbUser, dbPwd);

					System.out.println("Persisting parameters for stream " + streamName + "...");
					manager.persistParameters(streamName, variation, frequency);

					System.out.println("Recording tuples for stream " + streamName + "...");
					manager.persistStream(streamName, variation, stream.getModel(), elements);
					System.out.println("Stream " + streamName + " recorded successfully!");
					return;
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if(command.equalsIgnoreCase("REPLAY")){
			try {
				IPersistenceConnector manager = new JdbcPersistenceManager("streamsim", dbHost, dbUser, dbPwd);

				System.out.println("Welcome to the Stream Simulator by Roland 2K");
				System.out.println("The stream " + streamName + " will be replayed from database located on host " + dbHost);
				System.out.println("Time interval between each shard : " + frequency + " second(s)");

				System.out.println("The output rate will vary according to variation " + variation);

				if(streamModel.equalsIgnoreCase(StreamModelName.REL.toString())){
					stream = new RelationalStream(streamModel, variation);
				}

				stream.initializeModel();
				stream.initializeVariations();

				System.out.println("Loading the stream from the database...");
				if(streamModel.equalsIgnoreCase(StreamModelName.REL.toString())){
					elements = ((JdbcPersistenceManager) manager).getElements(streamName, variation, ((IRelationalModel) stream.getModel()).getAttributes());
				}
				
				pSize = stream.getProfiles().size();
				tSize = stream.getTransitions().size();

			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}

		if(command.equalsIgnoreCase("PLAY") || command.equalsIgnoreCase("REPLAY")){
			/*Initialize stream producer*/
			if(consumer.equalsIgnoreCase(MessagingType.RMI.toString())){
				String rmiHost = global.getRmiHost();
				Integer rmiPort = global.getRmiPort();
				producer = (IProducer) new RMIStreamProducer(rmiHost, rmiPort);
			}
			if(consumer.equalsIgnoreCase(MessagingType.KFK.toString())){
				String kfkHost = global.getKafkaHost();
				//TODO implement a kafka producer
			}
			
			int i = 0;
			int j = 0;
			int nextP = 1;
			System.out.println("Starting transmission in a few moment...");
			Thread.sleep(5000);
			while(i < pSize){
				stream.setCurrentProfile(stream.getProfiles().get(i));
				System.out.println("Profile " + i + "....");
				long durationP = (long) stream.getCurrentProfile().getDuration();
				int k = 0;
				while(k < durationP){
					String pChunkKey = "P" + i + "It" + k;
					IElement[] packet = elements.get(pChunkKey);
					ExecutorService executorP = Executors.newCachedThreadPool();
					Future<?> futureP = executorP.submit(new PacketSubmitter(producer, streamName, packet, frequency));
					try {
						futureP.get(frequency, TimeUnit.SECONDS);
						k += frequency;
					} catch (TimeoutException e) {
						futureP.cancel(false);
					} catch (ExecutionException e) {
						futureP.cancel(false);
					} catch (InterruptedException e) {
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
					stream.getCurrentTransition().solveTransitionFunc(stream.getCurrentProfile().getNbElementPerTick(), stream.getNextProfile().getNbElementPerTick(), frequency);

					long durationT = (long) stream.getCurrentTransition().getDuration();
					
					int l = 0;
					while(l < durationT){

						String tChunkKey = "T" + j + "It" + l;
						IElement[] packet = elements.get(tChunkKey);
						ExecutorService executorT = Executors.newCachedThreadPool();
						Future<?> futureT = executorT.submit(new PacketSubmitter(producer, streamName, packet, frequency));
						try {
							futureT.get(frequency, TimeUnit.SECONDS);
							l += frequency;
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
