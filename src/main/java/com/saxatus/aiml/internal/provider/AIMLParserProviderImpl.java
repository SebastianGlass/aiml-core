package com.saxatus.aiml.internal.provider;

import javax.inject.Inject;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.api.provider.AIMLParsingSessionProvider;
import com.saxatus.aiml.internal.parsing.JaxbAIMLParserImpl;

public class AIMLParserProviderImpl implements AIMLParserProvider
{
    @Inject
    AIMLParsingSessionProvider aimlParsingSessionProvider;


    @Override
    public AIMLParser provideTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode)
    {
        return new JaxbAIMLParserImpl(pattern, input, real, aimlHandler);
        /*
         * TagParameter tp = new TagParameterImpl(input, pattern, real, aimlHandler.getStaticMemory(),
         * aimlHandler.getNonStaticMemory()); AIMLParsingSession session = aimlParsingSessionProvider.create(tp,
         * aimlHandler); return new AIMLTemplateParserImpl(session, parseNode);
         */
    }
}
