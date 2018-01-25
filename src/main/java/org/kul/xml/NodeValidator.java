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

public class NodeValidator{
  
  private final HashMap<String, NodeValidator> kinder;
  private final ArrayList<NodeAttributeValidator> atVals;
  private final int min;
  private final int max;
  private final boolean text;
  
  public NodeValidator(
      final HashMap<String, NodeValidator> kinder,
      final ArrayList<NodeAttributeValidator> atVals, 
      final int min, final int max, final boolean text){
    this.kinder = kinder;
    this.atVals = atVals;
    this.min = min;
    this.max = max;
    this.text = text;
  }  
  public NodeValidator(final HashMap<String, NodeValidator> kinder, final int min, final int max, final boolean text){
    this(kinder, new ArrayList<NodeAttributeValidator>(), min, max, text);
  }
  public NodeValidator(final ArrayList<NodeAttributeValidator> atVals, final int min, final int max, final boolean text){
    this(new HashMap<String, NodeValidator>(), atVals, min, max, text);
  }
  public NodeValidator(final int min, final int max, final boolean text){
    this(new HashMap<String, NodeValidator>(), new ArrayList<NodeAttributeValidator>(), min, max, text);
  }  
  
  public final HashMap<String, NodeValidator>  getChildren()    { return this.kinder; }
  public final ArrayList<NodeAttributeValidator>           getAtVals()     { return atVals; }
  public final int                           minimum()      { return this.min; }
  public final  int                           maximum()      { return this.max; }
  public final  boolean                      isText()      { return this.text; }
}