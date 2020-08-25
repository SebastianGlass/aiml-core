package com.saxatus.aiml.internal.tags;

import java.util.List;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("that")
public class ThatTag extends AbstractAIMLTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String string = super.getOptionalAttribute(context, "index", "1");
        if (string.contains(","))
        {
            // TODO: other behavior
            string = string.split(",")[0];
        }
        int index = Integer.parseInt(string);

        List<String> l = getAIMLHandler(context).getOutputHistory();
        if (l.size() >= index)
            return l.get(l.size() - index);
        return "";
    }

}
