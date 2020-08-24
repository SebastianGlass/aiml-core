package example;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.module.AIMLModule;

public class BasicExample
{

    @Inject
    AIMLHandlerBuilder aimlHandlerBuilder;
    
    private static final Log log = LogFactory.getLog(BasicExample.class);
    private Map<String, String> botMem = new HashMap<>();

    private AIMLHandler getAIMLHandler()
    {
        URL r = getClass().getResource("/example.aiml");
        File file = new File(r.getFile());
        botMem.put("name", "TestBot");

        return aimlHandlerBuilder.nonStaticMemory(new HashMap<>())
                        .withBotMemory(botMem)
                        .withAimlProvider(new AIMLFileReader(file))
                        .build();
    }

    public void getAnswer()
    {
        AIMLHandler handler = getAIMLHandler();

        String response = handler.getAnswer("What's your name?");
        log.info(response);

        response = handler.getAnswer("My name is User");
        log.info(response);
    }

    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new AIMLModule());
        BasicExample example = new BasicExample();
        injector.injectMembers(example);
        example.getAnswer();

    }
}
