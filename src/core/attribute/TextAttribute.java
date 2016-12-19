/**
 * 
 */
package core.attribute;

import java.util.ArrayList;

import core.attribute.type.AttributeType;

/**
 * @author Roland
 * Implementation of an attribute taking only string values of a predefined number of characters
 */
public class TextAttribute implements IAttribute {

	private String name;
	private AttributeType type;
	private int minValue;
	private int maxValue;
	
	
	/**
	 * 
	 */
	public TextAttribute(String name, int min, int max) {
		this.name = name;
		this.type = AttributeType.TEXT;
		this.minValue = min;
		this.maxValue = max;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getType()
	 */
	@Override
	public AttributeType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getReferenceValue()
	 */
	@Override
	public ArrayList<String> getReferenceValue() {
		ArrayList<String> result = new ArrayList<String>();
		result.add(this.minValue + "");
		result.add(this.maxValue + "");
		return result;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getValueSpace()
	 */
	@Override
	public String getValueSpace() {
		return "[a-Z]*[0-9]*";
	}
}
