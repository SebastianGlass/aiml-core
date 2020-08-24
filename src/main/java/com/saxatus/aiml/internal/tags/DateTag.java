package com.saxatus.aiml.internal.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("date")
public class DateTag extends AbstractBotTag
{
    private String format;

    public DateTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
        format = getOptionalAttribute("format", "").replace("%Y", "yyyy")
                        .replace("%B", "MM")
                        .replace("%A", "EEE")
                        .replace("%p", "a")
                        .replace("%I", "h");
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);

        SimpleDateFormat df = new SimpleDateFormat(format);

        Date dt = new Date();
        String value = df.format(dt);
        return " " + value + " ";
    }

    @Override
    public String getDebugInformation()
    {
        return getTag() + " (" + format + ")";
    }

}
