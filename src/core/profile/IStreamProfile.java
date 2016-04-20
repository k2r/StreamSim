/**
 * 
 */
package core.profile;

import java.util.ArrayList;

import core.profile.type.ProfileType;

/**
 * @author Roland KOTTO KOMBI
 * Interface defining a stream profile
 */
public interface IStreamProfile {

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
	 * 
	 * @param min the minimum value that an attribute can take, null if enumerate attribute
	 * @param max the maximum value that an attribute can take, null if enumerate attribute
	 * @param enumVals a list of possible values for an enumerate attribute, null if other type of attribute 
	 * @param type the type of the attribute 
	 * @return the value of the next element to consider
	 */
	public Object getNextValue(int min, int max, ArrayList<String> enumVals, String type);
}
