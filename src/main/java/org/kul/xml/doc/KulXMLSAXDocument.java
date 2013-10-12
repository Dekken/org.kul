package org.kul.xml.doc;

import java.io.File;

import org.kul.xml.doc.node.AKulXMLNode;
import org.kul.xml.err.KulXMLDocumentException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class KulXMLSAXDocument extends AKulXMLDocument{

	//private final KulXMLSAXNode root;

	public KulXMLSAXDocument(final File file) throws KulXMLDocumentException { 
		super(file);

		if(!this.file().exists()){}		
	}

	public AKulXMLNode root(){
		throw new NotImplementedException();
	}

	@Override
	public void writeXPathNodeText(String query, String text)	throws KulXMLDocumentException {
		throw new NotImplementedException();
	}

	@Override
	public void writeXPathNodeAttribute(String query, String key, String value) 	throws KulXMLDocumentException {
		throw new NotImplementedException();
	}

	@Override
	public void writeXPathNode(String query, String node) throws KulXMLDocumentException {
		throw new NotImplementedException();
	}
	
	public void save() throws KulXMLDocumentException{
		throw new NotImplementedException();
	}
}
