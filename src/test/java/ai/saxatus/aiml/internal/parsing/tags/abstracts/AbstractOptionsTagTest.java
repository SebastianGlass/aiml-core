package ai.saxatus.aiml.internal.parsing.tags.abstracts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbstractOptionsTagTest
{

    static AbstractOptionsTag tag = new TestOptionTag();

    @Test
    void testGetContentToStringNoChildren()
    {
        assertEquals("", tag.contentToString());
    }

    @Test
    void testFallback()
    {
        assertEquals("", AbstractOptionsTag.FALLBACK_LITAG.getWrappedText("content"));
        assertEquals("", AbstractOptionsTag.FALLBACK_LITAG.getValue());
    }

}
