/**
 * 
 */
package core.element.relational;

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
	
}
