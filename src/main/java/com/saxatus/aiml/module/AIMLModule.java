package com.saxatus.aiml.module;

import com.google.inject.AbstractModule;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.internal.AIMLHandlerBuilderImpl;

public class AIMLModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(AIMLHandlerBuilder.class).to(AIMLHandlerBuilderImpl.class);
    }

}
