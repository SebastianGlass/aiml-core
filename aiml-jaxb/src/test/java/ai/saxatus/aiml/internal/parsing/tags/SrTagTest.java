package ai.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;
import ai.saxatus.aiml.api.parsing.tags.LeafNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class SrTagTest
{

    SrTag tag;

    @BeforeEach
    void setUp()
    {
        tag = new SrTag();
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof LeafNode);
        assertTrue(tag instanceof ContentNeedsOwnRequestNode);
    }

    @Test
    void testGetText()
    {
        tag.setStars(Arrays.asList("content", "other"));
        assertEquals("content", tag.getText());
    }

    @Test
    void testGetTextNoStars()
    {
        tag.setStars(Arrays.asList());
        assertEquals("RANDOM PICKUP LINE", tag.getText());
    }

    @Test
    void testToString()
    {
        assertEquals("<sr/>", tag.toString());
    }

}
