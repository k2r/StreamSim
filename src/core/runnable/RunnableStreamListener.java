/**
 * 
 */
package core.runnable;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Logger;

import core.element.IElement;
import core.network.rmi.source.IRMIStreamSource;
import core.network.rmi.source.RMIInfoSource;

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
	private ArrayList<String> inputs;
	private Integer nbItems;
	public static Logger logger = Logger.getLogger("RunnableStreamListener");
	
	public RunnableStreamListener(String host, Integer port, String resourceName, String type, Integer nbItems) {
		this.host = host;
		this.port = port;
		this.resourceName = resourceName;
		this.runFlag = true;
		this.type = type;
		this.inputs = new ArrayList<>();
		this.nbItems = nbItems;
	}
	
	public ArrayList<String> getInputs(){
		return this.inputs;
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
							this.inputs.add(allItems.get(lastIndex - i));
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
				try {
		            Registry registry = LocateRegistry.getRegistry(host, port);
		            if(registry != null){
		            	ArrayList<String> allItems = new ArrayList<>();
		            	RMIInfoSource stub = (RMIInfoSource) registry.lookup(this.resourceName);
		            	allItems = stub.getInfo();
		            	int lastIndex = allItems.size() - 1;
						this.nbItems = Math.min(this.nbItems, lastIndex);
						for(int i = 0; i < this.nbItems; i++){
							this.inputs.add(allItems.get(lastIndex - i));
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
		}
	}

}
