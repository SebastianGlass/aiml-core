package com.saxatus.aiml.api.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;

public interface AIMLParseTag
{

    String handle(AIMLParsingSessionContext context);

    String getTag();

    default String getDebugInformation(AIMLParsingSessionContext c)
    {
        return this.getTag();
    }

    default AIMLParsingSession getSession(AIMLParsingSessionContext c)
    {
        return c.getSession();
    }

    default Node getXMLNode(AIMLParsingSessionContext c)
    {
        return c.getXMLNode();
    }

    default AIMLParseNode getDebugNode(AIMLParsingSessionContext c)
    {
        return c.getDebugNode();
    }

    default AIMLHandler getAIMLHandler(AIMLParsingSessionContext c)
    {
        return getSession(c).getAIMLHandler();
    }

}
