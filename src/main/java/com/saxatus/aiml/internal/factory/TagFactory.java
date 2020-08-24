package com.saxatus.aiml.internal.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.parsing.TagParameter;
import com.saxatus.aiml.internal.parsing.TagRepository;
import com.saxatus.aiml.internal.tags.AbstractAIMLTag;

public class TagFactory
{

    private static final Log log = LogFactory.getLog(TagFactory.class);

    static
    {
        Reflections reflections = new Reflections("com.saxatus");
        List<Class<? extends AbstractAIMLTag>> classes = reflections.getSubTypesOf(AbstractAIMLTag.class)
                        .stream()
                        .filter(TagFactory::invokeRegisterMethodIfNotAbstract)
                        .collect(Collectors.toList());
        log.debug("loaded Tags:");
        for (Class<? extends AbstractAIMLTag> class1 : classes)
        {
            log.debug(" +- " + class1);
        }
    }

    private static boolean invokeRegisterMethodIfNotAbstract(Class<?> clazz)
    {
        try
        {
            if (!Modifier.isAbstract(clazz.getModifiers()) && AbstractAIMLTag.class.isAssignableFrom(clazz))
            {
                clazz.getMethod("register")
                                .invoke(null);
                return true;
            }
        }
        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            log.warn(clazz.getName() + " will not be registered.");
        }
        return false;
    }

    private final TagParameter parameter;
    private AIMLHandler handler;

    public TagFactory(TagParameter parameter, AIMLHandler handler)
    {
        this.parameter = parameter;
        this.handler = handler;
    }

    public AIMLParseTag createTag(Node node)
    {
        TagSupplier supplier = TagRepository.getByName(node.getNodeName());
        return supplier.supply(node, this);
    }

    public TagParameter getParameter()
    {
        return parameter;
    }

    public AIMLHandler getAIMLHandler()
    {
        return handler;
    }

}
