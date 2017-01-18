/**
 * 
 */
package servlets.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import beans.CreatorBean;
import beans.StreamAttribute;
import beans.TextPattern;

/**
 * @author Roland
 *
 */
public class Utils {

	public static ArrayList<String> getStreamList(String schemaPath){
		ArrayList<String> result = new ArrayList<>();
		File schemaFolder = new File(schemaPath);
		File[] schemas = schemaFolder.listFiles();
		if(schemas != null){
			for(File schema : schemas){
				if(schema.getName().endsWith(".xml")){
					String schemaName = schema.getName().split("Schema")[0];
					result.add(schemaName);
				}
			}
		}
		return result;
	}
	
	public static ArrayList<Long> normalizedTimestamps(HashMap<Long, Integer> rawData){
		ArrayList<Long> result = new ArrayList<>();
		int nbTimestamp = rawData.keySet().size();
		Long start = Long.MAX_VALUE;
		for(Long timestamp: rawData.keySet()){
			if(timestamp < start){
				start = timestamp;
			}
			result.add(timestamp);
		}
		for(int i = 0; i < nbTimestamp; i++){
			result.set(i, result.get(i) - start);
		}
		Collections.sort(result);
		return result;
	}
	
	
	public static ArrayList<Integer> rateValues(HashMap<Long, Integer> rawData){
		int nbTimestamp = rawData.keySet().size();
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<Long> rawTimestamps = new ArrayList<>();
		for(Long timestamp : rawData.keySet()){
			rawTimestamps.add(timestamp);
		}
		Collections.sort(rawTimestamps);
		for(int i = 0; i< nbTimestamp; i++){
			result.add(rawData.get(rawTimestamps.get(i)));
		}
		return result;
	}
	
	public static void saveStreamAsXml(CreatorBean bean, ServletContext context) throws ParserConfigurationException, TransformerException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		DOMImplementation domImpl = doc.getImplementation();
		DocumentType docType = domImpl.createDocumentType("doctype", "stream", "streamDocType.dtd");
		Element streamElement = doc.createElement("stream");
		doc.appendChild(streamElement);
		
		Element name = doc.createElement("name");
		streamElement.appendChild(name);
		name.appendChild(doc.createTextNode(bean.getStreamName()));
		
		Element attributes = doc.createElement("attributes");
		
		int nbAttrs = bean.getNbAttributes();
		ArrayList<StreamAttribute> streamAttrs = bean.getAttributes();
		for(int i = 0; i < nbAttrs; i++){
			StreamAttribute attr = streamAttrs.get(i);
			String type = attr.getType();
			Element attribute = doc.createElement("attribute");
			attribute.setAttribute("type", type);
			attribute.setAttribute("name", attr.getName());
			
			HashMap<String, Object> parameters = attr.getParameters();
			if(type.equalsIgnoreCase("enumerate")){
				String[] values = (String[]) parameters.get("values");
				int n = values.length;
				for(int j = 0; j < n; j++){
					Element value = doc.createElement("value");
					value.setAttribute("type", "option");
					value.appendChild(doc.createTextNode(values[j]));
					attribute.appendChild(value);
				}
			}
			
			if(type.equalsIgnoreCase("integer")){
				Integer min = (Integer) parameters.get("min");
				Integer max = (Integer) parameters.get("max");
				
				Element valueMin = doc.createElement("value");
				valueMin.setAttribute("type", "min");
				valueMin.appendChild(doc.createTextNode(min.toString()));
				attribute.appendChild(valueMin);
				
				Element valueMax = doc.createElement("value");
				valueMax.setAttribute("type", "max");
				valueMax.appendChild(doc.createTextNode(max.toString()));
				attribute.appendChild(valueMax);
			}
			
			if(type.equalsIgnoreCase("text")){
				TextPattern pattern = (TextPattern) parameters.get("pattern");
				
				Element value = doc.createElement("value");
				value.setAttribute("type", "pattern");
				value.setAttribute("length", pattern.getLength().toString());
				value.appendChild(doc.createTextNode(pattern.getType()));
				attribute.appendChild(value);
			}
			attributes.appendChild(attribute);
		}
		streamElement.appendChild(attributes);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getPublicId());
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());
		DOMSource source = new DOMSource(doc);
		String path = context.getRealPath("/schemas") + "/" + bean.getStreamName() + "Schema.xml";
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}
}
