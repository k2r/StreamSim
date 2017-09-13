/**
 * 
 */
package core.element;

import java.io.Serializable;

/**
 * @author Roland
 * Subinterfaces must provide a method to access to the stream element
 */
public interface IElement extends Serializable{

	/**
	 * 
	 * @return the timestamp associated to the stream element
	 */
	public Integer getTimestamp();

}
