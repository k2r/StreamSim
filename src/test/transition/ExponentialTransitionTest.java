/**
 * 
 */
package test.transition;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.transition.ExponentialTransition;
import core.transition.type.TransitionType;

/**
 * @author Roland
 *
 */
public class ExponentialTransitionTest {

	private ExponentialTransition transition;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.transition = new ExponentialTransition(100);
		this.transition.solveTransitionFunc(10.0, 1000.0, 1.0);
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getType()}.
	 */
	@Test
	public void testGetType() {
		assertEquals(TransitionType.EXP, this.transition.getType());
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getDuration()}.
	 */
	@Test
	public void testGetDuration() {
		assertEquals(100.0, this.transition.getDuration(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getTickRate()}.
	 */
	@Test
	public void testGetTickRate() {
		assertEquals(1.0, this.transition.getTickRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getCurrentRate()}.
	 */
	@Test
	public void testGetCurrentRate() {
		assertEquals(10.0, this.transition.getCurrentRate(), 0.0);
		this.transition.setCurrentRate(500.0);
		assertEquals(500.0, this.transition.getCurrentRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getInitRate()}.
	 */
	@Test
	public void testGetInitRate() {
		assertEquals(10.0, this.transition.getInitRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.ExponentialTransition#getIntermediateValue()}.
	 */
	@Test
	public void testGetIntermediateValue() {
		for(int i = 0; i < 100; i++){
			Double expectedRate = 10.0 * (Math.pow(100.0, ((1.0 / 100.0) * i)));
			assertEquals(expectedRate, this.transition.getIntermediateValue(), 0);
		}
	}

}