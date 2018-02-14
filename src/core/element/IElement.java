/**
 * 
 */
package core.element;

import java.io.Serializable;
import java.util.ArrayList;

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

	/**
	 * 
	 * @param description a flat model description of a stream element (the list of attribute names for relational model, the list of edges for a RDF graph, etc.)
	 * @return the string representing the current element.
	 */
	public String toString(ArrayList<String> description);
}
