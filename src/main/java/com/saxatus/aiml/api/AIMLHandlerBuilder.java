package com.saxatus.aiml.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.api.io.AIMLProvider;
import com.saxatus.aiml.api.parsing.AIML;


public interface AIMLHandlerBuilder
{

    AIMLHandlerBuilderWithAimls withAiml(List<AIML> aimls);

    AIMLHandlerBuilder withLearnFile(File file);

    AIMLHandlerBuilderWithAimls withAiml(AIMLFileReader aimls);

    AIMLHandlerBuilder nonStaticMemory(Map<String, String> nonStaticMemory);

    AIMLHandlerBuilderWithBotMemory withBotMemory(Map<String, String> botMemory);

    public interface AIMLHandlerBuilderWithBotMemory
    {

        AIMLHandlerBuilderWithAimls withAimlProvider(AIMLProvider provider);
    }

    public interface AIMLHandlerBuilderWithAimls
    {

        AIMLHandler build();
    }
}
