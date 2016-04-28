/**
 * 
 */
package core.network.socket.source;

import java.io.DataOutputStream;
//import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
//import java.util.function.Consumer;
//import java.util.stream.Stream;

import core.element.IElement;

/**
 * @author Roland
 *
 */

public class ChunkSender extends RecursiveAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1200586223832198097L;
	private IElement[] chunk;
	private DataOutputStream dos;
	private int from;
	private int to;
	private int maxSeqTasks;
	private final ArrayList<String> attrNames;

	/**
	 * 
	 */
	public ChunkSender(IElement[] chunk, ArrayList<String> attrNames, DataOutputStream dos, int from, int to, int maxSeqTasks) {
		this.setChunk(chunk);
		this.dos = dos;
		this.from = from;
		this.to = to;
		this.maxSeqTasks = maxSeqTasks;
		this.attrNames = attrNames;
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
	protected void compute() {
		/*IElement[] chunk = this.getChunk();
		int maxSeqTasks = this.maxSeqTasks;
		int n = to - from;
		if(n < maxSeqTasks){
			Stream<IElement> stream = Arrays.stream(chunk);
			stream.parallel().forEach(new StreamPrinter(dos, attrNames));
		}else{
			int mid = n / 2;
			new ChunkSender(chunk, attrNames, dos, from, mid, maxSeqTasks).fork();
			new ChunkSender(chunk, attrNames, dos, mid, n, maxSeqTasks).fork();
		}*/
	}

	/*private class StreamPrinter implements Consumer<IElement>{

		@SuppressWarnings("unused")
		private DataOutputStream dos;
		private PrintWriter writer;
		private final ArrayList<String> attrNames;
		
		public StreamPrinter(DataOutputStream dos, ArrayList<String> attrNames) {
			this.dos = dos;
			this.writer = new PrintWriter(dos);
			this.attrNames = attrNames;
		}
		
		@Override
		public void accept(IElement t) {
			this.writer.println(t.toString(attrNames));
			this.writer.flush();
		}
		
	}
*/}
