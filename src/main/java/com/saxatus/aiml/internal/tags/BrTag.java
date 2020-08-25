package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("br")
@TagName("#comment")
public class BrTag extends AbstractAIMLTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return "";
    }

}
