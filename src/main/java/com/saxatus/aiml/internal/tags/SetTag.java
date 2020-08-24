package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;

@TagName("set")
public class SetTag extends AbstractBotTag
{

    private String key;

    public SetTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
        key = getOptionalAttribute("name", "");
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);

        String value = handleSubNodes();
        nonStaticMemory.put(key, value);
        return " " + value.replace("  ", " ")
                        .trim() + " ";
    }

    @Override
    public String getDebugInformation()
    {
        return getTag() + " (" + key + ")";
    }

}