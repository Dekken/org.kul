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
import java.util.HashMap;
import java.util.Map;

import org.kul.xml.err.NoSuchAttributeException;
import org.kul.xml.err.NoSuchNodeException;
import org.kul.xml.err.NotATextNodeException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DOMNode extends ANode{
  
  //private static final Logger LOGGER =  Logger.getLogger(DOMNode.class);

  private final Element element;
  
  public DOMNode(final Element element){
    this.element = element;
  }

  public String name() {
    return element.getNodeName();
  }
  
  public String text() throws NotATextNodeException{
    if (element.getFirstChild().getNodeType() == Node.TEXT_NODE) 
      return element.getFirstChild().getNodeValue();
    throw new NotATextNodeException("Node " + name() + " is not a text node");
  }

  public  String attribute(final String s) throws NoSuchAttributeException{
    throw new NotImplementedException();
  }
  
  public Map<String, String> attributes() {
    HashMap<String, String> rAttMap = new HashMap<String, String>();
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attr = (Attr) attributes.item(i);        
      rAttMap.put(attr.getNodeName(), attr.getNodeValue());
    }
    return rAttMap;
  }

  @SuppressWarnings("unused")
  public ANode child(String child) throws NoSuchNodeException {  
    NodeList elements = element.getElementsByTagName(child);
    for (int i = 0; i < elements.getLength(); i++)
      return new DOMNode((Element) elements.item(i));    
    
    throw new NoSuchNodeException("No child node " + child + " found under node + " + element.getNodeName());
  }

  public ArrayList<ANode> children() {
    ArrayList<ANode> kinder = new ArrayList<ANode>();
    
    NodeList elements = element.getChildNodes();
    for (int i = 0; i < elements.getLength(); i++)
      if(Element.class.isAssignableFrom(elements.item(i).getClass()))
        kinder.add(new DOMNode((Element) elements.item(i)));
    return kinder;
  }  
}