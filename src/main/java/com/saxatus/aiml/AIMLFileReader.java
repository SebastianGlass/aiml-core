package com.saxatus.aiml;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

public class AIMLFileReader implements AutoCloseable
{
    private int readingLine = 1;

    private String fileName;
    private static Transformer xmlTransformer;
    static
    {
        try
        {
            xmlTransformer = TransformerFactory.newInstance()
                            .newTransformer();
            xmlTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

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
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(file);
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
            // there are two possible kinds of nodes in an aiml tag:
            // * category : descripes a I/O pair, with optional topic and that field
            // * topic : works as wrapper around other category tags to create a default topic for this pairings
            switch(nNode.getNodeName())
            {
                case "category":
                    addCategory((Element)nNode, dict);
                    break;
                case "topic":
                    handleTopicTag(dict, nNode);
                    break;
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
        String template = purify(nodeToString(eElement.getElementsByTagName("template")
                        .item(0)));
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

    private static String nodeToString(Node node)
    {
        StringWriter sw = new StringWriter();
        try
        {
            xmlTransformer.transform(new DOMSource(node), new StreamResult(sw));
        }
        catch(TransformerException te)
        {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
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
