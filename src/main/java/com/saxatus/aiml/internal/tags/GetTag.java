package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("get")
public class GetTag extends AbstractBotTag
{

    private String key;

    public GetTag(Node node, AIMLParsingSession factory)
    {
        super(node, factory);
        key = getOptionalAttribute("name", "").toLowerCase();
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String value = nonStaticMemory.getOrDefault(key, "Unknown");
        return " " + value + " ";
    }

    @Override
    public String getDebugInformation()
    {
        return getTag() + " (" + key + ")";
    }

}
