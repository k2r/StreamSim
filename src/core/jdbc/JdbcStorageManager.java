/**
 * 
 */
package core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import core.attribute.IStreamAttribute;
import core.attribute.type.AttributeType;
import core.element.IElement;
import core.element.element1.Element1;
import core.element.element1.IElement1;
import core.element.element2.Element2;
import core.element.element2.IElement2;
import core.element.element3.Element3;
import core.element.element3.IElement3;
import core.element.element4.Element4;
import core.element.element4.IElement4;

/**
 * @author Roland
 *
 */
public class JdbcStorageManager {

	private final Connection connection;
	
	public JdbcStorageManager(String dbHost, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException{
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://"+ dbHost +"/streamsim";
		Class.forName(jdbcDriver);
		this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		this.createParametersTable();
	}
	
	public void createStreamTable(String streamName, ArrayList<IStreamAttribute> attributes){
		String query = "CREATE TABLE stream_" + streamName + "(";
		query += "chunkId VARCHAR(255)";
		for(int i = 0; i < attributes.size(); i++){
			IStreamAttribute attribute = attributes.get(i);
			String attrName = attribute.getName();
			AttributeType attrType = attribute.getType();
			String attrDbType = "VARCHAR(255)";
			if(attrType == AttributeType.INT){
				attrDbType = "INT";
			}
			query += "," + attrName + " " + attrDbType; 
		}
		query += ")";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createParametersTable(){
		String query = "CREATE TABLE parameters"
				+ "("
				+ "stream VARCHAR(255) PRIMARY KEY NOT NULL,"
				+ "port INT,"
				+ "variation VARCHAR(255),"
				+ "tickDelay INT"
				+ ")";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T,U,V,W> void recordStream(String streamName, ArrayList<IStreamAttribute> attributes, HashMap<String, IElement[]> elements){
		try {
			Integer nbAttributes = attributes.size();
			Statement statement = this.connection.createStatement();
			for(String chunkId : elements.keySet()){
				IElement[] chunk = elements.get(chunkId);
				for(IElement element : chunk){
					T value1;
					U value2;
					V value3;
					W value4;
					switch(nbAttributes){
					case(1): 	IElement1<T> e1 = (IElement1<T>) element;
								value1 = e1.getValue();
								String query1 = "INSERT INTO stream_" + streamName 
										+ " VALUES(" + chunkId + ", " + value1;
								statement.addBatch(query1);
								break;
								
					case(2):	IElement2<T, U> e2 = (IElement2<T, U>) element;
								value1 = e2.getFirstValue();
								value2 = e2.getSecondValue();
								String query2 = "INSERT INTO stream_" + streamName 
										+ " VALUES(" + chunkId + ", " + value1 + ", " + value2;
								statement.addBatch(query2);
								break;
								
					case(3):	IElement3<T, U, V> e3 = (IElement3<T, U, V>) element;
								value1 = e3.getFirstValue();
								value2 = e3.getSecondValue();
								value3 = e3.getThirdValue();
								String query3 = "INSERT INTO stream_" + streamName 
										+ " VALUES(" + chunkId + ", " + value1 + ", " + value2
										+ ", " + value3;
								statement.addBatch(query3);
								break;

					case(4):	IElement4<T, U, V, W> e4 = (IElement4<T, U, V, W>) element;
								value1 = e4.getFirstValue();
								value2 = e4.getSecondValue();
								value3 = e4.getThirdValue();
								value4 = e4.getFourthValue();
								String query4 = "INSERT INTO stream_" + streamName 
										+ " VALUES(" + chunkId + ", " + value1 + ", " + value2
										+ ", " + value3 + ", "  + value4;
								statement.addBatch(query4);
								break;
					}
				}
				statement.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void recordParameters(String streamName, Integer port, String variation, Long tickDelay){
		String query = "INSERT INTO parameters VALUES("
				+ streamName + ", " + port + ", " + variation + ", " + tickDelay + ")";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T,U,V,W> HashMap<String, IElement[]> getElements(String streamName, ArrayList<IStreamAttribute> attributes){
		int nbAttributes = attributes.size();
		HashMap<String, IElement[]> elements = new HashMap<>();
		String query = "SELECT * FROM stream_" + streamName;
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()){
				String chunk = results.getString("chunkId");
				IElement element = null;
				ArrayList<Object> values = new ArrayList<>();
				switch(nbAttributes){
				case(1): 	for(int i = 0; i < nbAttributes; i++){
								Object val = (Object) results.getObject(attributes.get(i).getName());
								values.add(val);
							}
							element = (IElement) new Element1<T>((T) values.get(0), System.currentTimeMillis()); 
							break;
							
				case(2):	for(int i = 0; i < nbAttributes; i++){
								Object val = (Object) results.getObject(attributes.get(i).getName());
								values.add(val);
							}
							element = (IElement) new Element2<T, U>((T) values.get(0), (U) values.get(1), System.currentTimeMillis()); 
							break;
							
				case(3):	for(int i = 0; i < nbAttributes; i++){
								Object val = (Object) results.getObject(attributes.get(i).getName());
								values.add(val);
							}
							element = (IElement) new Element3<T, U, V>((T) values.get(0), (U) values.get(1), (V) values.get(2), System.currentTimeMillis()); 
							break;

				case(4):	for(int i = 0; i < nbAttributes; i++){
								Object val = (Object) results.getObject(attributes.get(i).getName());
								values.add(val);
							}
							element = (IElement) new Element4<T, U, V, W>((T) values.get(0), (U) values.get(1), (V) values.get(2), (W) values.get(3),  System.currentTimeMillis()); 
							break;
				}
				if(elements.containsKey(chunk)){
					IElement[] chunkElements = elements.get(chunk);
					IElement[] update = new IElement[chunkElements.length + 1];
					for(int i = 0; i < chunkElements.length; i++){
						update[i] = chunkElements[i];
					}
					update[chunkElements.length] = element;
					elements.remove(chunk);
					elements.put(chunk, update);
				}else{
					IElement[] init = new IElement[1];
					init[0] = element;
					elements.put(chunk, init);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elements;
	}
	
	public Integer getPort(String streamName){
		Integer port = null;
		String query = "SELECT port FROM parameters WHERE stream = '" + streamName + "'";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()){
				port = results.getInt("port");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return port;
	}
	
	public String getVariation(String streamName){
		String variation = null;
		String query = "SELECT variation FROM parameters WHERE stream = '" + streamName + "'";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()){
				variation = results.getString("variation");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return variation;
	}
	
	public Long getTickDelay(String streamName){
		Long tickDelay = null;
		String query = "SELECT tickDelay FROM parameters WHERE stream = '" + streamName + "'";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()){
				tickDelay = results.getLong("tickDelay");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tickDelay;
	}
}