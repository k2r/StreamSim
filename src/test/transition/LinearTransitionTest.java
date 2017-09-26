/**
 * 
 */
package test.transition;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.transition.LinearTransition;
import core.transition.type.TransitionType;

/**
 * @author Roland
 *
 */
public class LinearTransitionTest {

	private LinearTransition transition;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.transition = new LinearTransition(100);
		this.transition.solveTransitionFunc(10, 1000, 1);//should increase rate by 10 per tick
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getType()}.
	 */
	@Test
	public void testGetType() {
		assertEquals(TransitionType.LINEAR, this.transition.getType());
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getDuration()}.
	 */
	@Test
	public void testGetDuration() {
		assertEquals(100, this.transition.getDuration(), 0);
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getTickRate()}.
	 */
	@Test
	public void testGetTickRate() {
		assertEquals(1.0, this.transition.getTickRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getCurrentRate()}.
	 */
	@Test
	public void testGetCurrentRate() {
		assertEquals(10.0, this.transition.getCurrentRate(), 0.0);
		this.transition.setCurrentRate(500.0);
		assertEquals(500.0, this.transition.getCurrentRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getInitRate()}.
	 */
	@Test
	public void testGetInitRate() {
		assertEquals(0.0, this.transition.getInitRate(), 0.0);
	}

	/**
	 * Test method for {@link core.transition.LinearTransition#getIntermediateValue()}.
	 */
	@Test
	public void testGetIntermediateValue() {
		Double expectedRate = 19.9; 
		for(int i = 0; i < 100; i++){
			assertEquals(expectedRate, this.transition.getIntermediateValue(), 0.0);
			expectedRate += 9.9;
		}
	}

}
