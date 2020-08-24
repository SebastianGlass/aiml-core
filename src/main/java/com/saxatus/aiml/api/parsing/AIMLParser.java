package com.saxatus.aiml.api.parsing;

import org.w3c.dom.Node;

public interface AIMLParser
{
    String parse(Node node);

    AIMLParseNode getParseNode();
}
