package ai.saxatus.aiml.internal.parsing.tags.abstracts;

import ai.saxatus.aiml.api.parsing.tags.LiNode;

public abstract class AbstractLiTag extends AbstractContentEnclosingTag implements LiNode
{
    public abstract String getValue();
}
