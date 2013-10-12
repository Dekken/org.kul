package org.kul.xml.err;

import org.kul.xml.KulXMLException;

@SuppressWarnings("serial")
public class KulXMLNodeAttributeValidationException extends KulXMLException {
	
	public KulXMLNodeAttributeValidationException(final String msg){
		super(msg);
	}
}
