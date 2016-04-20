/**
 * 
 */
package core.util.xmlNodes;

/**
 * @author Roland KOTTO KOMBI
 * Enumerate all xml node names describing streams, profiles and transitions
 */
public enum NodeNames {
	
	STREAM("stream"),
	PROFILES("profiles"),
	PROFILE("profile"),
	TRANSITIONS("transitions"),
	TRANSITION("transition"),
	NAME("name"),
	ATTRIBUTES("attributes"),
	ATTRIBUTE("attribute"),
	REFERENCE("reference"),
	FREQDELTA("freqdelta"),
	SIZEDELTA("sizedelta"),
	LOAD("load"),
	DURATION("duration"),
	VALUE("value"),
	EXECUTORS("executors"),
	TICKINTERVAL("interval"),
	NBTHREAD("threads"),
	SEQOPER("sequential"),
	PARAM("parameters");
	
	private String name = "";
	
	private NodeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
