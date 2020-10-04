package ai.saxatus.aiml.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringUtilsTest
{

    @BeforeEach
    protected void setUp() throws Exception
    {
    }

    @Test
    void testClearString() throws Exception
    {
        assertEquals("foo bar", StringUtils.clearString("foo:\n bar?"));
    }

    @Test
    void testInnerTrim() throws Exception
    {
        assertEquals("foo bar", StringUtils.innerTrim(" foo  bar "));
    }

    @Test
    void testToRegex() throws Exception
    {
        assertEquals("^a\\s(.+)\\sb$", StringUtils.toRegex("a * b"));
    }

    @Test
    void testCompareTo() throws Exception
    {
        assertEquals(0, StringUtils.compareTo(null, null));
        assertEquals(-1, StringUtils.compareTo("a", null));
        assertEquals(1, StringUtils.compareTo(null, "b"));
        assertEquals(-1, StringUtils.compareTo("a", "b"));

    }

}
