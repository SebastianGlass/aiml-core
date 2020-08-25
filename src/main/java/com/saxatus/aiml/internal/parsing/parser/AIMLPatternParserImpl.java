package com.saxatus.aiml.internal.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionContextImpl;

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
        AIMLParseTag tag = session.createTag(node);
        AIMLParsingSessionContextImpl context = new AIMLParsingSessionContextImpl(new AIMLParseNode("AIML"), node,
                        session);
        return tag.handle(context);
    }

}
