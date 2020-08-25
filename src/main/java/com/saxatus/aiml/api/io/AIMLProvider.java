package com.saxatus.aiml.api.io;

import java.util.Collection;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParser;

public interface AIMLProvider
{

    Collection<AIML> provide(AIMLParser aimlParser) throws AIMLCreationException;

}
