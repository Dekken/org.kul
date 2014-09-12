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

public abstract class ANode{

	protected ANode(){}	

	public abstract String name();

	public abstract String attribute(final String s) throws NoSuchAttributeException;

	public abstract Map<String, String> attributes();

	public abstract String text() throws NotATextNodeException;

	public abstract ANode child(String child) throws NoSuchNodeException;

	public abstract ArrayList<ANode> children();

	public Long parseTextAsLong() throws org.kul.Exception{
		Long l = new Long(0);
		try{ l = Long.parseLong(this.text()); }
		catch(NumberFormatException e){ 
			throw new org.kul.Exception("Node " + this.name() + " contains invalid data, expected num"); 
		}
		return l;
	}

	public Long parseNonEmptyTextAsLong() throws org.kul.Exception{
		Long l = new Long(0);
		if(this.text() != null && !this.text().isEmpty())
			l = parseTextAsLong();
		return l;
	}
}