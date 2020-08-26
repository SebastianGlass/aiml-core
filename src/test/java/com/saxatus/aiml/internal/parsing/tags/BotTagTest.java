package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.StaticMemoryUsingNode;

class BotTagTest
{

    BotTag tag;
    Map<String, String> memory;

    @BeforeEach
    void setup()
    {
        memory = new HashMap<>();
        tag = new BotTag();
        tag.setStaticMemory(memory);
        tag.setName("foo");
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AIMLContentNode);
        assertTrue(tag instanceof LeafNode);
        assertTrue(tag instanceof StaticMemoryUsingNode);
    }

    @Test
    void testGetTextNotSet()
    {
        assertEquals("Unknown", tag.getText());
    }

    @Test
    void testGetText()
    {
        memory.put("foo", "bar");
        assertEquals("bar", tag.getText());
    }

    @Test
    void testToString()
    {
        assertEquals("<bot name=\"foo\"/>", tag.toString());
    }


}
