package example;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.saxatus.aiml.AIMLHandler;
import com.saxatus.aiml.AIMLHandlerBuilder;
import com.saxatus.aiml.api.io.AIMLFileReader;
import com.saxatus.aiml.api.parsing.AIML;

public class BasicExample
{
    private static final Log log = LogFactory.getLog(BasicExample.class);
    private static Map<String, String> botMem = new HashMap<>();

    private static AIMLHandler getAIMLHandler()
    {
        File file = new File(BasicExample.class.getResource("/example.aiml")
                        .getFile());
        botMem.put("name", "TestBot");
        List<AIML> aimlList = new ArrayList<>();
        try (AIMLFileReader reader = new AIMLFileReader(file))
        {
            aimlList = reader.withBotMemory(botMem)
                            .stream()
                            .collect(Collectors.toList());
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return new AIMLHandlerBuilder().nonStaticMemory(new HashMap<>())
                        .withBotMemory(botMem)
                        .withAiml(aimlList)
                        .build();
    }

    public static void main(String[] args)
    {
        AIMLHandler handler = getAIMLHandler();

        String response = handler.getAnswer("What's your name?");
        log.info(response);

        response = handler.getAnswer("My name is User");
        log.info(response);
    }
}
