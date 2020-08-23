package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

class TagTests extends AbstractAIMLTagTest
{

    @Test
    void testSetTag()
    {
        String response;
        String template = "<set name='a'>b</set>";

        Map<String, String> botMemory = new HashMap<>();
        AIMLParseTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", botMemory.get("a")
                        .trim());
        assertEquals("b", response);

    }

    @Test
    void testNestedSetTag()
    {
        String response;
        String template = "<set name='a'><set name='b'>b</set></set>";

        Map<String, String> botMemory = new HashMap<>();
        AIMLParseTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", botMemory.getOrDefault("a", "undefined")
                        .trim());
        assertEquals("b", botMemory.getOrDefault("b", "undefined")
                        .trim());
        assertEquals("b", response.trim());

    }

    @Test
    void testThinkTag()
    {
        String response;
        String template = "<think>nom</think>";

        AIMLParseTag tag = getAIMLTag(template, "", "");

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("", response);

    }

    @Test
    void testStarTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a <star/> is not a <star index = '2'/>?";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When a dog is not a cute animal?", response);

    }

    @Test
    void testBrReduction()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a<br/>?";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When a?", response);

    }

    @Test
    void testUnknownTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\"></a>";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Click here <a src=\"123\"></a>", response);

    }

    @Test
    void testNestedUnknownTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\">Nom</a>";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Click here <a src=\"123\">Nom</a>", response);

    }

    @Test
    void testRandomTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "<random><li>a</li><li>b</li></random>";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertTrue(Arrays.asList("a", "b")
                        .contains(response));

    }

    @Test
    void testPersonTag()
    {
        String response;
        String request = "Your dog is a cute animal.";
        String pattern = "* is a *.";
        String template = "<person/>?";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("my dog?", response);

    }

    @Test
    void testPersonStar2Tag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Nom <person><star index='2'/></person>?";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Nom cute animal?", response);

    }

    @Test
    void testPersonTranscriptionTag()
    {
        String response;
        String request = "I like you.";
        String pattern = "* like *.";
        String template = "<person/> like <person><star index='2'/></person>?";

        AIMLParseTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("you like me?", response);

    }

}
