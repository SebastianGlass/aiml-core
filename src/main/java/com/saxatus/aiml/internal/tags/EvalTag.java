package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class EvalTag extends AbstractAIMLTag
{

    private EvalTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "eval";

    public static void register()
    {
        TagRepository.addTag(TAG, EvalTag::new);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes();
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
