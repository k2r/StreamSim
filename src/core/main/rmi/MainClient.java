package core.main.rmi;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import core.element.IElement;
import core.network.rmi.source.IRMIStreamSource;
import core.network.socket.receiver.SocketStreamReceiver;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class MainClient {

	private MainClient() {}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		String host = (args.length < 1) ? null : args[0];
		int port = (args[1] == null) ? 0 : Integer.parseInt(args[1]);
		String type = (args[2] == null) ? "streamsim" : args[2];

		if(type.equalsIgnoreCase("streamsim")){

			try {
				Registry registry = LocateRegistry.getRegistry(host, port);
				while(registry != null){
					long start = System.currentTimeMillis();
					IRMIStreamSource stub = (IRMIStreamSource) registry.lookup("tuples");
					IElement[] istream = stub.getInputStream();
					ArrayList<String> attrNames = stub.getAttrNames();
					registry.unbind("tuples");
					int n = istream.length;
					for(int i = 0; i < n; i++){
						System.out.println(istream[i].toString(attrNames));
					}
					long end = System.currentTimeMillis();
					long remaining = 1000 - (end - start); //the time remaining after the complete print
					if(remaining > 0){
						Thread.sleep(remaining);
					}
				}
			}catch(Exception e){
				System.out.println("Waiting for stream items on host " + host + " port " + port + "...");
				Thread.sleep(1000);
				main(args);
			}
		}

		if(type.equalsIgnoreCase("raw")){
			SocketStreamReceiver receiver = new SocketStreamReceiver(host, port);
			while(true){
				String tuple = receiver.getMessage();
				if(tuple == null){
					try{
						Thread.sleep(50);
						tuple = receiver.getMessage();
					}catch(InterruptedException e){
						System.out.println("Waiting for stream items on host " + host + " port " + port + "...");
						Thread.sleep(200);
					}
				}else{
					System.out.println(tuple);
				}
			}
		}
	}
}
