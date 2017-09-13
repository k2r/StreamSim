/**
 * 
 */
package core.element.relational;

import java.util.ArrayList;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public interface IRelationalElement extends IElement{
	
	/**
	 * 
	 * @return values describing the IRelationalElement
	 */
	public Object[] getStreamElement();
	
	/**
	 * 
	 * @param index the index of the attribute 
	 * @param o the new value to assign
	 */
	public void setStreamElement(Integer index, Object o);
	
	/**
	 * 
	 * @param attrNames the list of attribute names
	 * @return the string representing the current element.
	 */
	public String toString(ArrayList<String> attrNames);
}
