package com.saxatus.aiml.internal.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSession.TagParameter;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.utils.XMLUtils;

class LearnTagTest
{

    LearnTag tag;
    @Mock
    AIMLParsingSession session;
    @Mock
    TagParameter tagParameter;

    @BeforeEach
    void setup() throws ParserConfigurationException, SAXException, IOException
    {
        MockitoAnnotations.initMocks(this);
        when(session.getParameter()).thenReturn(tagParameter);
        when(tagParameter.getBotMemory()).thenReturn(Collections.emptyMap());
        when(tagParameter.getNonStaticMemory()).thenReturn(Collections.emptyMap());
        tag = new LearnTag(newNode(), session);
        when(session.createTag(any())).then(answer());

    }

    private Answer<AIMLParseTag> answer()
    {

        return new Answer<AIMLParseTag>()
        {
            public AIMLParseTag answer(InvocationOnMock invocation)
            {
                Object arg = invocation.getArguments()[0];
                if (arg instanceof Node)
                {
                    String name = ((Node)arg).getNodeName();
                    AIMLParseTag p = mock(AIMLParseTag.class);
                    when(p.handle(any())).thenReturn(name);
                    return p;
                }
                return null;

            }
        };
    }

    private Node newNode() throws ParserConfigurationException, SAXException, IOException
    {
        return XMLUtils.parseStringToXMLNode(
                        "<!--comment--><category><pattern>pattern</pattern><template>template</template><unused></unused></category>",
                        "aiml");

    }

    @Test
    void test()
    {
        AIML result = tag.createAIML();
        assertEquals("template", result.getTemplate());
        assertEquals("pattern", result.getPattern());
    }

}
