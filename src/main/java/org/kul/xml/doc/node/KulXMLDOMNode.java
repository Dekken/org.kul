package org.kul.xml.doc.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kul.xml.err.KulXMLNoSuchAttributeException;
import org.kul.xml.err.KulXMLNoSuchNodeException;
import org.kul.xml.err.KulXMLNotATextNodeException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class KulXMLDOMNode extends AKulXMLNode{
	
	//private static final Logger LOGGER =  Logger.getLogger(KulXMLDOMNode.class);

	private final Element element;
	
	public KulXMLDOMNode(final Element element){
		this.element = element;
	}

	public String name() {
		return element.getNodeName();
	}
	
	public String text() throws KulXMLNotATextNodeException{
		if (element.getFirstChild().getNodeType() == Node.TEXT_NODE) 
			return element.getFirstChild().getNodeValue();
		throw new KulXMLNotATextNodeException("Node " + name() + " is not a text node");
	}

	public  String attribute(final String s) throws KulXMLNoSuchAttributeException{
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
	public AKulXMLNode child(String child) throws KulXMLNoSuchNodeException {	
		NodeList elements = element.getElementsByTagName(child);
		for (int i = 0; i < elements.getLength(); i++)
			return new KulXMLDOMNode((Element) elements.item(i));		
		
		throw new KulXMLNoSuchNodeException("No child node " + child + " found under node + " + element.getNodeName());
	}

	public ArrayList<AKulXMLNode> children() {
		ArrayList<AKulXMLNode> kinder = new ArrayList<AKulXMLNode>();
		
		NodeList elements = element.getChildNodes();
		for (int i = 0; i < elements.getLength(); i++)
			if(Element.class.isAssignableFrom(elements.item(i).getClass()))
				kinder.add(new KulXMLDOMNode((Element) elements.item(i)));
		return kinder;
	}	
}