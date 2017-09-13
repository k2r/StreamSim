/**
 * 
 */
package core.model.relational;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import core.model.relational.attribute.IAttribute;

/**
 * @author Roland
 *
 */
public class RelationalModel implements IRelationalModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681889010443766152L;
	
	private HashMap<Object, Object> attributes;
	
	public RelationalModel() {
		this.attributes = new HashMap<>();
	}
	
	public RelationalModel(HashMap<Object, Object> attributes){
		this.attributes = attributes;
	}
	
	@Override
	public HashMap<Object, Object> getModel() {
		return this.attributes;
	}
	
	/* (non-Javadoc)
	 * @see core.element.IRelationalModel#getAttributes()
	 */
	@Override
	public ArrayList<IAttribute> getAttributes() {
		ArrayList<IAttribute> result = new ArrayList<>();
		Set<Object> keySet = this.attributes.keySet();
		for(Object o : keySet){
			IAttribute attribute = (IAttribute) this.attributes.get(o);
			result.add(attribute);
		}
		return result;
	}

	@Override
	public IAttribute getAttribute(String name) {
		IAttribute result = null;
		Set<Object> keySet = this.attributes.keySet();
		for(Object o : keySet){
			String attrName = (String) o;
			if(name.equalsIgnoreCase(attrName)){
				result = (IAttribute) this.attributes.get(o);
				break;
			}
		}
		return result;
	}
}