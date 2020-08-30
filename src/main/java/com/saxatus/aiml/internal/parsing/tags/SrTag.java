package com.saxatus.aiml.internal.parsing.tags;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;
import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.StarRequiringNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "sr")
public class SrTag extends AbstractAIMLContentTag implements ContentNeedsOwnRequestNode, LeafNode, StarRequiringNode
{

    private String target;

    @Override
    public void setStars(List<String> stars)
    {
        if (stars.isEmpty())
        {
            this.target = "RANDOM PICKUP LINE";
        }
        else
        {
            this.target = stars.get(0);
        }
    }

    @Override
    public String getText()
    {
        return target;
    }

    @Override
    public String toString()
    {
        return "<sr/>";
    }
}
