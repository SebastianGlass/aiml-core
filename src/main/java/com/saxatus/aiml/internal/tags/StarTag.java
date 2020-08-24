package com.saxatus.aiml.internal.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.api.utils.StringUtils;

@TagName("star")
public class StarTag extends AbstractBotTag
{

    protected final String request;
    protected final String pattern;

    public StarTag(Node node, TagFactory factory)
    {
        super(node, factory);
        this.request = factory.getParameter()
                        .getRequest();
        this.pattern = factory.getParameter()
                        .getPattern();

    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return this.replaceStars();
    }

    protected String replaceStars()
    {
        int index = Integer.parseInt(super.getOptionalAttribute("index", "1"));

        List<String> replacements = resolveStars(request, pattern);
        getAIMLHandler().setThatStar(replacements);
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

    public static List<String> resolveStars(String request, String input)
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
