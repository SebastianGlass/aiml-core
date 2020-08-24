package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.StringUtils;

@TagName("#text")
public class TextTag extends AbstractAIMLTag
{

    public TextTag(Node node, AIMLParsingSession factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return getNode().getTextContent();
    }

    @Override
    public String getDebugInformation()
    {
        return getTag() + " (" + StringUtils.clearString(getNode().getTextContent()) + ")";
    }

}
