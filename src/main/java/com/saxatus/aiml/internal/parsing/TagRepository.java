package com.saxatus.aiml.internal.parsing;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.tags.TagName;
import com.saxatus.aiml.internal.tags.AbstractAIMLTag;

public class TagRepository
{

    private static final Log log = LogFactory.getLog(TagRepository.class);

    private final HashMap<String, TagSupplier> tagSupplierMap = new HashMap<>();

    public TagRepository()
    {
        Reflections reflections = new Reflections("com.saxatus");
        List<Class<? extends AbstractAIMLTag>> classes = reflections.getSubTypesOf(AbstractAIMLTag.class)
                        .stream()
                        .filter(this::invokeRegisterMethodIfNotAbstract)
                        .collect(Collectors.toList());
        log.debug("loaded Tags:");
        for (Class<? extends AbstractAIMLTag> class1 : classes)
        {
            log.debug(" +- " + class1);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean invokeRegisterMethodIfNotAbstract(Class<?> clazz)
    {
        if (!Modifier.isAbstract(clazz.getModifiers()) && AbstractAIMLTag.class.isAssignableFrom(clazz))
        {
            Class<AbstractAIMLTag> aimlClazz = (Class<AbstractAIMLTag>)clazz;
            TagName[] tagNames = clazz.getAnnotationsByType(TagName.class);
            if (tagNames.length == 0)
            {
                return false;
            }
            Arrays.stream(tagNames)
                            .forEach(tagName -> this.addTag(tagName.value(), getTagSupplierForClass(aimlClazz)));

            return true;
        }

        return false;
    }

    private static TagSupplier getTagSupplierForClass(Class<AbstractAIMLTag> clazz)
    {
        return (node, fac) -> {
            try
            {
                return (AbstractAIMLTag)clazz.getConstructor(Node.class, AIMLParsingSession.class)
                                .newInstance(node, fac);
            }
            catch(Exception e)
            {
                log.error(e);
            }
            return null;
        };
    }

    public void addTag(String tagName, TagSupplier tagSupplier)
    {
        synchronized(tagSupplierMap)
        {
            tagSupplierMap.put(tagName, tagSupplier);
        }
    }

    public TagSupplier getByName(String name)
    {
        TagSupplier supplier;

        synchronized(tagSupplierMap)
        {
            supplier = tagSupplierMap.get(name);
            if (supplier == null)
            {
                supplier = tagSupplierMap.get("unknown");
            }

        }
        return supplier;
    }

    @FunctionalInterface
    interface TagSupplier
    {
        AbstractAIMLTag supply(Node node, AIMLParsingSession manager);
    }

}
