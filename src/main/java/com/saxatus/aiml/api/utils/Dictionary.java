package com.saxatus.aiml.api.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Dictionary<K extends Serializable, V extends Serializable> implements Map<K, Set<V>>, Serializable
{

    private static final long serialVersionUID = 3406133571779137771L;

    private final HashMap<K, Set<V>> map = new HashMap<>();

    public void put(K key, V value)
    {
        Set<V> l = map.getOrDefault(key, new TreeSet<>());
        l.add(value);
        map.put(key, l);
    }

    public void putAll(Dictionary<K, V> dict)
    {
        if (dict == null)
            return;
        this.putAll(dict.map);
    }

    @Override
    public int size()
    {
        return map.values()
                        .stream()
                        .mapToInt(Set::size)
                        .sum();
    }

    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }

    @Override
    public String toString()
    {
        return map.toString();
    }

    @Override
    public Set<Entry<K, Set<V>>> entrySet()
    {
        return map.entrySet();
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return map.values()
                        .stream()
                        .anyMatch(val -> val.contains(value));
    }

    @Override
    public Set<V> get(Object key)
    {
        return map.get(key);
    }

    @Override
    public Set<V> put(K key, Set<V> value)
    {
        synchronized(this)
        {
            Set<V> pervious = map.get(key);
            if (value == null)
            {
                return pervious;
            }
            if (!map.containsKey(key))
            {
                map.put(key, new TreeSet<>());
            }
            value.forEach(map.get(key)::add);

            return pervious;
        }

    }

    @Override
    public Set<V> remove(Object key)
    {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Set<V>> m)
    {
        synchronized(this)
        {
            for (Entry<? extends K, ? extends Set<V>> entry : m.entrySet())
            {
                for (V value : entry.getValue())
                {
                    this.put(entry.getKey(), value);
                }
            }
        }
    }

    @Override
    public void clear()
    {
        map.clear();

    }

    @Override
    public Collection<Set<V>> values()
    {
        return map.values();
    }

}
