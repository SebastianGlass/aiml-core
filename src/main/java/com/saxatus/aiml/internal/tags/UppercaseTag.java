package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("uppercase")
public class UppercaseTag extends SubNodeContainingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return handleSubNodes(context).toUpperCase();
    }

}
