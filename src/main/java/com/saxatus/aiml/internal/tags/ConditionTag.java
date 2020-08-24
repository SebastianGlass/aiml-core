package com.saxatus.aiml.internal.tags;

import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class ConditionTag extends AbstractBotTag
{

    public ConditionTag(Node node, TagFactory factory)
    {
        super(node, factory);

    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        String key = Optional.ofNullable(getNode().getAttributes()
                        .getNamedItem("name"))
                        .map(Node::getNodeValue)
                        .map(String::toLowerCase)
                        .orElse("");

        String value = Optional.ofNullable(getNode().getAttributes()
                        .getNamedItem("value"))
                        .map(Node::getNodeValue)
                        .orElse(null);

        if (value != null)
        {
            return handleIfTrue(debugNode, key, value);
        }
        else
        {
            return handleSwitch(debugNode, key);
        }
    }

    public String handleIfTrue(AIMLParseNode debugNode, String key, String value)
    {
        super.handle(debugNode);
        String response = "";

        if (value.equals(nonStaticMemory.get(key)))
        {
            return getNode().getTextContent();
        }
        return response;
    }

    private static final String TAG = "condition";

    public static void register()
    {
        TagRepository.addTag(TAG, ConditionTag::new);
    }

    public String handleSwitch(AIMLParseNode debugNode, String key)
    {
        super.handle(debugNode);
        return StringUtils.isEmpty(key) ? handleKeyValuePairs(debugNode) : handleValueForSetKey(debugNode, key);

    }

    private String handleValueForSetKey(AIMLParseNode debugNode, String key)
    {
        return handleKeyValuePairs(debugNode, childNode -> Optional.of(key));
    }

    private String handleKeyValuePairs(AIMLParseNode debugNode)
    {
        return handleKeyValuePairs(debugNode, childNode -> Optional.ofNullable(childNode.getAttributes()
                        .getNamedItem("name"))
                        .map(Node::getNodeValue));
    }

    private String handleKeyValuePairs(AIMLParseNode debugNode, Function<Node, Optional<String>> keyFunction)
    {
        NodeList childNodes = getNode().getChildNodes();
        AIMLParseTag fallBack = null;
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName()
                            .equals("li"))
            {
                Optional<String> conditionValue = Optional.ofNullable(childNode.getAttributes()
                                .getNamedItem("value"))
                                .map(Node::getNodeValue);

                if (conditionValue.isPresent())
                {
                    String cond = conditionValue.get();
                    String conditionKey = keyFunction.apply(childNode)
                                    .orElseThrow(() -> new IllegalArgumentException(
                                                    "Value provided but no key in condition-Tag"));
                    String val = nonStaticMemory.get(conditionKey)
                                    .trim();

                    if (cond.equals(val) || cond.equals("*"))
                    {
                        AIMLParseTag liTag = getFactory().createTag(childNode);
                        return liTag.handle(debugNode);
                    }
                }
                else
                {
                    fallBack = getFactory().createTag(childNode);
                }
            }

        }
        if (fallBack == null)
        {
            throw new IllegalArgumentException("No fallback was provided.");
        }
        return fallBack.handle(debugNode);

    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
