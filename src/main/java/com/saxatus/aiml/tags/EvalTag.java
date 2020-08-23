package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class EvalTag extends AIMLTag
{

    private EvalTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "eval";

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        // TODO DO SOMETHING
        return super.handle(debugNode);
    }

    public static void register()
    {
        TagFactory.addTag(TAG, EvalTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
