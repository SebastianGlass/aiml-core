package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;

@XmlRootElement(name = "get")
public class GetTag extends AIMLContentNode implements LeafNode, NonStaticMemoryUsingNode
{
    private static final long serialVersionUID = -9177243850221155782L;

    @XmlAttribute(name = "name")
    private String name;
    private Map<String, String> memory;

    @Override
    public void setNonStaticMemory(Map<String, String> memory)
    {
        this.memory = memory;
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
