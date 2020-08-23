package com.saxatus.aiml;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIML;
import com.saxatus.aiml.parsing.AIMLParseNode;
import com.saxatus.aiml.parsing.TagParameter;
import com.saxatus.aiml.utils.XMLUtils;

public class AIMLFileReader implements AutoCloseable
{
    private int readingLine = 1;

    private String fileName;

    private Document doc;

    private Map<String, String> botMemory;

    /**
     * A reader for a single.aiml file. This file must syntatically correspond to an xml file, otherwise an exception
     * will be thrown. To get the individual AIML categories, the method {@code withBotMemory} must be called.
     *
     * @param file
     *            a valid .aiml
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public AIMLFileReader(File file) throws ParserConfigurationException, SAXException, IOException
    {
        this.fileName = file.getAbsolutePath();
        this.doc = XMLUtils.parseFileToXMLDocument(file);
    }

    private Collection<AIML> loadFromFile()
    {
        readingLine = 1;
        Collection<AIML> dict = new HashSet<>();

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
                addCategory((Element)nNode, dict);
            }
            else if ("topic".equals(nodeName))
            {
                handleTopicTag(dict, nNode);
            }
        }
        return dict;
    }

    private void addCategory(Element eElement, Collection<AIML> subList) throws DOMException
    {
        addCategory(eElement, null, subList);
    }

    private void addCategory(Element eElement, String topic, Collection<AIML> subList) throws DOMException
    {

        TagParameter tp = new TagParameter("", "", "", botMemory, null);
        String pattern = new TagFactory(tp, null).createTag(eElement.getElementsByTagName("pattern")
                        .item(0))
                        .handle(new AIMLParseNode("AIML"));
        pattern = purify(pattern).toUpperCase();
        String template;
        try
        {
            template = purify(XMLUtils.parseXMLToString(eElement.getElementsByTagName("template")
                            .item(0)));
        }
        catch(TransformerException e)
        {
            // TODO: error logging
            template = "";
        }
        String that = null;
        NodeList nodeList = eElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            switch(node.getNodeName())
            {
                case "that":
                    that = purify(node.getTextContent());

                    break;
                case "topic":
                    topic = purify(node.getTextContent());
                    break;

            }

        }
        AIML aiml = new AIML(pattern, template, that, topic, fileName, readingLine++);
        subList.add(aiml);
    }

    private void handleTopicTag(Collection<AIML> dict, Node nNode)
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
                addCategory((Element)sNode, topic, dict);
            }
        }
    }

    private String purify(String input)
    {
        return input.replaceAll("\r", "")
                        .replaceAll("\n", "")
                        .replaceAll("\t", "")
                        .replaceAll("  ", "");
    }

    public StreamableAIMLFileReader withBotMemory(Map<String, String> map)
    {
        this.botMemory = map;
        return new StreamableAIMLFileReader();

    }

    public class StreamableAIMLFileReader
    {
        public StreamableAIMLFileReader()
        {
        }

        public Stream<AIML> stream()
        {
            return loadFromFile().stream();
        }
    }

    @Override
    public void close() throws Exception
    {
        // TODO Auto-generated method stub

    }
}
