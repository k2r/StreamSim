/**
 * 
 */
package core.element.element2;

import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class Element2<T, U> implements IElement2<T, U> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6422388427792808293L;
	private T value1;
	private U value2;
	private double timestamp;

	/**
	 * 
	 */
	public Element2(T value1, U value2, double timestamp) {
		this.value1 = value1;
		this.value2 = value2;
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#getFirstValue()
	 */
	@Override
	public T getFirstValue() {
		return this.value1;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#setFirstValue(java.lang.Object)
	 */
	@Override
	public void setFirstValue(T value) {
		this.value1 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#getSecondValue()
	 */
	@Override
	public U getSecondValue() {
		return value2;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#setSecondValue(java.lang.Object)
	 */
	@Override
	public void setSecondValue(U value) {
		this.value2 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#getSize(int)
	 */
	@Override
	public int getSize(int index) {
		if(index == 1){
			return this.value1.toString().length();
		}
		if(index == 2){
			return this.value2.toString().length();
		}else{
			return Integer.MIN_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#getTimestamp()
	 */
	@Override
	public double getTimestamp() {
		return this.timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element2.IElement2#toString()
	 */
	@Override
	public String toString(ArrayList<String> attrNames){
		return "[Element2 : <" + attrNames.get(0) + ":" + this.getFirstValue() + ";" + attrNames.get(1) + ":" + this.getSecondValue() + ";@" + this.getTimestamp() + ">]";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj){
		Element2 element = (Element2) obj;
		return (this.getFirstValue() == element.getFirstValue() &&
				this.getSecondValue() == element.getSecondValue());
	}
}