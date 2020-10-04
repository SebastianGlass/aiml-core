package ai.saxatus.aiml.api.parsing.tags;

import java.util.function.UnaryOperator;

public interface StaticMemoryUsingNode
{
    void setStaticMemory(UnaryOperator<String> memory);
}
