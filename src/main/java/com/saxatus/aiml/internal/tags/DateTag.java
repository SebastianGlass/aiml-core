package com.saxatus.aiml.internal.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public class DateTag extends AbstractBotTag
{

    private static final String TAG = "date";
    private String format;

    private DateTag(Node node, TagFactory factory)
    {
        super(node, factory);
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

    public static void register()
    {
        TagFactory.addTag(TAG, DateTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String getDebugInformation()
    {
        return TAG + " (" + format + ")";
    }

}
