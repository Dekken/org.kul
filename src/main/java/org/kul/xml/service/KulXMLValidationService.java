package org.kul.xml.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.kul.xml.KulXMLException;
import org.kul.xml.doc.AKulXMLDocument;
import org.kul.xml.doc.node.AKulXMLNode;
import org.kul.xml.doc.node.KulXMLNodeAttributeValidator;
import org.kul.xml.doc.node.KulXMLNodeValidator;
import org.kul.xml.err.KulXMLDocumentException;
import org.kul.xml.err.KulXMLNoSuchAttributeException;
import org.kul.xml.err.KulXMLNodeAttributeValidationException;
import org.kul.xml.err.KulXMLNodeValidationException;

public class KulXMLValidationService{

	//private static final Logger LOGGER =  Logger.getLogger(KulXMLValidationService.class);

	public static class INSTANCE{
		private static  KulXMLValidationService instance;
		public static KulXMLValidationService GET() { if(instance == null) instance = new KulXMLValidationService(); return instance;}
	}

	public final AKulXMLDocument create(final File f,  final Pair<String, KulXMLNodeValidator> nv, final Class<? extends AKulXMLDocument> docType) throws KulXMLDocumentException, KulXMLException{
		AKulXMLDocument doc = AKulXMLDocument.create(f, docType);
		AKulXMLNode docRoot = doc.root();
		if(!nv.getLeft().equals(docRoot.name()))
			throw new KulXMLNodeValidationException("XML Exception: Element " + docRoot.name() + " is unknown");
		this.validate(docRoot,  nv.getRight());
		ArrayList<AKulXMLNode> rList = new ArrayList<AKulXMLNode>();
		rList.add(docRoot);
		for(final KulXMLNodeAttributeValidator nav : nv.getRight().getAtVals())
			validateAttributes(rList, nav);
		return doc;
	}

	private final void validate(AKulXMLNode node, final KulXMLNodeValidator nv) throws KulXMLException{

		Map<String, Integer> occurences = new HashMap<String, Integer>();
		for(final AKulXMLNode n : node.children()){
			if(occurences.keySet().contains(n.name())) occurences.put(n.name(), occurences.get(n.name()) + 1); 
			else occurences.put(n.name(), 1);

			boolean found = false;
			for(final Map.Entry<String, KulXMLNodeValidator> nvE : nv.getChildren().entrySet()){

				if(n.name().equals(nvE.getKey())){
					if(n.attributes().size() > 0){
						for(Map.Entry<String, String> atts : n.attributes().entrySet()){						
							boolean attFound = false;
							for(final KulXMLNodeAttributeValidator nav : nvE.getValue().getAtVals()){
								if(nav.allowedValues().keySet().contains(atts.getKey())) { attFound = true; break;}								
							}	
							if(!attFound) {
								throw new KulXMLNodeValidationException("XML Exception: Attribute: \"" + atts.getKey() + "\" for Element : \"" + n.name() + "\" is unknown");
							}
						}
					}

					found = true; break;
				}
			}
			if(!found){
				throw new KulXMLNodeValidationException("XML Exception: Element " +n.name() + " is unknown");
			}
		}

		for(final AKulXMLNode n : node.children()){
			final int i = occurences.get(n.name());
			final KulXMLNodeValidator nvK = nv.getChildren().get(n.name());
			if(nvK.minimum() != 0 && i < nvK.minimum()){
				throw new KulXMLNodeValidationException("XML Exception: Invalid minimum number of Element: " + n.name());
			}
			if(nvK.maximum() != 0 && i > nvK.maximum()){
				throw new KulXMLNodeValidationException("XML Exception: Invalid maximum number of Element: " + n.name());
			}			
		}
		for(final AKulXMLNode n : node.children()){
			if(nv.getChildren().get(n.name()).isText()){
				n.text();
			}
			validate(n, nv.getChildren().get(n.name()));
		}
	}

	private void validateAttributes(final ArrayList<AKulXMLNode> nodes, final KulXMLNodeAttributeValidator nv) throws KulXMLException{
		for(final String allValKey : nv.allowedValues().keySet()){

			ArrayList<String> allValList = nv.allowedValues().get(allValKey);

			for(final AKulXMLNode n : nodes){
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
				if(!f && nv.isChecked()) throw new KulXMLNodeAttributeValidationException("Attribute \"" + allValKey + "\" on node \"" + n.name() + "\" is not one of the expected values!");
			}

			for(final AKulXMLNode n : nodes){
				if(nv.isMandatory()){
					try{
						n.attribute(allValKey);
					}catch(KulXMLNoSuchAttributeException e){ throw new KulXMLNodeAttributeValidationException("Attribute \"" + allValKey + "\" on node \"" + n.name() + "\" is MANDATORY!");}
				}
			}

			if(nv.isUnique()){
				String name = "";
				ArrayList<String> atts = new ArrayList<String>();
				for(final AKulXMLNode n : nodes){
					name = n.name();
					for(final String attKey  : n.attributes().keySet()){
						if(allValKey.equals(attKey))
							atts.add(n.attributes().get(attKey));
					}
					for(final String s1 : atts){
						int i = 0;
						for(final String s2 : atts)
							if(s1.equals(s2)) i++;
						if(i > 1) throw new KulXMLNodeAttributeValidationException("Attribute " + allValKey + " on node " + name + " must be unique!");
					}				
				}
			}
		}
	}
	/*
	private void validateAttributes(final AKulXMLNode node, final KulXMLNodeValidator nodeV) throws KulXMLException{
		for(final AKulXMLNode n : node.children()){
			for(final String nvKey : nodeV.getChildren().keySet()){
				if(nvKey.equals(n.name())){
					final KulXMLNodeValidator nv = nodeV.getChildren().get(nvKey);
					for(final KulXMLNodeAttributeValidator nav : nv.getAtVals()){
						ArrayList<AKulXMLNode> ns = new ArrayList<AKulXMLNode>();
						for(final AKulXMLNode in : node.children())
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