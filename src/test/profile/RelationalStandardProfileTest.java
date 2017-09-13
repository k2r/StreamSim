/**
 * 
 */
package test.profile;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import core.model.relational.attribute.EnumAttribute;
import core.model.relational.attribute.IntAttribute;
import core.model.relational.attribute.TextAttribute;
import core.model.relational.attribute.type.AttributeType;
import core.profile.IStreamProfile;
import core.profile.relational.IRelationalStreamProfile;
import core.profile.relational.RelationalStandardProfile;
import core.profile.type.ProfileType;

/**
 * @author Roland
 *
 */
public class RelationalStandardProfileTest {

	private IStreamProfile profile;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.profile = new RelationalStandardProfile(60.0, 100);
	}

	/**
	 * Test method for {@link core.profile.relational.RelationalStandardProfile#getType()}.
	 */
	@Test
	public void testGetType() {
		ProfileType expected = ProfileType.STANDARD;
		assertEquals(expected, this.profile.getType());
	}

	/**
	 * Test method for {@link core.profile.relational.RelationalStandardProfile#getDuration()}.
	 */
	@Test
	public void testGetDuration() {
		Double expected = 60.0;
		assertEquals(expected, this.profile.getDuration(), 0.0);
	}

	/**
	 * Test method for {@link core.profile.relational.RelationalStandardProfile#getNbElementPerTick()}.
	 */
	@Test
	public void testGetNbElementPerTick() {
		Integer expected  = 100;
		assertEquals(expected, this.profile.getNbElementPerTick(), 0);
	}

	/**
	 * Test method for {@link core.profile.relational.RelationalStandardProfile#getNextValue(core.model.relational.attribute.type.AttributeType, java.util.HashMap)}.
	 */
	@Test
	public void testGetNextValue() {
		Integer min = 0;
		Integer max = 10;
		
		HashMap<Integer, HashMap<String, Integer>> patterns = new HashMap<>();
		HashMap<String, Integer> pattern = new HashMap<>();
		pattern.put("[A-Z]", 5);
		patterns.put(0, pattern);
		
		ArrayList<String> values = new ArrayList<>();
		values.add("value 1");
		values.add("value 2");
		values.add("value 3");
		
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put(IntAttribute.MIN_PARAM, min);
		parameters.put(IntAttribute.MAX_PARAM, max);
		parameters.put(TextAttribute.PATTERNS, patterns);
		parameters.put(EnumAttribute.VALUES_PARAM, values);
		
		int size = 1000;
		Integer[] intValues = new Integer[size];
		String[] textValues = new String[size];
		String[] enumValues = new String[size];
		IRelationalStreamProfile relProfile = (IRelationalStreamProfile) this.profile;
		for(int i = 0; i < size; i++){
			intValues[i] = (Integer) relProfile.getNextValue(AttributeType.INT, parameters);
			textValues[i] = (String) relProfile.getNextValue(AttributeType.TEXT, parameters);
			enumValues[i] = (String) relProfile.getNextValue(AttributeType.ENUM, parameters);
		}
		
		for(int j = 0; j < size; j++){
			assertTrue(min <= intValues[j] && intValues[j] < max);
			assertTrue(textValues[j].matches("[A-Z]{5}+"));
			assertTrue(enumValues[j].equalsIgnoreCase("value 1") || enumValues[j].equalsIgnoreCase("value 2") || enumValues[j].equalsIgnoreCase("value 3"));
		}
	}

}