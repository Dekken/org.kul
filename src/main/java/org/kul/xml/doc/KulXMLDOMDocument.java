package org.kul.xml.doc;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.kul.xml.doc.node.AKulXMLNode;
import org.kul.xml.doc.node.KulXMLDOMNode;
import org.kul.xml.err.KulXMLDocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class KulXMLDOMDocument extends AKulXMLDocument{
	
	private static final Logger LOGGER = Logger.getLogger(KulXMLDOMDocument.class);

	private KulXMLDOMNode root;
	private Document domDoc;

	public KulXMLDOMDocument(final File file)  throws KulXMLDocumentException{
		super(file);
		
		if(!this.file().exists()){
			throw new KulXMLDocumentException("File : " + file + " does not exist on the location machine");
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();			
			domDoc = dBuilder.parse(this.file());
			root = new KulXMLDOMNode(domDoc.getDocumentElement());
		}catch (SAXException e) {
			throw new KulXMLDocumentException(e.getMessage());
		} catch (IOException e) {
			throw new KulXMLDocumentException(e.getMessage());
		}catch (ParserConfigurationException e) {
			throw new KulXMLDocumentException(e.getMessage());
		}
	}

	public AKulXMLNode root(){
		return root;
	}
	
	public  void writeXPathNode(final String query, final String node) throws KulXMLDocumentException{
		XPath xPath =  XPathFactory.newInstance().newXPath();				
		Element element = domDoc.createElement(node);
		
		try {
			((Node) xPath.compile(query).evaluate(domDoc, XPathConstants.NODE)).appendChild(element);
		} catch (XPathExpressionException e) {
			LOGGER.error(e.getMessage());
			throw new KulXMLDocumentException("Failed to write xpath node to file");
		}		
	}
	
	public  void writeXPathNodeText(final String query, final String value) throws KulXMLDocumentException{
		XPath xPath =  XPathFactory.newInstance().newXPath();		
		LOGGER.error("writeXPathNodeAttribute(" + query + ", " + value);
		try {
			((Node) xPath.compile(query).evaluate(domDoc, XPathConstants.NODE)).setTextContent(value);;			
		} catch (XPathExpressionException e) {
			LOGGER.error(e.getMessage());
			throw new KulXMLDocumentException("Failed to write xpath text to file");
		}		
	}	
	
	public void writeXPathNodeAttribute(final String query, final String key, final String value) throws KulXMLDocumentException{
		XPath xPath =  XPathFactory.newInstance().newXPath();				
		LOGGER.error("writeXPathNodeAttribute(" + query + ", " + key + " , " + value);
		try {
			((Element) xPath.compile(query).evaluate(domDoc, XPathConstants.NODE)).setAttribute(key, value);			
		} catch (XPathExpressionException e) {
			LOGGER.error(e.getMessage());
			throw new KulXMLDocumentException("Failed to write xpath attribute to file");
		}		
	}
	
	public void save() throws KulXMLDocumentException{
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			Source source = new DOMSource(domDoc);
			Result output = new StreamResult(this.file());
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, output);
		} 
		catch (TransformerConfigurationException e) 		{ e.printStackTrace(); }
		catch (TransformerFactoryConfigurationError e) 	{ e.printStackTrace(); }
		catch (TransformerException e) 							{ e.printStackTrace(); }
	}
}