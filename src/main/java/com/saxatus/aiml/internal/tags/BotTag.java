package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

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
        TagRepository.addTag(TAG, BotTag::new);
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
