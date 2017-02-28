/**
 * 
 */
package core.element;

import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class StreamElement implements IElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2440769237741161891L;
	private Double timestamp;
	private Object[] values;
	
	public StreamElement(Integer nbAttributes, Double timestamp){
		this.timestamp = timestamp;
		this.values = new Object[nbAttributes];
	}
	
	public StreamElement(Integer nbAttributes, Double timestamp, Object[] values) {
		this.timestamp = timestamp;
		this.values = values;
	}
	
	/* (non-Javadoc) 
	 * @see core.element.IElement#getTimestamp()
	 */
	@Override
	public double getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @return the values
	 */
	@Override
	public Object[] getValues() {
		return this.values;
	}

	/* (non-Javadoc) 
	 * @see core.element.IElement#setValue()
	 */
	@Override
	public void setValue(Integer index, Object o){
		this.values[index] = o;
	}
	
	/* (non-Javadoc)
	 * @see core.element.IElement#toString(java.util.ArrayList)
	 */
	@Override
	public String toString(ArrayList<String> attrNames) {
		int n = attrNames.size();
		String result = "[Element{ ";
		for(int i = 0; i < n; i++){
			String attribute = attrNames.get(i);
			Object value = this.values[i];
			result += attribute + ":" + value + ";";
		}
		result += "}@" + this.getTimestamp() + "]";
		return result;
	}
	
	@Override
	public String toString() {
		int n = this.values.length;
		String result = "[Element{ ";
		for(int i = 0; i < n; i++){
			Object value = this.values[i];
			result += "value" + i + " --> " + value + ";";
		}
		result += "}@" + this.getTimestamp() + "]";
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		boolean result = true;
		IElement element = (IElement) o;
		Object[] toCompare = element.getValues();
		Object[] values = this.getValues();
		int n = toCompare.length;
		if(this.timestamp != element.getTimestamp()){
			result = false;
		}
		for(int i = 0; i < n; i++){
			if(!values[i].equals(toCompare[i])){
				result = false;
				break;
			}
		}
		return result;
	}
}