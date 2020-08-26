package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "br")
public class BrTag extends AbstractAIMLContentTag implements LeafNode
{

    @Override
    public String getText()
    {
        return "\n";
    }

    @Override
    public String toString()
    {
        return "<br/>";
    }

}
