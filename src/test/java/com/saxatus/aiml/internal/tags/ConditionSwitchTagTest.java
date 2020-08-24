package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.AIMLParseTag;

class ConditionSwitchTagTest extends AbstractAIMLTagTest
{
    @Test
    void testSwitchTag()
    {
        String response;
        String template = "<think>" //
                        + "<set name=\"branch\">" //
                        + "<get name=\"birthday\"/>" //
                        + "</set>" //
                        + "</think>" //
                        + "<condition name=\"branch\">" //
                        + "  <li value=\"Unknown\">When is your birthday?</li>" //
                        + "  <li value=\"OM\">When is your birthday?</li>" //
                        + "  <li><get name=\"birthday\" /></li>" //
                        + "</condition>";

        Map<String, String> botMemory = new HashMap<>();
        AIMLParseTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When is your birthday?", response);

        botMemory.put("birthday", "now");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("now", response);

    }

    @Test
    void testSwitchTagWithName()
    {
        String response;
        String template = "<think>" //
                        + "<set name=\"branch\">" //
                        + "<get name=\"birthday\"/>" //
                        + "</set>" //
                        + "</think>" //
                        + "<condition>" //
                        + "  <li name=\"branch\" value=\"Unknown\">When is your birthday?</li>" //
                        + "  <li name=\"branch\" value=\"OM\">When is your birthday?</li>" //
                        + "  <li><get name=\"birthday\" /></li>" //
                        + "</condition>";

        Map<String, String> botMemory = new HashMap<>();
        AIMLParseTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When is your birthday?", response);

        botMemory.put("birthday", "now");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("now", response);

    }
}
