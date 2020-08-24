package com.saxatus.aiml.internal.factory;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.internal.tags.AbstractAIMLTag;

@FunctionalInterface
public interface TagSupplier
{
    AbstractAIMLTag supply(Node node, TagFactory manager);
}
