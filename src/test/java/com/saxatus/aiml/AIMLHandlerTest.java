package com.saxatus.aiml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.module.AIMLModule;

class AIMLHandlerTest
{
    @Inject
    private AIMLHandlerBuilder aimlHandlerBuilder;

    @BeforeEach
    void setup()
    {
        Injector injector = Guice.createInjector(new AIMLModule());
        injector.injectMembers(this);
    }

    @Test
    void testAIMLHandlerBuilderWithList() throws AIMLCreationException
    {
        List<AIML> list = Arrays.asList(new AIML("", "", "", "", "", 1));
        AIMLHandler h = aimlHandlerBuilder.withAiml(new TestAIMLProvier(list))
                        .build();
        assertTrue(h instanceof AIMLHandlerImpl);
        assertEquals(1, ((AIMLHandlerImpl)h).getDict()
                        .size());
    }

    @Test
    void testAIMLHandlerBuilderWithReader() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler h = aimlHandlerBuilder.withBotMemory(new HashMap<>())
                        .withAimlProvider(reader)
                        .build();
        assertTrue(h instanceof AIMLHandlerImpl);
        assertEquals(8, ((AIMLHandlerImpl)h).getDict()
                        .size());

    }

    @Test
    void integTest() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler handler = aimlHandlerBuilder.withBotMemory(new HashMap<>())
                        .withAimlProvider(reader)
                        .build();
        String answer = handler.getAnswer("pattern");
        assertEquals("Correct", answer);

    }

}
