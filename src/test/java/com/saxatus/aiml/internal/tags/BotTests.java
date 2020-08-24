package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.saxatus.aiml.TestAIMLProvier;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.module.AIMLModule;

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
    @Inject
    private AIMLHandlerBuilder aimlHandlerBuilder;

    private AIMLHandler answeringStrategy;

    @BeforeEach
    void setup() throws AIMLCreationException
    {
        Injector injector = Guice.createInjector(new AIMLModule());
        injector.injectMembers(this);

        answeringStrategy = getAIMLHandler(aiml);
    }

    @Test
    void testTopic() throws URISyntaxException
    {
        String[] request = { "Let us talk about cars", "They are cool" };

        String[] correctAnswers = { "Sure, cars sounds like a good topic.", "Yeah, I like cars so much." };

        for (int i = 0; i < request.length; i++)
        {
            String answer = answeringStrategy.getAnswer(request[i]);
            assertEquals(correctAnswers[i], answer);
        }

    }

    @Test
    void testNotSetTopic() throws URISyntaxException
    {

        String[] request = { "Let us talk about dogs", "They are cool" };

        String[] correctAnswers = { "Sure, dogs sounds like a good topic.", "What?" };

        for (int i = 0; i < request.length; i++)
        {
            String answer = answeringStrategy.getAnswer(request[i]);
            assertEquals(correctAnswers[i], answer);
        }
    }

    @Test
    void testBotTag() throws URISyntaxException
    {
        String answer = answeringStrategy.getAnswer("What is your name?");
        assertEquals("My name is test", answer);
    }

    @Test
    void testSraiTag() throws URISyntaxException
    {
        String answer = answeringStrategy.getAnswer("Identify");
        assertEquals("My name is test", answer);
    }

    @Test
    void testSrTag() throws URISyntaxException
    {
        String answer = answeringStrategy.getAnswer("Do Cry");
        assertEquals(":'(", answer);
    }

    @Test
    void testOrder() throws URISyntaxException, AIMLCreationException
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

    private AIMLHandler getAIMLHandler(List<AIML> aimlList) throws AIMLCreationException
    {

        Map<String, String> map = new HashMap<>();
        map.put("name", "test");
        AIMLHandler a = aimlHandlerBuilder.withBotMemory(map)
                        .withAimlProvider(new TestAIMLProvier(aimlList))
                        .build();
        return a;
    }

}
