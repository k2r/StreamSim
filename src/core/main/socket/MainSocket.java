/**
 * 
 */
package core.main.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.element.IElement;
import core.network.ChunckSubmitter;
import core.network.socket.source.SocketStreamSource;
import core.stream.ElementStream;
import core.stream.IElementStream;
import core.util.XmlParamParser;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class MainSocket{
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the Stream Simulator by Roland 2K");
		
		XmlParamParser params = new XmlParamParser(args[0]);
		System.out.println("Reading Stream Simulator parameters (file parameters.xml)...");
		params.initParameters();
		
		Long tickDelay = params.getTick_interval();
		Integer nbExecutors = params.getMax_nb_executors();
		Integer nbThreads = params.getMax_threads();
		Integer maxSeqEmission = params.getMax_seq_emit();
		System.out.println("Time interval between each tick : " + tickDelay + " second(s)");
		System.out.println("Number of executors : " + nbExecutors);
		System.out.println("Maximum number of threads for stream elements submission : " + nbThreads);
		System.out.println("Maximum number of sequential emission of stream elements : " + maxSeqEmission);
		System.out.println();
		
		System.out.print("Enter the name of the stream to play : ");
		String schema = sc.nextLine();
		sc.reset();
		System.out.println("Select the variation model to apply of stream " + schema );
		System.out.println("1. Strictly increasing input rate (Linear)");
		System.out.println("2. Strictly increasing input rate (Scale)");
		System.out.println("3. Strictly increasing input rate (Exponential)");
		System.out.println("4. Strictly increasing input rate (Logarithmic)");
		System.out.println("5. Strictly decreasing input rate (Linear)");
		System.out.println("6. Strictly decreasing input rate (Scale)");
		System.out.println("7. Strictly decreasing input rate (Exponential)");
		System.out.println("8. Increasing and decreasing input rate (All types)");
		System.out.println("9. No input rate variation");
		System.out.print("Your selection : ");
		
		int selVar = sc.nextInt();
		String variation = "";
		switch(selVar){
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
		sc.reset();
		
		System.out.print("Select the port to transmit data elements : ");
		int port = sc.nextInt();
		IElementStream stream = new ElementStream(port, schema, variation);
		SocketStreamSource source = (SocketStreamSource)stream.getSource();
		sc.close();
		
		stream.initializeSchema();
		stream.initializeVariations();
		stream.generateStream(tickDelay);
		
		HashMap<String, IElement[]> elements = stream.getElements();
		
		int pSize = stream.getProfiles().size();
		int tSize = stream.getTransitions().size();
		
		int i = 0;
		int j = 0;
		int nextP = 1;
		
		source.accept();
		System.out.println("Starting transmission...");
		while(i < pSize){
			stream.setCurrentProfile(stream.getProfiles().get(i));
			System.out.println("Profile " + i + "....");
			double rateP = stream.getCurrentProfile().getNbElementPerTick();
			long durationP = (long) stream.getCurrentProfile().getDuration();
			for(int k = 0; k < durationP; k += tickDelay){
				String pChunkKey = "P" + i + "It" + k;
				IElement[] pElements = elements.get(pChunkKey);
				ExecutorService executorP = Executors.newCachedThreadPool();
				//ExecutorService executorP = Executors.newWorkStealingPool(nbExecutors);
		        Future<?> futureP = executorP.submit(new ChunckSubmitter(pElements, rateP, stream, tickDelay, nbThreads, maxSeqEmission));
		        try {
		            System.out.println("Started..");
		            System.out.println(futureP.get(tickDelay, TimeUnit.SECONDS));
		            System.out.println("Finished!");
		        } catch (TimeoutException e) {
		            futureP.cancel(true);
		            System.out.println("Terminated!");
		        } catch (ExecutionException e) {
		        	futureP.cancel(true);
		            System.out.println("Terminated!");
				}
		        executorP.shutdownNow();
			}    
			i++;
			
			if(j < tSize && nextP < pSize){
				stream.setTransition(true);
				stream.setCurrentTransition(stream.getTransitions().get(j));
				stream.setNextProfile(stream.getProfiles().get(nextP));
				
				System.out.println(stream.getCurrentTransition().getType().toString() + " transition " + j + "....");
				stream.getCurrentTransition().solveTransitionFunc(stream.getCurrentProfile().getNbElementPerTick(), stream.getNextProfile().getNbElementPerTick(), tickDelay);
				
				long durationT = (long) stream.getCurrentTransition().getDuration();
				
				
				System.out.println("Started..");
				for(int l = 0; l < durationT; l += tickDelay){
					double rateT = stream.getCurrentTransition().getIntermediateValue();
					
					String tChunkKey = "T" + j + "It" + l;
					IElement[] tElements = elements.get(tChunkKey);
					ExecutorService executorT = Executors.newCachedThreadPool();
					//ExecutorService executorT = Executors.newWorkStealingPool(nbExecutors);
			        Future<?> futureT = executorT.submit(new ChunckSubmitter(tElements, rateT, stream, tickDelay, nbThreads, maxSeqEmission));
					
			        try {
			            System.out.println(futureT.get(tickDelay, TimeUnit.SECONDS));
			            System.out.println("Finished!");
			        } catch (TimeoutException e) {
			            futureT.cancel(true);
			            System.out.println("Terminated!");
			        } catch (ExecutionException e) {
			        	futureT.cancel(true);
			            System.out.println("Terminated!");
					}
			        executorT.shutdownNow();
				}
		        stream.setTransition(false);
		        j++;
		        nextP++;
			}
		}
		source.close();
	}
}
