package com.saxatus.aiml.internal.tags;

import java.util.List;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("thatstar")
public class ThatStarTag extends AbstractAIMLTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return replaceStars(context);
    }

    protected String replaceStars(AIMLParsingSessionContext context)
    {
        int index = Integer.parseInt(super.getOptionalAttribute(context, "index", "1"));

        List<String> replacements = getAIMLHandler(context).getThatStar();
        String value;
        if (replacements.size() > index - 1)
        {
            value = replacements.get(index - 1);
        }
        else
        {
            value = "";
        }
        return value;
    }

}
