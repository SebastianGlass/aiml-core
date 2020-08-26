package com.saxatus.aiml.internal.parsing.tags.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;

import com.saxatus.aiml.api.parsing.tags.OptionEnclosingNode;
import com.saxatus.aiml.internal.parsing.tags.LiTag;

public abstract class AbstractOptionsTag extends AbstractAIMLContentTag implements OptionEnclosingNode<LiTag>
{

    @XmlElementRef
    @XmlMixed
    private List<LiTag> content;

    public List<LiTag> getContent()
    {
        return content;
    }

    public void setContent(List<LiTag> content)
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
