package com.saxatus.aiml.internal.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.utils.Dictionary;

class AIMLResolverTest
{
    // TODO: add topic + that

    private int k = 0;
    AIMLResolver resolver;

    @Nested
    class BasicAIMLResolverTest
    {

        private Dictionary<String, AIML> dict;

        @BeforeEach
        void setUp()
        {
            dict = new Dictionary<>();
            dict.put("A", new TreeSet<>(Arrays.asList(aiml("A B C _"), aiml("A B C D"), aiml("A B"), aiml("A B C"),
                            aiml("A B *"))));

            resolver = new AIMLResolver(dict, new HashMap<>());
        }

        @Test
        void testHighPrioWildcard()
        {
            AIML aiml = resolver.getAIML("A B C D");
            assertNotNull(aiml);
            assertEquals("A B C _", aiml.getPattern());
        }

        @Test
        void testLowPrioWildcard()
        {
            AIML aiml = resolver.getAIML("A B C");
            assertNotNull(aiml);
            assertEquals("A B C", aiml.getPattern());
        }

        @Test
        void testHighPrioWildcardAtStart()
        {
            dict.put("_", new TreeSet<>(Arrays.asList(aiml("_"))));
            AIML aiml = resolver.getAIML("B");
            assertNotNull(aiml);
            assertEquals("_", aiml.getPattern());
        }

        @Test
        void testLowPrioWildcardAtStart()
        {
            dict.put("*", new TreeSet<>(Arrays.asList(aiml("*"))));
            AIML aiml = resolver.getAIML("B");
            assertNotNull(aiml);
            assertEquals("*", aiml.getPattern());
        }

        @Test
        void testNoFound()
        {
            AIML aiml = resolver.getAIML("A E");
            assertNull(aiml);
        }

        @Test
        void test()
        {
            AIML aiml = resolver.getAIML("A B");
            assertNotNull(aiml);
            assertEquals("A B", aiml.getPattern());

        }
    }

    private AIML aiml(String pattern)
    {
        return new AIML(pattern, "", null, null, "", k++);
    }
}
