/**
 * 
 */
package core.profile.relational;

import java.util.HashMap;

import core.model.relational.attribute.type.AttributeType;
import core.profile.IStreamProfile;

/**
 * @author Roland
 *
 */
public interface IRelationalStreamProfile extends IStreamProfile{

	/**
	 * 
	 * @param attributeType the type of the attribute to consider
	 * @param parameters the map containing parameters of the current attribute
	 * @return the next value for a given attribute defined by a type and some parameters to generate new values
	 */
	public Object getNextValue(AttributeType attributeType, HashMap<String, Object> parameters);
}
