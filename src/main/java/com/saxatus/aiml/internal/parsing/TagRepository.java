package com.saxatus.aiml.internal.parsing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;

import com.saxatus.aiml.internal.factory.TagSupplier;
import com.saxatus.aiml.internal.tags.AbstractAIMLTag;

public class TagRepository
{

    private static final Log log = LogFactory.getLog(TagRepository.class);

    private static final HashMap<String, TagSupplier> tagSupplierMap = new HashMap<>();

    static
    {
        Reflections reflections = new Reflections("com.saxatus");
        List<Class<? extends AbstractAIMLTag>> classes = reflections.getSubTypesOf(AbstractAIMLTag.class)
                        .stream()
                        .filter(TagRepository::invokeRegisterMethodIfNotAbstract)
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

    public static void addTag(String tagName, TagSupplier tagSupplier)
    {
        synchronized(tagSupplierMap)
        {
            tagSupplierMap.put(tagName, tagSupplier);
        }
    }

    public static TagSupplier getByName(String name)
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

    private TagRepository()
    {

    }
}
