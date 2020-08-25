package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "aiml")
public class AIMLRootTag extends AbstractContentEnclosingTag
{
    private static final long serialVersionUID = -2008353300629663557L;

    /**
     * The {@code <aiml>} tag is only the entrypoint for AIML parsing. There is no specific logic behind this tag.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<aiml>%s</aiml>", contentToString());
    }
}
