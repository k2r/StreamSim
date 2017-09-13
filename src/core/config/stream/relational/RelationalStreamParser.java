/**
 * 
 */
package core.config.stream.relational;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.config.stream.IStreamParser;
import core.config.xmlNodes.NodeAttributeNames;
import core.config.xmlNodes.NodeNames;
import core.model.relational.attribute.EnumAttribute;
import core.model.relational.attribute.IAttribute;
import core.model.relational.attribute.IntAttribute;
import core.model.relational.attribute.TextAttribute;
import core.model.relational.attribute.type.AttributeType;
import core.profile.IStreamProfile;
import core.profile.relational.RelationalStandardProfile;
import core.profile.relational.RelationalZipfProfile;
import core.profile.type.ProfileType;
import core.transition.*;
import core.transition.type.TransitionType;

/**
 * @author Roland KOTTO KOMBI
 *	Class providing functions for easier XML stream description file parsing
 */
public class RelationalStreamParser implements IStreamParser{
	
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	public RelationalStreamParser(String filename) throws ParserConfigurationException, SAXException, IOException{
		this.filename = filename;
		this.factory = DocumentBuilderFactory.newInstance();
		this.builder = factory.newDocumentBuilder();
		this.document = builder.parse(this.getFilename());
	}

	@Override
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
		String name  = attribute.getAttribute(NodeAttributeNames.NAME.toString());
		final NodeList values  = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		int min = 0;
		int max = 0;
		for (int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(NodeAttributeNames.TYPE.toString()) && value.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(NodeAttributeNames.MIN.toString())){
					min  = Integer.parseInt(value.getTextContent());
				}
				if(value.hasAttribute(NodeAttributeNames.TYPE.toString()) && value.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(NodeAttributeNames.MAX.toString())){
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
	 * @return a stream attribute taking random string values according to a user-defined pattern 
	 */
	public IAttribute getStreamTextAttribute(Element attribute){
		String name = attribute.getAttribute(NodeAttributeNames.NAME.toString());
		final NodeList values = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		HashMap<Integer, HashMap<String, Integer>> patterns = new HashMap<>();
		for (int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			HashMap<String, Integer> pattern = new HashMap<>();
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(NodeAttributeNames.TYPE.toString()) && value.hasAttribute(NodeAttributeNames.LGTH.toString()) && value.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(NodeAttributeNames.PATT.toString())){
					int length = Integer.parseInt(value.getAttribute(NodeAttributeNames.LGTH.toString()));
					String regExp = value.getTextContent();
					pattern.put(regExp, length);
				}			
			}
			if(!pattern.isEmpty()){
				patterns.put(i, pattern);
			}
		}
		IAttribute result = (IAttribute) new TextAttribute(name, patterns);
		return result;
	}
	
	/**
	 * 
	 * @param attribute an Element (according to W3C norm) corresponding to a stream attribute
	 * @return a stream attribute taking string values on a set of user defined values
	 */
	public IAttribute getStreamEnumAttribute(Element attribute){
		String name = attribute.getAttribute(NodeAttributeNames.NAME.toString());
		final NodeList values = attribute.getElementsByTagName(NodeNames.VALUE.toString());
		int n = values.getLength();
		ArrayList<String> enumVals = new ArrayList<String>();
		for(int i = 0; i < n; i++){
			final Element value = (Element) values.item(i);
			if(value.getNodeType() == Node.ELEMENT_NODE){
				if(value.hasAttribute(NodeAttributeNames.TYPE.toString()) && value.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(NodeAttributeNames.OPTION.toString())){
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
	public HashMap<Object, Object> getStreamAttributes() {
		Document doc = this.getDocument();
		HashMap<Object, Object> result = new HashMap<>();
		final NodeList nodes = doc.getElementsByTagName(NodeNames.ATTRIBUTE.toString());
		int n = nodes.getLength();
		for(int i = 0; i < n; i++){
			final Element attribute  = (Element) nodes.item(i);
			if(attribute.hasAttribute(NodeAttributeNames.TYPE.toString()) && attribute.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.INT.toString())){
				IAttribute intAttribute = this.getStreamIntAttribute(attribute);
				result.put(intAttribute.getName(), intAttribute);
			}
			if(attribute.hasAttribute(NodeAttributeNames.TYPE.toString()) && attribute.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.TEXT.toString())){
				IAttribute textAttribute = this.getStreamTextAttribute(attribute);
				result.put(textAttribute.getName(), textAttribute);
			}
			if(attribute.hasAttribute(NodeAttributeNames.TYPE.toString()) && attribute.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(AttributeType.ENUM.toString())){
				IAttribute enumAttribute = this.getStreamEnumAttribute(attribute);
				result.put(enumAttribute.getName(), enumAttribute);
			}
		}
		return result;
	}
	
	@Override
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
			if(profile.hasAttribute(NodeAttributeNames.TYPE.toString()) && profile.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(ProfileType.STANDARD.toString())){
				int nbElementPerTick = Integer.parseInt(profile.getAttribute(NodeAttributeNames.RATE.toString()));
				final NodeList durations = profile.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamProfile sp = (IStreamProfile) new RelationalStandardProfile(duration, nbElementPerTick);
				result.add(sp);
			}
			/*Zipf-1 can be defined without specifying the skew*/
			if(profile.hasAttribute(NodeAttributeNames.TYPE.toString()) && profile.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(ProfileType.ZIPF.toString())
					&& !profile.hasAttribute(NodeAttributeNames.SKEW.toString())){
				int nbElementPerTick = Integer.parseInt(profile.getAttribute(NodeAttributeNames.RATE.toString()));
				final NodeList durations = profile.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamProfile sp = (IStreamProfile) new RelationalZipfProfile(duration, nbElementPerTick);
				result.add(sp);
			}
			/*Zipf distribution other than 1 must specify a value for an attribute skew*/
			if(profile.hasAttribute(NodeAttributeNames.TYPE.toString()) && profile.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(ProfileType.ZIPF.toString())
					&& profile.hasAttribute(NodeAttributeNames.SKEW.toString())){
				int nbElementPerTick = Integer.parseInt(profile.getAttribute(NodeAttributeNames.RATE.toString()));
				final NodeList durations = profile.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				double skew = Double.parseDouble(profile.getAttribute(NodeAttributeNames.SKEW.toString()));
				IStreamProfile sp = (IStreamProfile) new RelationalZipfProfile(duration, nbElementPerTick, skew);
				result.add(sp);
			}
		}
		return result;
	}
	
	@Override
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
			if(transition.hasAttribute(NodeAttributeNames.TYPE.toString()) && transition.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.LINEAR.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new LinearTransition(duration);
				result.add(st);
			}
			if(transition.hasAttribute(NodeAttributeNames.TYPE.toString()) && transition.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.EXP.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new ExponentialTransition(duration);
				result.add(st);
			}
			if(transition.hasAttribute(NodeAttributeNames.TYPE.toString()) && transition.getAttribute(NodeAttributeNames.TYPE.toString()).equalsIgnoreCase(TransitionType.LOG.toString())){
				final NodeList durations = transition.getElementsByTagName(NodeNames.DURATION.toString());
				double duration = Double.parseDouble(durations.item(0).getTextContent());
				IStreamTransition st = (IStreamTransition) new LogarithmicTransition(duration);
				result.add(st);
			}
		}
		return result;
	}
}