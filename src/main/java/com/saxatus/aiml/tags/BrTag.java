package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class BrTag extends AbstractAIMLTag
{

    public BrTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return "";
    }

    private static final String TAG = "br";

    public static void register()
    {
        TagFactory.addTag(TAG, BrTag::new);
        TagFactory.addTag("#comment", BrTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
