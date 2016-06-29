/**
 * 
 */
package core.element.element1;

import java.util.ArrayList;

/**
 * @author Roland KOTTO KOMBI
 *	Class implementing a stream element described by a single attribute
 */
public class Element1<T> implements IElement1<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3939904402643750575L;
	private T value;
	private double timestamp;
	
	public Element1(T value, double timestamp) {
		this.value = value;
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see core.element.element1.IElement1#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see core.element.element1.IElement1#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element1.IElement1#getSize()
	 */
	@Override
	public int getSize() {
		return this.value.toString().length();
	}

	/* (non-Javadoc)
	 * @see core.element.element1.IElement1#getTimestamp()
	 */
	@Override
	public double getTimestamp() {
		return this.timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element1.IElement1#toString()
	 */
	@Override
	public String toString(ArrayList<String> attrNames){
		return "[Element1 : <" + attrNames.get(0) + " :" + this.getValue() + ";@" + this.getTimestamp() + ">]";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj){
		Element1 element = (Element1) obj;
		return this.getValue() == element.getValue();
	}
}
