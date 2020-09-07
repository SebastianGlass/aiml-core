package ai.saxatus.aiml.api.parsing.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ai.saxatus.aiml.api.AIMLResponse;

public class ResolverMap
{

    private List<Function<?, AIMLResponse>> values = new ArrayList<>();
    private List<Class<?>> keys = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <T> Function<T, AIMLResponse> getResolverFor(Class<?> resolver)
    {
        for (int i = 0; i < keys.size(); i++)
        {
            if (keys.get(i)
                            .isAssignableFrom(resolver))
            {
                return (Function<T, AIMLResponse>)values.get(i);
            }
        }
        return null;
    }

    public <T> void put(Class<T> clazz, Function<T, AIMLResponse> resolver)
    {
        this.keys.add(clazz);
        this.values.add(resolver);
    }

    public int size()
    {
        return keys.size();
    }
}
