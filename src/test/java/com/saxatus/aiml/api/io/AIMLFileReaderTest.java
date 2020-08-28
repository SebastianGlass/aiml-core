package com.saxatus.aiml.api.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import com.saxatus.aiml.api.parsing.AIML;

class AIMLFileReaderTest
{

    private Map<String, String> map = new HashMap<>();

    @BeforeEach
    void setup()
    {
    }

    // @Test
    void readComplexAIMLFile()
    {
        URL fileURL = this.getClass()
                        .getResource("/complexAIML.aiml");

        try
        {
            AIMLFileReader reader = new AIMLFileReader(new File(fileURL.getFile()));
            map.put("test", "test");
            Collection<AIML> streambable = reader.provide();
            assertEquals(8, streambable.stream()
                            .count());
            assertEquals(2, streambable.stream()
                            .filter(aiml -> "that".equals(aiml.getThat()))
                            .count());
            assertEquals(5, streambable.stream()
                            .filter(aiml -> "topic".equals(aiml.getTopic()))
                            .count());
            assertEquals(3, streambable.stream()
                            .filter(aiml -> aiml.getTopic() == null)
                            .count());
            assertEquals(6, streambable.stream()
                            .filter(aiml -> aiml.getThat() == null)
                            .count());
            assertEquals(0, streambable.stream()
                            .filter(aiml -> aiml.getPattern() == null)
                            .count());
            assertEquals(0, streambable.stream()
                            .filter(aiml -> aiml.getTemplate() == null)
                            .count());
            assertEquals(2, streambable.stream()
                            .filter(aiml -> "PATTERN".equals(aiml.getPattern()))
                            .count());
            assertEquals(2, streambable.stream()
                            .filter(aiml -> "THAT1".equals(aiml.getPattern()))
                            .count());
            assertEquals(1, streambable.stream()
                            .filter(aiml -> "THAT1".equals(aiml.getPattern()))
                            .filter(aiml -> aiml.getTopic() == null)
                            .count());
            assertEquals(1, streambable.stream()
                            .filter(aiml -> "TEST 123".equals(aiml.getPattern()))
                            .count());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
