package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.BotMemoryUsingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("bot")
public class BotTag extends AbstractAIMLTag implements BotMemoryUsingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);

        String key = getKey(context);
        String value = getBotMemory(context).get(key);
        if (value == null || value.equals("") || value.equals("Unknown"))
        {
            return "'BOTPROPERTY." + key.toUpperCase() + "'";
        }
        return value;
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        return getTag() + " (" + getKey(context) + ")";
    }

    private String getKey(AIMLParsingSessionContext context)
    {

        return getOptionalAttribute(context, "name", "").toLowerCase();
    }

}
