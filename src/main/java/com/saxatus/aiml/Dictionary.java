package com.saxatus.aiml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Dictionary<K extends Serializable, V extends Serializable> implements Serializable
{

    private static final long serialVersionUID = 3406133571779137771L;

    private final HashMap<K, Set<V>> map = new HashMap<>();

    public void put(K key, V value)
    {
        Set<V> l = map.getOrDefault(key, new TreeSet<>());
        l.add(value);
        map.put(key, l);
    }

    public Set<V> get(K key)
    {
        return map.get(key);
    }

    public void putAll(Dictionary<K, V> dict)
    {
        if (dict == null)
            return;
        this.putAll(dict.map);
    }

    public void putAll(Map<K, ? extends Iterable<V>> map)
    {
        synchronized(this)
        {
            for (Entry<K, ? extends Iterable<V>> entry : map.entrySet())
            {
                Iterable<V> v = entry.getValue();
                for (V value : v)
                {
                    this.put(entry.getKey(), value);
                }
            }
        }
    }

    public int size()
    {
        return map.values()
                        .stream()
                        .mapToInt(Set::size)
                        .sum();
    }

    public Set<K> keySet()
    {
        return map.keySet();
    }

    public Set<V> toSet()
    {
        Set<V> set = new TreeSet<>();
        for (K a : keySet())
        {
            set.addAll(map.get(a));
        }
        return set;

    }

    public Map<K, Set<V>> getMap()
    {
        return map;
    }

    @Override
    public String toString()
    {
        return map.toString();
    }

    public Set<Entry<K, Set<V>>> entrySet()
    {
        return map.entrySet();
    }

}
