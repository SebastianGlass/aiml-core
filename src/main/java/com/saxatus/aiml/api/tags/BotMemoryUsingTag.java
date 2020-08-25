package com.saxatus.aiml.api.tags;

import java.util.Map;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;

public interface BotMemoryUsingTag extends AIMLParseTag
{
    default Map<String, String> getBotMemory(AIMLParsingSessionContext c)
    {
        return getSession(c).getParameter()
                        .getBotMemory();
    }

}
