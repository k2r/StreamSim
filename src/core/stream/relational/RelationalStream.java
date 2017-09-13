/**
 * 
 */
package core.stream.relational;

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

import core.config.stream.relational.RelationalStreamParser;
import core.element.relational.IRelationalElement;
import core.element.IElement;
import core.element.relational.RelationalStreamElement;
import core.model.IModel;
import core.model.relational.IRelationalModel;
import core.model.relational.RelationalModel;
import core.model.relational.attribute.IAttribute;
import core.model.relational.attribute.type.AttributeType;
import core.profile.IStreamProfile;
import core.profile.relational.IRelationalStreamProfile;
import core.stream.IStream;
import core.transition.IStreamTransition;

/**
 * @author Roland
 *
 */
public class RelationalStream implements IStream{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3157381972651298760L;
	
	private Path modelPath;
	private Path varPath;
	private IModel model;
	private ArrayList<IStreamProfile> profiles;
	private ArrayList<IStreamTransition> transitions;
	private IStreamProfile currentProfile;
	private IStreamProfile nextProfile;
	private IStreamTransition currentTransition;
	private final double refTime = System.currentTimeMillis();
	private boolean isTransition;
	public static Logger logger = Logger.getLogger("RelationalStream");
	private HashMap<String, IElement[]> elements;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public RelationalStream(String model, String variation) throws IOException {
		this.modelPath = Paths.get("models/" + model + "Model.xml");
		this.varPath = Paths.get("variations/models/" + variation + "Var.xml");
		this.isTransition = false;
		this.elements = new HashMap<>();
	}
	
	public RelationalStream(String schema, String variation, ServletContext context) throws IOException, URISyntaxException {
		this.modelPath = Paths.get(context.getRealPath("/models/" + schema + "Model.xml"));
		this.varPath = Paths.get(context.getRealPath("/variations/models/" + variation + "Var.xml"));
		this.isTransition = false;
		this.elements = new HashMap<>();
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#initializeSchema()
	 */
	@Override
	public void initializeModel() {
		try {
			RelationalStreamParser parser = new RelationalStreamParser(this.modelPath.toString());
			this.model = new RelationalModel(parser.getStreamAttributes());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getAttributes()
	 */
	@Override
	public IModel getModel() {
		return this.model;
	}


	/* (non-Javadoc)
	 * @see core.stream.IStream#initializeVariations()
	 */
	@Override
	public void initializeVariations() {
		try {
			RelationalStreamParser parser = new RelationalStreamParser(this.varPath.toString());
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
	 * @see core.stream.IStream#getProfiles()
	 */
	@Override
	public ArrayList<IStreamProfile> getProfiles() {
		return this.profiles;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getTransitions()
	 */
	@Override
	public ArrayList<IStreamTransition> getTransitions() {
		return this.transitions;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getCurrentProfile()
	 */
	@Override
	public IStreamProfile getCurrentProfile() {
		return this.currentProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#setCurrentProfile(core.profile.IStreamProfile)
	 */
	@Override
	public void setCurrentProfile(IStreamProfile currentProfile) {
		this.currentProfile = currentProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getNextProfile()
	 */
	@Override
	public IStreamProfile getNextProfile() {
		return this.nextProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#setNextProfile(core.profile.IStreamProfile)
	 */
	@Override
	public void setNextProfile(IStreamProfile nextProfile) {
		this.nextProfile = nextProfile;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getCurrentTransition()
	 */
	@Override
	public IStreamTransition getCurrentTransition() {
		return this.currentTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#setCurrentTransition(core.transition.IStreamTransition)
	 */
	@Override
	public void setCurrentTransition(IStreamTransition currentTransition) {
		this.currentTransition = currentTransition;

	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#isTransition()
	 */
	@Override
	public boolean isTransition() {
		return this.isTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#setTransition(boolean)
	 */
	@Override
	public void setTransition(boolean isTransition) {
		this.isTransition = isTransition;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#getRefTime()
	 */
	@Override
	public double getRefTime() {
		return this.refTime;
	}
	
	/* (non-Javadoc)
	 * @see core.stream.IStream#getElements()
	 */
	@Override
	public HashMap<String, IElement[]> getElements(){
		return this.elements;
	}
	
	/* (non-Javadoc)
	 * @see core.stream.IStream#generateStream(long)
	 */
	@Override
	public void generateStream(long tickDelay) {
		logger.info("Generating relationalElements for the stream...");
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
				logger.fine("Chunk " + k + " of profile " + i + " (key " + pChunkKey + ") generated with " + this.elements.get(pChunkKey).length + " stream relationalElements");
				numPrElem += this.elements.get(pChunkKey).length;
			}
			nbElem += numPrElem;
			logger.fine("Profile " + i + " generated ("+ numPrElem + " stream relationalElements)...");
			if(nextPIndex < pSize && j < tSize){
				this.setNextProfile(this.getProfiles().get(nextPIndex));
				this.setCurrentTransition(this.getTransitions().get(j));
				ArrayList<IElement[]> tElements = this.generateTransition(tickDelay);
				int m = tElements.size();
				int numTrElem = 0;
				for(int l = 0; l < m; l++){
					String tChunkKey = "T" + i + "It" + l;
					this.elements.put(tChunkKey, tElements.get(l));
					logger.fine("Chunk " + l + " of profile " + j + " (key " + tChunkKey + ") generated with " + this.elements.get(tChunkKey).length + " stream relationalElements");
					numTrElem += this.elements.get(tChunkKey).length;
				}
				nbElem += numTrElem;
				logger.fine("Transition " + j + " generated ("+ numTrElem + " stream relationalElements)...");
			}
			i++;
			j++;
			nextPIndex++;
		}
		logger.info(nbElem + " stream relationalElements generated");
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#generateProfile(long)
	 */
	@Override
	public ArrayList<IElement[]> generateProfile(long tickDelay) {
		IRelationalStreamProfile profile = (IRelationalStreamProfile) this.getCurrentProfile();
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
		int nbAttributes = ((IRelationalModel) this.model).getAttributes().size();

		int rate = profile.getNbElementPerTick();
		double iterations = profile.getDuration() / tickDelay;

		for(int i = 0; i < iterations; i++){
			Integer timestamp = (int) System.currentTimeMillis();

			IRelationalElement[] iter = new IRelationalElement[rate];
			for(int j = 0; j < rate; j++){
				IRelationalElement relationalElement = (IRelationalElement) new RelationalStreamElement(nbAttributes,timestamp);
				for(int k = 0; k < nbAttributes; k++){
					IAttribute attribute = ((IRelationalModel) this.model).getAttributes().get(k);
					HashMap<String, Object> parameters = attribute.getParameters();
					String type = attribute.getType().toString();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int intValue = (Integer)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.INT, parameters);
						relationalElement.setStreamElement(k, intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						String textValue = (String)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.TEXT, parameters);
						relationalElement.setStreamElement(k, textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						String enumValue = (String)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.ENUM, parameters);
						relationalElement.setStreamElement(k, enumValue);
					}
				}
				iter[j] = relationalElement;
			}
			result.add(iter);
		}
		logger.fine("this profile has generated " + (iterations * rate) + " stream element(s) for " + iterations + " timetamp(s)");
		return result;
	}

	/* (non-Javadoc)
	 * @see core.stream.IStream#generateTransition(long)
	 */
	@Override
	public ArrayList<IElement[]> generateTransition(long tickDelay) {
		IStreamProfile former = this.getCurrentProfile();
		IStreamProfile next = this.getNextProfile();
		IStreamTransition transition = this.getCurrentTransition();
		int nbAttributes = ((IRelationalModel) this.model).getAttributes().size();

		transition.solveTransitionFunc(former.getNbElementPerTick(), next.getNbElementPerTick(), tickDelay);

		double iterations = transition.getDuration() / tickDelay;

		int nbElements = 0;
		ArrayList<IElement[]> result = new ArrayList<IElement[]>();
	
		for(int i = 0; i < iterations; i++){
			int rate = (int)transition.getIntermediateValue();
			IElement[] iter = new IElement[rate];
			Integer timestamp = (int) System.currentTimeMillis();

			for(int j = 0; j < rate; j++){

				IRelationalElement relationalElement = (IRelationalElement) new RelationalStreamElement(nbAttributes, timestamp);
				for(int k = 0; k < nbAttributes; k++){
					IAttribute attribute = ((IRelationalModel) this.model).getAttributes().get(k);
					String type = attribute.getType().toString();
					HashMap<String, Object> parameters = attribute.getParameters();
					if(type.equalsIgnoreCase(AttributeType.INT.toString())){
						int intValue = (Integer)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.INT, parameters);
						relationalElement.setStreamElement(k, intValue);
					}
					if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
						String textValue = (String)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.TEXT, parameters);
						relationalElement.setStreamElement(k, textValue);
					}
					if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
						String enumValue = (String)((IRelationalStreamProfile) this.getCurrentProfile()).getNextValue(AttributeType.ENUM, parameters);
						relationalElement.setStreamElement(k, enumValue);
					}
				}
				iter[j] = relationalElement;
				nbElements++;
			}
			result.add(iter);
		}
		logger.fine("this transition has generated " + nbElements + " stream element(s) for " + iterations + " timestamp(s)");
		return result;
	}
}
