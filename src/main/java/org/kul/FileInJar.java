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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class FileInJar{

  private static final Logger LOGGER = Logger.getLogger(FileInJar.class);

  private final InputStream file;

  public FileInJar(final String file) throws FileNotFoundException{
    this.file = getClass().getResourceAsStream(file);
    if(this.file == null) throw new FileNotFoundException("No file - \"" + file + "\" in jar");
  }

  public void copy(final String dir, final String file) throws UnsupportedEncodingException, IOException {    

    BufferedReader input = new BufferedReader(new InputStreamReader(this.file));

    new File(dir).mkdirs();          
    PrintWriter writer = new PrintWriter(dir + "/" + file, "UTF-8");
    String line = input.readLine();  
    while (line != null){
      writer.println(line);
      line = input.readLine();
    }      
    writer.close();
  }

  public void print() throws IOException{
    BufferedReader input = new BufferedReader(new InputStreamReader(this.file));
    String line = input.readLine();  
    while (line != null){
      LOGGER.info(line);
      line = input.readLine();
    }    
  }
}