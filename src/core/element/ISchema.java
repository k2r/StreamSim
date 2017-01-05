/**
 * 
 */
package core.element;

import java.io.Serializable;
import java.util.ArrayList;

import core.attribute.IAttribute;

/**
 * @author Roland
 *
 */
public interface ISchema extends Serializable{

	/**
	 * 
	 * @return the ordered list of attributes composing the schema
	 */
	public ArrayList<IAttribute> getAttributes();

}
