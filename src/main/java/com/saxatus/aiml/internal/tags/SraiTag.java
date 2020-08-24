package com.saxatus.aiml.internal.tags;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.AIMLHandlerImpl;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class SraiTag extends AbstractBotTag
{
    private static final Log log = LogFactory.getLog(SraiTag.class);

    private static List<String> handledPatterns = new LinkedList<>();

    private SraiTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "srai";

    public static void register()
    {
        TagRepository.addTag(TAG, SraiTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String input = handleSubNodes().replace("\n", "")
                        .replace("\t", "");
        String pattern = getFactory().getParameter()
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
        AIMLHandlerImpl handler = getAIMLHandler();
        try
        {
            return handler.getAIMLResponse(pattern, pattern, debugNode);
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
