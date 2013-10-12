package org.kul.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

public class KulIOService {

	public static class INSTANCE{
		private static KulIOService instance;
		public static KulIOService GET(){ if(instance == null) instance = new KulIOService(); return instance; }
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
