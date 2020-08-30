package ai.saxatus.aiml.internal.parsing.tags.abstracts;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TestOptionTag")
public class TestOptionTag extends AbstractOptionsTag
{

    @Override
    public String getWrappedText(String childContent)
    {
        return childContent;
    }

}
