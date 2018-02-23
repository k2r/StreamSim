/**
 * 
 */
package core.network.kafka.producer;

import core.element.IElement;
import core.network.IProducer;

/**
 * @author Roland
 *
 */
public interface IKafkaStreamProducer extends IProducer{

	/**
	 * 
	 * @return
	 */
	public IElement[] getPacket();
	
	/**
	 * 
	 * @return
	 */
	public int getPacketCounter();
}
