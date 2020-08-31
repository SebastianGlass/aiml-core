package ai.saxatus.aiml.internal.parsing.parser;

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
import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLResponse;
import ai.saxatus.aiml.api.parsing.AIMLParser;
import ai.saxatus.aiml.api.parsing.parser.AIMLTransformException;
import ai.saxatus.aiml.api.parsing.parser.ResolverMap;
import ai.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import ai.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;
import ai.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;
import ai.saxatus.aiml.api.parsing.tags.DecisionMakingNode;
import ai.saxatus.aiml.api.parsing.tags.LeafNode;
import ai.saxatus.aiml.api.parsing.tags.LiNode;
import ai.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import ai.saxatus.aiml.api.parsing.tags.StarRequiringNode;
import ai.saxatus.aiml.api.parsing.tags.StaticMemoryUsingNode;
import ai.saxatus.aiml.api.utils.StringUtils;
import ai.saxatus.aiml.internal.parsing.tags.TemplateTag;
import ai.saxatus.aiml.internal.parsing.tags.TextNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

public class JaxbAIMLParserImpl implements AIMLParser
{

    private static final Log log = LogFactory.getLog(JaxbAIMLParserImpl.class);

    ResolverMap resolverByClass = new ResolverMap();

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

        init();

    }

    void init()
    {
        resolverByClass.put(ContentNeedsOwnRequestNode.class, s -> {
            String childContent = resolveChildPattern(s).getAnswer()
                            .toUpperCase();
            return handler.increaseDepth()
                            .getAnswer(childContent);
        });
        resolverByClass.put(ContentEnclosingNode.class, this::parseContentEnclosing);
        resolverByClass.put(DecisionMakingNode.class, this::parseDecision);
        resolverByClass.put(LeafNode.class, a -> new AIMLResponse(a.getText(), a.toString()));

    }

    @Override
    public AIMLResponse parse(Node node)
    {
        try
        {
            TemplateTag aiml = transformer.transform(node);
            return parseContentEnclosing(aiml);
        }
        catch(AIMLTransformException e)
        {
            log.error("Error parsing template to Jaxb AIML", e);
        }
        return null;

    }

    private AIMLResponse parseDecision(DecisionMakingNode s)
    {
        LiNode node = s.getDecision();
        AIMLResponse childContent = resolverByClass.getResolverFor(ContentEnclosingNode.class)
                        .apply(node);

        return new AIMLResponse(s.getWrappedText(childContent.getAnswer()), s.toString());
    }

    private AIMLResponse parseContentEnclosing(ContentEnclosingNode<?> s)
    {
        if (s.getContent() == null)
        {
            return new AIMLResponse(s.getWrappedText(""), s.toString());
        }
        List<?> o = new ArrayList<>(s.getContent());
        String childContent = o.stream()
                        .map(a -> a instanceof String ? new TextNode((String)a) : a)
                        .map(AbstractAIMLContentTag.class::cast)
                        .map(this::parse)
                        .map(AIMLResponse::getAnswer)
                        .collect(Collectors.joining(" "));
        return new AIMLResponse(s.getWrappedText(childContent), s.toString());
    }

    private AIMLResponse parse(AIMLContentNode s)
    {
        // preprocessing
        preprocessNodes(s);

        AIMLResponse content = resolverByClass.getResolverFor(s.getClass())
                        .apply(s);
        if (content != null)
            return content;
        return new AIMLResponse("?", null);
    }

    private AIMLResponse resolveChildPattern(Object s)
    {
        AIMLResponse childContent = new AIMLResponse("RANDOM PICKUP LINE", null);
        if (s instanceof ContentEnclosingNode<?>)
        {
            childContent = resolverByClass.getResolverFor(ContentEnclosingNode.class)
                            .apply(s);
        }
        else if (s instanceof LeafNode)
        {
            childContent = resolverByClass.getResolverFor(LeafNode.class)
                            .apply(s);
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
