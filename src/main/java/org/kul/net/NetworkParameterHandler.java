/**

Created on: 12 Sept 2014

Copyright (c) 2013, Philip Deegan

This file is part of org.kul.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this library.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.kul.net;

import java.util.HashMap;

import org.kul.StringArgsParsingService;


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