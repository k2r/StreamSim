/**
 * 
 */
package core.attribute;

import java.util.ArrayList;

import core.attribute.type.AttributeType;

/**
 * @author Roland
 * Implementation of an attribute taking only predefined string values
 */
public class EnumAttribute implements IAttribute {

	private String name;
	private ArrayList<String> values;
	
	/**
	 * 
	 */
	public EnumAttribute(String name, ArrayList<String> values) {
		this.name = name;
		this.values = values;
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

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getReferenceValue()
	 */
	@Override
	public ArrayList<String> getReferenceValue() {
		return this.values;
	}

	/* (non-Javadoc)
	 * @see core.attribute.IAttribute#getValueSpace()
	 */
	@Override
	public String getValueSpace() {
		String res = "";
		ArrayList<String> val = this.getReferenceValue(); 
		int n = val.size();
		for(int i = 0; i < n; i++){
			res += val.get(i);
			res += ";";
		}
		return res;
	}
}
