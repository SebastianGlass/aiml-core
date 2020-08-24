package com.saxatus.aiml.internal.tags;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.tags.TagName;

public abstract class AbstractAIMLTag implements AIMLParseTag
{

    private final Node node;
    private AIMLParseNode debugNode;
    private final AIMLParsingSession session;

    public AbstractAIMLTag(Node node, AIMLParsingSession session)
    {
        this.node = node;
        this.session = session;
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
            AIMLParseTag tag = getSession().createTag(childNode);

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

    public AIMLParsingSession getSession()
    {
        return session;
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
        return getSession().getAIMLHandler();
    }

    @Override
    public String getTag()
    {
        TagName[] names = this.getClass()
                        .getAnnotationsByType(TagName.class);
        return names.length > 0 ? names[0].value() : "UNKNOWN";
    }

}
