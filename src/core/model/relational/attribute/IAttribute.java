/**
 * 
 */
package core.model.relational.attribute;

import java.io.Serializable;
import java.util.HashMap;

import core.model.relational.attribute.type.AttributeType;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a stream attribute
 */
public interface IAttribute extends Serializable{
	
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
	 * @return the Map containing parameters of the attribute
	 */
	public HashMap<String, Object> getParameters();
	
	/**
	 * 
	 * @param o an object to compare with the current attribute
	 * @return
	 */
	public boolean equals(Object o);
}
