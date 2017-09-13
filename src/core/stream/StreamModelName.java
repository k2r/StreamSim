/**
 * 
 */
package core.stream;

/**
 * @author Roland
 *
 */
public enum StreamModelName {

	REL("relational");
	
	private String name = "";
	
	private StreamModelName(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
