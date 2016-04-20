/**
 * 
 */
package core.attribute;

import java.util.ArrayList;

import core.attribute.type.AttributeType;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a stream attribute
 */
public interface IStreamAttribute {
	
	/**
	 * 
	 * @return the name of the current attribute
	 */
	public String getName();
	
	/**
	 * 
	 * @return the type of the current attribute
	 */
	public AttributeType getType();
	
	/**
	 * 
	 * @return the reference value list of the attribute (only if relevant)
	 */
	public ArrayList<String> getReferenceValue();
	
	/**
	 * 
	 * @return the value space for the attribute
	 */
	public String getValueSpace();

}
