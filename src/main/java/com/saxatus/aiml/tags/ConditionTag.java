package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class ConditionTag extends AbstractBotTag
{

    protected String key;
    private final String value;
    private final String content;

    public ConditionTag(Node node, TagFactory factory, String key, String value, String content)
    {
        super(node, factory);
        this.key = key.toLowerCase();
        this.value = value;
        this.content = content;

    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String response = "";

        if (value.equals(nonStaticMemory.get(key)))
        {
            return content;
        }
        return response;
    }

    private static final String TAG = "condition";

    public static void register()
    {
        TagFactory.addTag(TAG, (Node node, TagFactory factory) -> {
            String key = "";
            String value = null;
            String content = "";
            for (int i = 0; i < node.getAttributes()
                            .getLength(); i++)
            {
                Node n = node.getAttributes()
                                .item(i);
                if ("name".equals(n.getNodeName()))
                    key = n.getNodeValue();
                if ("value".equals(n.getNodeName()))
                    value = n.getNodeValue();
                content = n.getTextContent();
            }
            if (value != null)
                return new ConditionTag(node, factory, key, value, content);

            return new ConditionSwitchTag(node, factory, key, node.getChildNodes());
        });
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
