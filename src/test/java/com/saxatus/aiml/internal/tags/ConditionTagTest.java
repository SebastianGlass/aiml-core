package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.AIMLParseTag;

class ConditionTagTest extends AbstractAIMLTagTest
{

    @Test
    void testConditionTag()
    {
        String response;
        String template = "<condition name='a' value='b'>b</condition><condition name='a' value='c'>c</condition>";

        Map<String, String> nonStaticMemory = new HashMap<>();

        AIMLParseTag tag = getAIMLTag(template, "", "", new HashMap<>(), nonStaticMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("", response);
        nonStaticMemory.put("a", "b");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", response);

        nonStaticMemory.put("a", "c");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("c", response);

    }
}
