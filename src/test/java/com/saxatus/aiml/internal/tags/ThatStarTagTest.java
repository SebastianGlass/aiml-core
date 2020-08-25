package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.parsing.AIMLParsingSessionImpl.TagParameterImpl;

class ThatStarTagTest
{

    private ThatStarTag tag = new ThatStarTag();
    private Node node = mock(Node.class);
    private AIMLParsingSession session = mock(AIMLParsingSession.class);
    private TagParameterImpl tagParameter = mock(TagParameterImpl.class);
    private NamedNodeMap namedNodeMap = mock(NamedNodeMap.class);
    private AIMLHandlerImpl aimlHandler = mock(AIMLHandlerImpl.class);
    AIMLParseNode parseNode = mock(AIMLParseNode.class);

    @Mock
    AIMLParsingSessionContext context;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        when(tagParameter.getBotMemory()).thenReturn(new HashMap<>());
        when(session.getParameter()).thenReturn(tagParameter);
        when(session.getAIMLHandler()).thenReturn(aimlHandler);
        when(aimlHandler.getThatStar()).thenReturn(Arrays.asList("A", "B", "C"));
        when(node.getAttributes()).thenReturn(namedNodeMap);

        when(context.getXMLNode()).thenReturn(node);
        when(context.getSession()).thenReturn(session);
        when(context.getDebugNode()).thenReturn(parseNode);
    }

    @Test
    void testThatWithNoParameter()
    {
        when(namedNodeMap.getLength()).thenReturn(0);
        String result = tag.handle(context);
        assertEquals("A", result);

    }

    @Test
    void testThat1()
    {
        Node node1 = mock(Node.class);
        when(namedNodeMap.getNamedItem("index")).thenReturn(node1);
        when(namedNodeMap.getLength()).thenReturn(1);
        when(node1.getNodeValue()).thenReturn("1");
        String result = tag.handle(context);
        assertEquals("A", result);

    }

    @Test
    void testThat2()
    {
        Node node1 = mock(Node.class);
        when(namedNodeMap.getNamedItem("index")).thenReturn(node1);
        when(namedNodeMap.getLength()).thenReturn(1);
        when(node1.getNodeValue()).thenReturn("2");
        String result = tag.handle(context);
        assertEquals("B", result);

    }

}
