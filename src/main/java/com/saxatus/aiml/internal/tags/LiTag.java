package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

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
        TagRepository.addTag(TAG, LiTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
