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

import org.apache.commons.lang3.tuple.Pair;

public abstract class ANodeUser{
	
	private final ADocument doc;	
	
	protected ANodeUser(final File f, Class<? extends ADocument> docType) throws org.kul.Exception{
		this.doc = ValidationService.INSTANCE.GET().create(f, validator(), docType);	
	}	
	
	public final ADocument doc(){ return doc;}	
	
	protected abstract Pair<String, NodeValidator> validator();
}
