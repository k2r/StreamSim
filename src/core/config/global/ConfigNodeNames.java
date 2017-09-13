/**
 * 
 */
package core.config.global;

/**
 * @author Roland
 *
 */
public enum ConfigNodeNames {
	
	COMMAND("command"),
	DBHOST("db_host"),
	DBUSER("db_user"),
	DBPWD("db_password"),
	PARAMETERS("parameters"),
	NAME("stream_name"),
	MODEL("stream_model"),
	VARIATION("stream_variation"),
	CONSUM("consumer"),
	RMIHOST("rmi_host"),
	RMIPORT("rmi_port"),
	KFKHOST("kafka_host"), 
	FREQ("frequency");
	
	private String name = "";
	
	private ConfigNodeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
