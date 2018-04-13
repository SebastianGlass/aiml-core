package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class SrTag extends StarTag
{

    private SrTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "sr";

    public static void register()
    {
        TagFactory.addTag(TAG, SrTag::new);
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

        result = resolvePattern(resolveStars(request, pattern).get(0), debugNode);
        return result;
    }

    private String resolvePattern(String pattern, AIMLParseNode debugNode)
    {
        if (SraiTag.wasTraversed(pattern))
            return ":(";
        SraiTag.addTraversed(pattern);
        return getAIMLHandler().getAnswer(pattern, getAIMLParseNode());
    }

}
