package com.saxatus.aiml.api.tags;

import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public interface AIMLParseTag
{

    String handle(AIMLParseNode debugNode);

    String getTag();

    default String getDebugInformation()
    {
        return this.getTag();
    }

}
