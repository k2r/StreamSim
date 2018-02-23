/**
 * 
 */
package core.element.relational;


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
	
	public RelationalStreamElement(String stringified){
		String valuesAndTime = stringified.split("{")[1];
		String values = valuesAndTime.split("@")[0];
		this.timestamp = Integer.parseInt(valuesAndTime.split("@")[1]);
		int valuesLength = values.length();
		values = values.substring(0, valuesLength - 1);// get rid off the closing bracket
		String[] vals = values.split(";");
		int valsLength = vals.length;
		this.values = new Object[valsLength];
		for(int i = 0; i < valsLength; i++){
			this.values[i] = vals[i];
		}
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

	@Override
	public String stringify() {
		int n = this.values.length;
		String result = "{";
		for(int i = 0; i < n; i++){
			Object value = this.values[i];
			result += value + ";";
		}
		result += "}@" + this.getTimestamp();
		return result;
	}

	
}