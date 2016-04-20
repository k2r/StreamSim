/**
 * 
 */
package core.util.xmlNodes;

/**
 * @author Roland
 *
 */
public enum GlobalConfigNodeNames {

	PARAMETERS("parameters"),
	HOME("bench_home_directory"),
	SGCONF("stream_generator_conf_file"),
	NAME("stream_name"),
	SGPORT("stream_port"),
	VARIATION("stream_type"),
	SMCONF("system_monitor_conf_file"),
	SMPORT("system_monitor_port"),
	SGHOST("stream_host"),
	SGHOSTPORT("stream_host_port"),
	SMHOST("monitor_host"),
	SMHOSTPORT("monitor_host_port"),
	NBSUPERVISORS("nb_supervisors"),
	SIZE("window_size"),
	STEP("window_step");
	
	private String name = "";
	
	private GlobalConfigNodeNames(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
