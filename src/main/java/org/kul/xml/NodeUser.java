package org.kul.xml.doc.node;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;
import org.kul.xml.KulXMLException;
import org.kul.xml.doc.AKulXMLDocument;
import org.kul.xml.service.KulXMLValidationService;

public abstract class AKulXMLNodeUser{
	
	private final AKulXMLDocument doc;	
	
	protected AKulXMLNodeUser(final File f, Class<? extends AKulXMLDocument> docType) throws KulXMLException{
		this.doc = KulXMLValidationService.INSTANCE.GET().create(f, validator(), docType);	
	}	
	
	public final AKulXMLDocument doc(){ return doc;}	
	
	protected abstract Pair<String, KulXMLNodeValidator> validator();
}
