package ai.saxatus.aiml.internal.parsing.tags;

import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;

import ai.saxatus.aiml.api.parsing.tags.DecisionMakingNode;
import ai.saxatus.aiml.api.parsing.tags.LiNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractOptionsTag;

@XmlRootElement(name = "random")
public class RandomTag extends AbstractOptionsTag implements DecisionMakingNode
{

    Random random = new Random();

    @Override
    public LiNode getDecision()
    {
        if (getContent() == null)
        {
            return FALLBACK_LITAG;
        }
        int id = random.nextInt(getContent().size());
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
