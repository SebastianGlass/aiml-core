package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class BotTag extends AbstractBotTag
{

    private static final String TAG = "bot";
    private String key;

    private BotTag(Node node, TagFactory factory)
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

    public static void register()
    {
        TagFactory.addTag(TAG, BotTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String getDebugInformation()
    {
        return TAG + " (" + key + ")";
    }

}
