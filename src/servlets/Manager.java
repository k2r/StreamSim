/**
 * 
 */
package servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import beans.ListenerBean;
import core.stream.IElementStream;

/**
 * @author Roland
 *
 */
public class Manager extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2432143607394307618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String generate = (String) req.getParameter("generate");
		String listen = (String) req.getParameter("listen");
		
		String page = "/Index.jsp";
		
		if(generate != null){
			ElementStreamBean stream = (ElementStreamBean) req.getSession().getAttribute("stream");
			if(stream != null){
				IElementStream eStream = stream.getStream();
				if(eStream != null){
					eStream.getSource().releaseRegistry();
				}
			}
			stream = new ElementStreamBean();
			req.getSession().setAttribute("stream", stream);
			
			ArrayList<String> schemas = Manager.getStreamList(this.getServletContext().getRealPath("/schemas"));
			req.setAttribute("schemas", schemas);
 			page = "/Generator.jsp";
		}
		
		if(listen != null){
			ListenerBean listener = (ListenerBean) req.getSession().getAttribute("listener");
			listener = new ListenerBean();
			req.getSession().setAttribute("listener", listener);
			page = "/Listener.jsp";
		}
		this.getServletContext().getRequestDispatcher(page).forward(req, resp);
 	}
	
	public static ArrayList<String> getStreamList(String schemaPath){
		ArrayList<String> result = new ArrayList<>();
		File schemaFolder = new File(schemaPath);
		File[] schemas = schemaFolder.listFiles();
		if(schemas != null){
			for(File schema : schemas){
				String schemaName = schema.getName().split("Schema")[0];
				result.add(schemaName);
			}
		}
		return result;
	}
}
