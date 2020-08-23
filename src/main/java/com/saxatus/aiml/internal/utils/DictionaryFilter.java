package com.saxatus.aiml.internal.utils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.utils.Dictionary;

public class DictionaryFilter
{

    private Dictionary<String, AIML> dict;

    public DictionaryFilter(Dictionary<String, AIML> d)
    {
        this.dict = d;
    }

    public DictionaryFilter applyTopicFilter(String topic)
    {
        if (topic == null)
        {
            return this;
        }
        Dictionary<String, AIML> newDict = new Dictionary<>();
        dict.entrySet()
                        .stream()
                        .forEach(entry -> entry.getValue()
                                        .stream()
                                        .filter(aiml -> aiml.hasMatchingTopic(topic))
                                        .forEach(aiml -> newDict.put(entry.getKey(), aiml)));

        return new DictionaryFilter(newDict);

    }

    public DictionaryFilter applyThatFilter(String that)
    {

        if (that == null)
        {
            return this;
        }
        Dictionary<String, AIML> newDict = new Dictionary<>();
        dict.entrySet()
                        .stream()
                        .forEach(entry -> entry.getValue()
                                        .stream()
                                        .filter(aiml -> aiml.hasMatchingThat(that))
                                        .forEach(aiml -> newDict.put(entry.getKey(), aiml)));

        return new DictionaryFilter(newDict);
    }

    public DictionaryFilter applyPatternFilter(String pattern)
    {
        if (pattern == null)
        {
            return this;
        }
        pattern = StringUtils.clearString(pattern);
        Dictionary<String, AIML> d2 = new Dictionary<>();
        for (String string : dict.keySet())
        {
            Set<AIML> a = dict.get(string);
            for (AIML aiml : a)
            {
                String regex = StringUtils.toRegex(aiml.getPattern());
                Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(pattern);
                if (m.find())
                {
                    d2.put(string, aiml);
                }
            }

        }
        return new DictionaryFilter(d2);
    }

    public Dictionary<String, AIML> getDict()
    {
        return dict;
    }

}
