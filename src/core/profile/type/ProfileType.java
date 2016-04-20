/**
 * 
 */
package core.profile.type;

/**
 * @author Roland KOTTO KOMBI
 *	Enumerate primary profiles
 */
public enum ProfileType {

	STANDARD("standard"),
	WEIGHTED("weighted"),
	OCCURENCE("occurence"),
	LOADED("loaded");
	
	private String name = "";
	
	ProfileType(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
