package com.saxatus.aiml.api.tags;

import java.util.Map;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;

public interface NonStaticMemoryUsingTag extends AIMLParseTag
{
    default Map<String, String> getNonStaticMemory(AIMLParsingSessionContext c)
    {
        return getSession(c).getParameter()
                        .getNonStaticMemory();
    }

}
