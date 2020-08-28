package com.saxatus.aiml.internal.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionContextImpl;

public class AIMLTemplateParserImpl implements AIMLParser
{
    private AIMLParsingSession session;
    private AIMLParseNode parseNode;

    public AIMLTemplateParserImpl(AIMLParsingSession session, AIMLParseNode parseNode)
    {
        this.session = session;
        this.parseNode = parseNode;
    }

    @Override
    public String parse(Node node)
    {
        return session.createTag(node)
                        .handle(new AIMLParsingSessionContextImpl(parseNode, node, session));
    }

}