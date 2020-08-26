package com.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.utils.Strftime;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class DateTagTest
{

    DateTag tag;
    Map<String, String> memory;

    @BeforeEach
    void setup()
    {
        memory = new HashMap<>();
        tag = new DateTag();
        tag.setFormat("%x");
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof LeafNode);
    }

    @Test
    void testGetText()
    {
        assertEquals(new Strftime("%x").format(new Date()), tag.getText());
    }

    @Test
    void testToString()
    {
        assertEquals("<date format=\"%x\"/>", tag.toString());
    }

}
