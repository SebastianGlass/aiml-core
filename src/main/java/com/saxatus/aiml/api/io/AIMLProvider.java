package com.saxatus.aiml.api.io;

import java.util.Collection;

import com.saxatus.aiml.api.parsing.AIML;

public interface AIMLProvider
{

    Collection<AIML> provide() throws AIMLCreationException;

}
