/**
 * 
 */
package core.transition.type;

/**
 * @author Roland KOTTO KOMBI
 *	Enumerate transition types
 */
public enum TransitionType {

	LINEAR("linear"),
	EXP("exponential"),
	LOG("logarithmic");
	
	private String name = "";
	
	TransitionType(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
