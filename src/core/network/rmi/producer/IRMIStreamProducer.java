/**
 * 
 */
package core.network.rmi.producer;

import java.rmi.*;

import core.network.IProducer;

/**
 * @author Roland
 *
 */
public interface IRMIStreamProducer extends Remote, IProducer {
	
	public int getPacketCounter() throws RemoteException;

}
