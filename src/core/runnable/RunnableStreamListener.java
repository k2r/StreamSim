/**
 * 
 */
package core.runnable;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import core.element.IElement;
import core.network.rmi.source.IRMIStreamSource;
import core.network.socket.receiver.SocketStreamReceiver;

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

	private static CopyOnWriteArrayList<String> items;
	public static Logger logger = Logger.getLogger("RunnableStreamListener");
	
	public RunnableStreamListener(String host, Integer port, String resourceName, String type, Integer nbItems) {
		this.host = host;
		this.port = port;
		this.resourceName = resourceName;
		this.runFlag = true;
		this.type = type;
		this.nbItems = nbItems;
		if(RunnableStreamListener.items == null){
			RunnableStreamListener.items = new CopyOnWriteArrayList<>();
		}
	}

	public static CopyOnWriteArrayList<String> getItems(){
		return RunnableStreamListener.items;
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
				try {
		            Registry registry = LocateRegistry.getRegistry(host, port);
		            if(registry != null){
		            	ArrayList<String> allItems = new ArrayList<>();
		            	IRMIStreamSource stub = (IRMIStreamSource) registry.lookup(this.resourceName);
		            	IElement[] istream = stub.getInputStream();
						ArrayList<String> attrNames = stub.getAttrNames();
						registry.unbind(this.resourceName);
						int n = istream.length;
						for(int i = 0; i < n; i++){
							allItems.add(istream[i].toString(attrNames));
						}
						int lastIndex = allItems.size() - 1;
						this.nbItems = Math.min(this.nbItems, lastIndex);
						for(int i = 0; i < this.nbItems; i++){
							items.add(allItems.get(lastIndex - i));
						}
		            }
				}catch(Exception e){
					try {
						logger.fine("Nothing to retrieve because " + e);
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						logger.severe("Unable to wait for new info because " + e);
					}
				}
			}
			if(this.type.equalsIgnoreCase("RAW")){
				SocketStreamReceiver receiver;
				try {
					receiver = new SocketStreamReceiver(host, port);
					if(items.size() > nbItems){
						items.remove(0);
					}
					String tuple = receiver.getMessage();
					if(tuple == null){
						try{
							Thread.sleep(50);
						}catch(InterruptedException e){
							logger.severe("Unable to wait for new tuples because " + e);
						}
					}else{
						items.add(tuple);
					}
				} catch (IOException e1) {
					logger.severe("Unable to instanciate the stream listener because " + e1);;
				}
			}
		}
	}
}
