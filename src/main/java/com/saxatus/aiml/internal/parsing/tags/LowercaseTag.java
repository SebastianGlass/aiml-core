package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "lowercase")
public class LowercaseTag extends AbstractContentEnclosingTag
{

    /**
     * The {@code <think>} tag hides the results of its body.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return childContent.toLowerCase();
    }

    @Override
    public String toString()
    {
        return String.format("<lowercase>%s</lowercase>", contentToString());
    }
}
