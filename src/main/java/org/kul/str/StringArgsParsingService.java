package org.kul.str;

import java.util.HashMap;

@SuppressWarnings("serial")
public class StringArgsParsingService {

	
	private static StringArgsParsingService instance;
	
	
	public static StringArgsParsingService INSTANCE(){
		return instance == null ? (instance = new StringArgsParsingService()) : instance;
	}		

	public HashMap<String, String> returnParsedArguments(final String[] args){
		
		return new HashMap<String, String>(){{			
			for(String s : args){
				if(s.length() > 0){
					
					if(s.substring(0, 2).equals("-D"))
						s = s.substring(2);
					else if(s.substring(0, 1).equals("-")){
						s = s.substring(1);
					}
					
					if(s.contains("=")){
						put(s.split("=")[0], s.split("=")[1]); 
						continue;
					}else{
						put(s, ""); 
						continue;
					}
				}
			}			
		}};
	}
}
