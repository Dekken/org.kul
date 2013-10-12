package org.kul.xml.doc.node;

import java.util.ArrayList;
import java.util.HashMap;

public class KulXMLNodeAttributeValidator{


	final HashMap<String, ArrayList<String>> avs;
	final boolean checked;
	final boolean mandatory;
	final boolean unique;

	public KulXMLNodeAttributeValidator(final HashMap<String, ArrayList<String>> avs,  final boolean checked, final boolean mandatory, final boolean unique){
		this.avs = avs;
		this.checked  = checked;
		this.mandatory = mandatory;
		this.unique = unique;
	}
	
	public final boolean isChecked()		{ return this.checked;}
	public final boolean isMandatory()	{ return this.mandatory; }
	public final boolean isUnique()			{ return this.unique;}
	
	public final HashMap<String, ArrayList<String>> allowedValues(){ return this.avs;}
}