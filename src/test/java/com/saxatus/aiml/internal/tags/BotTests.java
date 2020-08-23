package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLProvider;
import com.saxatus.aiml.api.parsing.AIML;

class BotTests
{

    /*
     * http://www.alicebot.org/documentation/aiml-reference.html
     */
    private final List<AIML> aiml = Arrays.asList(new AIML("*", "", null, null, "", -1),
                    new AIML("LET US TALK ABOUT *", "Sure, <set name='topic'><person/></set> sounds like a good topic.",
                                    null, null, "test", 0),
                    new AIML("THEY ARE COOL", "Yeah, I like <get name='topic'/> so much.", null, "cars", "test", 1),
                    new AIML("THEY ARE COOL", "What?", null, null, "test", 2),
                    new AIML("WHAT IS YOUR NAME", "My name is <bot name='name'/>", null, null, "test", 3),
                    new AIML("IDENTIFY", "<srai>WHAT IS YOUR NAME</srai>", null, null, "test", 4),
                    new AIML("CRY", ":'(", null, null, "test", -1), new AIML("DO *", "<sr/>", null, null, "test", 5));

    @Test
    void testTopic() throws URISyntaxException
    {
        String[] request = { "Let us talk about cars", "They are cool" };

        String[] correctAnswers = { "Sure, cars sounds like a good topic.", "Yeah, I like cars so much." };

        AIMLHandler answeringStrategy = getAIMLHandler(aiml);

        for (int i = 0; i < request.length; i++)
        {
            String answer = answeringStrategy.getAnswer(request[i]);
            assertEquals(correctAnswers[i], answer);
        }

    }

    @Test
    void testNotSetTopic() throws URISyntaxException
    {

        AIMLHandler b = getAIMLHandler(aiml);
        String[] request = { "Let us talk about dogs", "They are cool" };

        String[] correctAnswers = { "Sure, dogs sounds like a good topic.", "What?" };

        for (int i = 0; i < request.length; i++)
        {
            String answer = b.getAnswer(request[i]);
            assertEquals(correctAnswers[i], answer);
        }
    }

    @Test
    void testBotTag() throws URISyntaxException
    {
        AIMLHandler b = getAIMLHandler(aiml);
        String answer = b.getAnswer("What is your name?");
        assertEquals("My name is test", answer);
    }

    @Test
    void testSraiTag() throws URISyntaxException
    {
        AIMLHandler b = getAIMLHandler(aiml);
        String answer = b.getAnswer("Identify");
        assertEquals("My name is test", answer);
    }

    @Test
    void testSrTag() throws URISyntaxException
    {
        AIMLHandler b = getAIMLHandler(aiml);
        String answer = b.getAnswer("Do Cry");
        assertEquals(":'(", answer);
    }

    @Test
    void testOrder() throws URISyntaxException
    {
        List<AIML> aiml = Arrays.asList(new AIML("A *", "1", "3", null, "", 1),
                        new AIML("A * IS", "2", "3", null, "", 2), new AIML("A _", "3", null, null, "", 3));

        AIMLHandler b = getAIMLHandler(aiml);
        String answer = b.getAnswer("A B");
        assertEquals("3", answer);

        answer = b.getAnswer("A B IS");
        assertEquals("2", answer);

        answer = b.getAnswer("A B");
        assertEquals("3", answer);

        answer = b.getAnswer("A B");
        assertEquals("1", answer);
    }

    private AIMLHandler getAIMLHandler(List<AIML> aimlList)
    {

        Map<String, String> map = new HashMap<>();
        map.put("name", "test");
        AIMLHandler a = new AIMLHandlerBuilder().withBotMemory(map)
                        .withAimlProvider(new TestAIMLProvier(aimlList))
                        .build();
        return a;
    }

    private static class TestAIMLProvier implements AIMLProvider
    {
        private List<AIML> aimlList;

        public TestAIMLProvier(List<AIML> aimlList)
        {
            this.aimlList = aimlList;
            // TODO Auto-generated constructor stub
        }

        @Override
        public AIMLProvider withBotMemory(Map<String, String> botMemory)
        {
            return this;
        }

        @Override
        public Collection<AIML> provide()
        {
            return aimlList;
        }
    }
}
