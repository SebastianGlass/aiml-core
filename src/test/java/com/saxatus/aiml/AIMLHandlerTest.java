package com.saxatus.aiml;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.saxatus.aiml.AIMLHandler.AIMLHandlerBuilder;
import com.saxatus.aiml.parsing.AIML;

public class AIMLHandlerTest
{

    @Test
    public void testAIMLHandlerBuilderWithList()
    {
        List<AIML> list = Arrays.asList(new AIML("", "", "", "", "", 1));
        AIMLHandler h = new AIMLHandlerBuilder().withAiml(list)
                        .build();
        assertEquals(1, h.getDict()
                        .size());
    }

    @Test()
    public void testAIMLHandlerBuilderWithInitializedReader() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler h = new AIMLHandlerBuilder().withAiml(reader.withBotMemory(new HashMap<>()))
                        .build();
        assertEquals(8, h.getDict()
                        .size());
        reader.close();

    }

    @Test()
    public void testAIMLHandlerBuilderWithReader() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler h = new AIMLHandlerBuilder().withBotMemory(new HashMap<>())
                        .withAiml(reader)
                        .build();
        assertEquals(8, h.getDict()
                        .size());
        reader.close();
    }

    @Test()
    public void integTest() throws Exception
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");
        AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
        AIMLHandler handler = new AIMLHandlerBuilder().withBotMemory(new HashMap<>())
                        .withAiml(reader)
                        .build();
        String answer = handler.getAnswer("pattern");
        assertEquals("Correct", answer);

    }

}
