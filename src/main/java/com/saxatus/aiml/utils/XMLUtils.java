package com.saxatus.aiml.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils
{
    private static TransformerFactory transformerFactory;
    private static DocumentBuilderFactory documentFactory;

    static
    {
        transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        documentFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    }

    private XMLUtils()
    {

    }

    public static String parseXMLToString(Node node) throws TransformerException
    {
        StringWriter sw = new StringWriter();
        Transformer t = transformerFactory.newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();

    }

    public static Document parseStringToXMLDocument(String xml)
                    throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder builder = documentFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static Document parseFileToXMLDocument(File file)
                    throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder dBuilder = documentFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    public static void writeXMLDocumentToFile(Document doc, File output) throws TransformerException
    {
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(output));
    }
}