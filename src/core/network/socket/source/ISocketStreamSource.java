package core.network.socket.source;

import java.util.ArrayList;

import core.element.IElement;

public interface ISocketStreamSource{

	/**
	 * 
	 * @return the port of the source
	 */
	public abstract int getPort();
	
	public abstract void emit(int maxThreads, int maxSeqEmit);
	
	public abstract void buffer(IElement[] chunk, ArrayList<String> attrNames);

}