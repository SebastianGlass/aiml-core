package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.StarReplacingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("star")
public class StarTag extends AbstractAIMLTag implements StarReplacingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return this.replaceStars(context);
    }

}
