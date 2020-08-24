package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

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
        TagRepository.addTag(TAG, BrTag::new);
        TagRepository.addTag("#comment", BrTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
