package com.saxatus.aiml.internal.tags;

import org.w3c.dom.NamedNodeMap;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.AttributeContainingTag;
import com.saxatus.aiml.api.tags.TagName;

public abstract class AbstractAIMLTag implements AttributeContainingTag
{

    protected AIMLParseNode debugNode;

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        if (null == this.debugNode)
        {
            this.debugNode = new AIMLParseNode(this.getDebugInformation(context));
        }
        getDebugNode(context).addChild(this.debugNode);
        return ("Exception: not overwritten!");
    }

    public String getOptionalAttribute(AIMLParsingSessionContext context, String string, String i)
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

    @Override
    public String getTag()
    {
        TagName[] names = this.getClass()
                        .getAnnotationsByType(TagName.class);
        return names.length > 0 ? names[0].value() : "UNKNOWN";
    }

}
