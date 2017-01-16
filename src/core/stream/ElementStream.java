/**
 * 
 */
package core.stream;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.attribute.IAttribute;
import core.attribute.type.AttributeType;
import core.config.XmlStreamParser;
import core.element.IElement;
import core.element.ISchema;
import core.element.StreamElement;
import core.element.StreamSchema;
import core.network.rmi.source.IRMIStreamSource;
import core.network.rmi.source.RMIStreamSource;
//import core.network.socket.source.ISocketStreamSource;
//import core.network.socket.source.SocketStreamSource;
import core.profile.IStreamProfile;
import core.transition.IStreamTransition;

/**
 * @author Roland
 *
 */
public class ElementStream implements IElementStream{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3157381972651298760L;
	
	private int port;
	private Path schemaPath;
	private Path varPath;
	private ISchema schema;
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
		this.schemaPath = Paths.get("/schemas/" + schema + "Schema.xml");
		this.varPath = Paths.get("/variations/models/" + variation + "Var.xml");
		this.isTransition = false;
	}
	
	public ElementStream(int port, String schema, String variation, ServletContext context) throws IOException, URISyntaxException {
		this.port = port;
		this.source = new RMIStreamSource(port);
		this.schemaPath = Paths.get(context.getRealPath("/schemas/" + schema + "Schema.xml"));
		this.varPath = Paths.get(context.getRealPath("/variations/models/" + variation + "Var.xml"));
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
			this.schema = new StreamSchema(parser.getStreamAttributes());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getAttributes()
	 */
	@Override
	public ISchema getSchema() {
		return this.schema;
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#getAttributeType(java.lang.String)
	 */
	@Override
	public String getAttributeType(String attributeName) {
		String result = null;
		ArrayList<IAttribute> attributes = this.schema.getAttributes();
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
		ArrayList<IAttribute> attrs = this.schema.getAttributes();
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
		logger.info("Generating elements for the stream...");
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
				logger.fine("Chunk " + k + " of profile " + i + " (key " + pChunkKey + ") generated with " + this.elements.get(pChunkKey).length + " stream elements");
				numPrElem += this.elements.get(pChunkKey).length;
			}
			nbElem += numPrElem;
			logger.fine("Profile " + i + " generated ("+ numPrElem + " stream elements)...");
			if(nextPIndex < pSize && j < tSize){
				this.setNextProfile(this.getProfiles().get(nextPIndex));
				this.setCurrentTransition(this.getTransitions().get(j));
				ArrayList<IElement[]> tElements = this.generateTransition(tickDelay);
				int m = tElements.size();
				int numTrElem = 0;
				for(int l = 0; l < m; l++){
					String tChunkKey = "T" + i + "It" + l;
					this.elements.put(tChunkKey, tElements.get(l));
					logger.fine("Chunk " + l + " of profile " + j + " (key " + tChunkKey + ") generated with " + this.elements.get(tChunkKey).length + " stream elements");
					numTrElem += this.elements.get(tChunkKey).length;
				}
				nbElem += numTrElem;
				logger.fine("Transition " + j + " generated ("+ numTrElem + " stream elements)...");
			}
			i++;
			j++;
			nextPIndex++;
		}
		logger.info(nbElem + " stream elements generated");
	}

	/* (non-Javadoc)
	 * @see core.stream.IElementStream#generateProfile(long)
	 */
	@Override
	public ArrayList<IElement[]> generateProfile(long tickDelay) {
		IStreamProfile profile = this.getCurrentProfile();
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
		int nbAttributes = this.schema.getAttributes().size();

		int rate = profile.getNbElementPerTick();
		double iterations = profile.getDuration() / tickDelay;

		for(int i = 0; i < iterations; i++){
			double timestamp = System.currentTimeMillis();

			IElement[] iter = new IElement[rate];
			for(int j = 0; j < rate; j++){
				IElement element = (IElement) new StreamElement(nbAttributes,timestamp);
				for(int k = 0; k < nbAttributes; k++){
					IAttribute attribute = this.schema.getAttributes().get(k);
					HashMap<String, Object> parameters = attribute.getParameters();
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int intValue = (Integer)this.getCurrentProfile().getNextValue(AttributeType.INT, parameters);
						element.setValue(k, intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						String textValue = (String)this.getCurrentProfile().getNextValue(AttributeType.TEXT, parameters);
						element.setValue(k, textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						String enumValue = (String)this.getCurrentProfile().getNextValue(AttributeType.ENUM, parameters);
						element.setValue(k, enumValue);
					}
				}
				iter[j] = element;
			}
			result.add(iter);
		}
		logger.fine("this profile has generated " + (iterations * rate) + " stream element(s) for " + iterations + " timetamp(s)");
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
		int nbAttributes = this.schema.getAttributes().size();

		transition.solveTransitionFunc(former.getNbElementPerTick(), next.getNbElementPerTick(), tickDelay);

		double iterations = transition.getDuration() / tickDelay;

		int nbElements = 0;
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
	
		for(int i = 0; i < iterations; i++){
			int rate = (int)transition.getIntermediateValue();
			IElement[] iter = new IElement[rate];
			double timestamp = System.currentTimeMillis();

			for(int j = 0; j < rate; j++){

				IElement element = (IElement) new StreamElement(nbAttributes, timestamp);
				for(int k = 0; k < nbAttributes; k++){
					IAttribute attribute = this.schema.getAttributes().get(k);
					String type = attribute.getType().toString();
					HashMap<String, Object> parameters = attribute.getParameters();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int intValue = (Integer)this.getCurrentProfile().getNextValue(AttributeType.INT, parameters);
						element.setValue(k, intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						String textValue = (String)this.getCurrentProfile().getNextValue(AttributeType.TEXT, parameters);
						element.setValue(k, textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						String enumValue = (String)this.getCurrentProfile().getNextValue(AttributeType.ENUM, parameters);
						element.setValue(k, enumValue);
					}
				}
				iter[j] = element;
				nbElements++;
			}
			result.add(iter);
		}
		logger.fine("this transition has generated " + nbElements + " stream element(s) for " + iterations + " timestamp(s)");
		return result;
	}
}
