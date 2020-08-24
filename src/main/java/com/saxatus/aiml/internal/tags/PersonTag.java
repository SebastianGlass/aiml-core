package com.saxatus.aiml.internal.tags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.TagName;

@TagName("person")
public class PersonTag extends StarTag
{

    private final Map<String, String> map = new HashMap<>();

    public PersonTag(Node node, TagFactory factory)
    {
        super(node, factory);
        map.put("i", "you");
        map.put("me", "you");
        map.put("my", "your");
        map.put("we", "you");

        map.put("you", "me");
        map.put("your", "my");
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String context;
        if (getNode().getChildNodes()
                        .getLength() == 0)
        {
            context = replaceStars();
        }
        else
        {
            context = handleSubNodes();
        }

        return Arrays.stream(context.split(" "))
                        .map(c -> map.getOrDefault(c.toLowerCase(), c))
                        .collect(Collectors.joining(" "));

    }

    @Override
    public String getDebugInformation()
    {
        if (getNode().getChildNodes()
                        .getLength() == 0)
        {
            return getTag() + " (" + replaceStars() + ")";
        }
        else
        {
            return getTag();
        }
    }

}
