package ai.saxatus.aiml.api.provider;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.inject.assistedinject.Assisted;
import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.parsing.AIML;

public interface AIMLHandlerProvider
{
    AIMLHandler provide(List<AIML> aimls, @Assisted("non-static") Map<String, String> nonStaticMemory,
                    @Assisted("static") Map<String, String> botMemory, File learnfile);
}
