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

import java.util.ArrayList;
import java.util.HashMap;

public class NodeAttributeValidator{


  final HashMap<String, ArrayList<String>> avs;
  final boolean checked;
  final boolean mandatory;
  final boolean unique;

  public NodeAttributeValidator(final HashMap<String, ArrayList<String>> avs,  final boolean checked, final boolean mandatory, final boolean unique){
    this.avs = avs;
    this.checked  = checked;
    this.mandatory = mandatory;
    this.unique = unique;
  }
  
  public final boolean isChecked()    { return this.checked;}
  public final boolean isMandatory()  { return this.mandatory; }
  public final boolean isUnique()      { return this.unique;}
  
  public final HashMap<String, ArrayList<String>> allowedValues(){ return this.avs;}
}