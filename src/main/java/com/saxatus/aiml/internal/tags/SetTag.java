package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.NonStaticMemoryUsingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("set")
public class SetTag extends SubNodeContainingTag implements NonStaticMemoryUsingTag
{
    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        String key = getOptionalAttribute(context, "name", "");
        super.handle(context);

        String value = handleSubNodes(context);
        getNonStaticMemory(context).put(key, value);
        return " " + value.replace("  ", " ")
                        .trim() + " ";
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        String key = getOptionalAttribute(context, "name", "");
        return getTag() + " (" + key + ")";
    }

}