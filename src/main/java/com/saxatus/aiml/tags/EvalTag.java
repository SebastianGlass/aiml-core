package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;

public class EvalTag extends AIMLTag
{

    private EvalTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "eval";

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
