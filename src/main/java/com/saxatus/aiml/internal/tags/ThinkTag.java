package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.TagName;

@TagName("think")
public class ThinkTag extends AbstractAIMLTag
{

    public ThinkTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        handleSubNodes();

        return "";
    }

}
