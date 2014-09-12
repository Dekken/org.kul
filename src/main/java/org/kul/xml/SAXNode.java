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
package org.kul.xml;

import java.util.ArrayList;
import java.util.Map;

import org.kul.xml.err.NoSuchAttributeException;
import org.kul.xml.err.NoSuchNodeException;
import org.kul.xml.err.NotATextNodeException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SAXNode extends ANode{

	//private sax element thing 
	public SAXNode(/*sax element thing */){
		/**/
	}
	
	public String name() {		
		throw new NotImplementedException();
	}
	
	public String text() throws NotATextNodeException{
		throw new NotImplementedException();
	}
	
	public  String attribute(final String s) throws NoSuchAttributeException{
		throw new NotImplementedException();
	}
	
	public Map<String, String> attributes() { 
		throw new NotImplementedException();
	}

	public ANode child(String child) throws NoSuchNodeException {
		throw new NotImplementedException();
	}

	public ArrayList<ANode> children() {
		throw new NotImplementedException();
	}	
}