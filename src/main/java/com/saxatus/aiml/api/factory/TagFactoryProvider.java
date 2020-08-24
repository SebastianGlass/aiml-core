package com.saxatus.aiml.api.factory;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.factory.TagFactory.TagParameter;

public interface TagFactoryProvider
{
    TagFactory create(TagParameter parameter, AIMLHandler handler);
}
