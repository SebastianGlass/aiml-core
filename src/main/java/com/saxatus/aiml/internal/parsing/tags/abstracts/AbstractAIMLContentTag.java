package com.saxatus.aiml.internal.parsing.tags.abstracts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractAIMLContentTag implements AIMLContentNode
{

    @Override
    public String toString()
    {
        return "<?/>";
    }
}
