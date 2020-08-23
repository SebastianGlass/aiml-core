package com.saxatus.aiml.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AIMLTest
{

    @Test
    void testAIMLObject()
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
    void testAIMLOrder()
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

    @Test
    void testEqualsNoSource()
    {

        AIML a = new AIML("_ a", "", null, null, "", 1);
        AIML noSource1 = new AIML("_ a", "", null, null, null, 1);
        AIML noSource2 = new AIML("_ a", "", null, null, null, 1);

        assertNotEquals(a, noSource1);
        assertNotEquals(noSource1, a);
        assertEquals(noSource1, noSource2);
    }

    @Test
    void testEqualsNoTemplate()
    {

        AIML a = new AIML("_ a", "", null, null, "", 1);
        AIML noSource1 = new AIML("_ a", null, null, null, "", 1);
        AIML noSource2 = new AIML("_ a", null, null, null, "", 1);

        assertNotEquals(noSource1, a);
        assertEquals(noSource1, noSource2);
    }

    @Test
    void testEqualsNoTopic()
    {

        AIML a = new AIML("_ a", "", "", "", "", 1);
        AIML noSource1 = new AIML("_ a", "", "", null, "", 1);
        AIML noSource2 = new AIML("_ a", "", "", null, "", 1);

        assertNotEquals(noSource1, a);
        assertEquals(noSource1, noSource2);
    }

    @Test
    void testEqualsNoThat()
    {

        AIML a = new AIML("_ a", "", "", null, "", 1);
        AIML noSource1 = new AIML("_ a", "", null, null, "", 1);
        AIML noSource2 = new AIML("_ a", "", null, null, "", 1);

        assertNotEquals(noSource1, a);
        assertEquals(noSource1, noSource2);
    }

    @Test
    void testEquals()
    {

        AIML a = new AIML("_ a", "", "", "", "", 1);
        AIML a3 = new AIML("_ a", "", "", "b", "", 1);
        AIML b = new AIML("a a", "", null, null, "", 1);
        AIML c = new AIML("_ a", "", "c", null, "", 1);
        AIML d = new AIML("_ a", "", null, "d", "", 1);
        AIML e = new AIML("_ a", "", "c", "d", "", 1);
        AIML a2 = new AIML("_ a", "", "", "", "", 1);
        AIML f2 = new AIML("_ a", "", null, null, "", 2);
        AIML g = new AIML("_ a", null, null, null, "", 1);
        AIML h = new AIML(null, null, null, null, "", 1);
        AIML h2 = new AIML(null, null, "c1", null, "", 1);
        String other = "o";
        String other2 = null;
        assertEquals(a, a);
        assertNotEquals(a, other2);
        assertNotEquals(a, b);
        assertNotEquals(a, other);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertEquals(a, a2);
        assertNotEquals(a, f2);
        assertNotEquals(a, g);
        assertNotEquals(h, h2);
        assertNotEquals(a, a3);

        assertNotEquals(h, g);
    }

    private void isSymetric(AIML a, AIML b)
    {
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
    }
}
