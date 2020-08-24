package com.saxatus.aiml.internal.tags;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;

@TagName("srai")
public class SraiTag extends AbstractBotTag
{
    private static final Log log = LogFactory.getLog(SraiTag.class);

    private static List<String> handledPatterns = new LinkedList<>();

    public SraiTag(Node node, AIMLParsingSession session)
    {
        super(node, session);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String input = handleSubNodes().replace("\n", "")
                        .replace("\t", "");
        String pattern = getSession().getParameter()
                        .getPattern();

        if (/* wasTraversed(pattern) || */ handledPatterns.size() > 30)
        {
            // Circle-Patterns
            return "";
        }
        addTraversed(pattern);
        return resolvePattern(input, getAIMLParseNode());
    }

    private String resolvePattern(String pattern, AIMLParseNode debugNode)
    {
        AIMLHandler handler = getAIMLHandler();
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
