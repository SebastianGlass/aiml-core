package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("uppercase")
public class UppercaseTag extends AbstractAIMLTag
{

    public UppercaseTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes().toUpperCase();
    }

}
