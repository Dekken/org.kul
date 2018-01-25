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
package org.kul;

import java.lang.instrument.Instrumentation;
import java.util.HashSet;

import org.kul.err.InstrumentationNotInstantiatedException;


//requires java -javaagent:main.jar Main
//View pom of app project for manifest/premium class info

public class InstrumentationService{

  //private static final Logger LOGGER = Logger.getLogger(KulInstrumentationService.class);

  public static void premain(String args, Instrumentation inst) {
    InstrumentationService.INSTANCE.instantiate(inst);
  }

  private Instrumentation instrumentation;

  InstrumentationService(Instrumentation inst) {
    this.instrumentation = inst;  
  }

  public static class INSTANCE{
    protected static void instantiate(Instrumentation inst){ instance = new InstrumentationService(inst);}
    private static InstrumentationService instance;
    public static InstrumentationService GET() throws InstrumentationNotInstantiatedException{ 
      if(instance == null) 
        throw new InstrumentationNotInstantiatedException("Java agent not instantiated");
      return instance;
    }
  }

  public Long getObjectSize(final Object o){
    return instrumentation.getObjectSize(o);
  }

  /**
   * Returns complete object memory allocation for object and all fields
   * Ignores static fields - use getObjectSize instead
   */  
  public <T extends Object> Long getDeepObjectSize(final T t){    
    return getDeepObjectSizeRecursive(t, new HashSet<Integer>());
  }

  private <T extends Object> Long getDeepObjectSizeRecursive(final T t, HashSet<Integer> hashes){
    Long bytes = new Long(0);

    HashSet<java.lang.reflect.Field> fields = new HashSet<java.lang.reflect.Field>();
    for(Class<?> c = t.getClass(); !c.isAssignableFrom(Object.class) ; c = c.getSuperclass())
      fields.addAll(java.util.Arrays.asList(c.getDeclaredFields()));
    
    try {
      for(java.lang.reflect.Field field : new HashSet<java.lang.reflect.Field>(fields)){
        boolean access = field.isAccessible();
        if(!access)
          field.setAccessible(true);
        if(field.get(t) == null || hashes.contains(field.get(t).hashCode())
            ||  java.lang.reflect.Modifier.isStatic(field.getModifiers()))          
          fields.remove(field);      
        if(!access) 
          field.setAccessible(false);
      }

      for(java.lang.reflect.Field field : fields){
        boolean access = field.isAccessible();
        if(!access)
          field.setAccessible(true);
        hashes.add(field.get(t).hashCode());
        if(field.getType().isPrimitive())
          bytes += getObjectSize(field.get(t));
        else
          bytes += getDeepObjectSizeRecursive(field.get(t), hashes);
        if(!access) field.setAccessible(false);
      }        
    }
    catch (IllegalArgumentException e)   { e.printStackTrace(); }
    catch (IllegalAccessException e)     { e.printStackTrace(); }

    return bytes;
  }
}