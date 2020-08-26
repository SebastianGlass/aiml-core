package com.saxatus.aiml.internal.parsing.tags.abstracts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AbstractOptionsTagTest
{

    AbstractOptionsTag tag = new AbstractOptionsTag()
    {

        @Override
        public String getWrappedText(String childContent)
        {
            return childContent;
        }
    };

    @Test
    void testGetContentToStringNoChildren()
    {
        assertEquals("", tag.contentToString());
    }

}
