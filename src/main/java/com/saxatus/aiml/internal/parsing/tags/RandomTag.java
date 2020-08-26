package com.saxatus.aiml.internal.parsing.tags;

import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.DecisionMakingNode;
import com.saxatus.aiml.api.parsing.tags.LiNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractOptionsTag;

@XmlRootElement(name = "random")
public class RandomTag extends AbstractOptionsTag implements DecisionMakingNode
{
   

    @Override
    public LiNode getDecision()
    {
        if(getContent() == null)
        {
            return FALLBACK_LITAG;
        }
        int id = new Random().nextInt(getContent().size());
        return getContent().get(id);
    }

    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<random>%s</random>", contentToString());
    }

}
