package com.saxatus.aiml.internal.provider;

import java.util.Map;

import javax.inject.Inject;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSession.TagParameter;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.api.provider.AIMLParsingSessionProvider;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionImpl.TagParameterImpl;
import com.saxatus.aiml.internal.parsing.parser.AIMLPatternParserImpl;
import com.saxatus.aiml.internal.parsing.parser.AIMLTemplateParserImpl;

public class AIMLParserProviderImpl implements AIMLParserProvider
{
    @Inject
    AIMLParsingSessionProvider tagFactoryProvider;

    @Override
    public AIMLParser providePatternParser(Map<String, String> botMemory)
    {
        TagParameter tp = new TagParameterImpl("", "", "", botMemory, null);
        AIMLParsingSession tagFactory = tagFactoryProvider.create(tp, null);
        return new AIMLPatternParserImpl(tagFactory);
    }

    @Override
    public AIMLParser provideTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode)
    {
        TagParameter tp = new TagParameterImpl(input, pattern, real, aimlHandler.getStaticMemory(),
                        aimlHandler.getNonStaticMemory());
        AIMLParsingSession factory = tagFactoryProvider.create(tp, aimlHandler);

        return new AIMLTemplateParserImpl(factory, parseNode);
    }
}
