package ai.saxatus.aiml.internal.parsing.tags.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElementRef;

import ai.saxatus.aiml.api.parsing.tags.OptionEnclosingNode;

public abstract class AbstractOptionsTag extends AbstractAIMLContentTag implements OptionEnclosingNode<AbstractLiTag>
{

    public static final AbstractLiTag FALLBACK_LITAG = new AbstractLiTag()
    {

        @Override
        public String getValue()
        {
            return "";
        }

        @Override
        public String getWrappedText(String childContent)
        {
            return "";
        }
    };

    @XmlElementRef
    private List<AbstractLiTag> content;

    public List<AbstractLiTag> getContent()
    {
        return content;
    }

    public void setContent(List<AbstractLiTag> content)
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
