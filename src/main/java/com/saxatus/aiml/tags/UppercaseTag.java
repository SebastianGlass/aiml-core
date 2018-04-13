package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class UppercaseTag extends AbstractAIMLTag
{

    public UppercaseTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes().toUpperCase();
    }

    private static final String TAG = "uppercase";

    public static void register()
    {
        TagFactory.addTag(TAG, UppercaseTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
