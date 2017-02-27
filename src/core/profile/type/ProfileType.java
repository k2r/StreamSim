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
	ZIPF("zipf");
	
	private String name = "";
	
	ProfileType(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
