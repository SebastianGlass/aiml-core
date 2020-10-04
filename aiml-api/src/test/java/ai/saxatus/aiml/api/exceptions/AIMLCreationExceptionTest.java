package ai.saxatus.aiml.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIMLCreationExceptionTest
{

    AIMLCreationException e;
    Exception cause;

    @BeforeEach
    void setUp() throws Exception
    {
        cause = mock(Exception.class);

    }

    @Test
    void testAIMLTransformException() throws Exception
    {
        assertEquals(cause, new AIMLCreationException(cause).getCause());
    }

    @Test
    void testAIMLTransformExceptionMsg() throws Exception
    {
        assertEquals("uff", new AIMLCreationException("uff").getMessage());
    }

    @Test
    void testAIMLTransformException2() throws Exception
    {
        AIMLCreationException aimlCreationException = new AIMLCreationException("uff", cause);
        assertEquals("uff", aimlCreationException.getMessage());
        assertEquals(cause, aimlCreationException.getCause());
    }

}
