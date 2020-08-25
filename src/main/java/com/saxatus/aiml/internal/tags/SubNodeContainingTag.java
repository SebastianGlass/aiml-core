package com.saxatus.aiml.internal.tags;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.AIMLParseTag;

public abstract class SubNodeContainingTag extends AbstractAIMLTag
{

    protected String handleSubNodes(AIMLParsingSessionContext context)
    {
        StringBuilder result = new StringBuilder();
        NodeList childNodes = getXMLNode(context).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {

            Node childNode = childNodes.item(i);
            AIMLParseTag tag = getSession(context).createTag(childNode);

            result.append(" ")
                            .append(tag.handle(context.of(debugNode, childNode)))
                            .append(" ");
        }

        String response = result.toString()
                        .trim()
                        .replace("  ", " ");
        List<String> s = Arrays.asList(" ", "?", ",", "!", ".", ":", ";");
        for (String string : s)
        {
            while(response.contains(" " + string))
            {
                response = response.replace(" " + string, string);
            }
        }
        return response;
    }

}
