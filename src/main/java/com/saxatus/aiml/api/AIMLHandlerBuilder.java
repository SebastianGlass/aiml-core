package com.saxatus.aiml.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.api.io.AIMLProvider;
import com.saxatus.aiml.api.parsing.AIML;

public class AIMLHandlerBuilder
{
    private static final Log log = LogFactory.getLog(AIMLHandlerBuilder.class);
    private List<AIML> aimls;
    private Map<String, String> nonStaticMemory = new HashMap<>();
    private Map<String, String> botMemory = new HashMap<>();
    private File learnFile = new File("./temp.aiml");

    public AIMLHandlerBuilderWithAimls withAiml(List<AIML> aimls)
    {
        this.aimls = aimls;
        return new AIMLHandlerBuilderWithAimls();
    }

    public AIMLHandlerBuilder withLearnFile(File file)
    {
        this.learnFile = file;
        return this;
    }

    public AIMLHandlerBuilderWithAimls withAiml(AIMLFileReader aimls)
    {
        try
        {
            this.aimls = aimls.provide()
                            .stream()
                            .collect(Collectors.toList());
        }
        catch(Exception e)
        {
            log.error("Could not load aimls from file", e);
        }
        return new AIMLHandlerBuilderWithAimls();
    }

    public AIMLHandlerBuilder nonStaticMemory(Map<String, String> nonStaticMemory)
    {
        this.nonStaticMemory = nonStaticMemory;
        return this;
    }

    public AIMLHandlerBuilderWithBotMemory withBotMemory(Map<String, String> botMemory)
    {
        this.botMemory = botMemory;
        return new AIMLHandlerBuilderWithBotMemory();
    }

    public class AIMLHandlerBuilderWithBotMemory
    {

        public AIMLHandlerBuilderWithAimls withAimlProvider(AIMLProvider provider)
        {
            try
            {
                aimls = provider.withBotMemory(botMemory)
                                .provide()
                                .stream()
                                .collect(Collectors.toList());
            }
            catch(Exception e)
            {
                log.error("Error dua AIMLProvier::provide", e);
            }

            return new AIMLHandlerBuilderWithAimls();
        }
    }

    public class AIMLHandlerBuilderWithAimls
    {

        public AIMLHandler build()
        {
            return new AIMLHandler(aimls, nonStaticMemory, botMemory, learnFile);
        }
    }
}