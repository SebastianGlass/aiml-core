package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class ThinkTag extends AbstractAIMLTag
{

    public ThinkTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    private static final String TAG = "think";

    public static void register()
    {
        TagRepository.addTag(TAG, ThinkTag::new);
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
