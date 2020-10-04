package example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLHandlerBuilder;
import ai.saxatus.aiml.api.AIMLResponse;
import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.io.AIMLFileReader;
import ai.saxatus.aiml.module.AIMLModule;

class BasicIntegrationTest
{
    AIMLHandlerBuilder aimlHandlerBuilder;

    private Map<String, String> botMem = new HashMap<>();

    AIMLHandler handler;

    @BeforeEach
    void setUp() throws AIMLCreationException
    {
        Injector injector = Guice.createInjector(new AIMLModule());
        aimlHandlerBuilder = injector.getInstance(AIMLHandlerBuilder.class);

        URL r = getClass().getResource("/example.aiml");
        File file = new File(r.getFile());
        botMem.put("name", "TestBot");

        this.handler = aimlHandlerBuilder.nonStaticMemory(new HashMap<>())
                        .withBotMemory(botMem)
                        .withAimlProvider(new AIMLFileReader(file))
                        .build();
    }

    @Test
    void test()
    {
        AIMLResponse response = handler.getAnswer("Hello TestBot");
        assertEquals("Hello User.", response.getAnswer());

        response = handler.getAnswer("Condition test");
        assertEquals("No topic set.", response.getAnswer());

        response = handler.getAnswer("What's your name?");
        assertEquals("My name is TestBot", response.getAnswer());

        response = handler.getAnswer("My name is User");
        assertEquals("Random Name Sentence", response.getAnswer());

        response = handler.getAnswer("Condition test");
        assertEquals("Topic is names.", response.getAnswer());
    }
}
