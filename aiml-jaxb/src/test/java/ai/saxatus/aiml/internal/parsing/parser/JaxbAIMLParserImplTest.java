package ai.saxatus.aiml.internal.parsing.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Node;

import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLResponse;
import ai.saxatus.aiml.internal.parsing.tags.BotTag;
import ai.saxatus.aiml.internal.parsing.tags.ConditionTag;
import ai.saxatus.aiml.internal.parsing.tags.GetTag;
import ai.saxatus.aiml.internal.parsing.tags.LiTag;
import ai.saxatus.aiml.internal.parsing.tags.SrTag;
import ai.saxatus.aiml.internal.parsing.tags.SraiTag;
import ai.saxatus.aiml.internal.parsing.tags.StarTag;
import ai.saxatus.aiml.internal.parsing.tags.TemplateTag;
import ai.saxatus.aiml.internal.parsing.tags.TextNode;

class JaxbAIMLParserImplTest
{

    JaxbAIMLParserImpl parser;
    @Mock
    private AIMLHandler handler;
    @Mock
    private JaxbAIMLTransformer<TemplateTag> transformer;
    @Mock
    private Node node;
    @Mock
    TemplateTag tag;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        Map<String, String> staticMemory = new HashMap<>();
        staticMemory.put("bot", "tob");
        Map<String, String> nonStaticMemory = new HashMap<>();
        nonStaticMemory.put("get", "teg");

        when(handler.getStaticMemory()).thenReturn(staticMemory::get);
        when(handler.getNonStaticMemory()).thenReturn(nonStaticMemory);
        when(handler.increaseDepth()).thenReturn(handler);
        when(handler.getAnswer("SRAI PATTERN")).thenReturn(new AIMLResponse("SRAI ANSWER", null));

        when(transformer.transform(node)).thenReturn(tag);
        when(tag.getWrappedText(anyString())).then(input -> "t(" + input.getArgument(0) + ")");
        parser = new JaxbAIMLParserImpl("MY PATTERN *", "my pattern works", handler, transformer);
    }

    @Test
    void testInitWorked() throws Exception
    {
        assertEquals(4, parser.resolverByClass.size());
    }

    @Test
    void testParseNodeNoContent() throws Exception
    {
        AIMLResponse response = parser.parse(node);
        assertEquals("t()", response.getAnswer());
        when(tag.getContent()).thenReturn(null);
        response = parser.parse(node);
        assertEquals("t()", response.getAnswer());

    }

    @Test
    void testParseNodeStringContent() throws Exception
    {
        when(tag.getContent()).thenReturn(Collections.singletonList(new TextNode("Hallo")));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(Hallo)", response.getAnswer());
    }

    @Test
    void testParseNodeWithStarRequiringNode() throws Exception
    {
        when(tag.getContent()).thenReturn(Collections.singletonList(new StarTag()));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(works)", response.getAnswer());
    }

    @Test
    void testParseNodeWithStaticMemoryUsingNode() throws Exception
    {
        BotTag child = new BotTag();
        child.setName("bot");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(tob)", response.getAnswer());
    }

    @Test
    void testParseNodeWithNonStaticMemoryUsingNode() throws Exception
    {
        GetTag child = new GetTag();
        child.setName("get");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(teg)", response.getAnswer());
    }

    @Test
    void testParseNodeWitDecisionMakingNode() throws Exception
    {
        ConditionTag child = new ConditionTag();

        LiTag li1 = mock(LiTag.class);
        when(li1.getValue()).thenReturn("teg");
        when(li1.getWrappedText(any())).thenReturn("li1");
        LiTag li2 = mock(LiTag.class);
        when(li2.getWrappedText(any())).thenReturn("li2");

        child.setContent(Arrays.asList(li1, li2));

        child.setName("get");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(li1)", response.getAnswer());

        child.setName("foo");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        response = parser.parse(node);
        assertEquals("t(li2)", response.getAnswer());
    }

    @Test
    void testParseNodeWithContentNeedsOwnRequestNode() throws Exception
    {
        SraiTag child = mock(SraiTag.class);
        when(child.getWrappedText(any())).thenReturn("SRAI PATTERN");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(SRAI ANSWER)", response.getAnswer());
    }

    @Test
    void testParseNodeWithContentNeedsOwnRequestNodeWithLeaf() throws Exception
    {
        SrTag child = mock(SrTag.class);
        when(child.getText()).thenReturn("SRAI PATTERN");
        when(tag.getContent()).thenReturn(Collections.singletonList(child));
        AIMLResponse response = parser.parse(node);
        assertEquals("t(SRAI ANSWER)", response.getAnswer());
    }

}
