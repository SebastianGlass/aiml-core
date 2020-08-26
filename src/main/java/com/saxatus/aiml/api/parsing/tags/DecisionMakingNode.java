package com.saxatus.aiml.api.parsing.tags;

public interface DecisionMakingNode  
{
    LiNode getDecision();

    String getWrappedText(String childContent);
}
