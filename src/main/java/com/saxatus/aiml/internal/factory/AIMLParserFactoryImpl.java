package com.saxatus.aiml.internal.factory;

import java.util.Map;

import javax.inject.Inject;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.factory.TagFactory.TagParameter;
import com.saxatus.aiml.api.factory.TagFactoryProvider;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.internal.factory.TagFactoryImpl.TagParameterImpl;
import com.saxatus.aiml.internal.parsing.parser.AIMLPatternParserImpl;
import com.saxatus.aiml.internal.parsing.parser.AIMLTemplateParserImpl;

public class AIMLParserFactoryImpl implements AIMLParserFactory
{
    @Inject
    TagFactoryProvider tagFactoryProvider;

    @Override
    public AIMLParser createPatternParser(Map<String, String> botMemory)
    {
        TagParameter tp = new TagParameterImpl("", "", "", botMemory, null);
        TagFactory tagFactory = tagFactoryProvider.create(tp, null);
        return new AIMLPatternParserImpl(tagFactory);
    }

    @Override
    public AIMLParser createTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode)
    {
        TagParameter tp = new TagParameterImpl(input, pattern, real, aimlHandler.getStaticMemory(),
                        aimlHandler.getNonStaticMemory());
        TagFactory factory = tagFactoryProvider.create(tp, aimlHandler);

        return new AIMLTemplateParserImpl(factory, parseNode);
    }
}
