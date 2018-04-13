package com.saxatus.aiml.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;
import org.w3c.dom.Node;

import com.saxatus.aiml.AIMLHandler;
import com.saxatus.aiml.factory.AIMLDOMFactory;
import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;
import com.saxatus.aiml.parsing.TagParameter;

public class TagTests
{

    @Mock
    private AIMLHandler aimlHandlerMock = mock(AIMLHandler.class);

    /*
     * http://www.alicebot.org/documentation/aiml-reference.html
     */

    @Test
    public void testConditionTag()
    {
        String response;
        String template = "<condition name='a' value='b'>b</condition><condition name='a' value='c'>c</condition>";

        Map<String, String> nonStaticMemory = new HashMap<>();

        IAIMLTag tag = getAIMLTag(template, "", "", new HashMap<>(), nonStaticMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("", response);
        nonStaticMemory.put("a", "b");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", response);

        nonStaticMemory.put("a", "c");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("c", response);

    }

    @Test
    public void testSetTag()
    {
        String response;
        String template = "<set name='a'>b</set>";

        Map<String, String> botMemory = new HashMap<>();
        IAIMLTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", botMemory.get("a")
                        .trim());
        assertEquals("b", response);

    }

    @Test
    public void testSwitchTag()
    {
        String response;
        String template = "<think>" + "<set name=\"branch\">" + "<get name=\"birthday\"/>" + "</set>" + "</think>"
                        + "<condition name=\"branch\">" + " <li value=\"Unknown\">When is your birthday?</li>"
                        + " <li value=\"OM\">When is your birthday?</li>" + "  <li>" + "      <get name=\"birthday\" />"
                        + "   </li>" + "</condition>";

        Map<String, String> botMemory = new HashMap<>();
        IAIMLTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When is your birthday?", response);

        botMemory.put("birthday", "now");
        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("now", response);

    }

    @Test
    public void testNestedSetTag()
    {
        String response;
        String template = "<set name='a'><set name='b'>b</set></set>";

        Map<String, String> botMemory = new HashMap<>();
        IAIMLTag tag = getAIMLTag(template, "", "", new HashMap<>(), botMemory);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("b", botMemory.getOrDefault("a", "undefined")
                        .trim());
        assertEquals("b", botMemory.getOrDefault("b", "undefined")
                        .trim());
        assertEquals("b", response.trim());

    }

    @Test
    public void testThinkTag()
    {
        String response;
        String template = "<think>nom</think>";

        IAIMLTag tag = getAIMLTag(template, "", "");

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("", response);

    }

    @Test
    public void testStarTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a <star/> is not a <star index = '2'/>?";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When a dog is not a cute animal?", response);

    }

    @Test
    public void testBrReduction()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "When a<br/>?";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("When a?", response);

    }

    @Test
    public void testUnknownTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\"></a>";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Click here <a src=\"123\"></a>", response);

    }

    @Test
    public void testNestedUnknownTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Click here <a src=\"123\">Nom</a>";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Click here <a src=\"123\">Nom</a>", response);

    }

    @Test
    public void testRandomTag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "<random><li>a</li><li>b</li></random>";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertTrue(Arrays.asList("a", "b")
                        .contains(response));

    }

    @Test
    public void testPersonTag()
    {
        String response;
        String request = "Your dog is a cute animal.";
        String pattern = "* is a *.";
        String template = "<person/>?";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("my dog?", response);

    }

    @Test
    public void testPersonStar2Tag()
    {
        String response;
        String request = "A dog is a cute animal.";
        String pattern = "A * is a *.";
        String template = "Nom <person><star index='2'/></person>?";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("Nom cute animal?", response);

    }

    @Test
    public void testPersonTranscriptionTag()
    {
        String response;
        String request = "I like you.";
        String pattern = "* like *.";
        String template = "<person/> like <person><star index='2'/></person>?";

        IAIMLTag tag = getAIMLTag(template, request, pattern);

        response = tag.handle(new AIMLParseNode("AIML"));
        assertEquals("you like me?", response);

    }

    private IAIMLTag getAIMLTag(String template, String request, String pattern)
    {
        return getAIMLTag(template, request, pattern, new HashMap<>(), new HashMap<>());
    }

    private IAIMLTag getAIMLTag(String template, String request, String pattern, Map<String, String> botMemory,
                    Map<String, String> nonStaticMeory)
    {
        Node rootNode;
        IAIMLTag tag = null;
        try
        {
            rootNode = new AIMLDOMFactory(template).getDocumentRoot();
            TagParameter tp = new TagParameter(request, pattern, "", botMemory, nonStaticMeory);
            TagFactory factory = new TagFactory(tp, aimlHandlerMock);
            tag = factory.createTag(rootNode);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        assertNotNull(tag);
        return tag;
    }

}
