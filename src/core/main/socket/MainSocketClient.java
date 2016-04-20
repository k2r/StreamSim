/**
 * 
 */
package core.main.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import core.network.socket.receiver.IStreamReceiver;
import core.network.socket.receiver.SocketStreamReceiver;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class MainSocketClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = Integer.parseInt(args[1]);
		IStreamReceiver receiver = new SocketStreamReceiver(args[0], port);
		while(receiver.getMessage() != null){
			System.out.println(receiver.getMessage());
		}
		receiver.close();
	}

}
