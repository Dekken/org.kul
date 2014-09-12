package org.kul.net.param;

public class NetworkParameters {

	private String host = "localhost";
	private String port = "8080";
	
	public NetworkParameters(){}	
	
	//Getters/Setters	
	public String getHost() 			{ return host;}
	public void setHost(String host) 	{ this.host = host;	}
	
	public String getPort() 			{ return port; }
	public void setPort(String port) 	{ this.port = port; }		
}