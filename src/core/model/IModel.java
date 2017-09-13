/**
 * 
 */
package core.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Roland
 * SubInterfaces should provide methods to access each component of the model. 
 */
public interface IModel extends Serializable{

	/**
	 * 
	 * @return the flat model describing each stream element. 
	 */
	public HashMap<Object, Object> getModel();
}
