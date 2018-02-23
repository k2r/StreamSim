package core.network;

public enum MessagingType {

	RMI("rmi"),
	KFK("kafka");
	
	private String name = "";
	
	private MessagingType(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
