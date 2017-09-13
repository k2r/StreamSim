/**
 * 
 */
package test.config;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.config.global.ConfigParser;

/**
 * @author Roland
 *
 */
public class ConfigParserTest {
	
	private ConfigParser global;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		global = new ConfigParser("conf/properties.xml");
		global.initParameters();
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getFilename()}.
	 */
	@Test
	public void testGetFilename() {
		String expected = "conf/properties.xml";
		assertEquals(expected, global.getFilename());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getCommand()}.
	 */
	@Test
	public void testGetCommand() {
		String expected = "PLAY";
		assertEquals(expected, global.getCommand());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getDbHost()}.
	 */
	@Test
	public void testGetDbHost() {
		String expected = "localhost";
		assertEquals(expected, global.getDbHost());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getDbUser()}.
	 */
	@Test
	public void testGetDbUser() {
		String expected = "root";
		assertEquals(expected, global.getDbUser());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getDbPwd()}.
	 */
	@Test
	public void testGetDbPwd() {
		String expected = "";
		assertEquals(expected, global.getDbPwd());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getName()}.
	 */
	@Test
	public void testGetName() {
		String expected = "student";
		assertEquals(expected, global.getName());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getModel()}.
	 */
	@Test
	public void testGetModel() {
		String expected = "relational";
		assertEquals(expected, global.getModel());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getVariation()}.
	 */
	@Test
	public void testGetVariation() {
		String expected = "linearIncrease";
		assertEquals(expected, global.getVariation());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getFrequency()}.
	 */
	@Test
	public void testGetFrequency() {
		Long expected = 1L;
		assertEquals(expected, global.getFrequency(), 0L);
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getConsumer()}.
	 */
	@Test
	public void testGetConsumer() {
		String expected = "RMI";
		assertEquals(expected, global.getConsumer());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getRmiHost()}.
	 */
	@Test
	public void testGetRmiHost() {
		String expected = "localhost";
		assertEquals(expected, global.getRmiHost());
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getRmiPort()}.
	 */
	@Test
	public void testGetRmiPort() {
		Integer expected = 5354;
		assertEquals(expected, global.getRmiPort(), 0);
	}

	/**
	 * Test method for {@link core.config.global.ConfigParser#getKafkaHost()}.
	 */
	@Test
	public void testGetKafkaHost() {
		String expected = "localhost";
		assertEquals(expected, global.getKafkaHost());
	}

}
