/**
 * 
 */
package test.element;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import core.element.IElement;
import core.element.relational.RelationalStreamElement;

/**
 * @author Roland
 *
 */
public class RelationalStreamElementTest {

	private IElement element1;
	private IElement element2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.element1 = new RelationalStreamElement(5, 1);
		
		Object[] values = new Object[7];
		values[0] = "1";
		values[1] = "2";
		values[2] = 3;
		values[3] = 4.0;
		values[4] = 5L;
		values[5] = '6';
		values[6] = 7.0;
		this.element2 = new RelationalStreamElement(7, 2, values);
	}

	/**
	 * Test method for {@link core.element.relational.RelationalStreamElement#getTimestamp()}.
	 */
	@Test
	public void testGetTimestamp() {
		assertEquals(1, this.element1.getTimestamp(), 0);
		assertEquals(2, this.element2.getTimestamp(), 0);
	}

	/**
	 * Test method for {@link core.element.relational.RelationalStreamElement#getStreamElement()}.
	 */
	@Test
	public void testGetStreamElement() {
		Object[] expected1 = new Object[0];
		
		Object[] expected2 = new Object[7];
		expected2[0] = "1";
		expected2[1] = "2";
		expected2[2] = 3;
		expected2[3] = 4.0;
		expected2[4] = 5L;
		expected2[5] = '6';
		expected2[6] = 7.0;
		
		Object[] actual1 = ((RelationalStreamElement) this.element1).getStreamElement();
		Object[] actual2 = ((RelationalStreamElement) this.element2).getStreamElement();
		
		int size1 = expected1.length;
		int size2 = expected2.length;
		
		for(int i = 0; i < size1; i++){
			assertEquals(expected1[i], actual1[i]);
		}
		for(int j = 0; j < size2; j++){
			assertEquals(expected2[j], actual2[j]);
		}
	}

	/**
	 * Test method for {@link core.element.relational.RelationalStreamElement#toString()}.
	 */
	@Test
	public void testStringify() {
		String expected1 = "{null;null;null;null;null;}@1";
		String expected2 = "{1;2;3;4.0;5;6;7.0;}@2";
		
		assertEquals(expected1, ((RelationalStreamElement) this.element1).toString());
		assertEquals(expected2, ((RelationalStreamElement) this.element2).toString());
	}
}