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

import java.io.IOException;
import java.util.ArrayList;


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
