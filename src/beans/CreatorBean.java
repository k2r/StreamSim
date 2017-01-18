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
public class CreatorBean implements Serializable{

	private String streamName;
	private Integer nbAttributes;
	private ArrayList<StreamAttribute> attributes;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2157747449615833453L;

	public CreatorBean() {
	}

	/**
	 * @return the streamName
	 */
	public String getStreamName() {
		return streamName;
	}

	/**
	 * @param streamName the streamName to set
	 */
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	/**
	 * @return the nbAttributes
	 */
	public Integer getNbAttributes() {
		return nbAttributes;
	}

	/**
	 * @param nbAttributes the nbAttributes to set
	 */
	public void setNbAttributes(Integer nbAttributes) {
		this.nbAttributes = nbAttributes;
	}

	/**
	 * @return the attributes
	 */
	public ArrayList<StreamAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(ArrayList<StreamAttribute> attributes) {
		this.attributes = attributes;
	}
}
