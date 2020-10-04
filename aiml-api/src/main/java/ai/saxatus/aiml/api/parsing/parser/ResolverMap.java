package ai.saxatus.aiml.api.parsing.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResolverMap<T>
{

    private List<Function<?, T>> values = new ArrayList<>();
    private List<Class<?>> keys = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <R> Function<R, T> getResolverFor(Class<?> resolver)
    {
        for (int i = 0; i < keys.size(); i++)
        {
            if (keys.get(i)
                            .isAssignableFrom(resolver))
            {
                return (Function<R, T>)values.get(i);
            }
        }
        return null;
    }

    public <R> void put(Class<R> clazz, Function<R, T> resolver)
    {
        this.keys.add(clazz);
        this.values.add(resolver);
    }

    public int size()
    {
        return keys.size();
    }
}
