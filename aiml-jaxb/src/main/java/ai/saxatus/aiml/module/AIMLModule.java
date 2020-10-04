package ai.saxatus.aiml.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLHandlerBuilder;
import ai.saxatus.aiml.api.parsing.AIMLParser;
import ai.saxatus.aiml.api.parsing.AIMLTransformer;
import ai.saxatus.aiml.api.provider.AIMLHandlerProvider;
import ai.saxatus.aiml.api.provider.AIMLParserProvider;
import ai.saxatus.aiml.internal.AIMLHandlerBuilderImpl;
import ai.saxatus.aiml.internal.AIMLHandlerImpl;
import ai.saxatus.aiml.internal.parsing.parser.JaxbAIMLParserImpl;
import ai.saxatus.aiml.internal.parsing.parser.JaxbAIMLTransformer;

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
