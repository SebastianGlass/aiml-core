package com.saxatus.aiml.internal.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;

public class AIMLPatternParserImpl implements AIMLParser
{
    TagFactory tagFactory;

    public AIMLPatternParserImpl(TagFactory fac)
    {
        this.tagFactory = fac;
    }

    @Override
    public String parse(Node node)
    {
        return tagFactory.createTag(node)
                        .handle(getParseNode());
    }

    @Override
    public AIMLParseNode getParseNode()
    {
        return new AIMLParseNode("AIML");
    }

}
