/**
 * 
 */
package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ElementStreamBean;
import beans.LiveControlBean;
import core.runnable.RunnableStreamEmission;
import core.stream.IElementStream;

/**
 * @author Roland
 *
 */
public class LiveControl extends HttpServlet {

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/LiveControl.jsp").forward(req, resp);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer rate = Integer.parseInt((String) req.getParameter("rate"));
		String generate = (String) req.getParameter("generate");
		if(generate != null){
			ElementStreamBean streamBean = (ElementStreamBean) req.getSession().getAttribute("stream");
			IElementStream stream = streamBean.getStream();
	
			stream.getCurrentProfile().setNbElementPerTick(rate);
			if(this.thread == null){
				if(this.emission == null){
					this.emission = new RunnableStreamEmission(req, streamBean);
				}
				this.thread = new Thread(this.emission);
				this.thread.start();

				String startMessage = "Emission of the stream " + streamBean.getName() + " on port " + streamBean.getPort() + " with variation " + streamBean.getVariation() + "...";
				req.setAttribute("start", startMessage);
			}else{
				if(this.thread.isAlive()){
					String stopMessage = "";
					try {
						this.emission.stopEmission();

						this.thread.join(1000);

						stopMessage = "The emission of the stream " + streamBean.getName() + " have been stopped properly on port " + streamBean.getPort() + ".";
					} catch (InterruptedException e) {
						stopMessage = "The emission of the stream " + streamBean.getName() + " have failed because of exception " + e;
					}
					req.setAttribute("stop", stopMessage);

				}
				this.emission = new RunnableStreamEmission(req, streamBean);
				this.thread = new Thread(this.emission);
				this.thread.start();
				String setMessage = "Restarting the emission of the stream " + streamBean.getName() + " on port " + streamBean.getPort() + " with new rate (" + stream.getCurrentProfile().getNbElementPerTick() + " items/s)";
				req.setAttribute("set", setMessage);
			}
			
			LiveControlBean liveBean = (LiveControlBean) req.getSession().getAttribute("live");
			if(liveBean != null){
				HashMap<Long, Integer> rates = liveBean.getRates();
				if(rates == null){
					rates = new HashMap<>();
				}
				rates.put(System.currentTimeMillis() / 1000, rate);
				liveBean.setRates(rates);
				req.getSession().setAttribute("live", liveBean);
				//TODO In a view (something like VariationHistoric.jsp, use the liveBean to plot variations played by the user. In addition set the smaller timestamp to 0 and align the others.
			}
		}
		this.getServletContext().getRequestDispatcher("/LiveControl.jsp").forward(req, resp);
	}
}