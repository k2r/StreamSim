/**
 * 
 */
package core.stream;

import java.util.ArrayList;
import java.util.HashMap;

import core.element.IElement;
import core.element.ISchema;
//import core.network.socket.ISocketStreamSource;
import core.network.rmi.source.IRMIStreamSource;
import core.profile.IStreamProfile;
import core.transition.IStreamTransition;

/**
 * @author Roland KOTTO KOMBI
 * Interface representing a data stream 
 */
public interface IElementStream {
	
	/**
	 * 
	 * @return the port where the current stream is emitted
	 */
	public int getPort();
	
	/**
	 * 
	 * @param port the new port to emit the current stream
	 * Change the port to emit the current stream
	 */
	public void setPort(int port);
	
	/**
	 * 
	 * @return the stream source emitting tuples on the current stream port
	 */
	public IRMIStreamSource getSource();
	//public ISocketStreamSource getSource();
	
	/**
	 * Method extracting information on the stream schema and initializing the schema and value spaces 
	 */
	public void initializeSchema();
	
	/**
	 * 
	 * @return the schema of the current stream
	 */
	public ISchema getSchema();
	
	/**
	 * 
	 * @param attributeName the name of the selected attribute 
	 * @return the type of the attribute selected
	 */
	public String getAttributeType(String attributeName);
	
	/**
	 * 
	 * @param attributeName the name of the selected attribute
	 * @return the reference value list of the selected attribute
	 */
	public ArrayList<String> getReferenceValue(String attributeName);
	
	/**
	 * 
	 * @param attributeName the name of the selected attribute
	 * @return the value space (if the type extends Number or is an finite list of String) of the selected attribute
	 */
	public String getValueSpace(String attributeName);
	
	/**
	 * Method extracting the variations defined by the user and initializing profiles and transitions
	 */
	public void initializeVariations();
	 
	/**
	 * 
	 * @return the profile list available for the current stream
	 */
	public ArrayList<IStreamProfile> getProfiles();
	
	/**
	 * 
	 * @return the transition list available for the current stream
	 */
	public ArrayList<IStreamTransition> getTransitions();
	
	/**
	 * 
	 * @return the current profile of the stream or the the former one if the stream is changing his profile
	 */
	public IStreamProfile getCurrentProfile();
	
	/**
	 * 
	 * @param currentProfile the new current profile of the stream
	 */
	public void setCurrentProfile(IStreamProfile currentProfile);
	
	/**
	 * 
	 * @return the next profile of the stream if it exists
	 */
	public IStreamProfile getNextProfile();
	
	/**
	 * 
	 * @param nextProfile the new next profile of the stream
	 */
	public void setNextProfile(IStreamProfile nextProfile);
	
	/**
	 * 
	 * @return the current transition of the stream if it is changing from a profile to an other one or the next transition
	 */
	public IStreamTransition getCurrentTransition();
	
	/**
	 * 
	 * @param currentTransition the new transition to consider 
	 */
	public void setCurrentTransition(IStreamTransition currentTransition);
	
	/**
	 * 
	 * @return if the stream is moving from a profile to an other one
	 */
	public boolean isTransition();
	
	/**
	 * 
	 * @param isTransition the new transition state
	 */
	public void setTransition(boolean isTransition);
	
	/**
	 * 
	 * @return the reference timestamp of the current stream
	 */
	public double getRefTime();
	
	/**
	 * 
	 * @return the list of all tuples sent by the current stream
	 */
	public HashMap<String, IElement[]> getElements();
	
	/**
	 * 
	 * @return the list of all attribute names of the current stream
	 */
	public ArrayList<String> getAttributeNames();
	
	/**
	 * 
	 * @param tickDelay the time interval between two consecutive ticks
	 * Generate all tuples sent by the stream
	 */
	public void generateStream(long tickDelay);
	
	/**
	 * 
	 * @param tickDelay the time interval between two consecutive ticks
	 * @return an array including all tuples according to the current profile
	 */
	public ArrayList<IElement[]> generateProfile(long tickDelay);
	
	/**
	 * 
	 * @param tickDelay the time interval between two consecutive ticks
	 * @return an array including all tuples according to the current transition
	 */
	public ArrayList<IElement[]> generateTransition(long tickDelay);
}
