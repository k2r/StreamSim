/**
 * 
 */
package core.stream;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

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
import core.network.rmi.source.IRMIStreamSource;
import core.network.rmi.source.RMIStreamSource;
//import core.network.socket.source.ISocketStreamSource;
//import core.network.socket.source.SocketStreamSource;
import core.profile.IStreamProfile;
import core.transition.IStreamTransition;
import core.util.XmlStreamParser;

/**
 * @author Roland
 *
 */
public class ElementStream implements IElementStream {

	private int port;
	private Path schemaPath;
	private Path varPath;
	private ArrayList<IStreamAttribute> attributes;
	private ArrayList<IStreamProfile> profiles;
	private ArrayList<IStreamTransition> transitions;
	private IStreamProfile currentProfile;
	private IStreamProfile nextProfile;
	private IStreamTransition currentTransition;
	//private final ISocketStreamSource source;
	private final IRMIStreamSource source;
	private final double refTime = System.currentTimeMillis();
	private boolean isTransition;
	public static Logger logger = Logger.getLogger("ElementStream");
	private HashMap<String, IElement[]> elements = new HashMap<String, IElement[]>();
	
	/**
	 * @throws IOException 
	 * 
	 */
	public ElementStream(int port, String schema, String variation) throws IOException {
		this.port = port;
		//this.source = new SocketStreamSource(this.port);
		this.source = new RMIStreamSource(this.port);
		this.schemaPath = Paths.get("schemas/" + schema + "Schema.xml");
		this.varPath = Paths.get("variations/models/" + variation + "Var.xml");
		this.isTransition = false;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getPort()
	 */
	@Override
	public int getPort() {
		return this.port;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getSource()
	 */
	@Override
	public IRMIStreamSource getSource() {
		return this.source;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#initializeSchema()
	 */
	@Override
	public void initializeSchema() {
		try {
			XmlStreamParser parser = new XmlStreamParser(this.schemaPath.toString());
			this.attributes = parser.getStreamAttributes();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getAttributes()
	 */
	@Override
	public ArrayList<IStreamAttribute> getAttributes() {
		return this.attributes;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getAttributeType(java.lang.String)
	 */
	@Override
	public String getAttributeType(String attributeName) {
		String result = null;
		ArrayList<IStreamAttribute> attributes = this.getAttributes();
		int n = attributes.size();
		for (int i = 0; i < n; i++){
			String name = attributes.get(i).getName();
			if(name.equalsIgnoreCase(attributeName)){
				result = attributes.get(i).getType().toString();
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getReferenceValue(java.lang.String)
	 */
	@Override
	public ArrayList<String> getReferenceValue(String attributeName) {
		ArrayList<String> result = null;
		ArrayList<IStreamAttribute> attributes = this.getAttributes();
		int n = attributes.size();
		for (int i = 0; i < n; i++){
			String name = attributes.get(i).getName();
			if(name.equalsIgnoreCase(attributeName)){
				result = attributes.get(i).getReferenceValue();
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getValueSpace(java.lang.String)
	 */
	@Override
	public String getValueSpace(String attributeName) {
		String result = null;
		ArrayList<IStreamAttribute> attributes = this.getAttributes();
		int n = attributes.size();
		for (int i = 0; i < n; i++){
			String name = attributes.get(i).getName();
			if(name.equalsIgnoreCase(attributeName)){
				result = attributes.get(i).getValueSpace();
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#initializeVariations()
	 */
	@Override
	public void initializeVariations() {
		try {
			XmlStreamParser parser = new XmlStreamParser(this.varPath.toString());
			this.profiles = parser.getStreamProfiles();
			this.transitions = parser.getStreamTransitions();
			
			int pSize = this.getProfiles().size();
			if(pSize > 0){
				this.setCurrentProfile(this.getProfiles().get(0));
				if(pSize > 1){
					this.setNextProfile(this.getProfiles().get(1));
				}
			}
			
			int tSize = this.getTransitions().size();
			if(tSize > 0){
				this.setCurrentTransition(this.getTransitions().get(0));
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getProfiles()
	 */
	@Override
	public ArrayList<IStreamProfile> getProfiles() {
		return this.profiles;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getTransitions()
	 */
	@Override
	public ArrayList<IStreamTransition> getTransitions() {
		return this.transitions;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getCurrentProfile()
	 */
	@Override
	public IStreamProfile getCurrentProfile() {
		return this.currentProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#setCurrentProfile(core.profile.IStreamProfile)
	 */
	@Override
	public void setCurrentProfile(IStreamProfile currentProfile) {
		this.currentProfile = currentProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getNextProfile()
	 */
	@Override
	public IStreamProfile getNextProfile() {
		return this.nextProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#setNextProfile(core.profile.IStreamProfile)
	 */
	@Override
	public void setNextProfile(IStreamProfile nextProfile) {
		this.nextProfile = nextProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getCurrentTransition()
	 */
	@Override
	public IStreamTransition getCurrentTransition() {
		return this.currentTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#setCurrentTransition(core.transition.IStreamTransition)
	 */
	@Override
	public void setCurrentTransition(IStreamTransition currentTransition) {
		this.currentTransition = currentTransition;

	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#isTransition()
	 */
	@Override
	public boolean isTransition() {
		return this.isTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#setTransition(boolean)
	 */
	@Override
	public void setTransition(boolean isTransition) {
		this.isTransition = isTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getRefTime()
	 */
	@Override
	public double getRefTime() {
		return this.refTime;
	}
	
	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getElements()
	 */
	@Override
	public HashMap<String, IElement[]> getElements(){
		return this.elements;
	}
	
	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getAttributeNames()
	 */
	@Override
	public ArrayList<String> getAttributeNames(){
		ArrayList<IStreamAttribute> attrs = this.getAttributes();
		ArrayList<String> result = new ArrayList<String>();
		int n = attrs.size();
		for(int i = 0; i < n; i++){
			result.add(attrs.get(i).getName());
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see core.stream.IElementStream#generateStream(long)
	 */
	@Override
	public void generateStream(long tickDelay) {
		System.out.println("Generating elements for the stream...");
		int pSize = this.getProfiles().size();
		int tSize = this.getTransitions().size();
		
		int i = 0;
		int j = 0;
		int nextPIndex = 1;
		int nbElem = 0;
		
		while(i < pSize){
			this.setCurrentProfile(this.getProfiles().get(i));
			ArrayList<IElement[]> pElements = this.generateProfile(tickDelay);
			int n = pElements.size();
			int numPrElem = 0;
			for(int k = 0; k < n; k++){
				String pChunkKey = "P" + i + "It" + k;
				this.elements.put(pChunkKey, pElements.get(k));
				System.out.println("Chunk " + k + " of profile " + i + " (key " + pChunkKey + ") generated with " + this.elements.get(pChunkKey).length + " stream elements");
				numPrElem += this.elements.get(pChunkKey).length;
			}
			nbElem += numPrElem;
			System.out.println("Profile " + i + " generated ("+ numPrElem + " stream elements)...");
			if(nextPIndex < pSize && j < tSize){
				this.setNextProfile(this.getProfiles().get(nextPIndex));
				this.setCurrentTransition(this.getTransitions().get(j));
				ArrayList<IElement[]> tElements = this.generateTransition(tickDelay);
				int m = tElements.size();
				int numTrElem = 0;
				for(int l = 0; l < m; l++){
					String tChunkKey = "T" + i + "It" + l;
					this.elements.put(tChunkKey, tElements.get(l));
					System.out.println("Chunk " + l + " of profile " + j + " (key " + tChunkKey + ") generated with " + this.elements.get(tChunkKey).length + " stream elements");
					numTrElem += this.elements.get(tChunkKey).length;
				}
				nbElem += numTrElem;
				System.out.println("Transition " + j + " generated ("+ numTrElem + " stream elements)...");
			}
			i++;
			j++;
			nextPIndex++;
		}
		System.out.println(nbElem + " stream elements generated");
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#generateProfile(long)
	 */
	@Override
	public ArrayList<IElement[]> generateProfile(long tickDelay) {
		IStreamProfile profile = this.getCurrentProfile();
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
		int nbAttribute = this.getAttributes().size();

		int rate = profile.getNbElementPerTick();
		double iterations = profile.getDuration() / tickDelay;

		for(int i = 0; i < iterations; i++){
			double timestamp = System.currentTimeMillis();

			IElement[] iter = new IElement[rate];
			for(int j = 0; j < rate; j++){
				switch(nbAttribute){

				case(1):  
					IElement1<Object> e1 = (IElement1<Object>) new Element1<Object>(null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						e1.setValue((Integer)intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						e1.setValue(textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						e1.setValue(enumValue);
					}
				}
				iter[j] = (IElement) e1;	
				break;				
				case(2): 
					IElement2<Object,Object> e2 = (IElement2<Object,Object>) new Element2<Object, Object>(null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e2.setFirstValue((Integer)intValue);
						case(1):
							e2.setSecondValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e2.setFirstValue((String)textValue);
						case(1):
							e2.setSecondValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e2.setFirstValue((String)enumValue);
						case(1):
							e2.setSecondValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e2;
				break;
				case(3): 
					IElement3<Object,Object,Object> e3 = (IElement3<Object,Object,Object>) new Element3<Object,Object,Object>(null, null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e3.setFirstValue((Integer)intValue);
						case(1):
							e3.setSecondValue((Integer)intValue);
						case(2):
							e3.setThirdValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e3.setFirstValue((String)textValue);
						case(1):
							e3.setSecondValue((String)textValue);
						case(2):
							e3.setThirdValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e3.setFirstValue((String)enumValue);
						case(1):
							e3.setSecondValue((String)enumValue);
						case(2):
							e3.setThirdValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e3;
				break;
				case(4): 
					IElement4<Object,Object,Object,Object> e4 = (IElement4<Object,Object,Object,Object>) new Element4<Object,Object,Object,Object>(null, null, null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e4.setFirstValue((Integer)intValue);
						case(1):
							e4.setSecondValue((Integer)intValue);
						case(2):
							e4.setThirdValue((Integer)intValue);
						case(3):
							e4.setFourthValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e4.setFirstValue((String)textValue);
						case(1):
							e4.setSecondValue((String)textValue);
						case(2):
							e4.setThirdValue((String)textValue);
						case(3):
							e4.setFourthValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e4.setFirstValue((String)enumValue);
						case(1):
							e4.setSecondValue((String)enumValue);
						case(2):
							e4.setThirdValue((String)enumValue);
						case(3):
							e4.setFourthValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e4;
				break;
				}
			}
			result.add(iter);
		}
		System.out.println("this profile generated " + (iterations * rate) + " stream elements for " + iterations + " ticks");
		return result;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#generateTransition(long)
	 */
	@Override
	public ArrayList<IElement[]> generateTransition(long tickDelay) {
		IStreamProfile former = this.getCurrentProfile();
		IStreamProfile next = this.getNextProfile();
		IStreamTransition transition = this.getCurrentTransition();
		int nbAttribute = this.getAttributes().size();

		transition.solveTransitionFunc(former.getNbElementPerTick(), next.getNbElementPerTick(), tickDelay);

		double iterations = transition.getDuration() / tickDelay;

		int nbElements = 0;
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
	
		for(int i = 0; i < iterations; i++){
			int rate = (int)transition.getIntermediateValue();
			IElement[] iter = new IElement[rate];
			double timestamp = System.currentTimeMillis();

			for(int j = 0; j < rate; j++){
				switch(nbAttribute){

				case(1):  
					IElement1<Object> e1 = (IElement1<Object>) new Element1<Object>(null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						e1.setValue((Integer)intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);;
						e1.setValue(textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						e1.setValue(enumValue);
					}
				}
				iter[j] = (IElement) e1;	
				break;

				case(2): 
					IElement2<Object,Object> e2 = (IElement2<Object,Object>) new Element2<Object,Object>(null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e2.setFirstValue((Integer)intValue);
						case(1):
							e2.setSecondValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e2.setFirstValue((String)textValue);
						case(1):
							e2.setSecondValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e2.setFirstValue((String)enumValue);
						case(1):
							e2.setSecondValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e2;
				break;
				case(3): 
					IElement3<Object,Object,Object> e3 = (IElement3<Object,Object,Object>) new Element3<Object,Object,Object>(null, null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e3.setFirstValue((Integer)intValue);
						case(1):
							e3.setSecondValue((Integer)intValue);
						case(2):
							e3.setThirdValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e3.setFirstValue((String)textValue);
						case(1):
							e3.setSecondValue((String)textValue);
						case(2):
							e3.setThirdValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e3.setFirstValue((String)enumValue);
						case(1):
							e3.setSecondValue((String)enumValue);
						case(2):
							e3.setThirdValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e3;
				break;
				case(4):
					IElement4<Object,Object,Object,Object> e4 = (IElement4<Object,Object,Object,Object>) new Element4<Object,Object,Object,Object>(null, null, null, null, timestamp);
				for(int k = 0; k < nbAttribute; k++){
					IStreamAttribute attribute = attributes.get(k);
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						int intValue = (Integer)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e4.setFirstValue((Integer)intValue);
						case(1):
							e4.setSecondValue((Integer)intValue);
						case(2):
							e4.setThirdValue((Integer)intValue);
						case(3):
							e4.setFourthValue((Integer)intValue);
						}

					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						int min = Integer.parseInt(attribute.getReferenceValue().get(0));
						int max = Integer.parseInt(attribute.getReferenceValue().get(1));
						String textValue = (String)this.getCurrentProfile().getNextValue(min, max, null, type);
						switch(k){
						case(0): 
							e4.setFirstValue((String)textValue);
						case(1):
							e4.setSecondValue((String)textValue);
						case(2):
							e4.setThirdValue((String)textValue);
						case(3):
							e4.setFourthValue((String)textValue);
						}
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						ArrayList<String> vals = attribute.getReferenceValue();
						String enumValue = (String)this.getCurrentProfile().getNextValue(0, 0, vals, type);
						switch(k){
						case(0): 
							e4.setFirstValue((String)enumValue);
						case(1):
							e4.setSecondValue((String)enumValue);
						case(2):
							e4.setThirdValue((String)enumValue);
						case(3):
							e4.setFourthValue((String)enumValue);
						}
					}
				}
				iter[j] = (IElement) e4;
				break;
				}
				nbElements++;
			}
			result.add(iter);
		}
		System.out.println("this transition generated " + nbElements + " stream elements for " + iterations + " ticks");
		return result;
	}
}
