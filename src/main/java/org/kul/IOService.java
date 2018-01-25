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

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

public class IOService {

  public static class INSTANCE{
    private static IOService instance;
    public static IOService GET(){ if(instance == null) instance = new IOService(); return instance; }
  }
  
  public boolean isDir(final String dir){
    return new File(dir).isDirectory();
  }
  
  public void mkDir(final String dir) throws IOException{
    File f = new File(dir);
    if(!f.exists() && !f.mkdirs())      
      throw new IOException("ERROR: Insufficient privileges to make logging directories.");
  }
  
  public void mv(final File a, final File b) throws IOException{
    if(!a.renameTo(b))
      throw new IOException("ERROR: Unable to rename file.");
  }
  
  public String join(final String a, final String b){
    return a + "/" + b;
  }
  
  public ArrayList<File> getItems(final String dir) throws NotDirectoryException{
    if(!isDir(dir)) throw new NotDirectoryException("No such directory " + dir);
      
    ArrayList<File> items= new ArrayList<File>();
    File f = new File(dir);
    for (final File file : f.listFiles()) 
      items.add(file);
      
    return items;
  }
  
  public ArrayList<File> getDirs(final String dir) throws NotDirectoryException{
    if(!isDir(dir)) throw new NotDirectoryException("No such directory " + dir);
      
    ArrayList<File> dirs = new ArrayList<File>();
    File f = new File(dir);
    for (final File file : f.listFiles()) 
          if (file.isDirectory()) 
            dirs.add(file);          
      
    return dirs;
  }
  
  public ArrayList<File> getFiles(final String dir) throws NotDirectoryException{
    if(!isDir(dir)) throw new NotDirectoryException("No such directory " + dir);
      
    ArrayList<File> files = new ArrayList<File>();
    File f = new File(dir);
    for (final File file : f.listFiles()) 
          if (file.isFile()) 
            files.add(file);
    
    return files;
  }
}
