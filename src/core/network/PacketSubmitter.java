/**
 * 
 */
package core.network;

import java.io.Serializable;
import java.util.logging.Logger;

import core.element.IElement;

/**
 * @author Roland
 * Class sending an element of a generated stream through a given stream source
 */
public class PacketSubmitter implements Runnable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7948384213242513390L;
	private IProducer producer;
	private IElement[] packet;
	private final Long frequency;
	private static final Logger logger = Logger.getLogger("PacketSubmitter");
	
	/**
	 * 
	 */
	public PacketSubmitter(IProducer producer, IElement[] packet, Long frequency) {
		this.producer = producer;
		this.packet = packet;
		this.frequency = frequency;
	}

	/**
	 * @return the list of packet belonging to the current stream
	 */
	public IElement[] getPacket() {
		return packet;
	}


	/**
	 * @param packet the new list of packet to consider
	 */
	public void setShard(IElement[] packet) {
		this.packet = packet;
	}

	/**
	 * @return the time interval between two consecutive sent of tuples
	 */
	public Long getFrequency() {
		return this.frequency;
	}
	
	/**
	 * Send the element at the current index of the current stream 
	 */
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try{
			logger.fine("Number of elements : " + this.getPacket().length);
			this.producer.produce(this.getPacket());
			long end = System.currentTimeMillis();
			long remaining = (this.getFrequency() * 1000) - (end - start); //the time remaining after the complete emission
			if(remaining > 0){
				Thread.sleep(remaining);
			}
		}catch(Exception e){
			logger.severe("Unable to start the PacketSubmitter process because " + e);
			e.printStackTrace();
		}
	}

}
