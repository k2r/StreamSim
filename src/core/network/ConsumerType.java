package core.network;

public enum ConsumerType {

	RMI("rmi"),
	KFK("kafka");
	
	private String name = "";
	
	private ConsumerType(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
