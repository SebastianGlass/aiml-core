package com.saxatus.aiml.internal.tags;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.AIMLHandler;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public abstract class AbstractAIMLTag implements AIMLParseTag
{

    private final Node node;
    private AIMLParseNode debugNode;
    private final TagFactory factory;

    public AbstractAIMLTag(Node node, TagFactory factory)
    {
        this.node = node;
        this.factory = factory;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        if (null == this.debugNode)
        {
            this.debugNode = new AIMLParseNode(this.getDebugInformation());
        }
        debugNode.addChild(this.debugNode);
        return ("Exception: not overwritten!");
    }

    protected String handleSubNodes()
    {
        StringBuilder result = new StringBuilder();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {

            Node childNode = childNodes.item(i);
            AIMLParseTag tag = factory.createTag(childNode);

            result.append(" ")
                            .append(tag.handle(debugNode))
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

    public Node getNode()
    {
        return node;
    }

    public AIMLParseNode getAIMLParseNode()
    {
        return debugNode;
    }

    public TagFactory getFactory()
    {
        return factory;
    }

    public String getOptionalAttribute(String string, String i)
    {
        NamedNodeMap attributes = getNode().getAttributes();
        String index = i;
        if (attributes.getLength() != 0)
        {
            index = getNode().getAttributes()
                            .getNamedItem(string)
                            .getNodeValue();
        }

        return index;
    }

    protected AIMLHandler getAIMLHandler()
    {
        return factory.getAIMLHandler();
    }

}
