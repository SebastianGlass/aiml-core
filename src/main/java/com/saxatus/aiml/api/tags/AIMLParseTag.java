package com.saxatus.aiml.api.tags;

import com.saxatus.aiml.api.parsing.AIMLParseNode;

public interface AIMLParseTag
{

    String handle(AIMLParseNode debugNode);

    String getTag();

    default String getDebugInformation()
    {
        return this.getTag();
    }

}
