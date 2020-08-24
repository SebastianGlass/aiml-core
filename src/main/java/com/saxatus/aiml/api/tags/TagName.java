package com.saxatus.aiml.api.tags;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.saxatus.aiml.api.tags.TagName.TagNames;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(TagNames.class)
public @interface TagName
{
    String value();

    @Retention(RUNTIME)
    @Target(TYPE)
    public @interface TagNames
    {
        TagName[] value();
    }
}
