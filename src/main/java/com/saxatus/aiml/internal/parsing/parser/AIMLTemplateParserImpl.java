package com.saxatus.aiml.internal.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;

public class AIMLTemplateParserImpl implements AIMLParser
{
    private TagFactory tagFactory;
    private AIMLParseNode parseNode;

    public AIMLTemplateParserImpl(TagFactory fac, AIMLParseNode parseNode)
    {
        this.tagFactory = fac;
        this.parseNode = parseNode;
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
        return parseNode;
    }

}
