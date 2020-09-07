package ai.saxatus.aiml.api.io;

import java.util.Collection;

import ai.saxatus.aiml.api.parsing.AIML;

public interface AIMLProvider
{

    Collection<AIML> provide() throws AIMLCreationException;

}
