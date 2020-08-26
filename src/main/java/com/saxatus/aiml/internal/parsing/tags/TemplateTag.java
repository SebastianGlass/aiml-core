package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

import com.saxatus.aiml.internal.parsing.tags.abstracts.AbstractContentEnclosingTag;

@XmlRootElement(name = "template")
public class TemplateTag extends AbstractContentEnclosingTag
{

    /**
     * The {@code <template>} tag is only the entrypoint for AIML parsing. There is no specific logic behind this tag.
     */
    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

    @Override
    public String toString()
    {
        return String.format("<template>%s</template>", contentToString());
    }
}
