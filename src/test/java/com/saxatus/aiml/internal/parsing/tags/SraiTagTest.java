package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import com.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;

class SraiTagTest
{

    SraiTag tag;

    @BeforeEach
    void setup()
    {
        tag = new SraiTag();
    }
    
    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AIMLContentNode);
        assertTrue(tag instanceof ContentEnclosingNode);
        assertTrue(tag instanceof ContentNeedsOwnRequestNode);
    }

    @Test
    void testGetWrappedText()
    {
        assertEquals("content", tag.getWrappedText("content"));
    }

    @Test
    void testToString()
    {
        assertEquals("<srai></srai>", tag.toString());
    }

    @Test
    void testToStringWithContent()
    {
        AIMLContentNode node = mock(AIMLContentNode.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<srai>content</srai>", tag.toString());
    }

}
