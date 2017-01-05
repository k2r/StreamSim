/**
 * 
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import core.profile.IStreamProfile;
import core.stream.ElementStream;
import core.transition.IStreamTransition;

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
		this.getServletContext().getRequestDispatcher("/WEB-INF/Generator.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String load = (String) req.getParameter("load");
		
		if(load != null){
			String streamName = (String) req.getParameter("name");
			Integer port = Integer.parseInt((String) req.getParameter("port"));
			String variation = (String) req.getParameter("variation");

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

				this.getServletContext().getRequestDispatcher("/WEB-INF/Generator.jsp").forward(req, resp);
				
			} catch (URISyntaxException e) {
				PrintWriter out = resp.getWriter();
				out.println("Unable to load the stream schema/variation because of " + e);
				out.println("Please try again with an existing stream");
			}
		}
	}
}
