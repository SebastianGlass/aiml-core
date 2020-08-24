package com.saxatus.aiml.internal;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.factory.AIMLHandlerFactory;
import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.io.AIMLProvider;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParser;

public class AIMLHandlerBuilderImpl implements AIMLHandlerBuilder
{
    private AIMLHandlerFactory aimlHandlerFactory;
    private AIMLParserFactory aimlParserFactory;

    private AIMLProvider aimlProvider;

    private Map<String, String> nonStaticMemory = new HashMap<>();
    private Map<String, String> botMemory = new HashMap<>();
    private File learnFile = new File("./temp.aiml");

    @Inject
    public AIMLHandlerBuilderImpl(AIMLHandlerFactory aimlHandlerFactory, AIMLParserFactory aimlParserFactory)
    {
        this.aimlHandlerFactory = aimlHandlerFactory;
        this.aimlParserFactory = aimlParserFactory;
    }

    public AIMLHandlerBuilderImpl withLearnFile(File file)
    {
        this.learnFile = file;
        return this;
    }

    public AIMLHandlerBuilderWithAimlsImpl withAiml(AIMLProvider provider)
    {
        this.aimlProvider = provider;
        return new AIMLHandlerBuilderWithAimlsImpl();
    }

    public AIMLHandlerBuilderImpl nonStaticMemory(Map<String, String> nonStaticMemory)
    {
        this.nonStaticMemory = nonStaticMemory;
        return this;
    }

    public AIMLHandlerBuilderWithBotMemory withBotMemory(Map<String, String> botMemory)
    {
        this.botMemory = botMemory;
        return new AIMLHandlerBuilderWithBotMemoryImpl();
    }

    public class AIMLHandlerBuilderWithBotMemoryImpl implements AIMLHandlerBuilderWithBotMemory
    {
        public AIMLHandlerBuilderWithAimls withAimlProvider(AIMLProvider provider)
        {
            aimlProvider = provider;
            return new AIMLHandlerBuilderWithAimlsImpl();
        }
    }

    public class AIMLHandlerBuilderWithAimlsImpl implements AIMLHandlerBuilderWithAimls
    {
        public AIMLHandler build() throws AIMLCreationException
        {
            AIMLParser aimlParser = aimlParserFactory.createPatternParser(botMemory);
            List<AIML> aimls = aimlProvider.provide(aimlParser)
                            .stream()
                            .collect(Collectors.toList());
            return aimlHandlerFactory.create(aimls, nonStaticMemory, botMemory, learnFile);
        }
    }
}