/**
 * 
 */
package core.network.rmi.consumer;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import core.element.IElement;
import core.network.rmi.producer.IRMIStreamProducer;

/**
 * @author Roland
 *
 */
public class RMIStreamConsumer extends UnicastRemoteObject implements IRMIStreamConsumer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6325518038388152059L;
	private String host;
	private Integer port;
	private Registry registry;
	private String resource;
	private static final Logger logger = Logger.getLogger("RMIStreamConsumer");
	
	public RMIStreamConsumer(String host, Integer port, String resource) throws RemoteException {
		super(port);
		this.host = host;
		this.port = port;
		this.resource = resource;
		System.setProperty("java.rmi.server.hostname", host);
	}
	
	/* (non-Javadoc)
	 * @see core.network.IConsumer#connect()
	 */
	@Override
	public void connect() {
		try {
			this.registry = LocateRegistry.getRegistry(host, port);
		} catch (RemoteException e) {
			logger.severe("No registry found on host " + this.host + ":" + this.port);
		}
	}

	/* (non-Javadoc)
	 * @see core.network.IConsumer#consume()
	 */
	@Override
	public IElement[] consume() {
		IElement[] result = null;
		if(this.registry != null){
			try {
				IRMIStreamProducer stub = (IRMIStreamProducer) registry.lookup(this.resource);
				result = stub.getPacket();
				this.registry.unbind(this.resource);
			} catch (RemoteException | NotBoundException e) {
				logger.fine("Unable to retrieve resource " + this.resource + " because " + e);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see core.network.IConsumer#disconnect()
	 */
	@Override
	public void disconnect() {
		try {
			UnicastRemoteObject.unexportObject(this.registry, true);
		} catch (NoSuchObjectException e) {
			logger.severe("There is no registry to release on given host/port");
		}
	}

	/* (non-Javadoc)
	 * @see core.network.rmi.consumer.IRMIStreamConsumer#getRegistry()
	 */
	@Override
	public Registry getRegistry() throws RemoteException {
		return this.registry;
	}
}
