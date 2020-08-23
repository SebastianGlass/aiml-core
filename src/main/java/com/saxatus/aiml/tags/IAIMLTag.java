package com.saxatus.aiml.tags;

import com.saxatus.aiml.parsing.AIMLParseNode;

public interface IAIMLTag
{

    String handle(AIMLParseNode debugNode);

    String getTag();

    default String getDebugInformation()
    {
        return this.getTag();
    }

}
