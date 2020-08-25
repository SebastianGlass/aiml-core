package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("aiml")
public class AIMLTag extends SubNodeContainingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return handleSubNodes(context);
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        return getTag() + " (" + getSession(context).getParameter()
                        .getPattern() + ")";
    }

}
