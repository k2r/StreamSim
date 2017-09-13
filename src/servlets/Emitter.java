/**
 * 
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import core.runnable.RunnableStreamEmission;
/**
 * @author Roland
 *
 */
public class Emitter extends HttpServlet {
	
	private Thread thread;
	private RunnableStreamEmission emission;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4515540561791221695L;

	/**
	 * 
	 */
	public Emitter() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Emitter.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ElementStreamBean bean = (ElementStreamBean) req.getSession().getAttribute("stream");
		String command = (String) req.getParameter("command");
		
		String generate = (String) req.getParameter("generate");
		if(generate != null){
			this.emission = new RunnableStreamEmission(req, bean, command);	
			
			if(command.equalsIgnoreCase("PLAY") || command.equalsIgnoreCase("REPLAY")){
				this.emission.startEmission();
				this.thread = new Thread(this.emission);
				this.thread.start();
				req.setAttribute("playing", true);
			}

			String startMessage = this.emission.getStateMsg();
			req.setAttribute("start", startMessage);
		}
		
		String stop = (String) req.getParameter("stop");
		String restart = (String) req.getParameter("restart");
		
		if(stop != null || restart != null){
			if(this.thread != null && this.emission != null){
				String stopMessage = "";
				try {
					this.emission.stopEmission();
					this.thread.join(1000);
					stopMessage = "The emission of the stream " + bean.getName() + " have been stopped properly.";
				} catch (InterruptedException e) {
					stopMessage = "The emission of the stream " + bean.getName() + " have failed because of exception " + e;
				}
				req.setAttribute("stop", stopMessage);
			}
		}
		
		if(restart != null){
			this.emission.startEmission();
			if(!this.thread.isAlive()){
				this.thread = new Thread(this.emission);
				this.thread.start();
				String restartMessage = "Restarting the emission of the stream " + bean.getName() + " on port.";
				req.setAttribute("restart", restartMessage);
			}
		}	
		
		this.getServletContext().getRequestDispatcher("/Emitter.jsp").forward(req, resp);
	}
}
