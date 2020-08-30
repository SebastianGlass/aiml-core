package com.saxatus.aiml.api.parsing.parser;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;

public interface AIMLTransformer<T extends AIMLContentNode>
{
    T transform(Node node) throws AIMLTransformException;

}
