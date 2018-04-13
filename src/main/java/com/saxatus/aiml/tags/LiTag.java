package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class LiTag extends AbstractAIMLTag
{

    private LiTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes();
    }

    private static final String TAG = "li";

    public static void register()
    {
        TagFactory.addTag(TAG, LiTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
