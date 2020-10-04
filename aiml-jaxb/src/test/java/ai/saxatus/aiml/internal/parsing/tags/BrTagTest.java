package ai.saxatus.aiml.internal.parsing.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.saxatus.aiml.api.parsing.tags.LeafNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

class BrTagTest
{

    BrTag tag;

    @BeforeEach
    void setUp()
    {
        tag = new BrTag();
    }

    @Test
    void testInterfaces()
    {
        assertTrue(tag instanceof AbstractAIMLContentTag);
        assertTrue(tag instanceof LeafNode);
    }

    @Test
    void testGetText()
    {
        assertEquals("\n", tag.getText());
    }

    @Test
    void testToString()
    {
        assertEquals("<br/>", tag.toString());
    }

}
