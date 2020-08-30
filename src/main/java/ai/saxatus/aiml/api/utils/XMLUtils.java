package ai.saxatus.aiml.api.utils;

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
import org.w3c.dom.NodeList;
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

    public static String parseXMLToString(NodeList nodeList) throws TransformerException
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            sb.append(parseXMLToString(nodeList.item(i)));
        }
        return sb.toString();
    }

    public static String parseXMLToString(Node node) throws TransformerException
    {
        StringWriter sw = new StringWriter();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }

    public static Document parseStringToXMLDocument(String xml)
                    throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder dbuilder = documentFactory.newDocumentBuilder();
        return dbuilder.parse(new InputSource(new StringReader(xml)));
    }

    public static Node parseStringToXMLNode(String xml, String enclosing)
                    throws ParserConfigurationException, SAXException, IOException
    {
        Document document = XMLUtils.parseStringToXMLDocument("<" + enclosing + ">" + xml + "</" + enclosing + ">");
        return document.getFirstChild();

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

    private XMLUtils()
    {

    }
}
