package org.kul.net.param;

import java.util.HashMap;

import org.kul.str.StringArgsParsingService;


public class NetworkParameterHandler {

	private static NetworkParameterHandler instance;
	
	public static NetworkParameterHandler INSTANCE(){
		return instance == null ? (instance = new NetworkParameterHandler()) : instance;
	} 
		
	public NetworkParameters handle(final String args[]){
		return new NetworkParameters(){{
			HashMap<String, String> argMap = StringArgsParsingService.INSTANCE().returnParsedArguments(args);
			for(String s : argMap.keySet()){
				if(s.equals("host"))	{ setHost(argMap.get(s));}
				if(s.equals("port"))	{ setPort(argMap.get(s));}
			}			
		}};		
	}
	

}