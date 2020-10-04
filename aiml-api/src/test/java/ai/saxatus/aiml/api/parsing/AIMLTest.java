package ai.saxatus.aiml.api.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIMLTest
{
    AIML aiml;
    AIML aimlWithTopic;
    AIML aimlWithThat;
    AIML aimlWithBoth;

    @BeforeEach
    protected void setUp()
    {
        aiml = new AIML("pattern *", "<template>content</template>", null, null, "Source", 1);
        aimlWithTopic = new AIML("pattern *", "<template>content</template>", null, "topic", "Source", 2);
        aimlWithThat = new AIML("pattern *", "<template>content</template>", "that", null, "Source", 3);
        aimlWithBoth = new AIML("pattern *", "<template>content</template>", "that", "topic", "Source", 4);
    }

    @Test
    void testHashCode()
    {
        assertEquals(-189603969, aiml.hashCode());
        assertEquals(-50428595, aimlWithTopic.hashCode());
        assertEquals(-22022154, aimlWithThat.hashCode());
        assertEquals(117153220, aimlWithBoth.hashCode());
    }

    @Test
    void testToString()
    {
        assertEquals("AIML [pattern=pattern *, template=<template>content</template>, src=Source, line=1]",
                        aiml.toString());
        assertEquals("AIML [pattern=pattern *, template=<template>content</template>, topic=topic, src=Source, line=2]",
                        aimlWithTopic.toString());
        assertEquals("AIML [pattern=pattern *, template=<template>content</template>, that=that, src=Source, line=3]",
                        aimlWithThat.toString());
        assertEquals("AIML [pattern=pattern *, template=<template>content</template>, that=that, topic=topic, src=Source, line=4]",
                        aimlWithBoth.toString());
    }

    @Test
    void testWithPattern()
    {
        AIML derive = aiml.withPattern("newpattern");
        assertEquals("pattern *", aiml.getPattern());
        assertEquals("newpattern", derive.getPattern());
    }

    @Test
    void testGetTemplate()
    {
        assertEquals("<template>content</template>", aiml.getTemplate());
    }

    @Test
    void testGetThat()
    {
        assertEquals("that", aimlWithThat.getThat());
        assertNull(aiml.getThat());
    }

    @Test
    void testGetTopic()
    {
        assertEquals("topic", aimlWithTopic.getTopic());
        assertNull(aiml.getTopic());
    }

    @Test
    void testGetSource()
    {
        assertEquals("Source", aimlWithTopic.getSource());
    }

    @Test
    void testCompareTo()
    {
        AIMLComparator comparator = mock(AIMLComparator.class);
        AIML.aimlComperator = comparator;
        aiml.compareTo(aimlWithBoth);
        verify(comparator).compare(aiml, aimlWithBoth);
    }

    @Test
    void testEquals()
    {
        AIML NULL = null;
        assertEquals(aiml, aiml);
        assertEquals(aiml, aiml.withPattern(aiml.getPattern()));
        assertNotEquals(aiml, NULL);
        assertNotEquals(aiml, Integer.valueOf(123));
    }

    @Test
    void testGetLine()
    {
        assertEquals(1, aiml.getLine());
        assertEquals(4, aimlWithBoth.getLine());
    }

}
