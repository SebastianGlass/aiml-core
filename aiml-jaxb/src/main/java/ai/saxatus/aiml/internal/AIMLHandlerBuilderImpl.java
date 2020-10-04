package ai.saxatus.aiml.internal;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLHandlerBuilder;
import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.provider.AIMLHandlerProvider;
import ai.saxatus.aiml.api.provider.AIMLProvider;

public class AIMLHandlerBuilderImpl implements AIMLHandlerBuilder
{
    private AIMLHandlerProvider aimlHandlerProvider;

    private AIMLProvider aimlProvider;

    private Map<String, String> nonStaticMemory = new HashMap<>();
    private UnaryOperator<String> botMemory = s -> null;
    private File learnFile = new File("./temp.aiml");

    @Inject
    public AIMLHandlerBuilderImpl(AIMLHandlerProvider aimlHandlerProvider)
    {
        this.aimlHandlerProvider = aimlHandlerProvider;
    }

    @Override
    public AIMLHandlerBuilderImpl withLearnFile(File file)
    {
        this.learnFile = file;
        return this;
    }

    @Override
    public AIMLHandlerBuilderWithAimlsImpl withAiml(AIMLProvider provider)
    {
        this.aimlProvider = provider;
        return new AIMLHandlerBuilderWithAimlsImpl();
    }

    @Override
    public AIMLHandlerBuilderImpl nonStaticMemory(Map<String, String> nonStaticMemory)
    {
        this.nonStaticMemory = nonStaticMemory;
        return this;
    }

    @Override
    public AIMLHandlerBuilderWithBotMemory withBotMemory(UnaryOperator<String> botMemory)
    {
        this.botMemory = botMemory;
        return new AIMLHandlerBuilderWithBotMemoryImpl();
    }

    public class AIMLHandlerBuilderWithBotMemoryImpl implements AIMLHandlerBuilderWithBotMemory
    {
        @Override
        public AIMLHandlerBuilderWithAimls withAimlProvider(AIMLProvider provider)
        {
            aimlProvider = provider;
            return new AIMLHandlerBuilderWithAimlsImpl();
        }
    }

    public class AIMLHandlerBuilderWithAimlsImpl implements AIMLHandlerBuilderWithAimls
    {
        @Override
        public AIMLHandler build() throws AIMLCreationException
        {

            List<AIML> aimls = aimlProvider.provide()
                            .stream()
                            .collect(Collectors.toList());
            return aimlHandlerProvider.provide(aimls, nonStaticMemory, botMemory, learnFile);
        }
    }
}