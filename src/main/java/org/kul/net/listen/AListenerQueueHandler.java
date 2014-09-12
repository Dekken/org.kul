package org.kul.net.listen;

import java.io.IOException;
import java.util.ArrayList;

import org.kul.net.exec.IExecutable;


public abstract class AListenerQueueHandler implements Runnable{

	protected Thread queueHandler = new Thread(this);

	protected Listener listener;

	public AListenerQueueHandler(){
		this(new String[0]);
	}
	public AListenerQueueHandler(String[] args){		
		try {
			listener = new Listener(args);
			queueHandler.start();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public ArrayList<IExecutable> getExecutables() { return listener.getExecutables(); }
	
	@Override
	public abstract void run();
}
