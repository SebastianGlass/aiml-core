package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("think")
public class ThinkTag extends SubNodeContainingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        handleSubNodes(context);

        return "";
    }

}
