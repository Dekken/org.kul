package org.kul.xml.doc.node;

import java.util.ArrayList;
import java.util.Map;

import org.kul.xml.KulXMLException;
import org.kul.xml.err.KulXMLNoSuchAttributeException;
import org.kul.xml.err.KulXMLNoSuchNodeException;
import org.kul.xml.err.KulXMLNotATextNodeException;

public abstract class AKulXMLNode{

	protected AKulXMLNode(){}	

	public abstract String name();

	public abstract String attribute(final String s) throws KulXMLNoSuchAttributeException;

	public abstract Map<String, String> attributes();

	public abstract String text() throws KulXMLNotATextNodeException;

	public abstract AKulXMLNode child(String child) throws KulXMLNoSuchNodeException;

	public abstract ArrayList<AKulXMLNode> children();

	public Long parseTextAsLong() throws KulXMLException{
		Long l = new Long(0);
		try{ l = Long.parseLong(this.text()); }
		catch(NumberFormatException e){ 
			throw new KulXMLException("Node " + this.name() + " contains invalid data, expected num"); 
		}
		return l;
	}

	public Long parseNonEmptyTextAsLong() throws KulXMLException{
		Long l = new Long(0);
		if(this.text() != null && !this.text().isEmpty())
			l = parseTextAsLong();
		return l;
	}
}