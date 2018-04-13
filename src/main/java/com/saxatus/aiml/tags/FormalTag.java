package com.saxatus.aiml.tags;

import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class FormalTag extends StarTag
{

    private FormalTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String context = replaceStars();
        return (WordUtils.capitalize(context));
    }

    private static final String TAG = "formal";

    public static void register()
    {
        TagFactory.addTag(TAG, FormalTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
