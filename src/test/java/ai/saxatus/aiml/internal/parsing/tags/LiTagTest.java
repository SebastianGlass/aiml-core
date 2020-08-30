package ai.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class LiTagTest
{

    LiTag tag;

    @BeforeEach
    void setUp()
    {
        tag = new LiTag();

    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof ContentEnclosingNode);
    }

    @Test
    void testGetWrappedText()
    {
        assertEquals("content", tag.getWrappedText("content"));
    }

    @Test
    void testToString()
    {
        assertEquals("<li></li>", tag.toString());
    }

    @Test
    void testToStringWithContent()
    {
        AbstractAIMLContentTag node = mock(AbstractAIMLContentTag.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<li>content</li>", tag.toString());
    }

    @Test
    void testToStringWithValue()
    {
        tag.setValue("val");
        assertEquals("<li value=\"val\"></li>", tag.toString());
    }

    @Test
    void testGetValue()
    {
        tag.setValue("val");
        assertEquals("val", tag.getValue());
    }

    @Test
    void testToStringWithContentWithValue()
    {
        tag.setValue("val");
        AbstractAIMLContentTag node = mock(AbstractAIMLContentTag.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<li value=\"val\">content</li>", tag.toString());
    }

}
