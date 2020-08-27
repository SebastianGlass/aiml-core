package com.saxatus.aiml.api.parsing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AIMLComparatorTest
{
    private int k = 0;
    // TODO: topic + that tests
    private AIMLComparator comparator = new AIMLComparator();

    @Nested
    class BasicAIMLComparatorTest
    {
        AIML a = aiml("A B C _");
        AIML b = aiml("A B C D");
        AIML c = aiml("A B");
        AIML d = aiml("A B C");
        AIML e = aiml("A B *");

        @Test
        void testCompareNoWildcards()
        {
            assertTrue(comparator.compare(c, d) > 1);
        }

        @Test
        void testCompareHighPrioWildcard()
        {
            assertTrue(comparator.compare(a, b) < 1);
        }

        @Test
        void testCompareLowPrioWildcard()
        {
            assertTrue(comparator.compare(d, e) < 1);
        }

        @Test
        void testCompareLengthDif()
        {
            assertTrue(comparator.compare(b, e) < 1);
        }

        private AIML aiml(String pattern)
        {
            return new AIML(pattern, "", null, null, "", k++);
        }
    }

}
