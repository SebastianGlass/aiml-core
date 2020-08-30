package com.saxatus.aiml.internal.parsing.tags;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.api.parsing.tags.LeafNode;
import com.saxatus.aiml.api.utils.Strftime;
import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "date")
public class DateTag extends AbstractAIMLContentTag implements LeafNode
{

    @XmlAttribute(name = "format")
    private String format;

    public void setFormat(String format)
    {
        this.format = format;
    }

    @Override
    public String getText()
    {
        return new Strftime(format).format(new Date());
    }

    @Override
    public String toString()
    {
        return String.format("<date format=\"%s\"/>", format);
    }

}
