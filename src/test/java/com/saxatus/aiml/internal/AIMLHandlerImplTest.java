package com.saxatus.aiml.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.io.AIMLCreationException;
import com.saxatus.aiml.api.parsing.AIML;

class AIMLHandlerImplTest
{
    @Mock
    AIMLParserFactory aimlParserFactory;

    @BeforeEach
    void setup()
    {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void testAIMLHandlerBuilderWithList() throws AIMLCreationException
    {
        List<AIML> list = Arrays.asList(new AIML("", "", "", "", "", 1), new AIML("", "", "", "", "", 2));

        AIMLHandlerImpl aimlHandler = new AIMLHandlerImpl(list, Collections.emptyMap(), Collections.emptyMap(), null,
                        aimlParserFactory);

        assertEquals(2, aimlHandler.getDict()
                        .size());
    }

}
