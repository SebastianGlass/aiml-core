package com.saxatus.aiml.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.factory.AIMLHandlerProvider;
import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.factory.TagFactoryProvider;
import com.saxatus.aiml.internal.AIMLHandlerBuilderImpl;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.factory.AIMLParserFactoryImpl;
import com.saxatus.aiml.internal.factory.TagFactoryImpl;

public class AIMLModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(AIMLHandlerBuilder.class).to(AIMLHandlerBuilderImpl.class);
        bind(AIMLParserFactory.class).to(AIMLParserFactoryImpl.class);
        install(new FactoryModuleBuilder().implement(AIMLHandler.class, AIMLHandlerImpl.class)
                        .build(AIMLHandlerProvider.class));

        install(new FactoryModuleBuilder().implement(TagFactory.class, TagFactoryImpl.class)
                        .build(TagFactoryProvider.class));
    }

}
