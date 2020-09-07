package ai.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "uppercase")
public class UppercaseTag extends AbstractContentEnclosingTag
{

    /**
     * The {@code <think>} tag hides the results of its body.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return childContent.toUpperCase();
    }

    @Override
    public String toString()
    {
        return String.format("<uppercase>%s</uppercase>", contentToString());
    }
}
