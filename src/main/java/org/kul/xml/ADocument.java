/**

Created on: 12 Sept 2014

Copyright (c) 2013, Philip Deegan

This file is part of org.kul.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this library.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.kul.xml;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.kul.xml.err.DocumentException;

public abstract class ADocument{

	private static final Logger LOGGER = Logger.getLogger(ADocument.class);

	protected File file;

	public static ADocument create(final File file, final Class<? extends ADocument> docType) throws DocumentException{
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
			if(DocumentException.class.isAssignableFrom(ex.getClass()))
				throw (DocumentException) ex;		
		}

		throw new DocumentException("XML Document creation failed, see stack trace");
	}

	protected ADocument(final File file)  throws DocumentException{
		this.file = file;
	}

	public final File file() { return this.file; }

	public abstract ANode root();

	public abstract void writeXPathNode(final String query, final String node) throws DocumentException;
	public abstract void writeXPathNodeText(final String query, final String text) throws DocumentException;
	public abstract void writeXPathNodeAttribute(final String query, final String key, final String value) throws DocumentException;

	public abstract void save() throws DocumentException;
}
