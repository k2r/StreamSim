/**
 * 
 */
package core.network.rmi.source;

import java.io.Serializable;
import java.rmi.*;
import java.util.ArrayList;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public interface IRMIStreamSource extends Remote, Serializable{
	
	public ArrayList<String> getAttrNames() throws RemoteException;
	
	/**
	 * 
	 * @return the new elements of the stream
	 * @throws RemoteException
	 */
	public IElement[] getInputStream() throws RemoteException;

}
