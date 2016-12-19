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
public interface ISchema {

	/**
	 * 
	 * @return the ordered list of attributes composing the schema
	 */
	public ArrayList<IAttribute> getAttributes();

}
