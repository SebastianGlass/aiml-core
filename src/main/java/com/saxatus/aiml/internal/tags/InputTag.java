package com.saxatus.aiml.internal.tags;

import java.util.List;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("input")
public class InputTag extends AbstractAIMLTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        int index = Integer.parseInt(super.getOptionalAttribute(context, "index", "1"));
        List<String> l = getAIMLHandler(context).getInputHistory();
        if (l.size() >= index)
            return l.get(l.size() - index);
        return "";
    }

}
