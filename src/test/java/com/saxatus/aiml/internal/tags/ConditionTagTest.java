package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ConditionTagTest extends AbstractAIMLTagTest
{

    @Test
    void testConditionTag()
    {
        String template = "<condition name='a' value='b'>b</condition><condition name='a' value='c'>c</condition>";

        Map<String, String> nonStaticMemory = new HashMap<>();

        String response = handleAIMLTag(template, "", "", new HashMap<>(), nonStaticMemory);

        assertEquals("", response);
        nonStaticMemory.put("a", "b");
        response = handleAIMLTag(template, "", "", new HashMap<>(), nonStaticMemory);
        assertEquals("b", response);

        nonStaticMemory.put("a", "c");
        response = handleAIMLTag(template, "", "", new HashMap<>(), nonStaticMemory);
        assertEquals("c", response);

    }

}
