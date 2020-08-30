package com.saxatus.aiml.api.parsing.tags;

import java.util.Map;

public interface NonStaticMemoryUsingNode
{
    void setNonStaticMemory(Map<String, String> memory);
}
