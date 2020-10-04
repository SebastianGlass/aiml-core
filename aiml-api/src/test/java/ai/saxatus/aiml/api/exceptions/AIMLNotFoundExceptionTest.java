package ai.saxatus.aiml.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIMLNotFoundExceptionTest
{

    AIMLNotFoundException e;

    @BeforeEach
    void setUp() throws Exception
    {
        e = new AIMLNotFoundException("INPUT");
    }

    @Test
    void testGetMessage()
    {
        assertEquals("No AIML found for input: INPUT", e.getMessage());
    }

    @Test
    void testGetInput() throws Exception
    {
        assertEquals("INPUT", e.getInput());
    }

}
