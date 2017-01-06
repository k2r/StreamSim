/**
 * 
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ListenerBean;
import core.runnable.RunnableStreamListener;


/**
 * @author Roland
 *
 */
public class Listener extends HttpServlet {

	private Thread thread;
	private RunnableStreamListener listener;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1473037731466545577L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/Listener.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ListenerBean bean = (ListenerBean) req.getSession().getAttribute("listener");
		
		String listen = (String) req.getParameter("listen");
		if(listen != null){
			String host = (String) req.getParameter("host");
			Integer port = Integer.parseInt((String) req.getParameter("port"));
			String type = (String) req.getParameter("type");
			String resource = (String) req.getParameter("resource");
			Integer nbItems = Integer.parseInt((String) req.getParameter("nbItems"));

			bean.setHost(host);
			bean.setNbItems(nbItems);
			bean.setPort(port);
			bean.setResource(resource);
			bean.setType(type);
		
			if(this.thread == null){
				if(this.listener == null){
					this.listener = new RunnableStreamListener(bean.getHost(), bean.getPort(), bean.getResource(), bean.getType(), bean.getNbItems());
				}
				this.thread = new Thread(this.listener);
				this.thread.start();

				String startMessage = "Listening updates on host " + bean.getHost() + " port " + bean.getPort() + " for resource " + bean.getResource() + "...";
				req.setAttribute("start", startMessage);
			}else{
				if(this.thread.isAlive()){
					String stopMessage = "";
					try {
						this.listener.stopListener();
						this.thread.join(1000);

						stopMessage = "Stopped listening updates on host " + bean.getHost() + " port " + bean.getPort() + " for resource " + bean.getResource() + ".";
					} catch (InterruptedException e) {
						stopMessage = "The listener has failed stopping itself because of exception " + e;
					}
					req.setAttribute("stop", stopMessage);

				}
				this.listener = new RunnableStreamListener(bean.getHost(), bean.getPort(), bean.getResource(), bean.getType(), bean.getNbItems());
				this.thread = new Thread(this.listener);
				this.thread.start();
				String setMessage = "Restarting to listen for updates on host " + bean.getHost() + " port " + bean.getPort() + " for resource " + bean.getResource() + "...";
				req.setAttribute("set", setMessage);
			}
			bean.setItems(this.listener.getInputs());
		}
		
		String stopListener = (String) req.getParameter("stopListen");
		
		if(stopListener != null){
			if(this.thread.isAlive()){
				String stopMessage = "";
				try {
					this.listener.stopListener();
					this.thread.join(1000);

					stopMessage = "Stopped listening updates on host " + bean.getHost() + " port " + bean.getPort() + " for resource " + bean.getResource() + ".";
				} catch (InterruptedException e) {
					stopMessage = "The listener has failed stopping itself because of exception " + e;
				}
				req.setAttribute("stop", stopMessage);
			}
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/Listener.jsp").forward(req, resp);
	}
}
