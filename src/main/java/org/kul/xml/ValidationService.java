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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.kul.xml.err.DocumentException;
import org.kul.xml.err.NoSuchAttributeException;
import org.kul.xml.err.NodeAttributeValidationException;
import org.kul.xml.err.NodeValidationException;

public class ValidationService{

  //private static final Logger LOGGER =  Logger.getLogger(KulXMLValidationService.class);

  public static class INSTANCE{
    private static  ValidationService instance;
    public static ValidationService GET() { if(instance == null) instance = new ValidationService(); return instance;}
  }

  public final ADocument create(final File f,  final Pair<String, NodeValidator> nv, final Class<? extends ADocument> docType) throws DocumentException, org.kul.Exception{
    ADocument doc = ADocument.create(f, docType);
    ANode docRoot = doc.root();
    if(!nv.getLeft().equals(docRoot.name()))
      throw new NodeValidationException("XML Exception: Element " + docRoot.name() + " is unknown");
    this.validate(docRoot,  nv.getRight());
    ArrayList<ANode> rList = new ArrayList<ANode>();
    rList.add(docRoot);
    for(final NodeAttributeValidator nav : nv.getRight().getAtVals())
      validateAttributes(rList, nav);
    return doc;
  }

  private final void validate(ANode node, final NodeValidator nv) throws org.kul.Exception{

    Map<String, Integer> occurences = new HashMap<String, Integer>();
    for(final ANode n : node.children()){
      if(occurences.keySet().contains(n.name())) occurences.put(n.name(), occurences.get(n.name()) + 1); 
      else occurences.put(n.name(), 1);

      boolean found = false;
      for(final Map.Entry<String, NodeValidator> nvE : nv.getChildren().entrySet()){

        if(n.name().equals(nvE.getKey())){
          if(n.attributes().size() > 0){
            for(Map.Entry<String, String> atts : n.attributes().entrySet()){            
              boolean attFound = false;
              for(final NodeAttributeValidator nav : nvE.getValue().getAtVals()){
                if(nav.allowedValues().keySet().contains(atts.getKey())) { attFound = true; break;}                
              }  
              if(!attFound) {
                throw new NodeValidationException("XML Exception: Attribute: \"" + atts.getKey() + "\" for Element : \"" + n.name() + "\" is unknown");
              }
            }
          }

          found = true; break;
        }
      }
      if(!found){
        throw new NodeValidationException("XML Exception: Element " +n.name() + " is unknown");
      }
    }

    for(final ANode n : node.children()){
      final int i = occurences.get(n.name());
      final NodeValidator nvK = nv.getChildren().get(n.name());
      if(nvK.minimum() != 0 && i < nvK.minimum()){
        throw new NodeValidationException("XML Exception: Invalid minimum number of Element: " + n.name());
      }
      if(nvK.maximum() != 0 && i > nvK.maximum()){
        throw new NodeValidationException("XML Exception: Invalid maximum number of Element: " + n.name());
      }      
    }
    for(final ANode n : node.children()){
      if(nv.getChildren().get(n.name()).isText()){
        n.text();
      }
      validate(n, nv.getChildren().get(n.name()));
    }
  }

  private void validateAttributes(final ArrayList<ANode> nodes, final NodeAttributeValidator nv) throws org.kul.Exception{
    for(final String allValKey : nv.allowedValues().keySet()){

      ArrayList<String> allValList = nv.allowedValues().get(allValKey);

      for(final ANode n : nodes){
        boolean f = true;
        for(final String attKey  : n.attributes().keySet()){
          String atVal = n.attributes().get(attKey);
          if(attKey.equals(allValKey)){
            f = false;
            for(final String s : allValList)
              if(s.equals(atVal)){ f = true; break; }
            if(f) break;
          }
        }
        if(!f && nv.isChecked()) throw new NodeAttributeValidationException("Attribute \"" + allValKey + "\" on node \"" + n.name() + "\" is not one of the expected values!");
      }

      for(final ANode n : nodes){
        if(nv.isMandatory()){
          try{
            n.attribute(allValKey);
          }catch(NoSuchAttributeException e){ throw new NodeAttributeValidationException("Attribute \"" + allValKey + "\" on node \"" + n.name() + "\" is MANDATORY!");}
        }
      }

      if(nv.isUnique()){
        String name = "";
        ArrayList<String> atts = new ArrayList<String>();
        for(final ANode n : nodes){
          name = n.name();
          for(final String attKey  : n.attributes().keySet()){
            if(allValKey.equals(attKey))
              atts.add(n.attributes().get(attKey));
          }
          for(final String s1 : atts){
            int i = 0;
            for(final String s2 : atts)
              if(s1.equals(s2)) i++;
            if(i > 1) throw new NodeAttributeValidationException("Attribute " + allValKey + " on node " + name + " must be unique!");
          }        
        }
      }
    }
  }
  /*
  private void validateAttributes(final ANode node, final KulXMLNodeValidator nodeV) throws KulXMLException{
    for(final ANode n : node.children()){
      for(final String nvKey : nodeV.getChildren().keySet()){
        if(nvKey.equals(n.name())){
          final KulXMLNodeValidator nv = nodeV.getChildren().get(nvKey);
          for(final NodeAttributeValidator nav : nv.getAtVals()){
            ArrayList<ANode> ns = new ArrayList<ANode>();
            for(final ANode in : node.children())
              if(n.name().equals(in.name()))
                ns.add(in);
            validateAttributes(ns, nav);
          }
          validateAttributes(n, nv);
        }
      }
    }
  }*/
}