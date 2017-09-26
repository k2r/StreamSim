/**
 * 
 */
package core.transition;

import core.transition.type.TransitionType;

/**
 * @author Roland
 * Implementation of a transition following an exponential evolution between two profiles
 */
public class ExponentialTransition implements IStreamTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9010059232809055217L;
	private TransitionType type;
	private double duration;
	private double tickRate;
	private double coeff;
	private double currentRate;
	private double index = 0;
	private double initRate;
	
	/**
	 * 
	 */
	public ExponentialTransition(double duration) {
		this.type = TransitionType.EXP;
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
	 * @see core.transition.IStreamTransition#getTickRate()
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
		this.coeff = rate2 / rate1;
		this.initRate = rate1;
		this.setCurrentRate(rate1);
		this.index = 0;
		this.setTickRate(tickRate);
	}

	/* (non-Javadoc)
	 * @see core.transition.IStreamTransition#getIntermediateValue()
	 */
	@Override
	public double getIntermediateValue() {
		double update = this.getInitRate() * (Math.pow(this.coeff, ((this.getTickRate() / this.getDuration()) * index)));
		this.index += this.getTickRate();
		this.setCurrentRate(update);
		return this.getCurrentRate();
	}
	
	/*Two transitions are equivalent if they have same type, duration and initial rate*/
	@Override
	public boolean equals(Object o) {
		IStreamTransition transition = (IStreamTransition) o;
		return (this.type == transition.getType() && this.duration == transition.getDuration() && this.initRate == transition.getInitRate());
	}
}