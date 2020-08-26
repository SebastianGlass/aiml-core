package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.parsing.tags.StaticMemoryUsingNode;

@XmlRootElement(name = "bot")
public class BotTag extends AIMLContentNode implements LeafNode, StaticMemoryUsingNode
{
    private static final long serialVersionUID = -9133149714765429183L;

    @XmlAttribute(name = "name")
    private String name;

    private Map<String, String> memory;

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setStaticMemory(Map<String, String> memory)
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
        return String.format("<bot name=\"%s\"/>", name);
    }

}
