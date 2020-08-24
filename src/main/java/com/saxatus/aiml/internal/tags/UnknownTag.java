package com.saxatus.aiml.internal.tags;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.XMLUtils;

@TagName("unknown")
public class UnknownTag extends AbstractAIMLTag
{

    public UnknownTag(Node node, AIMLParsingSession factory)
    {
        super(node, factory);
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

        String plainNode;
        try
        {
            plainNode = XMLUtils.parseXMLToString(getNode());
        }
        catch(TransformerException e)
        {
            plainNode = "";
        }
        String openTag;
        if (!plainNode.contains("/>"))
        {
            openTag = plainNode.substring(0, plainNode.indexOf('>')) + ">";
        }
        else
        {
            openTag = plainNode.substring(0, plainNode.indexOf("/>")) + ">";
        }
        String closeTag = "</" + getNode().getNodeName() + ">";
        return openTag + result + closeTag;
    }

}
