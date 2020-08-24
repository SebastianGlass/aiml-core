package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.factory.AIMLDOMFactory;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagParameter;

public abstract class AbstractAIMLTagTest
{

    protected AIMLHandlerImpl aimlHandlerMock = mock(AIMLHandlerImpl.class);

    protected AIMLParseTag getAIMLTag(String template, String request, String pattern)
    {
        return getAIMLTag(template, request, pattern, new HashMap<>(), new HashMap<>());
    }

    protected AIMLParseTag getAIMLTag(String template, String request, String pattern, Map<String, String> botMemory,
                    Map<String, String> nonStaticMeory)
    {
        Node rootNode;
        AIMLParseTag tag = null;
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
