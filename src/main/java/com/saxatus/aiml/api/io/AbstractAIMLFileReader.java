package com.saxatus.aiml.api.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.utils.XMLUtils;

public abstract class AbstractAIMLFileReader implements AIMLProvider
{
    private static final Log log = LogFactory.getLog(AbstractAIMLFileReader.class);

    private int readingLine = 1;

    protected String fileName;

    protected Document doc;

    protected File file;

    /**
     * A reader for a single.aiml file. This file must syntactically correspond to an xml file, otherwise an exception
     * will be thrown. To get the individual AIML categories, the method {@code withBotMemory} must be called.
     *
     * @param file
     *            a valid .aiml
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public AbstractAIMLFileReader(File file)
    {
        this.file = file;
        this.fileName = file.getAbsolutePath();
    }

    protected AbstractAIMLFileReader(AbstractAIMLFileReader aimlFileReader)
    {
        this.fileName = aimlFileReader.fileName;
        this.doc = aimlFileReader.doc;
        this.file = aimlFileReader.file;
    }

    protected Collection<AIML> loadFromFile(File file) throws ParserConfigurationException, SAXException, IOException
    {
        doc = XMLUtils.parseFileToXMLDocument(file);
        readingLine = 1;
        Collection<AIML> dict = new TreeSet<>();

        NodeList nodeList = doc.getElementsByTagName("aiml")
                        .item(0)
                        .getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }
            String nodeName = nNode.getNodeName();
            if ("category".equals(nodeName))
            {
                addCategory((Element)nNode, dict, file);
            }
            else if ("topic".equals(nodeName))
            {
                handleTopicTag(dict, nNode, file);
            }
        }
        System.out.println("Loading from file " + file.getAbsolutePath() + ": " + dict.size());
        return dict;
    }

    private void addCategory(Element eElement, Collection<AIML> subList, File f)
    {
        addCategory(eElement, null, subList, f);
    }

    private void addCategory(Element eElement, String topic, Collection<AIML> subList, File f)
    {
        String pattern;
        try
        {
            pattern = XMLUtils.parseXMLToString(eElement.getElementsByTagName("pattern")
                            .item(0)
                            .getChildNodes());
        }
        catch(TransformerException e1)
        {
            return;
        }

        pattern = purify(pattern).toUpperCase();
        String template;
        try
        {
            NodeList templateNode = eElement.getElementsByTagName("template")
                            .item(0)
                            .getChildNodes();
            template = purify(XMLUtils.parseXMLToString(templateNode));
        }
        catch(TransformerException e)
        {
            log.warn("Template not parseable", e);
            template = "";
        }
        String that = null;
        NodeList nodeList = eElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            String nodeName = node.getNodeName();
            if ("that".equals(nodeName))
            {
                that = purify(node.getTextContent());
            }
            else if ("topic".equals(nodeName))
            {
                topic = purify(node.getTextContent());
            }

        }
        AIML aiml = new AIML(pattern, template, that, topic, f.getAbsolutePath(), readingLine++);
        subList.add(aiml);
    }

    private void handleTopicTag(Collection<AIML> dict, Node nNode, File f)
    {
        NodeList sub = nNode.getChildNodes();
        for (int i = 0; i < sub.getLength(); i++)
        {
            Node sNode = sub.item(i);
            if (sNode.getNodeType() == Node.ELEMENT_NODE)
            {
                String topic = nNode.getAttributes()
                                .item(0)
                                .getNodeValue();
                addCategory((Element)sNode, topic, dict, f);
            }
        }
    }

    private String purify(String input)
    {
        return input.replace("\r", "")
                        .replace("\n", "")
                        .replace("\t", "")
                        .replace("  ", "");
    }

}
