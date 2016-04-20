package core.main.rmi;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import core.element.IElement;
import core.network.rmi.source.IRMIStreamSource;

/**
 * @author Roland KOTTO KOMBI
 *
 */
public class MainRMIClient {

	private MainRMIClient() {}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		String host = (args.length < 1) ? null : args[0];
		int port = (args[1] == null) ? 0 : Integer.parseInt(args[1]);
		
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            while(registry != null){
            	long start = System.currentTimeMillis();
				IRMIStreamSource stub = (IRMIStreamSource) registry.lookup("tuples");
				IElement[] istream = stub.getInputStream();
				ArrayList<String> attrNames = stub.getAttrNames();
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
			System.out.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
