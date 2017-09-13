/**
 * 
 */
package core.network.socket.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of a stream client receiving tuples through a socket
 */
public class SocketStreamReceiver implements IStreamReceiver {
	
	private final Socket socket;
	private final BufferedReader input;
	public static Logger logger = Logger.getLogger("SocketStreamReceiver");
	
	public SocketStreamReceiver(String hostname,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(hostname, port);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
	}
	
	/* (non-Javadoc)
	 * @see core.network.socket.IStreamReceiver#getMessage()
	 */
	@Override
	public String getMessage(){
		String line = null;
		try {
			line = this.input.readLine();
		} catch (IOException e) {
			logger.severe("No message can be retrieved on port " + this.socket.getLocalPort() + " because " + e);
		}
		return line;
	}
	
	/* (non-Javadoc)
	 * @see core.network.socket.IStreamReceiver#close()
	 */
	@Override
	public void close() throws IOException{
		this.socket.close();
	}
	
	public Integer getPort(){
		return this.socket.getPort();
	}
	
	public String getHost(){
		return this.socket.getInetAddress().getHostName();
	}
}
