package com.saxatus.aiml.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AIMLTest
{

    @Test
    public void testAIMLObject()
    {
        AIML a = new AIML("", "", null, null, "", 1);
        assertEquals("AIML [pattern=, template=, src=, line=1]", a.toString());
        a.setPattern("A");
        assertEquals("AIML [pattern=A, template=, src=, line=1]", a.toString());
        a.setTemplate("B");
        assertEquals("AIML [pattern=A, template=B, src=, line=1]", a.toString());
        a.setThat("C");
        assertEquals("AIML [pattern=A, template=B, that=C, src=, line=1]", a.toString());
        a.setTopic("D");
        assertEquals("AIML [pattern=A, template=B, that=C, src=, topic=D, line=1]", a.toString());
    }

    @Test
    public void testAIMLOrder()
    {
        AIML a = new AIML("_ a", "", null, null, "", 1);
        AIML b = new AIML("a a", "", null, null, "", 1);
        AIML c = new AIML("* a", "", null, null, "", 1);
        AIML d = new AIML("* a", "", "a", null, "", 1);
        isSymetric(a, b);
        isSymetric(b, c);
        isSymetric(a, c);
        isSymetric(d, c);

        AIML e = new AIML("I like *", "", null, null, "", 1);
        AIML f = new AIML("I *", "", null, null, "", 1);
        isSymetric(e, f);

        AIML g = new AIML("Hello", "", null, null, "", 1);
        AIML h = new AIML("*", "", null, null, "", 1);
        isSymetric(g, h);
    }

    private void isSymetric(AIML a, AIML b)
    {
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
    }
}
