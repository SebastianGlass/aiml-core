package com.saxatus.aiml.api.factory;

import java.io.IOException;
import java.util.Map;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLParserFactory
{

    AIMLParser createPatternParser(Map<String, String> botMemory);

    AIMLParser createTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode) throws IOException;
}
