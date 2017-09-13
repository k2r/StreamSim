/**
 * 
 */
package core.model.relational;

import java.util.ArrayList;

import core.model.IModel;
import core.model.relational.attribute.IAttribute;

/**
 * @author Roland
 *
 */
public interface IRelationalModel extends IModel{

	/**
	 * 
	 * @return the ordered list of all attributes composing the relational model
	 */
	public ArrayList<IAttribute> getAttributes();

	/**
	 * 
	 * @param name the name of the target attribute
	 * @return the attribute with the specified name
	 */
	public IAttribute getAttribute(String name);
}
