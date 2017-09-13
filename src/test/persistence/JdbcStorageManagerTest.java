/**
 * 
 */
package test.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import core.element.IElement;
import core.element.relational.IRelationalElement;
import core.element.relational.RelationalStreamElement;
import core.model.IModel;
import core.model.relational.RelationalModel;
import core.model.relational.attribute.IAttribute;
import core.model.relational.attribute.type.AttributeType;
import core.persistence.JdbcPersistenceManager;
import junit.framework.TestCase;

/**
 * @author Roland
 *
 */
public class JdbcStorageManagerTest extends TestCase {

	private IModel model;
	private ArrayList<IAttribute> attributes;
	private HashMap<String, IElement[]> packets;
	
	@Before
	public void setUp(){
		IAttribute attr1 = Mockito.mock(IAttribute.class);
		IAttribute attr2 = Mockito.mock(IAttribute.class);
		IAttribute attr3 = Mockito.mock(IAttribute.class);
		
		Mockito.when(attr1.getName()).thenReturn("attribute1");
		Mockito.when(attr1.getType()).thenReturn(AttributeType.ENUM);
		Mockito.when(attr2.getName()).thenReturn("attribute2");
		Mockito.when(attr2.getType()).thenReturn(AttributeType.INT);
		Mockito.when(attr3.getName()).thenReturn("attribute3");
		Mockito.when(attr3.getType()).thenReturn(AttributeType.TEXT);
		
		HashMap<Object, Object> flatModel = new HashMap<>();
		flatModel.put(attr1.getName(), attr1);
		flatModel.put(attr2.getName(), attr2);
		flatModel.put(attr3.getName(), attr3);
		
		this.model = new RelationalModel(flatModel);
		
		this.attributes = new ArrayList<>();
		this.attributes.add(attr1);
		this.attributes.add(attr2);
		this.attributes.add(attr3);
		
		Object[] values1 = {"enum1", 1, "text1"};
		Object[] values2 = {"enum2", 2, "text2"};
		Object[] values3 = {"enum3", 3, "text3"};
		Object[] values4 = {"enum4", 4, "text4"};
		Object[] values5 = {"enum5", 5, "text5"};
		Object[] values6 = {"enum6", 6, "text6"};
		
		IElement elem1 = (IRelationalElement) new RelationalStreamElement(3, 0, values1);
		IElement elem2 = (IRelationalElement) new RelationalStreamElement(3, 0, values2);
		IElement elem3 = (IRelationalElement) new RelationalStreamElement(3, 0, values3);
		IElement elem4 = (IRelationalElement) new RelationalStreamElement(3, 0, values4);
		IElement elem5 = (IRelationalElement) new RelationalStreamElement(3, 0, values5);
		IElement elem6 = (IRelationalElement) new RelationalStreamElement(3, 0, values6);
		
		IElement[] packet1 = new IElement[1];
		packet1[0] = elem1;
		
		IElement[] packet2 = new IElement[2];
		packet2[0] = elem2;
		packet2[1] = elem3;
		
		IElement[] packet3 = new IElement[3];
		packet3[0] = elem4;
		packet3[1] = elem5;
		packet3[2] = elem6;
		
		this.packets = new HashMap<>();
		packets.put("P1It1", packet1);
		packets.put("T1It1", packet2);
		packets.put("P2It1", packet3);
	}
	
	
	@After
	public void tearDown() throws ClassNotFoundException, SQLException{
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://localhost/streamsim_test";
		Class.forName(jdbcDriver);
		Connection connection = DriverManager.getConnection(dbUrl, "root", null);
		String cleanQuery1 = "DROP TABLE parameters";
		String cleanQuery2 = "DROP TABLE stream_teststream1_linear";
		String cleanQuery3 = "DROP TABLE stream_teststream2_linear";
		String cleanQuery4 = "DROP TABLE stream_teststream4_linear";
		ArrayList<String> queries = new ArrayList<>();
		queries.add(cleanQuery1);
		queries.add(cleanQuery2);
		queries.add(cleanQuery3);
		queries.add(cleanQuery4);
		for(String query : queries){
			try {
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				statement.executeUpdate(query);
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#createStreamTable(java.lang.String, java.util.ArrayList)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testCreateStreamTable() throws ClassNotFoundException, SQLException {		
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.createStreamTable("testStream1", "linear", model);
	}

	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#recordStream(java.lang.String, java.util.ArrayList, java.util.HashMap)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testRecordStream() throws ClassNotFoundException, SQLException {
		
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.persistStream("testStream2", "linear", model, packets);
	}

	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#recordParameters(java.lang.String, java.lang.Integer, java.lang.String, java.lang.Long)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testRecordParameters() throws ClassNotFoundException, SQLException {
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.persistParameters("testStream3", "linear", 1L);
	}

	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#getElements(java.lang.String, java.util.ArrayList)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testGetElements() throws ClassNotFoundException, SQLException {
		
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.persistStream("testStream4", "linear", model, packets);
		
		ArrayList<String> attrNames = new ArrayList<>();
		for(int i = 0; i < attributes.size(); i++){
			attrNames.add(attributes.get(i).getName());
		}
		
		HashMap<String, IElement[]> actual = manager.getElements("testStream4", "linear", attributes);
		for(String packetHeader : actual.keySet()){
			IElement[] expectedChunk = packets.get(packetHeader);
			IElement[] actualChunk = actual.get(packetHeader);
			int n = expectedChunk.length;
			for(int i = 0; i < n; i++){
				IElement expectedElem = expectedChunk[i];
				IElement actualElem = actualChunk[i];
				assertEquals(expectedElem, actualElem);
			}
		}
	}

	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#getVariation(java.lang.String)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testGetVariation() throws ClassNotFoundException, SQLException {
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.persistParameters("testStream8", "linearIncrease", 1L);
		manager.persistParameters("testStream9", "all", 1L);
		manager.persistParameters("testStream10", "exponentialDecrease", 1L);
		
		assertEquals("linearIncrease", manager.getVariation("testStream8"));
		assertEquals("all", manager.getVariation("testStream9"));
		assertEquals("exponentialDecrease", manager.getVariation("testStream10"));
	}

	/**
	 * Test method for {@link core.persistence.JdbcPersistenceManager#getFrequency(java.lang.String)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void testGetFrequency() throws ClassNotFoundException, SQLException {
		JdbcPersistenceManager manager = new JdbcPersistenceManager("streamsim_test", "localhost", "root", null);
		manager.persistParameters("testStream11", "linear", 1L);
		manager.persistParameters("testStream12", "linear", 2L);
		manager.persistParameters("testStream13", "linear", 3L);
		
		assertEquals(1L, manager.getFrequency("testStream11"), 0);
		assertEquals(2L, manager.getFrequency("testStream12"), 0);
		assertEquals(3L, manager.getFrequency("testStream13"), 0);
	}

}