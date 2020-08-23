package com.saxatus.aiml.factory;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.saxatus.aiml.utils.XMLUtils;

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
            document = XMLUtils.parseStringToXMLDocument("<aiml>" + template + "</aiml>");
            return document.getFirstChild();
        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
        throw new IOException();

    }

}
