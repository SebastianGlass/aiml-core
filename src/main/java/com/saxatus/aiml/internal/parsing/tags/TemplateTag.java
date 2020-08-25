package com.saxatus.aiml.internal.parsing.tags;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "template")
public class TemplateTag extends AbstractContentEnclosingTag
{
    private static final long serialVersionUID = -8562727905947839855L;

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
