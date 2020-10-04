package ai.saxatus.aiml.internal.parsing.tags;

import java.util.Optional;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ai.saxatus.aiml.api.parsing.tags.LiNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractLiTag;

@XmlRootElement(name = "li")
public class LiTag extends AbstractLiTag implements LiNode
{

    @XmlAttribute(name = "value")
    private String value;

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        String liOpeningTag = Optional.ofNullable(value)
                        .map(a -> String.format("<li value=\"%s\">", a))
                        .orElse("<li>");
        return String.format("%s%s</li>", liOpeningTag, contentToString());
    }
}
