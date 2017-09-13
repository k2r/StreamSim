/**
 * 
 */
package core.element.relational;

import java.util.ArrayList;

/**
 * @author Roland
 *
 */
public class RelationalStreamElement implements IRelationalElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2440769237741161891L;
	private Integer timestamp;
	private Object[] values;
	
	public RelationalStreamElement(Integer nbAttributes, Integer timestamp){
		this.timestamp = timestamp;
		this.values = new Object[nbAttributes];
	}
	
	public RelationalStreamElement(Integer nbAttributes, Integer timestamp, Object[] values) {
		this.timestamp = timestamp;
		this.values = values;
	}
	
	/* (non-Javadoc) 
	 * @see core.element.IRelationalElement#getTimestamp()
	 */
	@Override
	public Integer getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @return the values
	 */
	@Override
	public Object[] getStreamElement() {
		return this.values;
	}

	/* (non-Javadoc) 
	 * @see core.element.IRelationalElement#setValue()
	 */
	@Override
	public void setStreamElement(Integer index, Object o){
		this.values[index] = o;
	}
	
	/* (non-Javadoc)
	 * @see core.element.IRelationalElement#toString(java.util.ArrayList)
	 */
	@Override
	public String toString(ArrayList<String> attrNames) {
		int n = attrNames.size();
		String result = "[Element{";
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
		IRelationalElement relationalElement = (IRelationalElement) o;
		Object[] toCompare = relationalElement.getStreamElement();
		Object[] values = this.getStreamElement();
		int n = toCompare.length;
		if(this.timestamp != relationalElement.getTimestamp()){
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