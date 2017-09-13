/**
 * 
 */
package core.profile;

import java.io.Serializable;
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
}