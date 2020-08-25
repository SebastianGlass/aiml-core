package com.saxatus.aiml.internal.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("date")
public class DateTag extends AbstractAIMLTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {

        super.handle(context);
        String format = getFormat(context);
        SimpleDateFormat df = new SimpleDateFormat(format);

        Date dt = new Date();
        String value = df.format(dt);
        return " " + value + " ";
    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        return getTag() + " (" + getFormat(context) + ")";
    }

    private String getFormat(AIMLParsingSessionContext context)
    {
        return getOptionalAttribute(context, "format", "").replace("%Y", "yyyy")
                        .replace("%B", "MM")
                        .replace("%A", "EEE")
                        .replace("%p", "a")
                        .replace("%I", "h");
    }

}
