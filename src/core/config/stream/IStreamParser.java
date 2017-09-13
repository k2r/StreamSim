/**
 * 
 */
package core.config.stream;

import java.util.ArrayList;

import core.profile.IStreamProfile;
import core.transition.IStreamTransition;

/**
 * @author Roland
 *
 */
public interface IStreamParser {

	public String getFilename();
	
	public ArrayList<IStreamProfile> getStreamProfiles();
	
	public ArrayList<IStreamTransition> getStreamTransitions();
}
