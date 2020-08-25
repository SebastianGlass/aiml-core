package com.saxatus.aiml.internal.parsing.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;

import com.saxatus.aiml.api.parsing.tags.AIMLContentNode;
import com.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;

public abstract class AbstractContentEnclosingTag extends AIMLContentNode implements ContentEnclosingNode
{
    private static final long serialVersionUID = 6997878431517691832L;

    @XmlElementRef
    @XmlMixed
    private List<AIMLContentNode> content;

    @Override
    public List<AIMLContentNode> getContent()
    {
        return content;
    }

    @Override
    public void setContent(List<AIMLContentNode> content)
    {
        this.content = content;
    }

    protected String contentToString()
    {
        if (getContent() == null)
        {
            return "";
        }
        List<?> a = new ArrayList<>(getContent());
        return a.stream()
                        .map(Object::toString)
                        .map(String::trim)
                        .collect(Collectors.joining(" "));
    }
}
