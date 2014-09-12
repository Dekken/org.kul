package org.kul.xml.doc.node;

import java.util.ArrayList;
import java.util.HashMap;

public class KulXMLNodeValidator{
	
	private final HashMap<String, KulXMLNodeValidator> kinder;
	private final ArrayList<KulXMLNodeAttributeValidator> atVals;
	private final int min;
	private final int max;
	private final boolean text;
	
	public KulXMLNodeValidator(
			final HashMap<String, KulXMLNodeValidator> kinder,
			final ArrayList<KulXMLNodeAttributeValidator> atVals, 
			final int min, final int max, final boolean text){
		this.kinder = kinder;
		this.atVals = atVals;
		this.min = min;
		this.max = max;
		this.text = text;
	}	
	public KulXMLNodeValidator(final HashMap<String, KulXMLNodeValidator> kinder, final int min, final int max, final boolean text){
		this(kinder, new ArrayList<KulXMLNodeAttributeValidator>(), min, max, text);
	}
	public KulXMLNodeValidator(final ArrayList<KulXMLNodeAttributeValidator> atVals, final int min, final int max, final boolean text){
		this(new HashMap<String, KulXMLNodeValidator>(), atVals, min, max, text);
	}
	public KulXMLNodeValidator(final int min, final int max, final boolean text){
		this(new HashMap<String, KulXMLNodeValidator>(), new ArrayList<KulXMLNodeAttributeValidator>(), min, max, text);
	}	
	
	public final HashMap<String, KulXMLNodeValidator>	getChildren() 	 { return this.kinder; }
	public final ArrayList<KulXMLNodeAttributeValidator> 					getAtVals()		 { return atVals; }
	public final int 													minimum() 		 { return this.min; }
	public final  int 													maximum() 		 { return this.max; }
	public final  boolean											isText() 		 { return this.text; }
}