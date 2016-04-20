/**
 * 
 */
package core.transition;

import core.transition.type.TransitionType;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of a transition following a linear evolution between two profile
 */
public class LinearTransition implements IStreamTransition {
	
	private TransitionType type;
	private double duration;
	private double tickRate;
	private double delta;
	private double currentRate;
	private double initRate;
	
	public LinearTransition(double duration) {
		this.type = TransitionType.LINEAR;
		this.duration = duration;
	}
	
	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getType()
	 */
	@Override
	public TransitionType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getDuration()
	 */
	@Override
	public double getDuration() {
		return this.duration;
	}

	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getTickRateRate()
	 */
	@Override
	public double getTickRate() {
		return this.tickRate;
	}

	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#setTickRate(double)
	 */
	@Override
	public void setTickRate(double tickRate) {
		this.tickRate = tickRate;
	}
	
	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getCurrentRate()
	 */
	@Override
	public double getCurrentRate() {
		return this.currentRate;
	}
	
	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#setCurrentRate(double)
	 */
	@Override
	public void setCurrentRate(double update) {
		this.currentRate = update;
	}
	
	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getInitRate()
	 */
	@Override
	public double getInitRate() {
		return this.initRate;
	}
	
	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#solveTransitionFunc(double, double)
	 */
	@Override
	public void solveTransitionFunc(double rate1, double rate2, double tickRate) {
		double d = this.getDuration();
		this.delta =  (rate2 - rate1) / d; /*Give the rate increment on each second*/
		this.setCurrentRate(rate1);
	}

	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getIntermediateValue(double)
	 */
	@Override
	public double getIntermediateValue() {
		double update = this.getCurrentRate() + this.delta;
		this.setCurrentRate(update);
		return this.getCurrentRate();
	}

	

	

}
