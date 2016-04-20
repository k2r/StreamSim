/**
 * 
 */
package core.attribute;

import java.util.ArrayList;

import core.attribute.type.AttributeType;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of an attribute taking only integer values in a predefined interval
 */
public class IntAttribute implements IStreamAttribute {

	private String name;
	private AttributeType type;
	private int minValue;
	private int maxValue;

	public IntAttribute(String name, int minValue, int maxValue) {
		this.name = name;
		this.type = AttributeType.INT;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	/* (non-Javadoc)
	 * @see core.attribute.IStreamAttribute#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IStreamAttribute#getType()
	 */
	@Override
	public AttributeType getType() {
		return this.type;
	}
	
	/* (non-Javadoc)
	 * @see core.attribute.IStreamAttribute#getReferenceValue()
	 */
	@Override
	public ArrayList<String> getReferenceValue() {
		ArrayList<String> result = new ArrayList<String>();
		if(this.maxValue == Integer.MIN_VALUE){
			result.add("random");
		}else{
			result.add(this.minValue + "");
			result.add(this.maxValue + "");
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see core.attribute.IStreamAttribute#getValueSpace()
	 */
	@Override
	public String getValueSpace() {
		if(this.getReferenceValue().get(0).equalsIgnoreCase("random")){
			return "all integer values";
		}else{
			return "[" + this.getReferenceValue().get(0) + ";" + this.getReferenceValue().get(1) + "]";
		}
	}
}
