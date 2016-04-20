/**
 * 
 */
package core.network.socket.source;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import core.element.IElement;

/**
 * @author Roland KOTTO KOMBI
 * Implementation of a stream source sending messages through a socket
 */
public class SocketStreamSource implements ISocketStreamSource {
	
	private final int port;
	private final ServerSocket server;
	private Socket socket;
	private DataOutputStream dos;
	private IElement[] chunk;
	private ArrayList<String> attrNames;
	
	public SocketStreamSource(int port) throws IOException {
		this.port = port;
		this.server = new ServerSocket(port);
		System.out.println("Stream source ready to send data on port " + this.port);
	}
	
	/**
	 * Accept a client 
	 * @throws IOException
	 */
	public void accept() throws IOException{
		this.socket = this.server.accept();
		BufferedOutputStream buffer = new BufferedOutputStream(socket.getOutputStream());
		this.dos = new DataOutputStream(buffer);
	}
	
	/**
	 * 
	 * @return the port of the source
	 */
	public int getPort(){
		return this.port;
	}
	
	/**
	 * 
	 * @return the writer writer of the current stream source
	 */
	public DataOutputStream getDos(){
		return this.dos;
	}
	
	/**
	 * @return the chunk
	 */
	public IElement[] getChunk() {
		return chunk;
	}

	/**
	 * @param chunk the chunk to set
	 */
	public void setChunk(IElement[] chunk) {
		this.chunk = chunk;
	}

	@Override
	public void buffer(IElement[] chunk, ArrayList<String> attrNames) {
		this.setChunk(chunk);
		this.attrNames = attrNames;
	}

	/**
	 * Write a given message on the current stream source output 
	 * @param message the message to send through the current stream source
	 */
	@Override
	public void emit(int maxThreads, int maxSeqEmit) {
		try {
			ForkJoinPool pool = new ForkJoinPool(maxThreads);
			pool.invoke(new ChunkSender(chunk, attrNames, this.getDos(), 0, this.getChunk().length, maxSeqEmit));
			pool.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Stopped...");
		}
	}
	
	/**
	 * Close the current stream source
	 * @throws IOException
	 */
	public void close() throws IOException{
		this.socket.close();
		this.server.close();
	}
}
