/**
 * 
 */
package servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import beans.LiveControlBean;
import core.model.relational.RelationalModel;
import core.model.relational.attribute.IAttribute;
import core.network.MessagingType;
import core.network.IProducer;
import core.network.kafka.producer.KafkaStreamProducer;
import core.network.rmi.producer.RMIStreamProducer;
import core.profile.IStreamProfile;
import core.stream.IStream;
import core.stream.relational.RelationalStream;
import core.transition.IStreamTransition;
import servlets.utils.Utils;

/**
 * @author Roland
 *
 */
public class Generator extends HttpServlet {

	ServletContext context;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8800848739710775121L;

	/**
	 * 
	 */
	public Generator() {
	}

	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		this.context = config.getServletContext();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Generator.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String refresh = (String) req.getParameter("refreshGenerator");
		
		if(refresh != null){
			ElementStreamBean stream = (ElementStreamBean) req.getSession().getAttribute("stream");
			if(stream != null){
				IStream eStream = stream.getStream();
				if(eStream != null){
					stream.getProducer().disconnect();
				}
			}
			stream = new ElementStreamBean();
			req.getSession().setAttribute("stream", stream);
			
			LiveControlBean live = (LiveControlBean)req.getSession().getAttribute("live");
			live = new LiveControlBean();
			req.getSession().setAttribute("live", live);
			
			ArrayList<String> models = Utils.getStreamList(this.getServletContext().getRealPath("/models"));
			req.setAttribute("schemas", models);
			this.getServletContext().getRequestDispatcher("/Generator.jsp").forward(req, resp);
		}
		
		String load = (String) req.getParameter("load");
		
		if(load != null){
			String streamName = (String) req.getParameter("name");
			String variation = (String) req.getParameter("variation");
			//TODO add a field in the generator page to choose a network interface
			String network = (String) req.getParameter("network");
			String host = req.getParameter("host");
			int port = Integer.parseInt(req.getParameter("port")); 
			ElementStreamBean bean = (ElementStreamBean) req.getSession().getAttribute("stream");
			
			/*To support a different stream model, this part should be refactored*/
			RelationalStream stream;
			try {
				stream = new RelationalStream(streamName, variation, this.context);
				IProducer producer = null;
				if(network.equalsIgnoreCase(MessagingType.RMI.toString())){
					producer = new RMIStreamProducer(host, port);
				}
				if(network.equalsIgnoreCase(MessagingType.KFK.toString())){
					producer = new KafkaStreamProducer(host, port);
				}
				stream.initializeModel();
				stream.initializeVariations();

				ArrayList<IAttribute> attributes = ((RelationalModel)stream.getModel()).getAttributes();
				ArrayList<String> attrNames = new ArrayList<>();
				ArrayList<String> attrTypes = new ArrayList<>();
				Integer nbAttrs = attributes.size();
				for(int i = 0; i < nbAttrs; i++){
					attrNames.add(attributes.get(i).getName());
					attrTypes.add(attributes.get(i).getType().toString());
				}

				ArrayList<IStreamProfile> profiles = stream.getProfiles();
				int nbProfiles = profiles.size();

				ArrayList<IStreamTransition> transitions = stream.getTransitions();
				int nbTransitions = transitions.size();
				
				ArrayList<Double> varTimestamps = new ArrayList<>();
				ArrayList<Double> varRates = new ArrayList<>();
				
				Double timestamp = 0.0;
				int indexP = 0;
				int indexT = 0;
				
				while(indexP < nbProfiles){
					System.out.println("Generating profile " + indexP + " of " + nbProfiles);
					IStreamProfile profile = profiles.get(indexP);
					Double durationP = profile.getDuration();
					Double endTimestamp = timestamp + durationP;
					Double rateP = ((Integer)profile.getNbElementPerTick()).doubleValue();
					
					for(double i = timestamp; i < endTimestamp; i++){
						varTimestamps.add(i);
						varRates.add(rateP);
					}
					timestamp = endTimestamp;
					indexP++;
					if(indexT < nbTransitions && indexP < nbProfiles){
						IStreamProfile nextProfile = profiles.get(indexP);
						Double nextRate = ((Integer) nextProfile.getNbElementPerTick()).doubleValue();
						IStreamTransition transition = transitions.get(indexT);
						Double durationT = transition.getDuration();
						transition.solveTransitionFunc(rateP, nextRate, 1);
						endTimestamp += durationT; 
						for(double j = timestamp; j < endTimestamp; j++){
							varTimestamps.add(j);
							varRates.add(transition.getIntermediateValue());
						}
						timestamp = endTimestamp;
						indexT++;
					}
				}

				bean.setStream(stream);
				bean.setName(streamName);
				bean.setProducer(producer);
				bean.setNbAttrs(nbAttrs);
				bean.setVariation(variation);
				bean.setAttrNames(attrNames);
				bean.setAttrTypes(attrTypes);
				bean.setVarTimestamps(varTimestamps);
				bean.setVarRates(varRates);

				String live = (String) req.getParameter("live");
				if(live != null){
					this.getServletContext().getRequestDispatcher("/LiveControl.jsp").forward(req, resp);
				}else{
					this.getServletContext().getRequestDispatcher("/Generator.jsp").forward(req, resp);
				}
			} catch (URISyntaxException e) {
				String errorMessage = "Unable to load the stream model/variation because of " + e + "\n";
				errorMessage += "Please try again with an existing stream";
				req.setAttribute("error", errorMessage);
				this.getServletContext().getRequestDispatcher("/Generator.jsp");
			}
		}
	}
}