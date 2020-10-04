package ai.saxatus.aiml.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIMLTransformExceptionTest
{

    AIMLTransformException e;
    Exception cause;

    @BeforeEach
    void setUp() throws Exception
    {
        cause = mock(Exception.class);
        e = new AIMLTransformException(cause);
    }

    @Test
    void testAIMLTransformException() throws Exception
    {
        assertEquals(cause, e.getCause());
    }

}
