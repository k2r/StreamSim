/**
 * 
 */
package core.profile;

import java.util.ArrayList;
import java.util.Random;

import core.attribute.type.AttributeType;
import core.profile.type.ProfileType;

/**
 * @author Roland KOTTO KOMBI
 *	Class implementing a standard profile which maintain a fixed number of uniform tuples which takes values in a balanced value space
 */
public class StandardProfile implements IStreamProfile {

	private ProfileType type;
	private double duration;
	private int nbElementPerTick;
	
	public StandardProfile(double duration, int nbElementPerTick) {
		this.type = ProfileType.STANDARD;
		this.duration = duration;
		this.nbElementPerTick = nbElementPerTick;
	}
	
	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getType()
	 */
	@Override
	public ProfileType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getDuration()
	 */
	@Override
	public double getDuration() {
		return this.duration;
	}

	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getNbElementPerTick()
	 */
	@Override
	public int getNbElementPerTick() {
		return this.nbElementPerTick;
	}
	
	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getNextValue()
	 */
	@Override
	public Object getNextValue(int min, int max, ArrayList<String> vals, String type) {
		Object result = null;
		Random rand = new Random();
		int range = max - min;
		if(type.toString().equalsIgnoreCase(AttributeType.INT.toString())){
			result = min + rand.nextInt(range);
		}
		if(type.equalsIgnoreCase(AttributeType.TEXT.toString())){
			int avg = (min + max) / 2;
			result = "";
			for(int l = 0; l < avg; l++){
				result += "a";
			}
		}
		if(type.equalsIgnoreCase(AttributeType.ENUM.toString())){
			int n = vals.size();
			int next = rand.nextInt(n);
			result = vals.get(next);
		}
		return result;
	}
}
