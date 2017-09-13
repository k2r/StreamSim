/**
 * 
 */
package core.config.xmlNodes;

/**
 * @author Roland KOTTO KOMBI
 * Enumerate all xml node names describing streams, profiles and transitions
 */
public enum NodeNames {
	
	STREAM("stream"),
	TYPE("type"),
	PROFILES("profiles"),
	PROFILE("profile"),
	NAME("name"),
	ATTRIBUTES("attributes"),
	ATTRIBUTE("attribute"),
	DURATION("duration"),
	VALUE("value"), 
	TRANSITION("transition");
	
	private String name = "";
	
	private NodeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
