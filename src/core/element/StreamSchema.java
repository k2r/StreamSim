/**
 * 
 */
package core.element;

import java.util.ArrayList;

import core.attribute.IAttribute;

/**
 * @author Roland
 *
 */
public class StreamSchema implements ISchema {

	private ArrayList<IAttribute> attributes;
	
	public StreamSchema() {
		this.attributes = new ArrayList<>();
	}
	
	public StreamSchema(ArrayList<IAttribute> attributes){
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see core.element.ISchema#getAttributes()
	 */
	@Override
	public ArrayList<IAttribute> getAttributes() {
		return this.attributes;
	}

}
