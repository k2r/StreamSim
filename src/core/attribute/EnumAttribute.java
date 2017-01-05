/**
 * 
 */
package core.attribute;

import java.util.ArrayList;
import java.util.HashMap;

import core.attribute.type.AttributeType;

/**
 * @author Roland
 * Implementation of an attribute taking only predefined string values
 */
public class EnumAttribute implements IAttribute {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 722319712139830568L;
	private String name;
	private HashMap<String, Object> parameters;

	public static final String VALUES_PARAM = "values";
	
	/**
	 * 
	 */
	public EnumAttribute(String name, ArrayList<String> values) {
		this.name = name;
		this.parameters = new HashMap<>();
		this.parameters.put(VALUES_PARAM, values);
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
		return AttributeType.ENUM;
	}

	@Override
	public HashMap<String, Object> getParameters() {
		return this.parameters;
	}


}
