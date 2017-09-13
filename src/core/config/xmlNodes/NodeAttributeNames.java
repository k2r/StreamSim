/**
 * 
 */
package core.config.xmlNodes;

/**
 * @author Roland KOTTO KOMBI
 * Enumerate all xml node attribute names describing streams, profiles and transitions
 */
public enum NodeAttributeNames {

	TYPE("type"),
	MIN("min"),
	MAX("max"),
	NAME("name"),
	RATE("rate"),
	OPTION("option"),
	LGTH("length"),
	PATT("pattern"),
	SKEW("skew");
	
	private String name = "";
	
	private NodeAttributeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
