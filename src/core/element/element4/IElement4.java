/**
 * 
 */
package core.element.element4;

import core.element.IElement;

/**
 * @author Roland
 * Interface representing a stream element described by four attributes.
 */
public interface IElement4<T,U,V,W> extends IElement{
	
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
	 * @return the value of the third attribute describing the current element.
	 */
	public V getThirdValue();
	
	/**
	 * 
	 * @param value the new value for the third attribute describing the current stream element.
	 */
	public void setThirdValue(V value);
	
	/**
	 * 
	 * @return the value of the fourth attribute describing the current element.
	 */
	public W getFourthValue();
	
	/**
	 * 
	 * @param value the new value for the fourth attribute describing the current stream element.
	 */
	public void setFourthValue(W value);
	
	/**
	 * 
	 * @return the size of the string representing the current element value.
	 */
	public int getSize(int index);
}
