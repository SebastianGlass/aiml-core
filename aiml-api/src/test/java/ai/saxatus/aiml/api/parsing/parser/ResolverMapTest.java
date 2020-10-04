package ai.saxatus.aiml.api.parsing.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResolverMapTest
{
    ResolverMap<Integer> map;

    @BeforeEach
    void setUp() throws Exception
    {
        map = new ResolverMap<>();
        map.put(String.class, s -> s.length());
        map.put(Integer.class, s -> s);
    }

    @Test
    void testGetResolverFor() throws Exception
    {
        Function<String, Integer> resolver = map.getResolverFor(String.class);
        assertNotNull(resolver);
        assertEquals(4, resolver.apply("five"));
    }

    @Test
    void testGetResolverForUnknown() throws Exception
    {
        Function<Boolean, Integer> resolver = map.getResolverFor(Boolean.class);
        assertNull(resolver);
    }

    @Test
    void testSize() throws Exception
    {
        assertEquals(2, map.size());
    }

}
