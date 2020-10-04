package ai.saxatus.aiml.internal.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.utils.Dictionary;

class AIMLResolverTest
{
    // TODO: add topic + that

    private int k = 0;
    AIMLResolver resolver;

    @Nested
    class TopicAIMLResolverTest
    {
        private Dictionary<String, AIML> dict;

        Map<String, String> mem = new HashMap<>();

        @BeforeEach
        void setUp()
        {
            dict = new Dictionary<>();
            dict.put("A", new TreeSet<>(
                            Arrays.asList(aiml("_", null, "wrongTopic"), aiml("A B", null, "topic"), aiml("*"))));

            resolver = new AIMLResolver(dict, mem);
        }

        @Test
        void test()
        {
            mem.put("topic", "topic");
            AIML aiml = resolver.getAIML("A B");
            assertNotNull(aiml);
            assertEquals("A B", aiml.getPattern());
            assertEquals("topic", aiml.getTopic());

        }

        @Test
        void testOtherTopic()
        {
            mem.put("topic", "buscuits");
            AIML aiml = resolver.getAIML("A B");
            assertNotNull(aiml);
            assertEquals("*", aiml.getPattern());
            assertEquals(null, aiml.getTopic());

        }

    }

    @Nested
    class ThatAIMLResolverTest
    {
        private Dictionary<String, AIML> dict;

        Map<String, String> mem = new HashMap<>();

        @BeforeEach
        void setUp()
        {
            dict = new Dictionary<>();
            dict.put("A", new TreeSet<>(
                            Arrays.asList(aiml("_", "wrongThat", null), aiml("A B", "that", null), aiml("*"))));

            resolver = new AIMLResolver(dict, mem);
        }

        @Test
        void test()
        {
            mem.put("that", "that");
            AIML aiml = resolver.getAIML("A B");
            assertNotNull(aiml);
            assertEquals("A B", aiml.getPattern());
            assertEquals("that", aiml.getThat());

        }

        @Test
        void testOtherTopic()
        {
            mem.put("that", "buscuits");
            AIML aiml = resolver.getAIML("A B");
            assertNotNull(aiml);
            assertEquals("*", aiml.getPattern());
            assertEquals(null, aiml.getThat());

        }

    }

    private Dictionary<String, AIML> dict;

    @BeforeEach
    void setUp()
    {
        dict = new Dictionary<>();
        dict.put("A", new TreeSet<>(
                        Arrays.asList(aiml("A B C _"), aiml("A B C D"), aiml("A B"), aiml("A B C"), aiml("A B *"))));

        resolver = new AIMLResolver(dict, new HashMap<>());
    }

    @ParameterizedTest(name = "getAIML(\"{0}\") shuld return \"{1}\"")
    @MethodSource("provideGetAIMLData")
    void testGetAIML(String input, String expected)
    {
        AIML aiml = resolver.getAIML(input);
        assertNotNull(aiml);
        assertEquals(expected, aiml.getPattern());
    }

    private static Stream<Arguments> provideGetAIMLData()
    {
        return Stream.of(Arguments.of("A B C D", "A B C _"), Arguments.of("A B C", "A B C"),
                        Arguments.of("A B", "A B"));
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

    private AIML aiml(String pattern)
    {
        return aiml(pattern, null, null);
    }

    private AIML aiml(String pattern, String that, String topic)
    {
        return new AIML(pattern, "", that, topic, "", k++);
    }

}
