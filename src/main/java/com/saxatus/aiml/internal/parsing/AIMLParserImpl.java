package com.saxatus.aiml.internal.parsing;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.internal.factory.TagFactory;

public class AIMLParserImpl implements AIMLParser
{
    TagFactory tagFactory;

    public AIMLParserImpl(TagFactory fac)
    {
        this.tagFactory = fac;
    }

    @Override
    public String parse(Node node)
    {
        AIMLParseNode debugNode = new AIMLParseNode("AIML");
        return tagFactory.createTag(node)
                        .handle(debugNode);
    }

}
