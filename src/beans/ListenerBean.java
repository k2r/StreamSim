/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class ListenerBean implements Serializable {

	
	
	private String host;
	private Integer port;
	private String type;
	private String resource;
	private Integer nbItems;
	private ArrayList<String> items;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -431221303091422802L;
	
	public ListenerBean() {
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
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * @return the nbItems
	 */
	public Integer getNbItems() {
		return nbItems;
	}

	/**
	 * @param nbItems the nbItems to set
	 */
	public void setNbItems(Integer nbItems) {
		this.nbItems = nbItems;
	}

	/**
	 * @return the items
	 */
	public ArrayList<String> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}

}
