package ai.saxatus.aiml.internal.parsing.tags;

import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ai.saxatus.aiml.api.parsing.tags.LeafNode;
import ai.saxatus.aiml.api.parsing.tags.StaticMemoryUsingNode;
import ai.saxatus.aiml.internal.parsing.tags.abstracts.AbstractAIMLContentTag;

@XmlRootElement(name = "bot")
public class BotTag extends AbstractAIMLContentTag implements LeafNode, StaticMemoryUsingNode
{

    @XmlAttribute(name = "name")
    private String name;

    @XmlTransient
    private UnaryOperator<String> memory;

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setStaticMemory(UnaryOperator<String> memory)
    {
        this.memory = memory;
    }

    @Override
    public String getText()
    {
        return Optional.ofNullable(memory.apply(name.toLowerCase()))
                        .orElse("Unknown");
    }

    @Override
    public String toString()
    {
        return String.format("<bot name=\"%s\"/>", name);
    }

}
