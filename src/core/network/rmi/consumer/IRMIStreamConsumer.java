/**
 * 
 */
package core.network.rmi.consumer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import core.network.IConsumer;

/**
 * @author Roland
 *
 */
public interface IRMIStreamConsumer extends Remote, IConsumer{
	
	public Registry getRegistry() throws RemoteException;

}
