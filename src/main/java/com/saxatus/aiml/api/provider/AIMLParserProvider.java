package com.saxatus.aiml.api.provider;

import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLParserProvider
{

    AIMLParser provideTemplateParser(@Assisted("pattern") String pattern, @Assisted("input") String input,
                    @Assisted("handler") AIMLHandler aimlHandler);
}
