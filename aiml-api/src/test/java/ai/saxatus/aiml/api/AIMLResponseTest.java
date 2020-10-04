package ai.saxatus.aiml.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.saxatus.aiml.api.parsing.AIML;

class AIMLResponseTest
{

    @Mock
    AIML aiml;
    AIMLResponse response;
    AIMLResponse responseWithAIML;

    @BeforeEach
    protected void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        response = new AIMLResponse("answer", "xml");
        responseWithAIML = new AIMLResponse("answer", aiml, "xml");
    }

    @Test
    void testGetAnswer() throws Exception
    {
        assertEquals("answer", response.getAnswer());
    }

    @Test
    void testGetResolutionXML() throws Exception
    {
        assertEquals("xml", response.getResolutionXML());
    }

    @Test
    void testGetAimlPattern() throws Exception
    {
        assertNull(response.getAiml());
        assertEquals(aiml, responseWithAIML.getAiml());
    }

    @Test
    void testToString() throws Exception
    {
        assertEquals("AIMLResponse [string=answer, resolutionXML=xml]", responseWithAIML.toString());
    }

    @Test
    void testWithAIML() throws Exception
    {
        assertNotEquals(response, response.withAIML(aiml));
        assertEquals(aiml, response.withAIML(aiml)
                        .getAiml());
    }

}
