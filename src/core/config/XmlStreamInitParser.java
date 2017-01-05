/**
 * 
 */
package core.config;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.config.xmlNodes.GlobalConfigNodeNames;
import core.config.xmlNodes.NodeNames;

/**
 * @author Roland
 *
 */
public class XmlStreamInitParser {

	/*Launch file parameters*/
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	/*Stream generator parameters*/
	private String command;
	private String dbHost;
	private String dbUser;
	private String dbPwd;
	private String name;
	private String sgPort;
	private String variation;
	private long tick_interval;
	
	public XmlStreamInitParser(){
		this.factory = null;
		this.builder = null;
		this.document = null;
	}
	
	public XmlStreamInitParser(String filename) throws ParserConfigurationException, SAXException, IOException{
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
	 * @return the sgPort
	 */
	public String getSgPort() {
		return sgPort;
	}

	/**
	 * @param sgPort the sgPort to set
	 */
	public void setSgPort(String sgPort) {
		this.sgPort = sgPort;
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
	 * @return the tick_interval
	 */
	public long getTick_interval() {
		return tick_interval;
	}

	/**
	 * @param tick_interval the tick_interval to set
	 */
	public void setTick_interval(long tick_interval) {
		this.tick_interval = tick_interval;
	}
		
	public void initParameters() {
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(GlobalConfigNodeNames.PARAMETERS.toString()).item(0);
		final NodeList command = parameters.getElementsByTagName(GlobalConfigNodeNames.COMMAND.toString());
		this.setCommand(command.item(0).getTextContent());
		final NodeList dbHost = parameters.getElementsByTagName(GlobalConfigNodeNames.DBHOST.toString());
		this.setDbHost(dbHost.item(0).getTextContent());
		final NodeList dbUser = parameters.getElementsByTagName(GlobalConfigNodeNames.DBUSER.toString());
		this.setDbUser(dbUser.item(0).getTextContent());
		final NodeList dbPwd = parameters.getElementsByTagName(GlobalConfigNodeNames.DBPWD.toString());
		this.setDbPwd(dbPwd.item(0).getTextContent());
		final NodeList name = parameters.getElementsByTagName(GlobalConfigNodeNames.NAME.toString());
		this.setName(name.item(0).getTextContent());
		final NodeList sgport = parameters.getElementsByTagName(GlobalConfigNodeNames.SGPORT.toString());
		this.setSgPort(sgport.item(0).getTextContent());
		final NodeList variation = parameters.getElementsByTagName(GlobalConfigNodeNames.VARIATION.toString());
		this.setVariation(variation.item(0).getTextContent());
		final NodeList tickInterval = parameters.getElementsByTagName(NodeNames.TICKINTERVAL.toString());
		this.setTick_interval(Long.parseLong(tickInterval.item(0).getTextContent()));
	}
}
