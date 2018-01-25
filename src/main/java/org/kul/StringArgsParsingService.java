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
package org.kul;

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
