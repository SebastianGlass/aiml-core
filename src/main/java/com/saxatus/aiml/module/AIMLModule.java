package com.saxatus.aiml.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.provider.AIMLHandlerProvider;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.internal.AIMLHandlerBuilderImpl;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.provider.AIMLParserProviderImpl;

public class AIMLModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(AIMLHandlerBuilder.class).to(AIMLHandlerBuilderImpl.class);
        bind(AIMLParserProvider.class).to(AIMLParserProviderImpl.class);
        install(new FactoryModuleBuilder().implement(AIMLHandler.class, AIMLHandlerImpl.class)
                        .build(AIMLHandlerProvider.class));

    }

}
