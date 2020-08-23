package com.saxatus.aiml.tags;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.saxatus.aiml.AIMLHandler;
import com.saxatus.aiml.factory.AIMLDOMFactory;
import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.TagParameter;

public abstract class AbstractAIMLTagTest
{

    protected AIMLHandler aimlHandlerMock = mock(AIMLHandler.class);

    protected IAIMLTag getAIMLTag(String template, String request, String pattern)
    {
        return getAIMLTag(template, request, pattern, new HashMap<>(), new HashMap<>());
    }

    protected IAIMLTag getAIMLTag(String template, String request, String pattern, Map<String, String> botMemory,
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
