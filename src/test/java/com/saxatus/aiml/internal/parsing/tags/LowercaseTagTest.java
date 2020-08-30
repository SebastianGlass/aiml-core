package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class LowercaseTagTest
{

    LowercaseTag tag;

    @BeforeEach
    void setUp()
    {
        tag = new LowercaseTag();
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof ContentEnclosingNode);
    }

    @Test
    void testGetWrappedText()
    {
        assertEquals("content", tag.getWrappedText("cOntEnt"));
    }

    @Test
    void testToString()
    {
        assertEquals("<lowercase></lowercase>", tag.toString());
    }

    @Test
    void testToStringWithContent()
    {
        AbstractAIMLContentTag node = mock(AbstractAIMLContentTag.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<lowercase>content</lowercase>", tag.toString());
    }

}
