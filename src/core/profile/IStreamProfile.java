/**
 * 
 */
package core.profile;

import java.io.Serializable;
import java.util.HashMap;

import core.attribute.type.AttributeType;
import core.profile.type.ProfileType;

/**
 * @author Roland KOTTO KOMBI
 * Interface defining a stream profile
 */
public interface IStreamProfile extends Serializable{

	/**
	 * 
	 * @return the type of the stream profile (see ProfileType for more information)
	 */
	public ProfileType getType();
	
	/**
	 * 
	 * @return the duration of the current profile during stream emission
	 */
	public double getDuration();
	
	/**
	 * 
	 * @return the number of element to generate at each timestamp
	 */
	public int getNbElementPerTick();
	
	/**
	 * Set the number of element to generate at each timestamp
	 * @param rate the number of element to generate at each timestamp
	 */
	public void setNbElementPerTick(int rate);
	
	/**
	 * 
	 * @param attributeType the type of the attribute to consider
	 * @param parameters the map containing parameters of the current attribute
	 * @return the next value for a given attribute defined by a type and some parameters to generate new values
	 */
	public Object getNextValue(AttributeType attributeType, HashMap<String, Object> parameters);
}
