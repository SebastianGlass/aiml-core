package com.saxatus.aiml.internal.factory;

import java.util.Map;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.internal.parsing.TagParameter;
import com.saxatus.aiml.internal.parsing.parser.AIMLPatternParserImpl;
import com.saxatus.aiml.internal.parsing.parser.AIMLTemplateParserImpl;

public class AIMLParserFactoryImpl implements AIMLParserFactory
{
    @Override
    public AIMLParser createPatternParser(Map<String, String> botMemory)
    {
        TagParameter tp = new TagParameter("", "", "", botMemory, null);
        TagFactory tagFactory = new TagFactory(tp, null);
        return new AIMLPatternParserImpl(tagFactory);
    }

    @Override
    public AIMLParser createTemplateParser(String pattern, String input, String real, AIMLHandler aimlHandler,
                    AIMLParseNode parseNode)
    {
        TagParameter tp = new TagParameter(input, pattern, real, aimlHandler.getStaticMemory(),
                        aimlHandler.getNonStaticMemory());
        TagFactory factory = new TagFactory(tp, aimlHandler);

        return new AIMLTemplateParserImpl(factory, parseNode);
    }
}
