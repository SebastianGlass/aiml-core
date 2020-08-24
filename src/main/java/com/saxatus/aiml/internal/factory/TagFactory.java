package com.saxatus.aiml.internal.factory;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.parsing.TagParameter;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class TagFactory
{

    private final TagParameter parameter;
    private AIMLHandler handler;

    public TagFactory(TagParameter parameter, AIMLHandler handler)
    {
        this.parameter = parameter;
        this.handler = handler;
    }

    public AIMLParseTag createTag(Node node)
    {
        TagSupplier supplier = TagRepository.getByName(node.getNodeName());
        return supplier.supply(node, this);
    }

    public TagParameter getParameter()
    {
        return parameter;
    }

    public AIMLHandler getAIMLHandler()
    {
        return handler;
    }

}
