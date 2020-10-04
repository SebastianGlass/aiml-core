package ai.saxatus.aiml.api.provider;

import com.google.inject.assistedinject.Assisted;
import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLParserProvider
{

    AIMLParser provideTemplateParser(@Assisted("pattern") String pattern, @Assisted("input") String input,
                    @Assisted("handler") AIMLHandler aimlHandler);
}
