package com.saxatus.aiml.internal.parsing.tags;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.NonStaticMemoryUsingNode;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "set")
public class SetTag extends AbstractContentEnclosingTag implements NonStaticMemoryUsingNode
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
    public String getWrappedText(String childContent)
    {
        this.memory.put(name, childContent);
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<set name=\"%s\">%s</set>", name, contentToString());
    }

}
