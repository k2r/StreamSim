/**
 * 
 */
package core.network.rmi.producer;

import java.rmi.*;

import core.element.IElement;
import core.network.IProducer;

/**
 * @author Roland
 *
 */
public interface IRMIStreamProducer extends Remote, IProducer {
	
	/**
	 * 
	 * @return the last packet of stream elements produced by the instance
	 * @throws RemoteException if the packet cannot be retrieved
	 */
	public IElement[] getPacket() throws RemoteException;
	
	/**
	 * 
	 * @return the packet counter for the instance
	 * @throws RemoteException if the counter cannot be retrieved
	 */
	public int getPacketCounter() throws RemoteException;
}
