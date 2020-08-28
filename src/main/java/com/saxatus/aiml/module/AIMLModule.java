package com.saxatus.aiml.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.parser.AIMLTransformer;
import com.saxatus.aiml.api.provider.AIMLHandlerProvider;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.internal.AIMLHandlerBuilderImpl;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.parsing.parser.JaxbAIMLParserImpl;
import com.saxatus.aiml.internal.parsing.parser.JaxbAIMLTransformer;

public class AIMLModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(AIMLHandlerBuilder.class).to(AIMLHandlerBuilderImpl.class);

        bind(AIMLTransformer.class).to(JaxbAIMLTransformer.class);

        install(new FactoryModuleBuilder().implement(AIMLHandler.class, AIMLHandlerImpl.class)
                        .build(AIMLHandlerProvider.class));

        install(new FactoryModuleBuilder().implement(AIMLParser.class, JaxbAIMLParserImpl.class)
                        .build(AIMLParserProvider.class));

    }

}
