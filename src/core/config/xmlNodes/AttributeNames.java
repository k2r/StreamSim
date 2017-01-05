/**
 * 
 */
package core.config.xmlNodes;

/**
 * @author Roland KOTTO KOMBI
 * Enumerate all xml node attribute names describing streams, profiles and transitions
 */
public enum AttributeNames {

	TYPE("type"),
	MIN("min"),
	MAX("max"),
	NAME("name"),
	RATE("rate"),
	OPTION("option"),
	LGTH("length"),
	PATT("pattern");
	
	private String name = "";
	
	private AttributeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
