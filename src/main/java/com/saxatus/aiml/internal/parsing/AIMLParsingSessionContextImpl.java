package com.saxatus.aiml.internal.parsing;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;

public class AIMLParsingSessionContextImpl implements AIMLParsingSessionContext
{
    private final AIMLParseNode debugNode;
    private final Node xmlNode;
    private final AIMLParsingSession session;

    public AIMLParsingSessionContextImpl(AIMLParseNode debugNode, Node xmlNode, AIMLParsingSession session)
    {
        this.debugNode = debugNode;
        this.xmlNode = xmlNode;
        this.session = session;
    }

    @Override
    public AIMLParseNode getDebugNode()
    {
        return debugNode;
    }

    @Override
    public AIMLParsingSession getSession()
    {
        return session;
    }

    @Override
    public Node getXMLNode()
    {
        return xmlNode;
    }

    @Override
    public AIMLParsingSessionContext of(AIMLParseNode pNode, Node xmlNode)
    {
        return new AIMLParsingSessionContextImpl(pNode, xmlNode, session);
    }

}
