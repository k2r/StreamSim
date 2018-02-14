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
public interface IConsumer extends Serializable {
	
	/**
	 * This method establishes an active connection with the producing service
	 */
	public void connect() throws RemoteException;;
	
	/**
	 *
	 * @return the most recent packet of stream elements available through the producing service
	 */
	public IElement[] consume() throws RemoteException;;
	
	/**
	 * This method closes the connection with the producing service
	 */
	public void disconnect() throws RemoteException;;

}
