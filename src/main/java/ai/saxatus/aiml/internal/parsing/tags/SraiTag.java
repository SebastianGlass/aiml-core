package ai.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import ai.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "srai")
public class SraiTag extends AbstractContentEnclosingTag implements ContentNeedsOwnRequestNode
{

    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<srai>%s</srai>", contentToString());
    }
}
