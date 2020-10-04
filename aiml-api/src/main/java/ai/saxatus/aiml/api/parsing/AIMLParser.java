package ai.saxatus.aiml.api.parsing;

import org.w3c.dom.Node;

import ai.saxatus.aiml.api.AIMLResponse;

public interface AIMLParser
{
    AIMLResponse parse(Node node);

}
