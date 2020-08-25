package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.NonStaticMemoryUsingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("get")
public class GetTag extends AbstractAIMLTag implements NonStaticMemoryUsingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String key = getOptionalAttribute(context, "name", "").toLowerCase();
        String value = getNonStaticMemory(context).getOrDefault(key, "Unknown");
        return " " + value + " ";
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        String key = getOptionalAttribute(context, "name", "").toLowerCase();
        return getTag() + " (" + key + ")";
    }

}
