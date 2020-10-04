package ai.saxatus.aiml.api;

import java.io.File;
import java.util.Map;

import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.provider.AIMLProvider;

public interface AIMLHandlerBuilder
{

    AIMLHandlerBuilder withLearnFile(File file);

    AIMLHandlerBuilder nonStaticMemory(Map<String, String> nonStaticMemory);

    AIMLHandlerBuilderWithAimls withAiml(AIMLProvider aimlProvider);

    AIMLHandlerBuilderWithBotMemory withBotMemory(Map<String, String> botMemory);

    public interface AIMLHandlerBuilderWithBotMemory
    {

        AIMLHandlerBuilderWithAimls withAimlProvider(AIMLProvider provider);
    }

    public interface AIMLHandlerBuilderWithAimls
    {

        AIMLHandler build() throws AIMLCreationException;
    }
}
