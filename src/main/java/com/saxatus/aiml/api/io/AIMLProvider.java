package com.saxatus.aiml.api.io;

import java.util.Collection;
import java.util.Map;

import com.saxatus.aiml.api.parsing.AIML;

public interface AIMLProvider
{

    Collection<AIML> provide() throws AIMLCreationException;

    AIMLProvider withBotMemory(Map<String, String> botMemory);

}
