/**
 * 
 */
package core.network.socket.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of a stream client receiving tuples through a socket
 */
public class SocketStreamReceiver implements IStreamReceiver {
	
	private final Socket socket;
	private final BufferedReader input;
	
	public SocketStreamReceiver(String hostname,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(hostname, port);
		if(this.socket.isConnected()){
			System.out.println("the client is connected at the port " + port);
		}
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/* (non-Javadoc)
	 * @see core.network.socket.IStreamReceiver#getMessage()
	 */
	@Override
	public String getMessage(){
		try {
			String line = this.input.readLine();
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return "no message can be retrieved on port " + this.socket.getLocalPort();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see core.network.socket.IStreamReceiver#close()
	 */
	@Override
	public void close() throws IOException{
		this.socket.close();
	}
}
