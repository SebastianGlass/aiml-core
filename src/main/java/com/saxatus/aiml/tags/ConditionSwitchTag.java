package com.saxatus.aiml.tags;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class ConditionSwitchTag extends AbstractBotTag
{

    private static final String TAG = "condition";
    protected String key;

    private final NodeList childNodes;

    public ConditionSwitchTag(Node node, TagFactory factory, String key, NodeList childNodes)
    {
        super(node, factory);
        this.childNodes = childNodes;
        this.key = key.toLowerCase();
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String val = nonStaticMemory.getOrDefault(key, "Unknown")
                        .trim();
        IAIMLTag fallBack = null;

        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName()
                            .equals("li"))
            {
                if (childNode.getAttributes()
                                .getLength() == 2)
                {

                    key = childNode.getAttributes()
                                    .item(0)
                                    .getNodeValue();
                    val = nonStaticMemory.get(key)
                                    .trim();
                    String condition = childNode.getAttributes()
                                    .item(1)
                                    .getNodeValue();

                    if (condition.equals(val) || condition.equals("*"))
                    {
                        IAIMLTag liTag = getFactory().createTag(childNode);
                        return liTag.handle(debugNode);
                    }
                }
                else if (childNode.getAttributes()
                                .getLength() == 1)
                {

                    String condition = childNode.getAttributes()
                                    .item(0)
                                    .getNodeValue();
                    if (condition.equals(val) || condition.equals("*"))
                    {
                        IAIMLTag liTag = getFactory().createTag(childNode);
                        return liTag.handle(debugNode);
                    }
                }
                else
                {
                    fallBack = getFactory().createTag(childNode);
                }

            }
            else
            {
                // TODO: handle Syntax-Error
            }
        }
        return fallBack.handle(debugNode);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }
}
