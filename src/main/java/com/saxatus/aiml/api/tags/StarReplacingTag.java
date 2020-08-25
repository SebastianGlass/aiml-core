package com.saxatus.aiml.api.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.utils.StringUtils;

public interface StarReplacingTag extends AttributeContainingTag
{
    default String replaceStars(AIMLParsingSessionContext context)
    {

        String request = getSession(context).getParameter()
                        .getRequest();
        String pattern = getSession(context).getParameter()
                        .getPattern();
        int index = Integer.parseInt(getOptionalAttribute(context, "index", "1"));

        List<String> replacements = resolveStars(request, pattern);
        getAIMLHandler(context).setThatStar(replacements);
        String value;
        if (replacements.size() > index - 1)
        {
            value = replacements.get(index - 1);
        }
        else
        {
            value = "";
        }
        return value;
    }

    default List<String> resolveStars(String request, String input)
    {
        String regex = StringUtils.toRegex(input);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);

        List<String> l = new ArrayList<>();
        if (matcher.find())
        {
            for (int i = 1; i <= matcher.groupCount(); i++)
            {
                String r = matcher.group(i);
                l.add(r);
            }
        }

        return l;
    }
}
