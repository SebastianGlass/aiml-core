package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;

@XmlRootElement(name = "set")
public class SetTag extends AbstractContentEnclosingTag implements NonStaticMemoryUsingNode
{
    private static final long serialVersionUID = -5936529124810587625L;

    @XmlAttribute(name = "name")
    private String name;
    private Map<String, String> memory;

    @Override
    public void setNonStaticMemory(Map<String, String> memory)
    {
        this.memory = memory;
    }

    @Override
    public String getWrappedText(String childContent)
    {
        this.memory.put(name, childContent);
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<set name=\"%s\"/>%s</set>", name, contentToString());
    }

}
