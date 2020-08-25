package com.saxatus.aiml.internal.tags;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLNotFoundException;
import com.saxatus.aiml.api.parsing.AIMLParsingSessionContext;
import com.saxatus.aiml.api.tags.TagName;

@TagName("srai")
public class SraiTag extends SubNodeContainingTag
{
    private static final Log log = LogFactory.getLog(SraiTag.class);

    private static List<String> handledPatterns = new LinkedList<>();

    @Override
    public String handle(AIMLParsingSessionContext context)
    {
        super.handle(context);
        String input = handleSubNodes(context).replace("\n", "")
                        .replace("\t", "");
        String pattern = getSession(context).getParameter()
                        .getPattern();

        if (/* wasTraversed(pattern) || */ handledPatterns.size() > 30)
        {
            // Circle-Patterns
            return "";
        }
        addTraversed(pattern);
        return resolvePattern(context, input);
    }

    private String resolvePattern(AIMLParsingSessionContext context, String pattern)
    {
        AIMLHandler handler = getAIMLHandler(context);
        try
        {
            return handler.getAnswer(pattern, pattern, debugNode);
        }
        catch(AIMLNotFoundException e)
        {
            log.warn(e);
            return "";
        }
    }

    public static void resetHandledPatterns()
    {
        handledPatterns = new Vector<>();
    }

    public static boolean wasTraversed(String pattern)
    {
        return handledPatterns.contains(pattern.toUpperCase());
    }

    public static void addTraversed(String pattern)
    {
        handledPatterns.add(pattern.toUpperCase());
    }

}
