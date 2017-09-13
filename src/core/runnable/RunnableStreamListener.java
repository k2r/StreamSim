/**
 * 
 */
package core.runnable;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Logger;

import core.element.relational.IRelationalElement;
import core.network.rmi.producer.IRMIStreamProducer;
import core.network.socket.consumer.SocketStreamReceiver;

/**
 * @author Roland
 *
 */
public class RunnableStreamListener implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3647940151358368943L;

	private String host;
	private Integer port;
	private String resourceName;
	private String type;
	private Boolean runFlag;	
	private Integer nbItems;

	private ArrayList<String> items;
	private SocketStreamReceiver receiver;
	public static Logger logger = Logger.getLogger("RunnableStreamListener");
	
	public RunnableStreamListener(String host, Integer port, String resourceName, String type, Integer nbItems) {
		this.host = host;
		this.port = port;
		this.resourceName = resourceName;
		this.runFlag = true;
		this.type = type;
		this.nbItems = nbItems;
		this.items = new ArrayList<>();
	}

	public ArrayList<String> getItems(){
		return this.items;
	}
	
	public void startListener(){
		this.runFlag = true;
	}
	
	public void stopListener() {
		this.runFlag = false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		while(runFlag){
			if(this.type.equalsIgnoreCase("STREAMSIM")){
				ArrayList<String> temp = new ArrayList<>();
				try {
		            Registry registry = LocateRegistry.getRegistry(host, port);
		            if(registry != null){
		            	IRMIStreamProducer stub = (IRMIStreamProducer) registry.lookup(this.resourceName);
		            	//FIXME define and implement a RMI consumer and make a distinction between consumer services
		            	IRelationalElement[] istream = null; //= stub.getInputStream();
						ArrayList<String> attrNames = null; //stub.getAttrNames();
						registry.unbind(this.resourceName);
						int n = istream.length;
						this.nbItems = Math.min(this.nbItems, n);
						for(int i = 0; i < this.nbItems; i++){
							String readableItem = istream[i].toString(attrNames);
							temp.add(readableItem);
						}
						this.items = temp;
		            }
				}catch(Exception e){
					try {
						logger.fine("Nothing to retrieve because " + e);
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						logger.severe("Unable to wait for new info because " + e1);
					}
				}
			}
			if(this.type.equalsIgnoreCase("RAW")){
				try {
					if(this.receiver == null){
						receiver = new SocketStreamReceiver(host, port);
					}
					while(runFlag){
						ArrayList<String> temp = new ArrayList<>();
						updateBatch(temp, receiver);
						logger.fine("Batch updated");
						this.items = temp;
					}
				} catch (IOException e1) {
					logger.severe("Unable to instanciate the stream listener because " + e1);;
				}
			}
		}
	}
	
	public void updateBatch(ArrayList<String> buffer, SocketStreamReceiver receiver){
		while(buffer.size() < this.nbItems){
			logger.fine("Acquiring items...");
			String tuple = receiver.getMessage();
			if(tuple != null){
				buffer.add(tuple);
				logger.fine("Add of item " + tuple);
			}
		}
	}
}