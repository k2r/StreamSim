package core.network.socket.consumer;

import java.io.IOException;

public interface IStreamReceiver {

	/**
	 * 
	 * @return the message incoming through the port of the current client
	 */
	public abstract String getMessage();

	/**
	 * Close the current client
	 * @throws IOException
	 */
	public abstract void close() throws IOException;

}