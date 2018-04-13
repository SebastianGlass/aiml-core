package com.saxatus.aiml;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.saxatus.aiml.parsing.AIML;

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
                                        .filter(aiml -> hasMatchingTopic(topic, aiml))
                                        .forEach(aiml -> newDict.put(entry.getKey(), aiml)));

        return new DictionaryFilter(newDict);

    }

    public DictionaryFilter applyThatFilter(String that)
    {

        Dictionary<String, AIML> newDict = new Dictionary<>();
        dict.entrySet()
                        .stream()
                        .forEach(entry -> entry.getValue()
                                        .stream()
                                        .filter(aiml -> hasMatchingThat(that, aiml))
                                        .forEach(aiml -> newDict.put(entry.getKey(), aiml)));

        return new DictionaryFilter(newDict);
    }

    public DictionaryFilter applyPatternFilter(String pattern)
    {
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

    private boolean hasMatchingTopic(String topic, AIML aiml)
    {
        if (topic.equalsIgnoreCase("Unknown") && aiml.getTopic() == null)
            return true;

        return aiml.getTopic() != null && topic.equalsIgnoreCase(aiml.getTopic());

    }

    private boolean hasMatchingThat(String that, AIML aiml)
    {
        if (that == null && aiml.getThat() == null)
            return true;
        if (that != null && aiml.getThat() != null)
        {
            String regex = StringUtils.toRegex(aiml.getThat());
            that = StringUtils.clearString(that);
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(that);
            return m.find();
        }
        return false;
    }

}
