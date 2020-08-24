package com.saxatus.aiml.internal.tags;

import java.util.Optional;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

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
        TagRepository.addTag(TAG, (Node node, TagFactory factory) -> {
            String key = Optional.ofNullable(node.getAttributes()
                            .getNamedItem("name"))
                            .map(Node::getNodeValue)
                            .orElse("");

            String value = Optional.ofNullable(node.getAttributes()
                            .getNamedItem("value"))
                            .map(Node::getNodeValue)
                            .orElse(null);

            if (value == null)
            {
                return new ConditionSwitchTag(node, factory, key, node.getChildNodes());
            }
            return new ConditionTag(node, factory, key, value, node.getTextContent());
        });
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
