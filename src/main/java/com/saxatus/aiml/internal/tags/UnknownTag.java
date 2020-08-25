package com.saxatus.aiml.internal.tags;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.XMLUtils;

@TagName("unknown")
public class UnknownTag extends SubNodeContainingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String result = handleSubNodes(context);
        Node node = getXMLNode(context);

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
            plainNode = XMLUtils.parseXMLToString(node);
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
        String closeTag = "</" + node.getNodeName() + ">";
        return openTag + result + closeTag;
    }

}
