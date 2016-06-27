/**
 * 
 */
package core.util.xmlNodes;

/**
 * @author Roland
 *
 */
public enum GlobalConfigNodeNames {
	
	COMMAND("command"),
	DBHOST("db_host"),
	DBUSER("db_user"),
	DBPWD("db_password"),
	PARAMETERS("parameters"),
	NAME("stream_name"),
	SGPORT("stream_port"),
	VARIATION("stream_type");
	
	private String name = "";
	
	private GlobalConfigNodeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
