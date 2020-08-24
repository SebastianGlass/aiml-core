package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("sr")
public class SrTag extends StarTag
{

    public SrTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String result;

        result = resolvePattern(resolveStars(request, pattern).get(0));
        return result;
    }

    private String resolvePattern(String pattern)
    {
        if (SraiTag.wasTraversed(pattern))
            return ":(";
        SraiTag.addTraversed(pattern);
        return getAIMLHandler().getAnswer(pattern, getAIMLParseNode());
    }

}
