package ai.saxatus.aiml.api.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.parsing.AIML;

class AIMLFileReaderTest
{

    static final URL COMPLEX_AIML = AIMLFileReaderTest.class.getResource("/complexAIML.aiml");

    @Test
    void readComplexAIMLFile() throws AIMLCreationException
    {

        AIMLFileReader reader = new AIMLFileReader(new File(COMPLEX_AIML.getFile()));
        Collection<AIML> aimlList = reader.provide();
        assertElements(8, aimlList, aiml -> true);

        assertElements(2, aimlList, aiml -> "that".equals(aiml.getThat()));
        assertElements(6, aimlList, aiml -> aiml.getThat() == null);

        assertElements(5, aimlList, aiml -> "topic".equals(aiml.getTopic()));
        assertElements(3, aimlList, aiml -> aiml.getTopic() == null);

        assertElements(0, aimlList, aiml -> aiml.getPattern() == null);
        assertElements(0, aimlList, aiml -> aiml.getTemplate() == null);

        assertElements(2, aimlList, aiml -> "PATTERN".equals(aiml.getPattern()));
        assertElements(2, aimlList, aiml -> "THAT1".equals(aiml.getPattern()));
        assertElements(1, aimlList, aiml -> "THAT1".equals(aiml.getPattern()) && aiml.getTopic() == null);
        assertElements(1, aimlList, aiml -> "<BOT NAME=\"TEST\"/> 123".equals(aiml.getPattern()));

    }

    private void assertElements(int count, Collection<AIML> aimlList, Predicate<AIML> predicate)
    {
        assertEquals(count, aimlList.stream()
                        .filter(predicate)
                        .count());
    }

}
