package example;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.io.AIMLDirectoryReader;
import com.saxatus.aiml.module.AIMLModule;

public class InteractiveExample
{
    @Inject
    AIMLHandlerBuilder aimlHandlerBuilder;

    private static final Log log = LogFactory.getLog(InteractiveExample.class);

    private Map<String, String> botMem = new HashMap<>();

    private AIMLHandler getAIMLHandler() throws AIMLCreationException
    {
        URL r = getClass().getResource("/interactiveCorpus");
        File file = new File(r.getFile());
        botMem.put("name", "TestBot");

        return aimlHandlerBuilder.nonStaticMemory(new HashMap<>())
                        .withBotMemory(botMem)
                        .withAimlProvider(new AIMLDirectoryReader(file))
                        .build();
    }

    public void getAnswer()
    {
        AIMLHandler handler = null;
        try
        {

            handler = getAIMLHandler();

        }
        catch(AIMLCreationException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        try (Scanner sc = new Scanner(System.in))
        {
            String input;
            System.out.println("Interactive Bot initialized!");
            System.out.println("Hello my name is " + botMem.get("name"));
            do
            {
                System.out.print("Input: \t");
                input = sc.nextLine();
                handler.resetDepth();
                String response = handler.getAnswer(input);
                System.out.println("Result:\t" + response);
            }
            while(!input.equals("q"));
        }
        System.out.println("Interactive Bot terminated!");

    }

    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new AIMLModule());
        InteractiveExample example = new InteractiveExample();
        injector.injectMembers(example);
        example.getAnswer();

    }
}
