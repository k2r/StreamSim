/**
 * 
 */
package core.element.element4;

import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class Element4<T, U, V, W> implements IElement4<T, U, V, W> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1657683000081952260L;
	private T value1;
	private U value2;
	private V value3;
	private W value4;
	private double timestamp;
	/**
	 * 
	 */
	public Element4(T value1, U value2, V value3, W value4, double timestamp) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getFirstValue()
	 */
	@Override
	public T getFirstValue() {
		return this.value1;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#setFirstValue(java.lang.Object)
	 */
	@Override
	public void setFirstValue(T value) {
		this.value1 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getSecondValue()
	 */
	@Override
	public U getSecondValue() {
		return this.value2;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#setSecondValue(java.lang.Object)
	 */
	@Override
	public void setSecondValue(U value) {
		this.value2 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getThirdValue()
	 */
	@Override
	public V getThirdValue() {
		return this.value3;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#setThirdValue(java.lang.Object)
	 */
	@Override
	public void setThirdValue(V value) {
		this.value3 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getFourthValue()
	 */
	@Override
	public W getFourthValue() {
		return this.value4;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#setFourthValue(java.lang.Object)
	 */
	@Override
	public void setFourthValue(W value) {
		this.value4 = value;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getSize(int)
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
		}
		if(index == 4){
			return this.value4.toString().length();
		}else{
			return Integer.MIN_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#getTimestamp()
	 */
	@Override
	public double getTimestamp() {
		return this.timestamp;
	}

	/* (non-Javadoc)
	 * @see core.element.element4.IElement4#toString(java.util.ArrayList)
	 */
	@Override
	public String toString(ArrayList<String> attrNames) {
		return "[Element4 : <" + attrNames.get(0) + ":" + this.getFirstValue() + ";" + attrNames.get(1) + ":" + this.getSecondValue() + ";" + attrNames.get(2) + ":" + this.getThirdValue() + ";" + attrNames.get(3) + ":" + this.getFourthValue() + ";@" + this.getTimestamp() + ">]";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj){
		Element4 element = (Element4) obj;
		return (this.getFirstValue() == element.getFirstValue() &&
				this.getSecondValue() == element.getSecondValue() &&
				this.getThirdValue() == element.getThirdValue() &&
				this.getFourthValue() == element.getFourthValue());
	}
}