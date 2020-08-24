package com.saxatus.aiml.internal.factory;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.saxatus.aiml.api.utils.XMLUtils;

public class AIMLDOMFactory
{
    private static final Log log = LogFactory.getLog(AIMLDOMFactory.class);

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
            log.error(e);
        }
        throw new IOException();

    }

}
