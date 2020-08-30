package ai.saxatus.aiml.internal.parsing.tags.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;

import ai.saxatus.aiml.api.parsing.tags.ContentEnclosingNode;

public abstract class AbstractContentEnclosingTag extends AbstractAIMLContentTag
                implements ContentEnclosingNode<AbstractAIMLContentTag>
{

    @XmlElementRef
    @XmlMixed
    private List<AbstractAIMLContentTag> content;

    @Override
    public List<AbstractAIMLContentTag> getContent()
    {
        return content;
    }

    @Override
    public void setContent(List<AbstractAIMLContentTag> content)
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
