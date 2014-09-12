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

import org.kul.xml.err.DocumentException;


public class FileLogger{

	private final ADocument doc;
	
	public FileLogger(final ADocument doc) {
		this.doc = doc;
	}
	
	public void writeXPathNode(final String query, final String node) throws DocumentException{
		doc.writeXPathNode(query, node);
	}
	
	public void writeXPathNodeText(final String query, final String text) throws DocumentException{
		doc.writeXPathNodeText(query,  text);
	}
	public void writeXPathNodeAttribute(final String query, final String key, final String value) throws DocumentException{
		doc.writeXPathNodeAttribute(query, key, value);
	}
	
	public void save() throws DocumentException{
		doc.save();
	}
}