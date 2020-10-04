package ai.saxatus.aiml.internal;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, String> botMemory = new HashMap<>();
    private File learnFile = new File("./temp.aiml");

    @Inject
    public AIMLHandlerBuilderImpl(AIMLHandlerProvider aimlHandlerProvider)
    {
        this.aimlHandlerProvider = aimlHandlerProvider;
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

            List<AIML> aimls = aimlProvider.provide()
                            .stream()
                            .collect(Collectors.toList());
            return aimlHandlerProvider.provide(aimls, nonStaticMemory, botMemory, learnFile);
        }
    }
}