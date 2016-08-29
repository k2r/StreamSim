/**
 * 
 */
package core.network.rmi.source;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public class RMIStreamSource extends UnicastRemoteObject implements IRMIStreamSource {
	
	private int port;
	private IElement[] chunk;
	private ArrayList<String> attrNames;
	private Registry registry;
	
	public RMIStreamSource(int port) throws RemoteException {
		super(port);
		this.setPort(port);
		this.registry = LocateRegistry.createRegistry(this.getPort());
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7442458055103865656L;
	
	/* (non-Javadoc)
	 * @see core.network.rmi.IRMISource#getInputStream()
	 */
	@Override
	public IElement[] getInputStream() throws RemoteException {
		return this.chunk;
	}

	/**
	 * @return the attrNames
	 */
	@Override
	public ArrayList<String> getAttrNames() throws RemoteException{
		return attrNames;
	}

	/**
	 * @param attrNames the attrNames to set
	 */
	public void setAttrNames(ArrayList<String> attrNames) {
		this.attrNames = attrNames;
	}

	public void buffer(IElement[] chunk, ArrayList<String> attrNames){
		this.chunk = chunk;
		this.setAttrNames(attrNames);
		try {
			registry.bind("tuples", (IRMIStreamSource)this);
		} catch (RemoteException | AlreadyBoundException e) {
			System.out.println("Server unable to bind the remote object");
		}
	}
}
