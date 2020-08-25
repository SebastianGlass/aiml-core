package com.saxatus.aiml.internal.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("random")
public class RandomTag extends AbstractAIMLTag
{
    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        List<AIMLParseTag> options = new ArrayList<>();
        NodeList childNodes = getXMLNode(context).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName()
                            .equals("li"))
            {
                AIMLParseTag liTag = getSession(context).createTag(childNode);
                options.add(liTag);
            }
            else
            {
                // TODO: handle Syntax-Error
            }
        }

        int id = getRandom().nextInt(options.size());
        return options.get(id)
                        .handle(context.of(debugNode, childNodes.item(id)));
    }

    public Random getRandom()
    {
        return new Random();
    }

}