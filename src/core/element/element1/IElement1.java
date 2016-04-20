/**
 * 
 */
package core.element.element1;

import core.element.IElement;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a stream element described by a single attribute.
 */
public interface IElement1<T> extends IElement{

	/**
	 * 
	 * @return the value of the current element.
	 */
	public T getValue();
	
	/**
	 * 
	 * @param value the new value for the current stream element.
	 */
	public void setValue(T value);
	
	/**
	 * 
	 * @return the size of the string representing the current element value.
	 */
	public int getSize();
}
