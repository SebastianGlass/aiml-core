package com.saxatus.aiml.internal.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("that")
public class ThatTag extends AbstractBotTag
{

    public ThatTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
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
