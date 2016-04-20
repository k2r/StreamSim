/**
 * 
 */
package core.element.element3;

import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class Element3<T, U, V> implements IElement3<T, U, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2880136619433188706L;
	private T value1;
	private U value2;
	private V value3;
	private double timestamp;
	
	/**
	 * 
	 */
	public Element3(T value1, U value2, V value3, double timestamp) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#getFirstValue()
	 */
	@Override
	public T getFirstValue() {
		return this.value1;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#setFirstValue(java.lang.Object)
	 */
	@Override
	public void setFirstValue(T value) {
		this.value1 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#getSecondValue()
	 */
	@Override
	public U getSecondValue() {
		return this.value2;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#setSecondValue(java.lang.Object)
	 */
	@Override
	public void setSecondValue(U value) {
		this.value2 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#getThirdValue()
	 */
	@Override
	public V getThirdValue() {
		return this.value3;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#setThirdValue(java.lang.Object)
	 */
	@Override
	public void setThirdValue(V value) {
		this.value3 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#getSize(int)
	 */
	@Override
	public int getSize(int index) {
		if(index == 1){
			return this.value1.toString().length();
		}
		if(index == 2){
			return this.value2.toString().length();
		}
		if(index == 3){
			return this.value3.toString().length();
		}else{
			return Integer.MIN_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#getTimestamp()
	 */
	@Override
	public double getTimestamp() {
		return this.timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element3.IElement3#toString(java.util.ArrayList)
	 */
	@Override
	public String toString(ArrayList<String> attrNames) {
		return "[Element3 : <" + attrNames.get(0) + ":" + this.getFirstValue() + ";" + attrNames.get(1) + ":" + this.getSecondValue() + ";" + attrNames.get(2) + ":" + this.getThirdValue() + ";@" + this.getTimestamp() + ">]";
	}

}
