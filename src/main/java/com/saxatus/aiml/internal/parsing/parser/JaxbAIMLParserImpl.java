package com.saxatus.aiml.internal.parsing.parser;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParser;
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
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

public class JaxbAIMLParserImpl implements AIMLParser
{

    private static final Log log = LogFactory.getLog(JaxbAIMLParserImpl.class);

    private Unmarshaller jaxbUnmarshaller;
    private String pattern;
    private String request;
    private String real;
    private AIMLHandler handler;

    public JaxbAIMLParserImpl(String pattern, String request, String real, AIMLHandler handler)
    {
        this.pattern = pattern;
        this.request = request;
        this.real = real;
        this.handler = handler;

        Reflections reflections = new Reflections("com.saxatus");
        List<Class<? extends AbstractAIMLContentTag>> classes = reflections.getSubTypesOf(AbstractAIMLContentTag.class)
                        .stream()
                        .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers())
                                        && AbstractAIMLContentTag.class.isAssignableFrom(clazz))
                        .collect(Collectors.toList());
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        }
        catch(JAXBException e)
        {
            throw new RuntimeException("Problems with loading AIMLTags", e);
        }

    }

    @Override
    public String parse(Node node)
    {
        TemplateTag aiml;
        try
        {
            aiml = (TemplateTag)jaxbUnmarshaller.unmarshal(node);
            return parse(aiml).trim();
        }
        catch(JAXBException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        if (s instanceof StarRequiringNode)
        {
            List<String> stars = resolveStars(request, pattern);
            ((StarRequiringNode)s).setStars(stars);
        }
        if (s instanceof StaticMemoryUsingNode)
        {
            ((StaticMemoryUsingNode)s).setStaticMemory(handler.getStaticMemory());
        }
        if (s instanceof NonStaticMemoryUsingNode)
        {
            ((NonStaticMemoryUsingNode)s).setNonStaticMemory(handler.getNonStaticMemory());
        }

        if (s instanceof ContentNeedsOwnRequestNode)
        {
            String childContent;
            if (s instanceof ContentEnclosingNode<?>)
            {
                childContent = parse((ContentEnclosingNode<?>)s).trim()
                                .toUpperCase();
            }
            else if (s instanceof LeafNode)
            {
                childContent = parse((LeafNode)s).trim()
                                .toUpperCase();
            }
            else
            {
                childContent = "RANDOM PICKUP LINE";

            }
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

    // helper
    private List<String> resolveStars(String request, String input)
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
