package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.utils.XMLUtils;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionContextImpl;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionImpl;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionImpl.TagParameterImpl;

public abstract class AbstractAIMLTagTest
{

    protected AIMLHandlerImpl aimlHandlerMock = mock(AIMLHandlerImpl.class);

    protected String handleAIMLTag(String template, String request, String pattern)
    {
        return handleAIMLTag(template, request, pattern, new HashMap<>(), new HashMap<>(), new AIMLParseNode("AIML"));
    }

    protected String handleAIMLTag(String template, String request, String pattern, AIMLParseNode debugNode)
    {
        return handleAIMLTag(template, request, pattern, new HashMap<>(), new HashMap<>(), debugNode);
    }

    protected String handleAIMLTag(String template, String request, String pattern, Map<String, String> botMemory,
                    Map<String, String> nonStaticMeory)
    {
        return handleAIMLTag(template, request, pattern, botMemory, nonStaticMeory, new AIMLParseNode("AIML"));
    }

    protected String handleAIMLTag(String template, String request, String pattern, Map<String, String> botMemory,
                    Map<String, String> nonStaticMeory, AIMLParseNode debugNode)
    {
        Node rootNode;
        AIMLParseTag tag = null;
        try
        {
            rootNode = XMLUtils.parseStringToXMLNode(template, "aiml");
            TagParameterImpl tp = new TagParameterImpl(request, pattern, "", botMemory, nonStaticMeory);
            AIMLParsingSession session = new AIMLParsingSessionImpl(tp, aimlHandlerMock);
            tag = session.createTag(rootNode);
            assertNotNull(tag);
            return tag.handle(new AIMLParsingSessionContextImpl(debugNode, rootNode, session));
        }
        catch(IOException | ParserConfigurationException | SAXException e)
        {
            e.printStackTrace();
            fail();
        }
        return null;
    }

}
