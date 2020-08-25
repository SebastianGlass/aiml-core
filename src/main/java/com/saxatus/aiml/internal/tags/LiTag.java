package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("li")
public class LiTag extends SubNodeContainingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return handleSubNodes(context);
    }
}
