/**
 * 
 */
package core.network;

import java.io.Serializable;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public interface IProducer extends Serializable{
	
	/**
	 * This method establish an active connection to the consumer service
	 */
	public void connect();
	
	/**
	 * This method cast a new packet of stream elements on a consumer service 
	 * @param packet the packet of stream elements to send 
	 */
	public void cast(IElement[] packet);
	
	/**
	 * This method closes the connection to the consumer service
	 */
	public void release();
}
