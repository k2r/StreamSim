/**
 * 
 */
package core.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import core.attribute.IAttribute;
import core.attribute.type.AttributeType;
import core.element.IElement;
import core.element.StreamElement;

/**
 * @author Roland
 *
 */
public class JdbcStorageManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6619333306843261347L;
	private final Connection connection;
	private static final Logger logger = Logger.getLogger("JdbcStorageManager");
	
	public JdbcStorageManager(String dbName, String dbHost, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException{
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://" + dbHost + "/" + dbName;
		Class.forName(jdbcDriver);
		this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		this.createParametersTable();
	}
	
	public void createStreamTable(String streamName, String variation, ArrayList<IAttribute> attributes){
		String query = "CREATE TABLE stream_" + streamName + "_" + variation + "(";
		query += "chunkId VARCHAR(255)";
		for(int i = 0; i < attributes.size(); i++){
			IAttribute attribute = attributes.get(i);
			String attrName = attribute.getName();
			AttributeType attrType = attribute.getType();
			String attrDbType = "VARCHAR(255)";
			if(attrType == AttributeType.INT){
				attrDbType = "INT";
			}
			query += "," + attrName + " " + attrDbType; 
		}
		query += ", timestamp BIGINT)";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
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
			logger.info("Parameter table has already been created or can not be instanciate");
		}
	}

	public void recordStream(String streamName, String variation, ArrayList<IAttribute> attributes, HashMap<String, IElement[]> elements){
		logger.info("Recording elements for stream " + streamName + "...");
		try {
			this.createStreamTable(streamName, variation, attributes);
			Integer nbAttributes = attributes.size();
			Statement statement = this.connection.createStatement();
			for(String chunkId : elements.keySet()){
				IElement[] chunk = elements.get(chunkId);
				for(IElement element : chunk){
					Object[] values = element.getValues();
					String query = "INSERT INTO stream_" + streamName + "_" + variation + " VALUES('" + chunkId + "',";
					for(int i = 0; i < nbAttributes - 1; i++){
						query += "'" + values[i] + "' ,";
					}
					query += "'" + values[nbAttributes - 1] + "', '" + element.getTimestamp() + "')";
					statement.executeUpdate(query);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void recordParameters(String streamName, Integer port, String variation, Long tickDelay){
		logger.info("Recording parameters for stream " + streamName + " linked to port " + port + " and variation " + variation + "...");
		String query = "INSERT INTO parameters VALUES('"
				+ streamName + "', '" + port + "', '" + variation + "', '" + tickDelay + "')";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public HashMap<String, IElement[]> getElements(String streamName, String variation, ArrayList<IAttribute> attributes){
		logger.info("Recovering elements for stream " + streamName + "...");
		int nbAttributes = attributes.size();
		
		HashMap<String, IElement[]> elements = new HashMap<>();
		String query = "SELECT * FROM stream_" + streamName + "_" + variation;
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()){
				String chunk = results.getString("chunkId");
				
				Object[] values = new Object[nbAttributes];
				for(int i = 0; i < nbAttributes; i++){
					Object value = (Object) results.getObject(attributes.get(i).getName());
					values[i] = value;
				}
				Double timestamp = (Double) results.getDouble("timestamp");
				IElement element = (IElement) new StreamElement(nbAttributes, timestamp, values);
				
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
			e.printStackTrace();
		}
		return tickDelay;
	}
}