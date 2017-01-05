/**
 * 
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import core.runnable.RunnableStreamEmission;
import core.stream.IElementStream;

/**
 * @author Roland
 *
 */
public class LiveControl extends HttpServlet {

	ServletContext context;
	private Thread thread;
	private RunnableStreamEmission emission;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7985485027974923235L;

	/**
	 * 
	 */
	public LiveControl() {
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		this.context = config.getServletContext();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/LiveControl.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*String load = (String) req.getParameter("load");

		if(load != null){
			String streamName = (String) req.getParameter("name");
			Integer port = Integer.parseInt((String) req.getParameter("port"));
			String variation = "no";
			ElementStreamBean bean = (ElementStreamBean) req.getSession().getAttribute("stream");
			ElementStream stream;
			try {
				stream = new ElementStream(port, streamName, variation, this.context);
				stream.initializeSchema();
				stream.initializeVariations();

				ArrayList<String> attributes = stream.getAttributeNames();
				ArrayList<String> attrTypes = new ArrayList<>();
				Integer nbAttrs = attributes.size();
				for(int i = 0; i < nbAttrs; i++){
					attrTypes.add(stream.getAttributeType(attributes.get(i)));
				}

				HashMap<String, Double> variations = new HashMap<>();
				ArrayList<IStreamProfile> profiles = stream.getProfiles();
				int nbProfiles = profiles.size();

				ArrayList<IStreamTransition> transitions = stream.getTransitions();
				int nbTransitions = transitions.size();

				for(int i = 0; i < nbProfiles; i++){
					String profile = "profile" + i;
					variations.put(profile, profiles.get(i).getDuration());
				}
				for(int j = 0; j < nbTransitions; j++){
					String transition = "transition" + j;
					variations.put(transition, transitions.get(j).getDuration());
				}

				bean.setStream(stream);
				bean.setName(streamName);
				bean.setPort(port);
				bean.setNbAttrs(nbAttrs);
				bean.setVariation(variation);
				bean.setAttrNames(attributes);
				bean.setAttrTypes(attrTypes);
				bean.setVariations(variations);

				this.getServletContext().getRequestDispatcher("/WEB-INF/LiveControl.jsp").forward(req, resp);

			} catch (URISyntaxException e) {
				PrintWriter out = resp.getWriter();
				out.println("Unable to load the stream schema/variation because of " + e);
				out.println("Please try again with an existing stream");
			}
		}*/

		String generate = (String) req.getParameter("generate");
		if(generate != null){
			ElementStreamBean bean = (ElementStreamBean) req.getSession().getAttribute("stream");
			IElementStream stream = bean.getStream();

			Integer rate = Integer.parseInt((String) req.getParameter("rate"));
			stream.getCurrentProfile().setNbElementPerTick(rate);
			if(this.thread == null){
				if(this.emission == null){
					this.emission = new RunnableStreamEmission(req, bean);
				}
				this.thread = new Thread(this.emission);
				this.thread.start();

				String startMessage = "Emission of the stream " + bean.getName() + " on port " + bean.getPort() + " with variation " + bean.getVariation() + "...";
				req.setAttribute("start", startMessage);
			}else{
				if(this.thread.isAlive()){
					String stopMessage = "";
					try {
						String debugMessage = "Thread found and active";
						req.setAttribute("debug", debugMessage);
						this.emission.stopEmission();

						this.thread.join(1000);

						stopMessage = "The emission of the stream " + bean.getName() + " have been stopped properly on port " + bean.getPort() + ".";
					} catch (InterruptedException e) {
						stopMessage = "The emission of the stream " + bean.getName() + " have failed because of exception " + e;
					}
					req.setAttribute("stop", stopMessage);

				}
				this.emission = new RunnableStreamEmission(req, bean);
				this.thread = new Thread(this.emission);
				this.thread.start();
				String setMessage = "Restarting the emission of the stream " + bean.getName() + " on port " + bean.getPort() + " with new rate (" + stream.getCurrentProfile().getNbElementPerTick() + " items/s)";
				req.setAttribute("set", setMessage);
			}
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/LiveControl.jsp").forward(req, resp);
	}
}