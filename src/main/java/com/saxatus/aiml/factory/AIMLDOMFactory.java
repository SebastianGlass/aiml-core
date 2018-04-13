package com.saxatus.aiml.factory;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AIMLDOMFactory
{

    private final String template;

    public AIMLDOMFactory(String template)
    {
        this.template = template;
    }

    public Node getDocumentRoot() throws IOException
    {
        Document document = null;
        try
        {
            document = loadXMLFromString("<aiml>" + template + "</aiml>");
            return document.getFirstChild();
        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
        throw new IOException();

    }

    private Document loadXMLFromString(String xml) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

}
