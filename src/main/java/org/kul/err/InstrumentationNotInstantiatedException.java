package org.kul.inst.err;

import org.kul.KulException;

@SuppressWarnings("serial")
public class KulInstrumentationNotInstantiatedException extends KulException{

	public KulInstrumentationNotInstantiatedException(final String msg){
		super(msg);
	}
}