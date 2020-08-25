package com.saxatus.aiml.api.tags;

import org.w3c.dom.NamedNodeMap;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;

public interface AttributeContainingTag extends AIMLParseTag
{
    default String getOptionalAttribute(AIMLParsingSessionContext context, String string, String i)
    {
        NamedNodeMap attributes = getXMLNode(context).getAttributes();
        String index = i;
        if (attributes.getLength() != 0)
        {
            index = getXMLNode(context).getAttributes()
                            .getNamedItem(string)
                            .getNodeValue();
        }

        return index;
    }
}
