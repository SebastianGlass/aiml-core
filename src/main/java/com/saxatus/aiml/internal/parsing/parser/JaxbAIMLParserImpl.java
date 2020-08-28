package com.saxatus.aiml.internal.parsing.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.parsing.parser.AIMLTransformException;
import com.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import com.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;
import com.saxatus.aiml.api.parsing.tags.DecisionMakingNode;
import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.LiNode;
import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import com.saxatus.aiml.api.parsing.tags.StarRequiringNode;
import com.saxatus.aiml.api.parsing.tags.StaticMemoryUsingNode;
import com.saxatus.aiml.api.utils.StringUtils;
import com.saxatus.aiml.internal.parsing.tags.TemplateTag;

public class JaxbAIMLParserImpl implements AIMLParser
{

    private static final Log log = LogFactory.getLog(JaxbAIMLParserImpl.class);

    private JaxbAIMLTransformer<TemplateTag> transformer;
    private String pattern;
    private String request;
    private AIMLHandler handler;

    @Inject
    public JaxbAIMLParserImpl(@Assisted("pattern") String pattern, @Assisted("input") String request,
                    @Assisted("handler") AIMLHandler handler, JaxbAIMLTransformer<TemplateTag> transformer)
    {
        this.pattern = pattern;
        this.request = request;
        this.handler = handler;
        this.transformer = transformer;

    }

    @Override
    public String parse(Node node)
    {
        try
        {
            TemplateTag aiml = transformer.transform(node);
            return parse(aiml).trim();
        }
        catch(AIMLTransformException e)
        {
            log.error("Error parsing template to Jaxb AIML", e);
        }
        return null;

    }

    private String parse(String s)
    {
        return s.trim();
    }

    private String parse(LeafNode s)
    {
        return s.getText();
    }

    private String parse(DecisionMakingNode s)
    {
        LiNode node = s.getDecision();
        String childContent = this.parse(node);

        return s.getWrappedText(childContent);
    }

    private String parse(ContentEnclosingNode<?> s)
    {
        if (s.getContent() != null)
        {
            List<?> o = new ArrayList<>(s.getContent());
            String childContent = o.stream()
                            .map(this::parse)
                            .collect(Collectors.joining(" "));
            return s.getWrappedText(childContent);
        }

        return s.getWrappedText("");
    }

    private String parse(Object s)
    {
        // preprocessing
        preprocessNodes(s);

        if (s instanceof ContentNeedsOwnRequestNode)
        {
            String childContent = resolveChildPattern(s).toUpperCase();
            return handler.increaseDepth()
                            .getAnswer(childContent);
        }
        if (s instanceof String)
        {
            return parse((String)s).trim();
        }
        else if (s instanceof ContentEnclosingNode<?>)
        {
            return parse((ContentEnclosingNode<?>)s).trim();
        }
        else if (s instanceof DecisionMakingNode)
        {
            return parse((DecisionMakingNode)s).trim();
        }
        else if (s instanceof LeafNode)
        {
            return parse((LeafNode)s).trim();
        }

        return "?";
    }

    private String resolveChildPattern(Object s)
    {
        String childContent;
        if (s instanceof ContentEnclosingNode<?>)
        {
            childContent = parse((ContentEnclosingNode<?>)s).trim();
        }
        else if (s instanceof LeafNode)
        {
            childContent = parse((LeafNode)s).trim();
        }
        else
        {
            childContent = "RANDOM PICKUP LINE";
        }
        return childContent;
    }

    private void preprocessNodes(Object s)
    {
        if (s instanceof StarRequiringNode)
        {
            ((StarRequiringNode)s).setStars(resolveStars(request, pattern));
        }
        if (s instanceof StaticMemoryUsingNode)
        {
            ((StaticMemoryUsingNode)s).setStaticMemory(handler.getStaticMemory());
        }
        if (s instanceof NonStaticMemoryUsingNode)
        {
            ((NonStaticMemoryUsingNode)s).setNonStaticMemory(handler.getNonStaticMemory());
        }
    }

    private List<String> resolveStars(String request, String input)
    {
        String regex = StringUtils.toRegex(input);
        Pattern regexPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(request);

        List<String> l = new ArrayList<>();
        if (matcher.find())
        {
            for (int i = 1; i <= matcher.groupCount(); i++)
            {
                l.add(matcher.group(i));
            }
        }
        return l;
    }
}
