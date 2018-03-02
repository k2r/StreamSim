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
	private Integer port;
	private Registry registry;
	private IElement[] packet;
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
	public int getPacketCounter() throws RemoteException {
		return packetCounter;
	}

	/**
	 * @param packetCounter the packetCounter to set
	 */
	public void setPacketCounter(int packetCounter) throws RemoteException {
		this.packetCounter = packetCounter;
	}

	/**
	 * @return the host
	 */
	public String getHost() throws RemoteException {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) throws RemoteException {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() throws RemoteException {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) throws RemoteException {
		this.port = port;
	}

	@Override
	public void connect() throws RemoteException {
		this.registry = LocateRegistry.createRegistry(this.getPort());		
	}
	
	@Override
	public void produce(String streamName, IElement[] packet) throws RemoteException{
		this.packet = packet;
		try {
			try{
				registry.unbind(streamName);
			}catch(NotBoundException e1){
				logger.info("No packet submitted yet...");
			}
			registry.bind(streamName, (IRMIStreamProducer)this);
			logger.fine("Packet with id " + this.packetCounter + " has been submitted properly");
			this.packetCounter++;
		} catch (AlreadyBoundException e) {
			logger.info("Server unable to bind the remote object");
			logger.info("Re-sending packet...");
			this.produce(streamName, packet);
		}
	}
	
	@Override
	public void disconnect() throws RemoteException{
		try {
			UnicastRemoteObject.unexportObject(this.registry, true);
		} catch (NoSuchObjectException e) {
			logger.severe("There is no registry to release on given host/port");
		}
	}

	@Override
	public IElement[] getPacket() throws RemoteException {
		return this.packet;
	}
}