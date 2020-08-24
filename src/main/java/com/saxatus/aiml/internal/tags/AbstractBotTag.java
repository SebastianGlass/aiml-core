package com.saxatus.aiml.internal.tags;

import java.util.Map;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParsingSession;

public abstract class AbstractBotTag extends AbstractAIMLTag
{

    protected Map<String, String> botMemory;
    protected Map<String, String> nonStaticMemory;

    public AbstractBotTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
        botMemory = getSession().getParameter()
                        .getBotMemory();

        nonStaticMemory = getSession().getParameter()
                        .getNonStaticMemory();
    }

}
