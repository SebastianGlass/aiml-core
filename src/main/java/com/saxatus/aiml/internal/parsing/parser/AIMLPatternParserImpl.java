package com.saxatus.aiml.internal.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;

public class AIMLPatternParserImpl implements AIMLParser
{
    AIMLParsingSession session;

    public AIMLPatternParserImpl(AIMLParsingSession session)
    {
        this.session = session;
    }

    @Override
    public String parse(Node node)
    {
        return session.createTag(node)
                        .handle(getParseNode());
    }

    @Override
    public AIMLParseNode getParseNode()
    {
        return new AIMLParseNode("AIML");
    }

}
