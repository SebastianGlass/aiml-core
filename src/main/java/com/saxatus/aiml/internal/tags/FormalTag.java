package com.saxatus.aiml.internal.tags;

import org.apache.commons.lang3.text.WordUtils;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.StarReplacingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("formal")
public class FormalTag extends AbstractAIMLTag implements StarReplacingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return (WordUtils.capitalize(replaceStars(context)));
    }
}
