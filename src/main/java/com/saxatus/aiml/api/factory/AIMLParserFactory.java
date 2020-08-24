package com.saxatus.aiml.api.factory;

import java.util.Map;

import com.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLParserFactory
{

    AIMLParser createPatternParser(Map<String, String> botMemory);
}
