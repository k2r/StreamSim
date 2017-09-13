
/**
 * 
 */
package core.transition;

import java.io.Serializable;

import core.transition.type.TransitionType;

/**
 * @author Roland KOTTO KOMBI
 * Interface defining transitions between two stream profiles. 
 */
public interface IStreamTransition extends Serializable{
	
	/**
	 * 
	 * @return the type of the current transition
	 */
	public TransitionType getType();
	
	/**
	 * 
	 * @return the duration of the current transition
	 */
	public double getDuration();
	
	/**
	 * 
	 * @return the unit used for time discretization
	 */
	double getTickRate();
	
	/**
	 * 
	 * @param tickRate the new unit for time discretization
	 */
	void setTickRate(double tickRate);
	
	/**
	 * 
	 * @return the current input stream rate
	 */
	public double getCurrentRate();
	
	/**
	 * 
	 * @param update the new input rate for the current stream
	 */
	public void setCurrentRate(double update);
	
	/**
	 * 
	 * @return the initial rate at the start of the transition
	 */
	double getInitRate();
	
	/**
	 * 
	 * @param rate1 the intial stream output rate
	 * @param rate2 the expected stream output rate after a predefined duration 
	 * @param tickRate the time between two sent of tuples
	 * Method computing the function returning rate1 tuples for the current timestamp t0 and rate2 tuples for the timestamp t0 + duration of the transition according to the transition type.
	 */
	public void solveTransitionFunc(double rate1, double rate2, double tickRate);
	
	/**
	 * 
	 * @return the intermediate stream rate according to the transition function
	 */
	public double getIntermediateValue();	
}