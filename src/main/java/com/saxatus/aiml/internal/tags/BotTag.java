package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.TagName;

@TagName("bot")
public class BotTag extends AbstractBotTag
{

    private String key;

    public BotTag(Node node, TagFactory factory)
    {
        super(node, factory);
        key = getOptionalAttribute("name", "").toLowerCase();
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);

        String value = botMemory.get(key);
        if (value == null || value.equals("") || value.equals("Unknown"))
        {
            return "'BOTPROPERTY." + key.toUpperCase() + "'";
        }
        return value;
    }

    @Override
    public String getDebugInformation()
    {
        return getTag() + " (" + key + ")";
    }

}
