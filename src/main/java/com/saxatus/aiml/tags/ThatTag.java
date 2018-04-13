package com.saxatus.aiml.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class ThatTag extends AbstractBotTag
{

    private ThatTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "that";

    public static void register()
    {
        TagFactory.addTag(TAG, ThatTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String string = super.getOptionalAttribute("index", "1");
        if (string.contains(","))
        {
            // TODO: other behavior
            string = string.split(",")[0];
        }
        int index = Integer.parseInt(string);

        List<String> l = getAIMLHandler().getOutputHistory();
        if (l.size() >= index)
            return l.get(l.size() - index);
        return "";
    }

}
