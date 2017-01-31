/**
 * 
 */
package core.main.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Roland
 *
 */
public class MainMockSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer port = Integer.parseInt(args[0]);
		
		try {
			ServerSocket server = new ServerSocket(port);
			Socket client = server.accept();
			
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			int i = 0;
			while(i < 1000000){
				String item = "item " + i + "\n";
				out.writeUTF(item);
				out.flush();
				System.out.println("item " + i + " sent!");
				Thread.sleep(100);
				i++;
			}
			client.close();
			server.close();
		} catch (IOException e) {
			System.out.println("Unable to instanciate the server because " + e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Unable to sleep because " + e);
			e.printStackTrace();
		}
	}

}
