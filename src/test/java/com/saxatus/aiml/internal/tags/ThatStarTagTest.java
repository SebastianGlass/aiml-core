package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.factory.TagFactoryImpl.TagParameterImpl;

class ThatStarTagTest
{

    private ThatStarTag tag;
    private Node node = mock(Node.class);
    private TagFactory factory = mock(TagFactory.class);
    private TagParameterImpl tagParameter = mock(TagParameterImpl.class);
    private NamedNodeMap namedNodeMap = mock(NamedNodeMap.class);
    private AIMLHandlerImpl aimlHandler = mock(AIMLHandlerImpl.class);
    AIMLParseNode parseNode = mock(AIMLParseNode.class);

    @BeforeEach
    public void setup()
    {
        when(tagParameter.getBotMemory()).thenReturn(new HashMap<>());
        when(factory.getParameter()).thenReturn(tagParameter);
        when(factory.getAIMLHandler()).thenReturn(aimlHandler);
        when(aimlHandler.getThatStar()).thenReturn(Arrays.asList("A", "B", "C"));
        when(node.getAttributes()).thenReturn(namedNodeMap);
    }

    @Test
    void testThatWithNoParameter()
    {
        when(namedNodeMap.getLength()).thenReturn(0);
        tag = new ThatStarTag(node, factory);
        String result = tag.handle(parseNode);
        assertEquals("A", result);

    }

    @Test
    void testThat1()
    {
        Node node1 = mock(Node.class);
        when(namedNodeMap.getNamedItem("index")).thenReturn(node1);
        when(namedNodeMap.getLength()).thenReturn(1);
        when(node1.getNodeValue()).thenReturn("1");
        tag = new ThatStarTag(node, factory);
        String result = tag.handle(parseNode);
        assertEquals("A", result);

    }

    @Test
    void testThat2()
    {
        Node node1 = mock(Node.class);
        when(namedNodeMap.getNamedItem("index")).thenReturn(node1);
        when(namedNodeMap.getLength()).thenReturn(1);
        when(node1.getNodeValue()).thenReturn("2");
        tag = new ThatStarTag(node, factory);
        String result = tag.handle(parseNode);
        assertEquals("B", result);

    }

}
