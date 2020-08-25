package com.saxatus.aiml.internal.tags;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.StringUtils;

@TagName("#text")
public class TextTag extends AbstractAIMLTag
{
    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        return getXMLNode(context).getTextContent();
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        return getTag() + " (" + StringUtils.clearString(getXMLNode(context).getTextContent()) + ")";
    }

}
