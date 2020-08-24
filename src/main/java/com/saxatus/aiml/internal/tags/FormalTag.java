package com.saxatus.aiml.internal.tags;

import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

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
        TagRepository.addTag(TAG, FormalTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
