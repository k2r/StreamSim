/**
 * 
 */
package core.element;

import java.io.Serializable;

/**
 * @author Roland
 * 
 * Subinterfaces must provide a method to access to the stream element. Implementing classes should override 
 */
public interface IElement extends Serializable{

	/**
	 * 
	 * @return the timestamp associated to the stream element
	 */
	public Integer getTimestamp();

	/**
	 * 
	 * @return a string representation of the stream element
	 */
	public String toString();
	
}
