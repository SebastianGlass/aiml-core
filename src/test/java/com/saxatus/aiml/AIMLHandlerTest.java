package com.saxatus.aiml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.internal.AIMLHandlerBuilderImpl;
import com.saxatus.aiml.internal.AIMLHandlerImpl;

class AIMLHandlerTest
{

    @Test
    void testAIMLHandlerBuilderWithList()
    {
        List<AIML> list = Arrays.asList(new AIML("", "", "", "", "", 1));
        AIMLHandler h = new AIMLHandlerBuilderImpl().withAiml(list)
                        .build();
        assertTrue(h instanceof AIMLHandlerImpl);
        assertEquals(1, ((AIMLHandlerImpl)h).getDict()
                        .size());
    }

    @Test
    void testAIMLHandlerBuilderWithInitializedReader() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler h = new AIMLHandlerBuilderImpl().withAiml(reader.withBotMemory(new HashMap<>()))
                        .build();
        assertTrue(h instanceof AIMLHandlerImpl);
        assertEquals(8, ((AIMLHandlerImpl)h).getDict()
                        .size());

    }

    @Test
    void testAIMLHandlerBuilderWithReader() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler h = new AIMLHandlerBuilderImpl().withBotMemory(new HashMap<>())
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
        AIMLHandler handler = new AIMLHandlerBuilderImpl().withBotMemory(new HashMap<>())
                        .withAimlProvider(reader)
                        .build();
        String answer = handler.getAnswer("pattern");
        assertEquals("Correct", answer);

    }

}
