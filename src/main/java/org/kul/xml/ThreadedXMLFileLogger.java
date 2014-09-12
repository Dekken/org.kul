package org.kul.log.xml;

import org.kul.xml.doc.AKulXMLDocument;

public abstract class KulThreadedXMLFileLogger extends KulXMLFileLogger implements Runnable{

	private boolean lock = false;
	private Thread thread;

	public KulThreadedXMLFileLogger( final AKulXMLDocument doc){
		super(doc);
		thread = new Thread(this);
	}
	
	protected void start(){
		thread.start();
	}

	public void lock(){
		lock = true;
	}

	public void unLock(){
		lock = false;
	}

	public boolean isLocked(){
		return lock;
	}
	
	public void interrupt(){
		thread.interrupt();
	}
	
	public boolean isRunning(){
		return thread.isAlive();
	}
	
	public abstract void run();
}