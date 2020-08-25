package com.saxatus.aiml.internal.tags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.StarReplacingTag;
import com.saxatus.aiml.api.tags.TagName;

@TagName("person")
public class PersonTag extends SubNodeContainingTag implements StarReplacingTag
{

    private static final Map<String, String> map = new HashMap<>();

    static
    {
        map.put("i", "you");
        map.put("me", "you");
        map.put("my", "your");
        map.put("we", "you");

        map.put("you", "me");
        map.put("your", "my");
    }

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String result;
        if (getXMLNode(context).getChildNodes()
                        .getLength() == 0)
        {
            result = replaceStars(context);
        }
        else
        {
            result = handleSubNodes(context);
        }

        return Arrays.stream(result.split(" "))
                        .map(c -> map.getOrDefault(c.toLowerCase(), c))
                        .collect(Collectors.joining(" "));

    }

    @Override
    public String getDebugInformation(AIMLParsingSessionContext context)
    {
        if (getXMLNode(context).getChildNodes()
                        .getLength() == 0)
        {
            return getTag() + " (" + replaceStars(context) + ")";
        }
        else
        {
            return getTag();
        }
    }

}
