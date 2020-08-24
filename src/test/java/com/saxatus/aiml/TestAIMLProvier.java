package com.saxatus.aiml;

import java.util.Collection;
import java.util.List;

import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.io.AIMLProvider;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParser;

public class TestAIMLProvier implements AIMLProvider
{
    private List<AIML> aimlList;

    public TestAIMLProvier(List<AIML> aimlList)
    {
        this.aimlList = aimlList;
    }

    @Override
    public Collection<AIML> provide(AIMLParser aimlParser) throws AIMLCreationException
    {
        return aimlList;
    }
}