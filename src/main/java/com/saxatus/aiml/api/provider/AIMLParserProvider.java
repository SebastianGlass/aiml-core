package com.saxatus.aiml.api.provider;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLParserProvider
{


    AIMLParser provideTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode);
}
