package com.saxatus.aiml.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;

class AIMLHandlerImplTest
{
    @Mock
    AIMLParserProvider aimlParserFactory;
    @Mock
    AIMLParseNode node;
    @Mock
    private AIMLParser aimlParser;
    private Map<String, String> nonStatic;
    private AIMLHandlerImpl aimlHandler;

    @BeforeEach
    void setup()
    {
        nonStatic = new HashMap<String, String>();
        MockitoAnnotations.initMocks(this);
        when(aimlParser.parse(any())).thenReturn("it worked");
        List<AIML> list = Arrays.asList(new AIML("A B", "", null, null, "", 1),
                        new AIML("BRO KEN", "<>", null, null, "", 2), new AIML("A B", "", "that", "topic", "", 3));
        aimlHandler = new AIMLHandlerImpl(list, nonStatic, Collections.emptyMap(), null, aimlParserFactory);

        when(aimlParserFactory.provideTemplateParser(any(), any(), any(), any(), any())).thenReturn(aimlParser);

    }

    @Test
    void testGetDict() throws AIMLCreationException
    {

        assertEquals(3, aimlHandler.getDict()
                        .size());
    }

    @Test
    void testGetTopic() throws AIMLCreationException
    {
        nonStatic.put("topic", "topic");
        assertEquals(1, aimlHandler.getTopicDict()
                        .size());
    }

    @Test
    void testThatStar() throws AIMLCreationException
    {
        List<String> list = Collections.singletonList("star");
        aimlHandler.setThatStar(list);
        assertEquals(list, aimlHandler.getThatStar());
    }

    @Test
    void getAnswer2() throws AIMLNotFoundException, IOException
    {
        assertEquals("it worked", aimlHandler.getAnswer("a b", node));
        assertEquals("a b", aimlHandler.getInputHistory()
                        .get(0));
        assertEquals("it worked", aimlHandler.getOutputHistory()
                        .get(0));

    }

    @Test
    void getAnswer3() throws AIMLNotFoundException, IOException
    {
        assertEquals("it worked", aimlHandler.getAnswer("a b", "a b", node));

    }

    @Test
    void getAnswer3InternalException() throws AIMLNotFoundException, IOException
    {
        assertEquals("I've lost track, sorry.", aimlHandler.getAnswer("bro ken", "bro ken", node));
    }

    @Test
    void getAnswer2InternalException() throws AIMLNotFoundException, IOException
    {
        when(aimlParser.parse(any())).thenReturn("it worked");
        assertEquals("it worked", aimlHandler.getAnswer("NOT THERE", node));

    }

    @Test
    void getAnswer3NoAIMLException() throws AIMLNotFoundException, IOException
    {
        assertThrows(AIMLNotFoundException.class, () -> aimlHandler.getAnswer("NOT THERE", "NOT THERE", node));
    }

}
