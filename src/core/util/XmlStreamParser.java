/**
 * 
 */
package core.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.attribute.EnumAttribute;
import core.attribute.IAttribute;
import core.attribute.IntAttribute;
import core.attribute.TextAttribute;
import core.attribute.type.AttributeType;
import core.profile.IStreamProfile;
import core.profile.StandardProfile;
import core.profile.type.ProfileType;
import core.transition.*;
import core.transition.type.TransitionType;
import core.util.xmlNodes.AttributeNames;
import core.util.xmlNodes.NodeNames;

/**
 * @author Roland KOTTO KOMBI
 *	Class providing functions for easier XML stream description file parsing
 */
public class XmlStreamParser {
	
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	public XmlStreamParser(String filename) throws ParserConfigurationException, SAXException, IOException{
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
	 * 
	 * @param attribute an Element (according to W3C norm) corresponding to a stream attribute
	 * @return a stream attribute taking values in a defined range of integer values
	 */
	public IAttribute getStreamIntAttribute(Element attribute){
		String name  = attribute.getAttribute(AttributeNames.NAME.toString());
		final NodeList values  = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		int min = 0;
		int max = 0;
		for (int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(AttributeNames.TYPE.toString()) && value.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeNames.MIN.toString())){
					min  = Integer.parseInt(value.getTextContent());
				}
				if(value.hasAttribute(AttributeNames.TYPE.toString()) && value.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeNames.MAX.toString())){
					max  = Integer.parseInt(value.getTextContent());
				}
			}
		}
		IAttribute result = (IAttribute) new IntAttribute(name, min, max);
		return result;
	}
	
	/**
	 * 
	 * @param attribute an Element (according to W3C norm) corresponding to a stream attribute
	 * @return a stream attribute taking random string values of length contained between user defined values 
	 */
	public IAttribute getStreamTextAttribute(Element attribute){
		String name = attribute.getAttribute(AttributeNames.NAME.toString());
		final NodeList values = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		int min = 0;
		int max = 0;
		for (int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(AttributeNames.TYPE.toString()) && value.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeNames.MIN.toString())){
					min  = Integer.parseInt(value.getTextContent());
				}
				if(value.hasAttribute(AttributeNames.TYPE.toString()) && value.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeNames.MAX.toString())){
					max  = Integer.parseInt(value.getTextContent());
				}
			}
		}
		IAttribute result = (IAttribute) new TextAttribute(name, min, max);
		return result;
	}
	
	/**
	 * 
	 * @param attribute an Element (according to W3C norm) corresponding to a stream attribute
	 * @return a stream attribute taking string values on a set of user defined values
	 */
	public IAttribute getStreamEnumAttribute(Element attribute){
		String name = attribute.getAttribute(AttributeNames.NAME.toString());
		final NodeList values = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		ArrayList<String> enumVals = new ArrayList<String>();
		for(int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(AttributeNames.TYPE.toString()) && value.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeNames.OPTION.toString())){
					enumVals.add(value.getTextContent());
				}
			}
		}
		IAttribute result = (IAttribute) new EnumAttribute(name, enumVals);
		return result;
	}
	
	/**
	 * 
	 * @return the schema of the stream described by the xml file
	 */
	public ArrayList<IAttribute> getStreamAttributes() {
		Document doc = this.getDocument();
		ArrayList<IAttribute> result = new ArrayList<IAttribute>();
		final NodeList nodes = doc.getElementsByTagName(NodeNames.ATTRIBUTE.toString());
		int n = nodes.getLength();
		for(int i = 0; i < n; i++){
			final Element attribute  = (Element) nodes.item(i);
			if(attribute.hasAttribute(AttributeNames.TYPE.toString()) && attribute.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.INT.toString())){
				result.add(this.getStreamIntAttribute(attribute));
			}
			if(attribute.hasAttribute(AttributeNames.TYPE.toString()) && attribute.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.TEXT.toString())){
				result.add(this.getStreamTextAttribute(attribute));
			}
			if(attribute.hasAttribute(AttributeNames.TYPE.toString()) && attribute.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.ENUM.toString())){
				result.add(this.getStreamEnumAttribute(attribute));
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @return the list of stream profiles
	 */
	public ArrayList<IStreamProfile> getStreamProfiles(){
		Document doc = this.getDocument();
		ArrayList<IStreamProfile> result = new ArrayList<IStreamProfile>();
		final NodeList nodes = doc.getElementsByTagName(NodeNames.PROFILE.toString());
		int n = nodes.getLength();
		for(int i = 0; i < n; i++){
			final Element profile  = (Element) nodes.item(i);
			if(profile.hasAttribute(AttributeNames.TYPE.toString()) && profile.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(ProfileType.STANDARD.toString())){
				int nbElementPerTick = Integer.parseInt(profile.getAttribute(AttributeNames.RATE.toString()));
				final NodeList durations = profile.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamProfile sp = (IStreamProfile) new StandardProfile(duration, nbElementPerTick);
				result.add(sp);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @return the list of transitions between stream profiles
	 */
	public ArrayList<IStreamTransition> getStreamTransitions(){
		Document doc = this.getDocument();
		ArrayList<IStreamTransition> result = new ArrayList<IStreamTransition>();
		final NodeList nodes = doc.getElementsByTagName(NodeNames.TRANSITION.toString());
		int n = nodes.getLength();
		for(int i = 0; i < n; i++){
			final Element transition  = (Element) nodes.item(i);
			if(transition.hasAttribute(AttributeNames.TYPE.toString()) && transition.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.LINEAR.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new LinearTransition(duration);
				result.add(st);
			}
			if(transition.hasAttribute(AttributeNames.TYPE.toString()) && transition.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.EXP.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new ExponentialTransition(duration);
				result.add(st);
			}
			if(transition.hasAttribute(AttributeNames.TYPE.toString()) && transition.getAttribute(AttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.LOG.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new LogarithmicTransition(duration);
				result.add(st);
			}
		}
		return result;
	}
}