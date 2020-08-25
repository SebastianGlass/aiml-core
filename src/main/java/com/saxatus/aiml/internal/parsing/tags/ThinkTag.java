package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "think")
public class ThinkTag extends AbstractContentEnclosingTag
{
    private static final long serialVersionUID = 1844041133983012597L;

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
