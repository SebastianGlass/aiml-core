package com.saxatus.aiml.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class RandomTag extends AbstractAIMLTag
{

    private RandomTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "random";

    public static void register()
    {
        TagFactory.addTag(TAG, RandomTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        List<IAIMLTag> options = new ArrayList<>();
        NodeList childNodes = getNode().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals("li"))
            {
                IAIMLTag liTag = getFactory().createTag(childNode);
                options.add(liTag);
            }
            else
            {
                // TODO: handle Syntax-Error
            }
        }

        return options.get(getRandom().nextInt(options.size())).handle(getAIMLParseNode());
    }

    public Random getRandom()
    {
       return new Random();
    }

}