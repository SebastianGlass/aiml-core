package com.saxatus.aiml.factory;

import org.w3c.dom.Node;

import com.saxatus.aiml.tags.AbstractAIMLTag;

@FunctionalInterface
public interface TagSupplier
{
    AbstractAIMLTag supply(Node node, TagFactory manager);
}
