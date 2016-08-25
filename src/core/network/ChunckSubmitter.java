/**
 * 
 */
package core.network;

import java.util.ArrayList;
import java.util.logging.Logger;

import core.element.IElement;
import core.network.rmi.source.RMIStreamSource;
import core.stream.*;

/**
 * @author Roland
 * Class sending an element of a generated stream through a given stream source
 */
public class ChunckSubmitter implements Runnable{

	private IElement[] chunk;
	private double rate;
	private IElementStream stream;
	private final long tickRate;
	private ArrayList<String> attrNames;
	private int chunkCounter;
	private static final Logger logger = Logger.getLogger("ChunkSubmitter");
	
	/**
	 * 
	 */
	public ChunckSubmitter(IElement[] chunk, double rate, IElementStream stream, long tickRate, int chunkCounter) {
		this.chunk = chunk;
		this.rate = rate;
		this.stream = stream;
		this.tickRate = tickRate;
		this.attrNames = this.stream.getAttributeNames();
		this.chunkCounter = chunkCounter;
	}

	/**
	 * @return the list of chunk belonging to the current stream
	 */
	public IElement[] getChunk() {
		return chunk;
	}


	/**
	 * @param chunk the new list of chunk to consider
	 */
	public void setChunk(IElement[] chunk) {
		this.chunk = chunk;
	}


	/**
	 * @return the current stream rate
	 */
	public double getRate() {
		return rate;
	}


	/**
	 * @param rate the new rate to consider
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}


	/**
	 * @return the current stream
	 */
	public IElementStream getStream() {
		return stream;
	}


	/**
	 * @param stream the new stream to consider
	 */
	public void setStream(IElementStream stream) {
		this.stream = stream;
	}

	/**
	 * @return the time interval between two consecutive sent of tuples
	 */
	public long getTickRate() {
		return tickRate;
	}
	
	/**
	 * Send the element at the current index of the current stream 
	 */
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		RMIStreamSource source = (RMIStreamSource)this.getStream().getSource();
		try{
			System.out.println("Rate : " + this.getChunk().length);
			source.buffer(this.getChunk(), this.attrNames, this.chunkCounter);
			long end = System.currentTimeMillis();
			long remaining = (this.getTickRate() * 1000) - (end - start); //the time remaining after the complete emission
			if(remaining > 0){
				Thread.sleep(remaining);
			}
		}catch(Exception e){
			logger.severe("Unable to start the ChunkSubmitter process");
		}
	}

}
