package ai.saxatus.aiml.internal.parsing.tags;

import ai.saxatus.aiml.api.parsing.tags.LeafNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

public class TextNode extends AbstractAIMLContentTag implements LeafNode
{
    private String text;

    public TextNode(String text)
    {
        this.text = text;
    }

    @Override
    public String getText()
    {
        return text;
    }

}
