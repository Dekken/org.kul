package org.kul.xml.err;

import org.kul.xml.KulXMLException;

@SuppressWarnings("serial")
public class KulXMLNodeHasNoParentException extends KulXMLException {

	public KulXMLNodeHasNoParentException(String msg) {
		super(msg);
	}

}
