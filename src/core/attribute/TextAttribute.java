/**
 * 
 */
package core.attribute;

import java.util.HashMap;

import core.attribute.type.AttributeType;

/**
 * @author Roland
 * Implementation of an attribute taking only string values of a predefined number of characters
 */
public class TextAttribute implements IAttribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6172709283043947380L;
	private String name;
	private HashMap<String, Object> parameters;
	
	public static final String PATTERNS = "patterns";
	/**
	 * 
	 */
	public TextAttribute(String name, HashMap<Integer, HashMap<String, Integer>> patterns) {
		this.name = name;
		this.parameters = new HashMap<>();
		this.parameters.put(PATTERNS, patterns);
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
		return AttributeType.TEXT;
	}

	@Override
	public HashMap<String, Object> getParameters() {
		return this.parameters;
	}
}
