package com.saxatus.aiml.internal.parsing.tags.abstracts;

import com.saxatus.aiml.api.parsing.tags.LiNode;

public abstract class AbstractLiTag extends AbstractContentEnclosingTag implements LiNode
{
    public abstract String getValue();
}
