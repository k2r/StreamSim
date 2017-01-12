/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Roland
 *
 */
public class LiveControlBean implements Serializable{

	private HashMap<Long, Integer> rates;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3151740014866337541L;

	public LiveControlBean() {
	}

	/**
	 * @return the rates
	 */
	public HashMap<Long, Integer> getRates() {
		return rates;
	}

	/**
	 * @param rates the rates to set
	 */
	public void setRates(HashMap<Long, Integer> rates) {
		this.rates = rates;
	}
	
	
}
