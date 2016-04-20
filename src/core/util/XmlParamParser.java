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

import core.util.xmlNodes.NodeNames;

/**
 * @author Roland
 *
 */
public class XmlParamParser {
	
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	
	private long tick_interval;
	private int max_nb_executors;
	private int max_threads;
	private int max_seq_emit;
	
	/**
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * 
	 */
	public XmlParamParser(String filename) throws ParserConfigurationException, SAXException, IOException {
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

	/**
	 * @return the max_nb_executors
	 */
	public int getMax_nb_executors() {
		return max_nb_executors;
	}

	/**
	 * @param max_nb_executors the max_nb_executors to set
	 */
	public void setMax_nb_executors(int max_nb_executors) {
		this.max_nb_executors = max_nb_executors;
	}

	/**
	 * @return the max_threads
	 */
	public int getMax_threads() {
		return max_threads;
	}

	/**
	 * @param max_threads the max_threads to set
	 */
	public void setMax_threads(int max_threads) {
		this.max_threads = max_threads;
	}

	/**
	 * @return the max_seq_emit
	 */
	public int getMax_seq_emit() {
		return max_seq_emit;
	}

	/**
	 * @param max_seq_emit the max_seq_emit to set
	 */
	public void setMax_seq_emit(int max_seq_oper) {
		this.max_seq_emit = max_seq_oper;
	}

	public void initParameters() {
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(NodeNames.PARAM.toString()).item(0);
		final NodeList tickInterval = parameters.getElementsByTagName(NodeNames.TICKINTERVAL.toString());
		this.setTick_interval(Long.parseLong(tickInterval.item(0).getTextContent()));
		final NodeList executors = parameters.getElementsByTagName(NodeNames.EXECUTORS.toString());
		this.setMax_nb_executors(Integer.parseInt(executors.item(0).getTextContent()));
		final NodeList threads = parameters.getElementsByTagName(NodeNames.NBTHREAD.toString());
		this.setMax_threads(Integer.parseInt(threads.item(0).getTextContent()));
		final NodeList sequential = parameters.getElementsByTagName(NodeNames.SEQOPER.toString());
		this.setMax_seq_emit(Integer.parseInt(sequential.item(0).getTextContent()));
	}
}
