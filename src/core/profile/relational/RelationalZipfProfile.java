/**
 * 
 */
package core.profile.relational;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.math3.distribution.ZipfDistribution;

import core.model.relational.attribute.EnumAttribute;
import core.model.relational.attribute.IntAttribute;
import core.model.relational.attribute.TextAttribute;
import core.model.relational.attribute.type.AttributeType;
import core.profile.type.ProfileType;

/**
 * @author Roland
 *
 */
public class RelationalZipfProfile implements IRelationalStreamProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3445799850625917720L;
	private ProfileType type;
	private double duration;
	private int nbElementPerTick;
	private double skew;
	
	private ArrayList<Character> lowerCaseLetters;
	private ArrayList<Character> upperCaseLetters;
	private ArrayList<Character> digits;
	private ArrayList<Character> lowerAndUpperLetters;
	private ArrayList<Character> lowerLettersAndDigits;
	private ArrayList<Character> upperLettersAndDigits;
	
	public RelationalZipfProfile(double duration, int nbElementPerTick) {
		this.type = ProfileType.ZIPF;
		this.duration = duration;
		this.nbElementPerTick = nbElementPerTick;
		this.setSkew(1.0);//default value for the skew if nothing is precised
		
		this.lowerCaseLetters = new ArrayList<>();
		for(char c = 'a'; c <= 'z'; c++){
			this.lowerCaseLetters.add(c);
		}
		
		this.upperCaseLetters = new ArrayList<>();
		for(char c = 'A'; c <= 'Z'; c++){
			this.upperCaseLetters.add(c);
		}
		
		this.digits = new ArrayList<>();
		for(char c = '0'; c <= '9'; c++){
			this.digits.add(c);
		}
		
		this.lowerAndUpperLetters = new ArrayList<>();
		this.lowerAndUpperLetters.addAll(lowerCaseLetters);
		this.lowerAndUpperLetters.addAll(upperCaseLetters);
		
		this.lowerLettersAndDigits = new ArrayList<>();
		this.lowerLettersAndDigits.addAll(lowerCaseLetters);
		this.lowerLettersAndDigits.addAll(digits);
		
		this.upperLettersAndDigits = new ArrayList<>();
		this.upperLettersAndDigits.addAll(upperCaseLetters);
		this.upperLettersAndDigits.addAll(digits);
	}
	
	public RelationalZipfProfile(double duration, int nbElementPerTick, double skew) {
		this(duration, nbElementPerTick);
		this.setSkew(skew);
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
	 * @see core.profile.IStreamProfile#setNbElementPerTick(int)
	 */
	@Override
	public void setNbElementPerTick(int rate) {
		this.nbElementPerTick = rate;
	}

	/**
	 * @return the skew
	 */
	public double getSkew() {
		return this.skew;
	}

	/**
	 * @param skew the skew to set
	 */
	public void setSkew(double skew) {
		this.skew = skew;
	}

	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getNextValue(core.attribute.type.AttributeType, java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getNextValue(AttributeType attributeType, HashMap<String, Object> parameters) {
		Object result = null;
		if(attributeType.toString().equalsIgnoreCase(AttributeType.INT.toString())){
			Integer min = (Integer) parameters.get(IntAttribute.MIN_PARAM);
			Integer max = (Integer) parameters.get(IntAttribute.MAX_PARAM);
			int range = max - min;
			ZipfDistribution zipf = new ZipfDistribution(range, this.getSkew());
			result = min + zipf.sample();
		}
		if(attributeType.toString().equalsIgnoreCase(AttributeType.TEXT.toString())){
			HashMap<Integer, HashMap<String, Integer>> patterns = (HashMap<Integer, HashMap<String, Integer>>) parameters.get(TextAttribute.PATTERNS);
			ArrayList<Integer> patternIndexes = new ArrayList<>();
			for(Integer index : patterns.keySet()){
				patternIndexes.add(index);
			}
			Collections.sort(patternIndexes);
			int n = patternIndexes.size();
			String nextString = "";
			for(int i = 0; i < n; i++){
				int index = patternIndexes.get(i);
				HashMap<String, Integer> pattern = patterns.get(index);
				for(String regExp : pattern.keySet()){
					int nbChars = pattern.get(regExp);
					if(regExp.equals("[a-z]")){
						ZipfDistribution zipf = new ZipfDistribution(this.lowerCaseLetters.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerCaseLetters.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
					if(regExp.equals("[A-Z]")){
						ZipfDistribution zipf = new ZipfDistribution(this.upperCaseLetters.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.upperCaseLetters.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
					if(regExp.equals("[0-9]")){
						ZipfDistribution zipf = new ZipfDistribution(this.digits.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.digits.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
					if(regExp.equals("[a-Z]")){
						ZipfDistribution zipf = new ZipfDistribution(this.lowerAndUpperLetters.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerAndUpperLetters.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
					if(regExp.equals("[a-9]")){
						ZipfDistribution zipf = new ZipfDistribution(this.lowerLettersAndDigits.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerLettersAndDigits.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
					if(regExp.equals("[A-9]")){
						ZipfDistribution zipf = new ZipfDistribution(this.upperLettersAndDigits.size(), this.getSkew());
						for(int j = 0; j < nbChars; j++){
							char c = this.upperLettersAndDigits.get(zipf.sample() - 1);// to set it to the interval [0;n[ instead of [1;n]
							nextString += c;
						}
					}
				}
			}
			result = nextString;
		}
		if(attributeType.toString().equalsIgnoreCase(AttributeType.ENUM.toString())){
			ArrayList<String> vals = (ArrayList<String>) parameters.get(EnumAttribute.VALUES_PARAM);
			int n = vals.size();
			ZipfDistribution zipf = new ZipfDistribution(n, this.getSkew());
			int next = zipf.sample() - 1;// to set it to the interval [0;n[ instead of [1;n]
			result = vals.get(next);
		}
		
		return result;
	}

}
