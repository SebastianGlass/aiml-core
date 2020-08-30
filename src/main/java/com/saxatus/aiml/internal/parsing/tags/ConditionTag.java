package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.DecisionMakingNode;
import com.saxatus.aiml.api.parsing.tags.LiNode;
import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractOptionsTag;

@XmlRootElement(name = "condition")
public class ConditionTag extends AbstractOptionsTag implements NonStaticMemoryUsingNode, DecisionMakingNode
{

    @XmlAttribute(name = "name")
    private String name;

    private Map<String, String> memory;

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setNonStaticMemory(Map<String, String> memory)
    {
        this.memory = memory;

    }

    @Override
    public LiNode getDecision()
    {
        if (getContent() == null)
        {
            return FALLBACK_LITAG;
        }
        return getContent().stream()
                        .filter(a -> a.getValue() == null || a.getValue()
                                        .equals(memory.getOrDefault(name, "Unknown")))
                        .findFirst()
                        .orElse(FALLBACK_LITAG);
    }

    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<condition>%s</condition>", contentToString());
    }

}
