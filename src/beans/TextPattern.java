/**
 * 
 */
package beans;

import java.io.Serializable;

/**
 * @author Roland
 *
 */
public class TextPattern implements Serializable{

	private String type;
	private Integer length;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7250728976652907456L;
	
	public TextPattern() {
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
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	
}
