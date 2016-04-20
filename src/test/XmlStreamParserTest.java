/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import core.profile.IStreamProfile;
import core.transition.IStreamTransition;
import core.util.XmlStreamParser;
import core.attribute.*;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class XmlStreamParserTest {

	/**
	 * Test method for {@link core.util.XmlStreamParser#getFilename()}.
	 */
	@Test
	public void testGetFilename() {
		try {
			XmlStreamParser parser = new XmlStreamParser("schemas/testSchema.xml");
			assertEquals("schemas/testSchema.xml", parser.getFilename());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link core.util.XmlStreamParser#getStreamAttributes()}.
	 */
	@Test
	public void testGetStreamAttributes() {
		try {
			XmlStreamParser parser = new XmlStreamParser("schemas/testSchema.xml");
			ArrayList<IStreamAttribute> attributes = parser.getStreamAttributes();
			
			String name1 = attributes.get(0).getName();
			ArrayList<String> references1 = new ArrayList<String>();
			references1.add(attributes.get(0).getReferenceValue().get(0));
			references1.add(attributes.get(0).getReferenceValue().get(1));
			String type = attributes.get(0).getType().toString();
			String valueSpace1 = attributes.get(0).getValueSpace();
			assertEquals("temperature", name1);
			assertEquals("0", references1.get(0));
			assertEquals("25", references1.get(1));
			assertEquals("integer", type);
			assertEquals("[0;25]", valueSpace1);
			
			String name2 = attributes.get(1).getName();
			ArrayList<String> references2 = new ArrayList<String>();
			references2.add(attributes.get(1).getReferenceValue().get(0));
			references2.add(attributes.get(1).getReferenceValue().get(1));
			String type2 = attributes.get(1).getType().toString();
			String valueSpace2 = attributes.get(1).getValueSpace();
			assertEquals("windspeed", name2);
			assertEquals("5", references2.get(0));
			assertEquals("60", references2.get(1));
			assertEquals("integer", type2);
			assertEquals("[5;60]", valueSpace2);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link core.util.XmlStreamParser#getStreamProfiles()}.
	 */
	@Test
	public void testGetStreamProfiles() {
		try {
			XmlStreamParser parser = new XmlStreamParser("variations/testVar.xml");
			ArrayList<IStreamProfile> profiles = parser.getStreamProfiles();
			
			String type1 = profiles.get(0).getType().toString();
			int rate1 = profiles.get(0).getNbElementPerTick();
			double duration1 = profiles.get(0).getDuration();
			assertEquals("standard", type1);
			assertEquals(20, rate1);
			assertEquals(30, duration1, 0.0);
			
			String type2 = profiles.get(1).getType().toString();
			int rate2 = profiles.get(1).getNbElementPerTick();
			double duration2 = profiles.get(1).getDuration();
			assertEquals("standard", type2);
			assertEquals(100, rate2);
			assertEquals(40, duration2, 0.0);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link core.util.XmlStreamParser#getStreamTransitions()}.
	 */
	@Test
	public void testGetStreamTransitions() {
		try {
			XmlStreamParser parser = new XmlStreamParser("variations/testVar.xml");
			ArrayList<IStreamTransition> transitions = parser.getStreamTransitions();
			
			String type1 = transitions.get(0).getType().toString();
			double duration1 = transitions.get(0).getDuration();
			assertEquals("linear", type1);
			assertEquals(10, duration1, 0.0);
		}catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
