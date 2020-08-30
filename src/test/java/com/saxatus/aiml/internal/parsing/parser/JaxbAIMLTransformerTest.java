package com.saxatus.aiml.internal.parsing.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.utils.XMLUtils;
import com.saxatus.aiml.internal.parsing.tags.TemplateTag;

class JaxbAIMLTransformerTest
{
    JaxbAIMLTransformer<?> a;

    @BeforeEach
    void setUp()
    {
        a = new JaxbAIMLTransformer<>();
    }

    @Test
    void test() throws Exception
    {
        Node node = XMLUtils.parseStringToXMLNode("Hello", "template");
        AIMLContentNode result = a.transform(node);
        assertTrue(result instanceof TemplateTag);
        assertEquals(1, ((TemplateTag)result).getContent()
                        .size());
        assertTrue((Object)((TemplateTag)result).getContent()
                        .get(0) instanceof String);
    }

}
