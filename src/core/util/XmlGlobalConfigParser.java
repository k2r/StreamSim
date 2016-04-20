/**
 * 
 */
package core.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.util.xmlNodes.GlobalConfigNodeNames;

/**
 * @author Roland
 *
 */
public class XmlGlobalConfigParser {
	
	/*Launch file parameters*/
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	/*Stream generator parameters*/
	private String home;
	private String sgConfFile;
	private String name;
	private String sgPort;
	private String variation;
	
	/*System monitor parameters*/
	private String smConfFile;
	private String smPort;
	
	/*Storm execution parameters*/
	private String sgHost;
	private String sgHostPort;
	private String smHost;
	private String smHostPort;
	private String nbSupervisors;
	private String windowSize;
	private String windowStep;
	
	public XmlGlobalConfigParser(String filename) throws ParserConfigurationException, SAXException, IOException{
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
	 * @return the home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * @return the sgConfFile
	 */
	public String getSgConfFile() {
		return sgConfFile;
	}

	/**
	 * @param sgConfFile the sgConfFile to set
	 */
	public void setSgConfFile(String sgConfFile) {
		this.sgConfFile = sgConfFile;
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
	 * @return the smConfFile
	 */
	public String getSmConfFile() {
		return smConfFile;
	}

	/**
	 * @param smConfFile the smConfFile to set
	 */
	public void setSmConfFile(String smConfFile) {
		this.smConfFile = smConfFile;
	}

	/**
	 * @return the smPort
	 */
	public String getSmPort() {
		return smPort;
	}

	/**
	 * @param smPort the smPort to set
	 */
	public void setSmPort(String smPort) {
		this.smPort = smPort;
	}

	/**
	 * @return the sgHost
	 */
	public String getSgHost() {
		return sgHost;
	}

	/**
	 * @param sgHost the sgHost to set
	 */
	public void setSgHost(String sgHost) {
		this.sgHost = sgHost;
	}

	/**
	 * @return the sgHostPort
	 */
	public String getSgHostPort() {
		return sgHostPort;
	}

	/**
	 * @param sgHostPort the sgHostPort to set
	 */
	public void setSgHostPort(String sgHostPort) {
		this.sgHostPort = sgHostPort;
	}

	/**
	 * @return the smHost
	 */
	public String getSmHost() {
		return smHost;
	}

	/**
	 * @param smHost the smHost to set
	 */
	public void setSmHost(String smHost) {
		this.smHost = smHost;
	}

	/**
	 * @return the smHostPort
	 */
	public String getSmHostPort() {
		return smHostPort;
	}

	/**
	 * @param smHostPort the smHostPort to set
	 */
	public void setSmHostPort(String smHostPort) {
		this.smHostPort = smHostPort;
	}

	/**
	 * @return the nbSupervisors
	 */
	public String getNbSupervisors() {
		return nbSupervisors;
	}

	/**
	 * @param nbSupervisors the nbSupervisors to set
	 */
	public void setNbSupervisors(String nbSupervisors) {
		this.nbSupervisors = nbSupervisors;
	}

	/**
	 * @return the windowSize
	 */
	public String getWindowSize() {
		return windowSize;
	}

	/**
	 * @param windowSize the windowSize to set
	 */
	public void setWindowSize(String windowSize) {
		this.windowSize = windowSize;
	}

	/**
	 * @return the windowStep
	 */
	public String getWindowStep() {
		return windowStep;
	}

	/**
	 * @param windowStep the windowStep to set
	 */
	public void setWindowStep(String windowStep) {
		this.windowStep = windowStep;
	}
	
	public void initParameters() {
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(GlobalConfigNodeNames.PARAMETERS.toString()).item(0);
		final NodeList home = parameters.getElementsByTagName(GlobalConfigNodeNames.HOME.toString());
		this.setHome(home.item(0).getTextContent());
		final NodeList sgconf = parameters.getElementsByTagName(GlobalConfigNodeNames.SGCONF.toString());
		this.setSgConfFile(sgconf.item(0).getTextContent());
		final NodeList name = parameters.getElementsByTagName(GlobalConfigNodeNames.NAME.toString());
		this.setName(name.item(0).getTextContent());
		final NodeList sgport = parameters.getElementsByTagName(GlobalConfigNodeNames.SGPORT.toString());
		this.setSgPort(sgport.item(0).getTextContent());
		final NodeList variation = parameters.getElementsByTagName(GlobalConfigNodeNames.VARIATION.toString());
		this.setVariation(variation.item(0).getTextContent());
		final NodeList smconf = parameters.getElementsByTagName(GlobalConfigNodeNames.SMCONF.toString());
		this.setSmConfFile(smconf.item(0).getTextContent());
		final NodeList smport = parameters.getElementsByTagName(GlobalConfigNodeNames.SMPORT.toString());
		this.setSmPort(smport.item(0).getTextContent());
		final NodeList sghost = parameters.getElementsByTagName(GlobalConfigNodeNames.SGHOST.toString());
		this.setSgHost(sghost.item(0).getTextContent());
		final NodeList sghostport = parameters.getElementsByTagName(GlobalConfigNodeNames.SGHOSTPORT.toString());
		this.setSgHostPort(sghostport.item(0).getTextContent());
		final NodeList smhost = parameters.getElementsByTagName(GlobalConfigNodeNames.SMHOST.toString());
		this.setSmHost(smhost.item(0).getTextContent());
		final NodeList smhostport = parameters.getElementsByTagName(GlobalConfigNodeNames.SMHOSTPORT.toString());
		this.setSmHostPort(smhostport.item(0).getTextContent());
		final NodeList nbworkers = parameters.getElementsByTagName(GlobalConfigNodeNames.NBSUPERVISORS.toString());
		this.setNbSupervisors(nbworkers.item(0).getTextContent());
		final NodeList size = parameters.getElementsByTagName(GlobalConfigNodeNames.SIZE.toString());
		this.setWindowSize(size.item(0).getTextContent());
		final NodeList step = parameters.getElementsByTagName(GlobalConfigNodeNames.STEP.toString());
		this.setWindowStep(step.item(0).getTextContent());
	}
}
