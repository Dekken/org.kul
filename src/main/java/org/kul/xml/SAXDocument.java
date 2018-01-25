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

import java.io.File;

import org.kul.xml.err.DocumentException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SAXDocument extends ADocument{

  //private final KulXMLSAXNode root;

  public SAXDocument(final File file) throws DocumentException { 
    super(file);

    if(!this.file().exists()){}    
  }

  public ANode root(){
    throw new NotImplementedException();
  }

  @Override
  public void writeXPathNodeText(String query, String text)  throws DocumentException {
    throw new NotImplementedException();
  }

  @Override
  public void writeXPathNodeAttribute(String query, String key, String value)   throws DocumentException {
    throw new NotImplementedException();
  }

  @Override
  public void writeXPathNode(String query, String node) throws DocumentException {
    throw new NotImplementedException();
  }
  
  public void save() throws DocumentException{
    throw new NotImplementedException();
  }
}
