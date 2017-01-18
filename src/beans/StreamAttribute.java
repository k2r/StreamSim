/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Roland
 *
 */
public class StreamAttribute implements Serializable{
	
	private String name;
	private String type;
	private HashMap<String, Object> parameters;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4207384002216643884L;

	public StreamAttribute() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the parameters
	 */
	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(HashMap<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	
}
