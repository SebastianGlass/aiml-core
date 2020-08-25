package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.ContentNeedsOwnRequestNode;

@XmlRootElement(name = "srai")
public class SraiTag extends AbstractContentEnclosingTag implements ContentNeedsOwnRequestNode
{
    private static final long serialVersionUID = -3867937881711803589L;

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
