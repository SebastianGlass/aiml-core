package com.saxatus.aiml.internal.tags;

import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.tags.NonStaticMemoryUsingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("condition")
public class ConditionTag extends AbstractAIMLTag implements NonStaticMemoryUsingTag
{

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String key = Optional.ofNullable(getXMLNode(context).getAttributes()
                        .getNamedItem("name"))
                        .map(Node::getNodeValue)
                        .map(String::toLowerCase)
                        .orElse("");
        return Optional.ofNullable(getXMLNode(context).getAttributes()
                        .getNamedItem("value"))
                        .map(Node::getNodeValue)
                        .map(v -> handleIfTrue(context, key, v))
                        .orElseGet(() -> handleSwitch(context, key));

    }

    public String handleIfTrue(AIMLParsingSessionContext context, String key, String value)
    {
        String response = "";

        if (value.equals(getNonStaticMemory(context).get(key)))
        {
            return getXMLNode(context).getTextContent();
        }
        return response;
    }

    public String handleSwitch(AIMLParsingSessionContext context, String key)
    {
        return StringUtils.isEmpty(key) ? handleKeyValuePairs(context) : handleValueForSetKey(context, key);

    }

    private String handleValueForSetKey(AIMLParsingSessionContext context, String key)
    {
        return handleKeyValuePairs(context, childNode -> Optional.of(key));
    }

    private String handleKeyValuePairs(AIMLParsingSessionContext context)
    {
        return handleKeyValuePairs(context, childNode -> Optional.ofNullable(childNode.getAttributes()
                        .getNamedItem("name"))
                        .map(Node::getNodeValue));
    }

    private String handleKeyValuePairs(AIMLParsingSessionContext context, Function<Node, Optional<String>> keyFunction)
    {
        NodeList childNodes = getXMLNode(context).getChildNodes();
        String fallBack = null;
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
                    String val = getNonStaticMemory(context).get(conditionKey)
                                    .trim();

                    if (cond.equals(val) || cond.equals("*"))
                    {
                        AIMLParseTag liTag = getSession(context).createTag(childNode);
                        return liTag.handle(context.of(debugNode, childNode));
                    }
                }
                else
                {
                    fallBack = getSession(context).createTag(childNode)
                                    .handle(context.of(debugNode, childNode));
                }
            }

        }
        if (fallBack == null)
        {
            throw new IllegalArgumentException("No fallback was provided.");
        }
        return fallBack;

    }

}
