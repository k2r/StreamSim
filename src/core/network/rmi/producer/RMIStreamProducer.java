/**
 * 
 */
package core.network.rmi.producer;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public class RMIStreamProducer extends UnicastRemoteObject implements IRMIStreamProducer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7442458055103865656L;
	
	private int packetCounter;
	private String host;
	private int port;
	private Registry registry;
	private static final Logger logger = Logger.getLogger("RMIStreamProducer");
	
	public RMIStreamProducer(String host, int port) throws RemoteException {
		super(port);
		this.setHost(host);
		this.setPort(port);
		this.packetCounter = 1;
		System.setProperty("java.rmi.server.hostname", host);
	}
	
	/**
	 * @return the packetCounter
	 */
	@Override
	public int getPacketCounter() {
		return packetCounter;
	}

	/**
	 * @param packetCounter the packetCounter to set
	 */
	public void setChunkCounter(int chunkCounter) {
		this.packetCounter = chunkCounter;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
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

	@Override
	public void connect() {
		try {
			this.registry = LocateRegistry.createRegistry(this.getPort());
		} catch (RemoteException e) {
			logger.severe("Unable to export remote object because " + e);
		}
	}
	
	@Override
	public void cast(IElement[] packet){
		try {
			try{
				registry.unbind("tuples");
			}catch(NotBoundException e1){
				logger.info("No shard submitted yet...");
			}
			registry.bind("tuples", (IRMIStreamProducer)this);
			logger.info("Chunk with id " + this.packetCounter + " has been submitted properly");
			this.packetCounter++;
		} catch (RemoteException | AlreadyBoundException e) {
			logger.info("Server unable to bind the remote object");
			logger.info("Re-sending shard...");
			this.cast(packet);
		}
	}
	
	@Override
	public void release(){
		try {
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (NoSuchObjectException e) {
			logger.severe("There is no registry to release on given host/port");
		}
	}	
}