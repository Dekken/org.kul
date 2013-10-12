package org.kul.xml.doc.node;

import java.util.ArrayList;
import java.util.Map;

import org.kul.xml.err.KulXMLNoSuchAttributeException;
import org.kul.xml.err.KulXMLNoSuchNodeException;
import org.kul.xml.err.KulXMLNotATextNodeException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class KulXMLSAXNode extends AKulXMLNode{

	//private sax element thing 
	public KulXMLSAXNode(/*sax element thing */){
		/**/
	}
	
	public String name() {		
		throw new NotImplementedException();
	}
	
	public String text() throws KulXMLNotATextNodeException{
		throw new NotImplementedException();
	}
	
	public  String attribute(final String s) throws KulXMLNoSuchAttributeException{
		throw new NotImplementedException();
	}
	
	public Map<String, String> attributes() { 
		throw new NotImplementedException();
	}

	public AKulXMLNode child(String child) throws KulXMLNoSuchNodeException {
		throw new NotImplementedException();
	}

	public ArrayList<AKulXMLNode> children() {
		throw new NotImplementedException();
	}	
}