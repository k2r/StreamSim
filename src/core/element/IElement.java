/**
 * 
 */
package core.element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public interface IElement extends Serializable{
	
	/**
	 * 
	 * @return the timestamp of the current stream element.
	 */
	public double getTimestamp();
	
	/**
	 * 
	 * @param attrNames the list of attribute names
	 * @return the string representing the current element.
	 */
	public String toString(ArrayList<String> attrNames);
}
