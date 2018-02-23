/**
 * 
 */
package core.network.kafka.consumer;

import core.network.IConsumer;

/**
 * @author Roland
 *
 */
public interface IKafkaStreamConsumer extends IConsumer{

	public String getServerProperty(String property);
	
}
