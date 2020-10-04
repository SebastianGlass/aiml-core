package ai.saxatus.aiml.internal;

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
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.saxatus.aiml.api.AIMLResponse;
import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.exceptions.AIMLNotFoundException;
import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.parsing.AIMLParser;
import ai.saxatus.aiml.api.provider.AIMLParserProvider;

class AIMLHandlerImplTest
{
    @Mock
    AIMLParserProvider aimlParserProvider;
    @Mock
    AIMLParser aimlParser;

    private Map<String, String> nonStatic;
    private AIMLHandlerImpl aimlHandler;

    @BeforeEach
    void setUp()
    {
        nonStatic = new HashMap<String, String>();
        MockitoAnnotations.initMocks(this);
        when(aimlParser.parse(any())).thenReturn(new AIMLResponse("it worked", null));
        List<AIML> list = Arrays.asList(new AIML("A B", "", null, null, "", 1),
                        new AIML("BRO KEN", "<>", null, null, "", 2), new AIML("A B", "", "that", "topic", "", 3));
        aimlHandler = new AIMLHandlerImpl(list, nonStatic, s -> "foo".equals(s) ? "bar" : null, null,
                        aimlParserProvider);

        when(aimlParserProvider.provideTemplateParser(any(), any(), any())).thenReturn(aimlParser);

    }

    @Test
    void testGetDict() throws AIMLCreationException
    {

        assertEquals(3, aimlHandler.getDict()
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
        assertEquals("it worked", aimlHandler.getAnswer("a b")
                        .getAnswer());
        assertEquals("a b", aimlHandler.getInputHistory()
                        .get(0));
        assertEquals("it worked", aimlHandler.getOutputHistory()
                        .get(0));

    }

    @Test
    void getAnswer3() throws AIMLNotFoundException, IOException
    {
        assertEquals("it worked", aimlHandler.getAnswer("a b", "a b")
                        .getAnswer());

    }

    @Test
    void getAnswer3InternalException() throws AIMLNotFoundException, IOException
    {
        assertEquals("I've lost track, sorry.", aimlHandler.getAnswer("bro ken", "bro ken")
                        .getAnswer());
    }

    @Test
    void getAnswer2InternalException() throws AIMLNotFoundException, IOException
    {
        when(aimlParser.parse(any())).thenReturn(new AIMLResponse("it worked", null));
        assertEquals("it worked", aimlHandler.getAnswer("NOT THERE")
                        .getAnswer());

    }

    @Test
    void getAnswer3NoAIMLException() throws AIMLNotFoundException, IOException
    {
        assertThrows(AIMLNotFoundException.class, () -> aimlHandler.getAnswer("NOT THERE", "NOT THERE"));
    }

    @Test
    void testReplaceBotTagsInPattern() throws Exception
    {
        String result = AIMLHandlerImpl.replaceBotTagsInPattern("input <BOT NAME=\"a\"/>", s -> s);
        assertEquals("input A", result);
    }

    @Test
    public void testGetStaticMemory() throws Exception
    {
        UnaryOperator<String> f = aimlHandler.getStaticMemory();
        assertEquals("bar", f.apply("foo"));
    }

}
