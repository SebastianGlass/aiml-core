package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.StarRequiringNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class StarTagTest
{

    StarTag tag;

    @BeforeEach
    void setup()
    {
        tag = new StarTag();
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof LeafNode);
        assertTrue(tag instanceof StarRequiringNode);
    }

    @Test
    void testGetText()
    {
        tag.setStars(Arrays.asList("A","B","C"));
        assertEquals("A", tag.getText());
    }

    @Test
    void testGetTextSetIndex()
    {
        tag.setIndex(2);
        tag.setStars(Arrays.asList("A","B","C"));
        assertEquals("B", tag.getText());
    }

    @Test
    void testToString()
    {
        assertEquals("<star index=\"1\"/>", tag.toString());
    }


}
