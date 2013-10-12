package org.kul.log.xml; 

import org.kul.xml.doc.AKulXMLDocument;
import org.kul.xml.err.KulXMLDocumentException;

public class KulXMLFileLogger{

	private final AKulXMLDocument doc;
	
	public KulXMLFileLogger(final AKulXMLDocument doc) {
		this.doc = doc;
	}
	
	public void writeXPathNode(final String query, final String node) throws KulXMLDocumentException{
		doc.writeXPathNode(query, node);
	}
	
	public void writeXPathNodeText(final String query, final String text) throws KulXMLDocumentException{
		doc.writeXPathNodeText(query,  text);
	}
	public void writeXPathNodeAttribute(final String query, final String key, final String value) throws KulXMLDocumentException{
		doc.writeXPathNodeAttribute(query, key, value);
	}
	
	public void save() throws KulXMLDocumentException{
		doc.save();
	}
}