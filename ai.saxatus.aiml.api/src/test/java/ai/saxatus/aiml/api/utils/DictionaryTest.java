package ai.saxatus.aiml.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest
{
    Dictionary<String, String> d;

    @BeforeEach
    void setUp()
    {
        d = new Dictionary<>();

        d.put("a", "1");
        d.put("a", "2");
        d.put("b", "1");
    }

    @Test
    void testPut()
    {
        // Put is part of the Setup
        assertEquals("[1, 2]", d.get("a")
                        .toString());

        assertEquals("[1]", d.get("b")
                        .toString());
        assertEquals(3, d.size());

        assertEquals("{a=[1, 2], b=[1]}", d.toString());
    }

    @Test
    void testPutSet()
    {
        d.put("c", Collections.singleton("3"));
        assertEquals("[[1, 2], [1], [3]]", d.values()
                        .toString());
        d.put("a", Collections.singleton("3"));
        assertEquals("[[1, 2, 3], [1], [3]]", d.values()
                        .toString());

    }

    @Test
    void testPutAllWithNull()
    {
        Dictionary<String, String> d2 = new Dictionary<>();
        d2.put("b", "3");

        d2.putAll((Dictionary<String, String>)null);
        assertEquals("[3]", d2.get("b")
                        .toString());
    }

    @Test
    void testPutAll()
    {
        Dictionary<String, String> d2 = new Dictionary<>();
        d2.put("b", "3");
        d2.putAll(d);
        assertEquals("[1, 2]", d2.get("a")
                        .toString());

        assertEquals("[1, 3]", d2.get("b")
                        .toString());
        assertEquals(4, d2.size());

    }

    @Test
    void testClear()
    {
        assertFalse(d.isEmpty());
        d.clear();
        assertEquals(0, d.size());
        assertTrue(d.isEmpty());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void testValues()
    {
        assertEquals("[[1, 2], [1]]", d.values()
                        .toString());
        assertTrue(d.containsValue("2"));
        assertFalse(d.containsValue("3"));

    }

    @Test
    void testKeySet()
    {
        assertEquals("[a, b]", d.keySet()
                        .toString());
        assertTrue(d.containsKey("a"));
        assertFalse(d.containsKey("c"));
    }

    @Test
    void testEntrySet()
    {
        assertEquals("[a=[1, 2], b=[1]]", d.entrySet()
                        .toString());
    }

    @Test
    void testRemove()
    {
        assertEquals("[1, 2]", d.remove("a")
                        .toString());
        assertEquals("[[1]]", d.values()
                        .toString());
    }

}
