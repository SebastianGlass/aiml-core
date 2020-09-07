package ai.saxatus.aiml.internal.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.parsing.AIMLDictionaryFilter;
import ai.saxatus.aiml.api.utils.Dictionary;

class AIMLDictionaryFilterTest
{

    AIMLDictionaryFilter filter;

    @BeforeEach
    void setUp()
    {
        Dictionary<String, AIML> dict = new Dictionary<>();
        dict.put("A", new AIML("_ a", "", "a", "1", "", 1));
        dict.put("A", new AIML("_ b", "", "b", "2", "", 2));
        dict.put("A", new AIML("a a", "", "c", "3", "", 3));
        dict.put("A", new AIML("a _", "", "d", "4", "", 4));
        dict.put("B", new AIML("b _", "", "a", "1", "", 5));
        dict.put("B", new AIML("b b", "", "b", "2", "", 6));
        dict.put("B", new AIML("b a", "", "c", "3", "", 7));
        dict.put("B", new AIML("_ _", "", "d", "4", "", 8));

        filter = new AIMLDictionaryFilter(dict);
    }

    @Test
    void testTopicFilter()
    {
        assertEquals(8, filter.applyTopicFilter(null)
                        .getDictionary()
                        .size());
        assertEquals(2, filter.applyTopicFilter("3")
                        .getDictionary()
                        .size());
    }

    @Test
    void testThatFilter()
    {
        assertEquals(8, filter.applyThatFilter(null)
                        .getDictionary()
                        .size());
        assertEquals(2, filter.applyThatFilter("b")
                        .getDictionary()
                        .size());
    }

    @Test
    void testPatternFilter()
    {
        assertEquals(8, filter.applyPatternFilter(null)
                        .getDictionary()
                        .size());
        assertEquals(4, filter.applyPatternFilter("a a")
                        .getDictionary()
                        .size());
    }

}
