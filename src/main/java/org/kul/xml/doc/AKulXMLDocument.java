package org.kul.xml.doc;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.kul.xml.doc.node.AKulXMLNode;
import org.kul.xml.err.KulXMLDocumentException;

public abstract class AKulXMLDocument{

	private static final Logger LOGGER = Logger.getLogger(AKulXMLDocument.class);

	protected File file;

	public static AKulXMLDocument create(final File file, final Class<? extends AKulXMLDocument> docType) throws KulXMLDocumentException{
		Throwable ex = null;
		try {
			return docType.getDeclaredConstructor(File.class).newInstance(file);				
		} 
		catch (InstantiationException e) 			{  LOGGER.error(ExceptionUtils.getStackTrace(e)); }
		catch (IllegalAccessException e) 			{  LOGGER.error(ExceptionUtils.getStackTrace(e)); }
		catch (IllegalArgumentException e) 		{  LOGGER.error(ExceptionUtils.getStackTrace(e)); }
		catch (InvocationTargetException e) 		{  ex = e.getCause(); }
		catch (NoSuchMethodException e) 		{  LOGGER.error(ExceptionUtils.getStackTrace(e)); }
		catch (SecurityException e) 					{  LOGGER.error(ExceptionUtils.getStackTrace(e)); }
		
		if(ex != null){
			if(KulXMLDocumentException.class.isAssignableFrom(ex.getClass()))
				throw (KulXMLDocumentException) ex;		
		}

		throw new KulXMLDocumentException("XML Document creation failed, see stack trace");
	}

	protected AKulXMLDocument(final File file)  throws KulXMLDocumentException{
		this.file = file;
	}

	public final File file() { return this.file; }

	public abstract AKulXMLNode root();

	public abstract void writeXPathNode(final String query, final String node) throws KulXMLDocumentException;
	public abstract void writeXPathNodeText(final String query, final String text) throws KulXMLDocumentException;
	public abstract void writeXPathNodeAttribute(final String query, final String key, final String value) throws KulXMLDocumentException;

	public abstract void save() throws KulXMLDocumentException;
}
