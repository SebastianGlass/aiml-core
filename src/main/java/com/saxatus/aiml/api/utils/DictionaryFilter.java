package com.saxatus.aiml.api.utils;

import java.io.Serializable;
import java.util.function.Predicate;

public class DictionaryFilter<T extends Serializable>
{

    private Dictionary<String, T> dict;

    public DictionaryFilter(Dictionary<String, T> d)
    {
        this.dict = d;
    }

    protected Dictionary<String, T> applyFilter(Predicate<T> filter)
    {
        Dictionary<String, T> newDict = new Dictionary<>();
        dict.entrySet()
                        .stream()
                        .forEach(entry -> entry.getValue()
                                        .stream()
                                        .filter(filter)
                                        .forEach(value -> newDict.put(entry.getKey(), value)));

        return newDict;

    }

    public Dictionary<String, T> getDictionary()
    {
        return dict;
    }

}
