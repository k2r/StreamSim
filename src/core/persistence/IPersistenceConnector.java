package core.persistence;

import java.io.Serializable;
import java.util.HashMap;

import core.element.IElement;
import core.model.IModel;

public interface IPersistenceConnector extends Serializable{

	/**
	 * This method persists main parameters associated to a stream to record
	 * @param streamName the name of the stream to record
	 * @param variation the variation model to apply on the stream
	 * @param frequency the emission frequency of each shard 
	 */
	public void persistParameters(String streamName, String variation, Long frequency);
	
	/**
	 * This method persists stream elements with associated timestamps in the database
	 */
	public void persistStream(String streamName, String variation, IModel model, HashMap<String, IElement[]> elements);
	
}
