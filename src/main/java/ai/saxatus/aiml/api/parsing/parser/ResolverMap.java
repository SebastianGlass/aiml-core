package ai.saxatus.aiml.api.parsing.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResolverMap
{

    private List<Function<?, String>> values = new ArrayList<>();
    private List<Class<?>> keys = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <T> Function<T, String> getResolverFor(Class<?> resolver)
    {
        for (int i = 0; i < keys.size(); i++)
        {
            if (keys.get(i)
                            .isAssignableFrom(resolver))
            {
                return (Function<T, String>)values.get(i);
            }
        }
        return null;
    }

    public <T> void put(Class<T> clazz, Function<T, String> resolver)
    {
        this.keys.add(clazz);
        this.values.add(resolver);
    }

    public int size()
    {
        return keys.size();
    }
}
