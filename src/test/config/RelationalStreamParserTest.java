/**
 * 
 */
package test.config;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import core.config.stream.relational.RelationalStreamParser;
import core.model.relational.attribute.IAttribute;
import core.model.relational.attribute.type.AttributeType;

/**
 * @author Roland
 *
 */
public class RelationalStreamParserTest {

	private RelationalStreamParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new RelationalStreamParser("models/studentModel.xml");
	}

	/**
	 * Test method for {@link core.config.stream.relational.RelationalStreamParser#getFilename()}.
	 */
	@Test
	public void testGetFilename() {
		String expected = "models/studentModel.xml";
		assertEquals(expected, parser.getFilename());
	}

	/**
	 * Test method for {@link core.config.stream.relational.RelationalStreamParser#getStreamAttributes()}.
	 */
	@Test
	public void testGetStreamAttributes() {
		HashMap<Object, Object> expected  = new HashMap<>();
		
		IAttribute identifiant = Mockito.mock(IAttribute.class);
		IAttribute prenom = Mockito.mock(IAttribute.class);
		IAttribute nom = Mockito.mock(IAttribute.class);
		IAttribute age = Mockito.mock(IAttribute.class);
		IAttribute dept = Mockito.mock(IAttribute.class);
		IAttribute promotion = Mockito.mock(IAttribute.class);
		IAttribute parcours = Mockito.mock(IAttribute.class);
		
		Mockito.when(identifiant.getName()).thenReturn("identifiant");
		Mockito.when(identifiant.getType()).thenReturn(AttributeType.TEXT);
		Mockito.when(prenom.getName()).thenReturn("prenom");
		Mockito.when(prenom.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(nom.getName()).thenReturn("nom");
		Mockito.when(nom.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(age.getName()).thenReturn("age");
		Mockito.when(age.getType()).thenReturn(AttributeType.INT);
		Mockito.when(dept.getName()).thenReturn("dept");
		Mockito.when(dept.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(promotion.getName()).thenReturn("promotion");
		Mockito.when(promotion.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(parcours.getName()).thenReturn("parcours");
		Mockito.when(parcours.getType()).thenReturn(AttributeType.ENUM);
		
		expected.put("identifiant", identifiant);
		expected.put("prenom", prenom);
		expected.put("nom", nom);
		expected.put("age", age);
		expected.put("dept", dept);
		expected.put("promotion", promotion);
		expected.put("parcours", parcours);
		
		HashMap<Object, Object> attributes = parser.getStreamAttributes();
		
		for(Object key : expected.keySet()){
			IAttribute expectedAttribute = (IAttribute) expected.get(key);
			IAttribute attribute = (IAttribute) attributes.get(key); 
					
			assertEquals(expectedAttribute.getName(), attribute.getName());
			assertEquals(expectedAttribute.getType(), attribute.getType());
		}
	}
}