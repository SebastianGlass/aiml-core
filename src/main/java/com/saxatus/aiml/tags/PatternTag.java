package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;

public class PatternTag extends AIMLTag
{

    private PatternTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "pattern";

    public static void register()
    {
        TagFactory.addTag(TAG, PatternTag::new);
        TagFactory.addTag("template", PatternTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
