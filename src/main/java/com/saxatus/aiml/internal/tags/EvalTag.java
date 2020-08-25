package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("eval")
public class EvalTag extends SubNodeContainingTag
{
    // TODO: i think here is logic missing

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return handleSubNodes(context);
    }
}
