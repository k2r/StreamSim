/**
 * 
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import beans.CreatorBean;
import beans.StreamAttribute;
import beans.TextPattern;
import servlets.utils.Utils;

/**
 * @author Roland
 *
 */
public class Creator extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8847296385289740692L;
	
	public Creator() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Creator.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CreatorBean bean = (CreatorBean) req.getSession().getAttribute("creator"); 
		
		String refresh = req.getParameter("refreshCreator");
		if(refresh != null){
			bean = new CreatorBean();
			req.getSession().setAttribute("creator", bean);
		}
		
		String define = req.getParameter("define");
		if(define != null){
			String name = req.getParameter("streamname");
			Integer nbAttrs = Integer.parseInt(req.getParameter("nbattrs"));
			bean.setStreamName(name);
			bean.setNbAttributes(nbAttrs);
			req.getSession().setAttribute("creator", bean);
		}
		
		String save = req.getParameter("save");
		if(save != null){
			/*Recover all informations about the stream*/
			ArrayList<StreamAttribute> attributes = new ArrayList<>();
			Integer nbAttrs = bean.getNbAttributes();
			for(int i = 1; i <= nbAttrs; i++){
				StreamAttribute attribute = new StreamAttribute();
				HashMap<String, Object> parameters = new HashMap<>();
				
				String attrName = req.getParameter("attrName" + i);
				String attrType = req.getParameter("attrType" + i);
				if(attrType.equals("enumerate")){
					String values = req.getParameter("values" + i);
					parameters.put("values", values.split(";"));
				}
				if(attrType.equals("integer")){
					Integer min = Integer.parseInt(req.getParameter("min" + i));
					Integer max = Integer.parseInt(req.getParameter("max" + i));
					parameters.put("min", min);
					parameters.put("max", max);
				}
				if(attrType.equals("text")){
					TextPattern pattern = new TextPattern();
					pattern.setType(req.getParameter("pattern" + i));
					pattern.setLength(Integer.parseInt(req.getParameter("length" + i)));
					parameters.put("pattern", pattern);
				}
				attribute.setName(attrName);
				attribute.setType(attrType);
				attribute.setParameters(parameters);
				attributes.add(attribute);
			}
			bean.setAttributes(attributes);
			/*Turn the bean into a XML document*/
			try {
				Utils.saveStreamAsXml(bean, this.getServletContext());
				String success = "The stream schema has been correctly saved !";
				req.setAttribute("success", success);
			} catch (ParserConfigurationException e) {
				String error = "A parsing exception happened while saving the stream because " + e;
				req.setAttribute("error", error);
			} catch (TransformerException e) {
				String error = "An exception happened while saving the xml file because " + e;
				req.setAttribute("error", error);
			}
		}
		this.getServletContext().getRequestDispatcher("/Creator.jsp").forward(req, resp);
	}
}
