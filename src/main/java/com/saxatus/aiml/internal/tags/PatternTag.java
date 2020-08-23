package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public class PatternTag extends AbstractAIMLTag
{

    private PatternTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "pattern";

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes();
    }

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
