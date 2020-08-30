package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.saxatus.aiml.api.parsing.tags.OptionEnclosingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class RandomTagTest
{

    RandomTag tag;
    Map<String, String> memory;

    @Mock
    LiTag node1;
    @Mock
    LiTag node2;
    @Mock
    LiTag node3;

    @BeforeEach
    void setUp()
    {
        tag = new RandomTag();
        MockitoAnnotations.initMocks(this);
        when(node1.toString()).thenReturn("A");
        when(node1.getValue()).thenReturn("1");
        when(node2.toString()).thenReturn("B");
        when(node2.getValue()).thenReturn("2");
        when(node3.toString()).thenReturn("C");
        tag.setContent(Arrays.asList(node1, node2, node3));
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof OptionEnclosingNode);
    }

    @Test
    void testGetDecision()
    {
        tag.random = new Random(0);
        assertEquals(node1, tag.getDecision());
    }

    @Test
    void testFallbackGetWrappingText()
    {
        assertEquals("", ConditionTag.FALLBACK_LITAG.getWrappedText("content"));
    }

    @Test
    void testGetDecisionFallbackSaveForCorruptStructure()
    {
        tag.setContent(null);
        assertEquals(ConditionTag.FALLBACK_LITAG, tag.getDecision());
    }

    @Test
    void testGetWrappedText()
    {
        assertEquals("content", tag.getWrappedText("content"));
    }

    @Test
    void testToStringWithContent()
    {
        assertEquals("<random>A B C</random>", tag.toString());
    }

}
