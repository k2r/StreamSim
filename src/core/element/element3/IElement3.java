/**
 * 
 */
package core.element.element3;


import core.element.IElement;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a stream element described by three attributes
 */
public interface IElement3<T,U,V> extends IElement{
	
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
	 * @return the size of the string representing the current element value.
	 */
	public int getSize(int index);
}
