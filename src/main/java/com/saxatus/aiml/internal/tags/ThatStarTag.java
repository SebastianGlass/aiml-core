package com.saxatus.aiml.internal.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("thatstar")
public class ThatStarTag extends AbstractBotTag
{

    public ThatStarTag(Node node, AIMLParsingSession session)
    {
        super(node, session);

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
