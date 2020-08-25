package com.saxatus.aiml.api.parsing;

import org.w3c.dom.Node;

public interface AIMLParsingSessionContext
{

    AIMLParseNode getDebugNode();

    Node getXMLNode();

    AIMLParsingSession getSession();

    AIMLParsingSessionContext of(AIMLParseNode pNode, Node xmlNode);
}
