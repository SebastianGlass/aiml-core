package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "think")
public class ThinkTag extends AbstractContentEnclosingTag
{

    /**
     * The {@code <think>} tag hides the results of its body.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return "";
    }

    @Override
    public String toString()
    {
        return String.format("<think>%s</think>", contentToString());
    }
}
