/**
 * 
 */
package core.model.relational.attribute.type;

/**
 * @author Roland KOTTO KOMBI
 * Enum of all considered attribute types.
 */
public enum AttributeType {

	INT("integer"),
	ENUM("enumerate"),
	TEXT("text");
	
	private String name = "";
	
	private AttributeType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
