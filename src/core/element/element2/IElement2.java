/**
 * 
 */
package core.element.element2;

import core.element.IElement;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a stream element described by two attributes.
 */
public interface IElement2<T,U> extends IElement{
	
	/**
	 * 
	 * @return the value of the first attribute describing the current element.
	 */
	public T getFirstValue();
	
	/**
	 * 
	 * @param value the new value for the first attribute describing the current stream element.
	 */
	public void setFirstValue(T value);
	
	/**
	 * 
	 * @return the value of the second attribute describing the current element.
	 */
	public U getSecondValue();
	
	/**
	 * 
	 * @param value the new value for the second attribute describing the current stream element.
	 */
	public void setSecondValue(U value);
	
	/**
	 * 
	 * @return the size of the string representing the current element value of the attribute at the index rank.
	 */
	public int getSize(int index);
}
