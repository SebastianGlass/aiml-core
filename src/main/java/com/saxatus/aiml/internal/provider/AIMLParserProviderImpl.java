package com.saxatus.aiml.internal.provider;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.internal.parsing.parser.JaxbAIMLParserImpl;

public class AIMLParserProviderImpl implements AIMLParserProvider
{

    @Override
    public AIMLParser provideTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler)
    {
        return new JaxbAIMLParserImpl(pattern, input, aimlHandler);

    }
}
