package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class SrTag extends StarTag
{

    private SrTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "sr";

    public static void register()
    {
        TagRepository.addTag(TAG, SrTag::new);
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
        String result;

        result = resolvePattern(resolveStars(request, pattern).get(0));
        return result;
    }

    private String resolvePattern(String pattern)
    {
        if (SraiTag.wasTraversed(pattern))
            return ":(";
        SraiTag.addTraversed(pattern);
        return getAIMLHandler().getAnswer(pattern, getAIMLParseNode());
    }

}
