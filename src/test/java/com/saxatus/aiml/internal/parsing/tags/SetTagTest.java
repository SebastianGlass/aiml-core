package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class SetTagTest
{

    SetTag tag;
    Map<String, String> memory;

    @BeforeEach
    void setUp()
    {
        memory = new HashMap<>();
        tag = new SetTag();
        tag.setNonStaticMemory(memory);
        tag.setName("foo");
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof ContentEnclosingNode);
        assertTrue(tag instanceof NonStaticMemoryUsingNode);
    }

    @Test
    void testGetWrappedText()
    {
        assertEquals(null, memory.get("foo"));
        assertEquals("content", tag.getWrappedText("content"));
        assertEquals("content", memory.get("foo"));
    }

    @Test
    void testToString()
    {
        assertEquals("<set name=\"foo\"></set>", tag.toString());
    }

    @Test
    void testToStringWithContent()
    {
        AbstractAIMLContentTag node = mock(AbstractAIMLContentTag.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<set name=\"foo\">content</set>", tag.toString());
    }

}
