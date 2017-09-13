/**
 * 
 */
package core.config.global;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Roland
 *
 */
public class ConfigParser {

	/*Launch file parameters*/
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	/*Stream generator parameters*/
	private String command;
	
	/*Stream parameters*/
	private String name;
	private String model;
	private String variation;
	private long frequency;
	
	/*Database parameters*/
	private String dbHost;
	private String dbUser;
	private String dbPwd;
	
	/*Consumer parameters*/
	private String consumer;
	private String rmiHost;
	private Integer rmiPort;
	private String kafkaHost;
	
	
	public ConfigParser(){
		this.factory = null;
		this.builder = null;
		this.document = null;
	}
	
	public ConfigParser(String filename) throws ParserConfigurationException, SAXException, IOException{
		this.filename = filename;
		this.factory = DocumentBuilderFactory.newInstance();
		this.builder = factory.newDocumentBuilder();
		this.document = builder.parse(this.getFilename());
	}
	
	/**
	 * 
	 * @return the path of the current xml file
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * 
	 * @param filename the new path of the xml file to parse
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * 
	 * @return the Document (according to W3C norm) corresponding to the current xml file
	 */
	public Document getDocument() {
		return this.document;
	}
	
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the dbHost
	 */
	public String getDbHost() {
		return dbHost;
	}

	/**
	 * @param dbHost the dbHost to set
	 */
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	/**
	 * @return the dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}

	/**
	 * @param dbUser the dbUser to set
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	/**
	 * @return the dbPwd
	 */
	public String getDbPwd() {
		return dbPwd;
	}

	/**
	 * @param dbPwd the dbPwd to set
	 */
	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the variation
	 */
	public String getVariation() {
		return variation;
	}

	/**
	 * @param variation the variation to set
	 */
	public void setVariation(String variation) {
		this.variation = variation;
	}

	/**
	 * @return the frequency
	 */
	public long getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
		
	/**
	 * @return the consumer
	 */
	public String getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the rmiHost
	 */
	public String getRmiHost() {
		return rmiHost;
	}

	/**
	 * @param rmiHost the rmiHost to set
	 */
	public void setRmiHost(String rmiHost) {
		this.rmiHost = rmiHost;
	}

	/**
	 * @return the rmiPort
	 */
	public Integer getRmiPort() {
		return rmiPort;
	}

	/**
	 * @param rmiPort the rmiPort to set
	 */
	public void setRmiPort(Integer rmiPort) {
		this.rmiPort = rmiPort;
	}

	/**
	 * @return the kafkaHost
	 */
	public String getKafkaHost() {
		return kafkaHost;
	}

	/**
	 * @param kafkaHost the kafkaHost to set
	 */
	public void setKafkaHost(String kafkaHost) {
		this.kafkaHost = kafkaHost;
	}

	public void initParameters() {
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(ConfigNodeNames.PARAMETERS.toString()).item(0);
		final NodeList command = parameters.getElementsByTagName(ConfigNodeNames.COMMAND.toString());
		this.setCommand(command.item(0).getTextContent());
		
		final NodeList name = parameters.getElementsByTagName(ConfigNodeNames.NAME.toString());
		this.setName(name.item(0).getTextContent());
		final NodeList model = parameters.getElementsByTagName(ConfigNodeNames.MODEL.toString());
		this.setModel(model.item(0).getTextContent());
		final NodeList variation = parameters.getElementsByTagName(ConfigNodeNames.VARIATION.toString());
		this.setVariation(variation.item(0).getTextContent());
		final NodeList frequency = parameters.getElementsByTagName(ConfigNodeNames.FREQ.toString());
		this.setFrequency(Long.parseLong(frequency.item(0).getTextContent()));
		
		final NodeList consumer = parameters.getElementsByTagName(ConfigNodeNames.CONSUM.toString());
		this.setConsumer(consumer.item(0).getTextContent());
		final NodeList rmihost = parameters.getElementsByTagName(ConfigNodeNames.RMIHOST.toString());
		this.setRmiHost(rmihost.item(0).getTextContent());
		final NodeList rmiport = parameters.getElementsByTagName(ConfigNodeNames.RMIPORT.toString());
		this.setRmiPort(Integer.parseInt(rmiport.item(0).getTextContent()));
	
		final NodeList kafkahost = parameters.getElementsByTagName(ConfigNodeNames.KFKHOST.toString());
		this.setKafkaHost(kafkahost.item(0).getTextContent());
		
		final NodeList dbHost = parameters.getElementsByTagName(ConfigNodeNames.DBHOST.toString());
		this.setDbHost(dbHost.item(0).getTextContent());
		final NodeList dbUser = parameters.getElementsByTagName(ConfigNodeNames.DBUSER.toString());
		this.setDbUser(dbUser.item(0).getTextContent());
		final NodeList dbPwd = parameters.getElementsByTagName(ConfigNodeNames.DBPWD.toString());
		this.setDbPwd(dbPwd.item(0).getTextContent());
	}
}
