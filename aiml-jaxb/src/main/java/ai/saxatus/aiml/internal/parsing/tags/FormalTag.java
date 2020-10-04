package ai.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.text.WordUtils;

import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "formal")
public class FormalTag extends AbstractContentEnclosingTag
{

    @Override
    public String getWrappedText(String childContent)
    {
        return WordUtils.capitalize(childContent);
    }

    @Override
    public String toString()
    {
        return String.format("<formal>%s</formal>", contentToString());
    }

}
