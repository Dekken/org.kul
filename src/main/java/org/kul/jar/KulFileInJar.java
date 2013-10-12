package org.kul.jar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class KulFileInJar{

	private static final Logger LOGGER = Logger.getLogger(KulFileInJar.class);

	private final InputStream file;

	public KulFileInJar(final String file) throws FileNotFoundException{
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