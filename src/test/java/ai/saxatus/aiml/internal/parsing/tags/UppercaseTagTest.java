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

class UppercaseTagTest
{

    UppercaseTag tag;

    @BeforeEach
    void setUp()
    {
        tag = new UppercaseTag();
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
        assertEquals("CONTENT", tag.getWrappedText("content"));
    }

    @Test
    void testToString()
    {
        assertEquals("<uppercase></uppercase>", tag.toString());
    }

    @Test
    void testToStringWithContent()
    {
        AbstractAIMLContentTag node = mock(AbstractAIMLContentTag.class);
        when(node.toString()).thenReturn("content");
        tag.setContent(Collections.singletonList(node));
        assertEquals("<uppercase>content</uppercase>", tag.toString());
    }

}
