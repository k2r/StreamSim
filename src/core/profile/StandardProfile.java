/**
 * 
 */
package core.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import core.attribute.EnumAttribute;
import core.attribute.IntAttribute;
import core.attribute.TextAttribute;
import core.attribute.type.AttributeType;
import core.profile.type.ProfileType;

/**
 * @author Roland KOTTO KOMBI
 *	Class implementing a standard profile which maintain a fixed number of uniform tuples which takes values in a balanced value space
 */
public class StandardProfile implements IStreamProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6227708938954361763L;
	private ProfileType type;
	private double duration;
	private int nbElementPerTick;
	
	private ArrayList<Character> lowerCaseLetters;
	private ArrayList<Character> upperCaseLetters;
	private ArrayList<Character> digits;
	private ArrayList<Character> lowerAndUpperLetters;
	private ArrayList<Character> lowerLettersAndDigits;
	private ArrayList<Character> upperLettersAndDigits;
	
	
	public StandardProfile(double duration, int nbElementPerTick) {
		this.type = ProfileType.STANDARD;
		this.duration = duration;
		this.nbElementPerTick = nbElementPerTick;
		
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
	
	@Override
	public void setNbElementPerTick(int rate) {
		this.nbElementPerTick = rate;
	}
	
	/* (non-Javadoc)
	 * @see core.profile.IStreamProfile#getNextValue()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getNextValue(AttributeType attributeType, HashMap<String, Object> parameters) {
		Object result = null;
		Random rand = new Random();
		if(attributeType.toString().equalsIgnoreCase(AttributeType.INT.toString())){
			Integer min = (Integer) parameters.get(IntAttribute.MIN_PARAM);
			Integer max = (Integer) parameters.get(IntAttribute.MAX_PARAM);
			int range = max - min;
			result = min + rand.nextInt(range);
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
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerCaseLetters.get(rand.nextInt(this.lowerCaseLetters.size()));
							nextString += c;
						}
					}
					if(regExp.equals("[A-Z]")){
						for(int j = 0; j < nbChars; j++){
							char c = this.upperCaseLetters.get(rand.nextInt(this.upperCaseLetters.size()));
							nextString += c;
						}
					}
					if(regExp.equals("[0-9]")){
						for(int j = 0; j < nbChars; j++){
							char c = this.digits.get(rand.nextInt(this.digits.size()));
							nextString += c;
						}
					}
					if(regExp.equals("[a-Z]")){
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerAndUpperLetters.get(rand.nextInt(this.lowerAndUpperLetters.size()));
							nextString += c;
						}
					}
					if(regExp.equals("[a-9]")){
						for(int j = 0; j < nbChars; j++){
							char c = this.lowerLettersAndDigits.get(rand.nextInt(this.lowerLettersAndDigits.size()));
							nextString += c;
						}
					}
					if(regExp.equals("[A-9]")){
						for(int j = 0; j < nbChars; j++){
							char c = this.upperLettersAndDigits.get(rand.nextInt(this.upperLettersAndDigits.size()));
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
			int next = rand.nextInt(n);
			result = vals.get(next);
		}
		return result;
	}

	
}