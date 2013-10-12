package org.kul.xml.err;

import org.kul.xml.KulXMLException;

@SuppressWarnings("serial")
public class KulXMLNodeValidationException extends KulXMLException {
	
	public KulXMLNodeValidationException(final String msg){
		super(msg);
	}
}
