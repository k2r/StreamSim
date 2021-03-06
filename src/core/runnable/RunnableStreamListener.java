/**
 * 
 */
package core.runnable;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Logger;

import core.element.IElement;
import core.element.relational.RelationalStreamElement;
import core.network.IConsumer;
import core.network.MessagingType;
import core.network.kafka.consumer.KafkaStreamConsumer;
import core.network.rmi.consumer.IRMIStreamConsumer;
import core.network.rmi.consumer.RMIStreamConsumer;
import core.network.socket.consumer.SocketStreamConsumer;

/**
 * @author Roland
 *
 */
public class RunnableStreamListener implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3647940151358368943L;

	private String network;
	private String host;
	private Integer port;
	private String resourceName;
	private String type;
	private Boolean runFlag;	
	private Integer nbItems;

	private ArrayList<String> items;
	private IConsumer consumer;
	private SocketStreamConsumer socketConsumer;
	public static Logger logger = Logger.getLogger("RunnableStreamListener");
	
	public RunnableStreamListener(String network, String host, Integer port, String resourceName, String type, Integer nbItems) {
		this.network = network;
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
		try {
			this.consumer.disconnect();
		} catch (Exception e) {
			logger.severe("The consumer cannot be disconnected because " + e);
			e.printStackTrace();
		}
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
					if(this.network.equalsIgnoreCase(MessagingType.RMI.toString())){
						if(this.consumer == null){
							this.consumer = new RMIStreamConsumer(this.host, this.port, this.resourceName);
						} 
						this.consumer.connect();//the RMI consumer should connnect to the remote JVM each time it consumes new stream elements
						Registry registry = ((IRMIStreamConsumer)consumer).getRegistry();
						if(registry != null){           	
							IElement[] istream = this.consumer.consume();
							int n = istream.length;
							this.nbItems = Math.min(this.nbItems, n);
							for(int i = 0; i < this.nbItems; i++){
								String readableItem = ((RelationalStreamElement)istream[i]).toString();
								temp.add(readableItem);
							}
							this.items = temp;
						}
					}
					if(this.network.equalsIgnoreCase(MessagingType.KFK.toString())){
						if(this.consumer == null){
							this.consumer = new KafkaStreamConsumer(this.host, this.port, this.resourceName);
							this.consumer.connect();//the Kafka consumer should connect to the cluster only once until it gets stopped
						}
						IElement[] istream = this.consumer.consume();
						int n = istream.length;
						this.nbItems = Math.min(this.nbItems, n);
						for(int i = 0; i < this.nbItems; i++){
							String readableItem = ((RelationalStreamElement)istream[i]).toString();
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
					if(this.socketConsumer == null){
						this.socketConsumer = new SocketStreamConsumer(host, port);
					}
					while(runFlag){
						ArrayList<String> temp = new ArrayList<>();
						updateBatch(temp, this.socketConsumer);
						logger.fine("Batch updated");
						this.items = temp;
					}
				} catch (IOException e1) {
					logger.severe("Unable to instanciate the stream listener because " + e1);;
				}
			}
		}
	}
	
	public void updateBatch(ArrayList<String> buffer, SocketStreamConsumer receiver){
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