/**
 * 
 */
package core.network.rmi.consumer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import core.network.IConsumer;

/**
 * @author Roland
 *
 */
public interface IRMIStreamConsumer extends Remote, IConsumer{
	
	public Remote getRegistry(String resource) throws RemoteException;

}
