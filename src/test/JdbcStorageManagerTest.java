/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

import core.attribute.IStreamAttribute;
import core.attribute.type.AttributeType;
import core.jdbc.JdbcStorageManager;

/**
 * @author Roland
 *
 */
public class JdbcStorageManagerTest {
	
	

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#createStreamTable(java.util.ArrayList)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testCreateStreamTable() throws SQLException, ClassNotFoundException {
		IStreamAttribute attr1 = Mockito.mock(IStreamAttribute.class);
		IStreamAttribute attr2 = Mockito.mock(IStreamAttribute.class);
		IStreamAttribute attr3 = Mockito.mock(IStreamAttribute.class);
		
		Mockito.when(attr1.getName()).thenReturn("attribute1");
		Mockito.when(attr1.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(attr2.getName()).thenReturn("attribute2");
		Mockito.when(attr2.getType()).thenReturn(AttributeType.INT);
		Mockito.when(attr3.getName()).thenReturn("attribute3");
		Mockito.when(attr3.getType()).thenReturn(AttributeType.TEXT);
		
		ArrayList<IStreamAttribute> attributes = new ArrayList<>();
		attributes.add(attr1);
		attributes.add(attr2);
		attributes.add(attr3);
		
		JdbcStorageManager manager = new JdbcStorageManager("localhost", "root", null);
		manager.createStreamTable("testStream", attributes);
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#recordStream(java.lang.String, java.util.HashMap)}.
	 */
	@Test
	public void testRecordStream() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#recordParameters(java.lang.String, java.lang.Integer, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public void testRecordParameters() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#getElements(java.lang.String)}.
	 */
	@Test
	public void testGetElements() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#getPort()}.
	 */
	@Test
	public void testGetPort() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#getVariation(java.lang.String)}.
	 */
	@Test
	public void testGetVariation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.jdbc.JdbcStorageManager#getTickDelay(java.lang.String)}.
	 */
	@Test
	public void testGetTickDelay() {
		fail("Not yet implemented");
	}

}
