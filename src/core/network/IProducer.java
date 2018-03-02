/**
 * 
 */
package core.network;

import java.io.Serializable;
import java.rmi.RemoteException;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public interface IProducer extends Serializable{
	
	/**
	 * This method establishes an active connection with the consumer service
	 */
	public void connect()throws RemoteException;
	
	/**
	 * This method produces a new packet of stream elements and sends it to a consuming service
	 * @param streamName the name of the stream to update 
	 * @param packet the packet of stream elements to send 
	 */
	public void produce(String streamName, IElement[] packet) throws RemoteException;
	
	/**
	 * This method closes the connection with the consuming service
	 */
	public void disconnect() throws RemoteException;
}
