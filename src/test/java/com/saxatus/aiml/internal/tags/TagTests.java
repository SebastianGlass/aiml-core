package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class TagTests extends AbstractAIMLTagTest
{

    @Test
    void testSetTag()
    {
        String response;
        String template = "<set name='a'>b</set>";

        Map<String, String> botMemory = new HashMap<>();
        response = handleAIMLTag(template, "", "", new HashMap<>(), botMemory);

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
        response = handleAIMLTag(template, "", "", new HashMap<>(), botMemory);

        assertEquals("b", botMemory.getOrDefault("a", "undefined")
                        .trim());
        assertEquals("b", botMemory.getOrDefault("b", "undefined")
                        .trim());
        assertEquals("b", response.trim());

    }

    @Test
    void testThinkTag()
    {
        String template = "<think>nom</think>";
        String response = handleAIMLTag(template, "", "");

        assertEquals("", response);

    }

    @Test
    void testStarTag()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a <star/> is not a <star index = '2'/>?";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("When a dog is not a cute animal?", response);

    }

    @Test
    void testBrReduction()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a<br/>?";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("When a?", response);

    }

    @Test
    void testUnknownTag()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\"></a>";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("Click here <a src=\"123\"></a>", response);

    }

    @Test
    void testNestedUnknownTag()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\">Nom</a>";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("Click here <a src=\"123\">Nom</a>", response);

    }

    @Test
    void testRandomTag()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "<random><li>a</li><li>b</li></random>";

        String response = handleAIMLTag(template, request, pattern);

        assertTrue(Arrays.asList("a", "b")
                        .contains(response));

    }

    @Test
    void testPersonTag()
    {
        String request = "Your dog is a cute animal.";
        String pattern = "* is a *.";
        String template = "<person/>?";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("my dog?", response);

    }

    @Test
    void testPersonStar2Tag()
    {
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Nom <person><star index='2'/></person>?";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("Nom cute animal?", response);

    }

    @Test
    void testPersonTranscriptionTag()
    {
        String request = "I like you.";
        String pattern = "* like *.";
        String template = "<person/> like <person><star index='2'/></person>?";

        String response = handleAIMLTag(template, request, pattern);

        assertEquals("you like me?", response);

    }

}
