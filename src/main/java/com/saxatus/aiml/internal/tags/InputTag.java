package com.saxatus.aiml.internal.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("input")
public class InputTag extends AbstractBotTag
{
    public InputTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        int index = Integer.parseInt(super.getOptionalAttribute("index", "1"));
        List<String> l = getAIMLHandler().getInputHistory();
        if (l.size() >= index)
            return l.get(l.size() - index);
        return "";
    }

}
