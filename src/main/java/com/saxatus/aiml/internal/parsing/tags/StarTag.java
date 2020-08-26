package com.saxatus.aiml.internal.parsing.tags;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.StarRequiringNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "star")
public class StarTag extends AbstractAIMLContentTag implements LeafNode, StarRequiringNode
{

    @XmlAttribute(name = "index")
    private int index = 1;
    private String star;

    @Override
    public void setStars(List<String> stars)
    {
        // AIML is 1 based indexed
        this.star = stars.get(index - 1);
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public String getText()
    {
        return star;
    }

    @Override
    public String toString()
    {
        return String.format("<star index=\"%s\"/>", index);
    }

}
