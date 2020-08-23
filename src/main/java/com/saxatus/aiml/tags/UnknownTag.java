package com.saxatus.aiml.tags;

import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class UnknownTag extends AbstractAIMLTag
{

    private TransformerFactory transformerFactory;

    private UnknownTag(Node node, TagFactory factory)
    {
        super(node, factory);
        transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    }

    private static final String TAG = "unknown";

    public static void register()
    {
        TagFactory.addTag(TAG, UnknownTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String result = handleSubNodes();
        Node node = getNode();

        do
        {
            NodeList childNodes = node.getChildNodes();
            if (childNodes.getLength() >= 1)
            {
                Node childNode = childNodes.item(0);
                node.removeChild(childNode);
            }
        }
        while(node.getChildNodes()
                        .getLength() >= 1);

        String plainNode = nodeToString(getNode());
        String openTag;
        if (!plainNode.contains("/>"))
        {
            openTag = plainNode.substring(0, plainNode.indexOf(">")) + ">";
        }
        else
        {
            openTag = plainNode.substring(0, plainNode.indexOf("/>")) + ">";
        }
        String closeTag = "</" + getNode().getNodeName() + ">";
        return openTag + result + closeTag;
    }

    private String nodeToString(Node node)
    {
        StringWriter sw = new StringWriter();
        try
        {
            Transformer t = transformerFactory.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        }
        catch(TransformerException te)
        {
            System.err.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

}
