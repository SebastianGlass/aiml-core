package ai.saxatus.aiml.api.provider;

import java.util.Collection;

import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.parsing.AIML;

public interface AIMLProvider
{

    Collection<AIML> provide() throws AIMLCreationException;

}
