package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "get")
public class GetTag extends AbstractAIMLContentTag implements LeafNode, NonStaticMemoryUsingNode
{

    @XmlAttribute(name = "name")
    private String name;
    private Map<String, String> memory;

    @Override
    public void setNonStaticMemory(Map<String, String> memory)
    {
        this.memory = memory;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getText()
    {
        return memory.getOrDefault(name, "Unknown");
    }

    @Override
    public String toString()
    {
        return String.format("<get name=\"%s\"/>", name);
    }

}
