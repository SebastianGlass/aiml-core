package com.saxatus.aiml.internal.factory;

import java.util.Map;

import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.internal.parsing.AIMLParserImpl;
import com.saxatus.aiml.internal.parsing.TagParameter;

public class AIMLParserFactoryImpl implements AIMLParserFactory
{
    @Override
    public AIMLParser createPatternParser(Map<String, String> botMemory)
    {
        TagParameter tp = new TagParameter("", "", "", botMemory, null);
        TagFactory tagFactory = new TagFactory(tp, null);
        return new AIMLParserImpl(tagFactory);
    }
}
