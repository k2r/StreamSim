/**
 * 
 */
package core.attribute;

import java.util.HashMap;

import core.attribute.type.AttributeType;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of an attribute taking only integer values in a predefined interval
 */
public class IntAttribute implements IAttribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3796245751672878597L;
	private String name;
	private HashMap<String, Object> parameters;

	public static final String MIN_PARAM = "min";
	public static final String MAX_PARAM = "max";

	public IntAttribute(String name, int minValue, int maxValue) {
		this.name = name;
		this.parameters = new HashMap<>();
		this.parameters.put(MIN_PARAM, minValue);
		this.parameters.put(MAX_PARAM, maxValue);
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
		return AttributeType.INT;
	}

	@Override
	public HashMap<String, Object> getParameters() {
		return this.parameters;
	}
	
	
}
