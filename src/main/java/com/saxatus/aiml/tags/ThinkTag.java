package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class ThinkTag extends AbstractAIMLTag
{

    public ThinkTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    private static final String TAG = "think";

    public static void register()
    {
        TagFactory.addTag(TAG, ThinkTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        handleSubNodes();

        return "";
    }

}
