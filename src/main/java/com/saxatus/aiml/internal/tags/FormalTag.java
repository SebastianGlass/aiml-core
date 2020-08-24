package com.saxatus.aiml.internal.tags;

import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("formal")
public class FormalTag extends StarTag
{

    public FormalTag(Node node, AIMLParsingSession factory)
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
}
