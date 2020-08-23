package com.saxatus.aiml.internal.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public class ThatStarTag extends AbstractBotTag
{

    protected ThatStarTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    private static final String TAG = "thatstar";

    public static void register()
    {
        TagFactory.addTag(TAG, ThatStarTag::new);
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
        return replaceStars();
    }

    protected String replaceStars()
    {
        int index = Integer.parseInt(super.getOptionalAttribute("index", "1"));

        List<String> replacements = getAIMLHandler().getThatStar();
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
