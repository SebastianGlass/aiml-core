package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.StarReplacingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("sr")
public class SrTag extends AbstractAIMLTag implements StarReplacingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String result;
        String request = getSession(context).getParameter()
                        .getRequest();
        String pattern = getSession(context).getParameter()
                        .getPattern();
        result = resolvePattern(context, resolveStars(request, pattern).get(0));
        return result;
    }

    private String resolvePattern(AIMLParsingSessionContext context, String pattern)
    {
      /*  if (SraiTag.wasTraversed(pattern))
            return ":(";
        SraiTag.addTraversed(pattern);
        return getAIMLHandler(context).getAnswer(pattern, debugNode);*/
        return null;
    }

}
