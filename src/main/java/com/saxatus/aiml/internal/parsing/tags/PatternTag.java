package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pattern")
public class PatternTag extends AbstractContentEnclosingTag
{
    private static final long serialVersionUID = -3797860751619380189L;

    /**
     * The {@code <pattern>} tag is only the entrypoint for AIML parsing. There is no specific logic behind this tag.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<pattern>%s</pattern>", contentToString());
    }
}
