package com.saxatus.aiml.internal.parsing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.utils.Dictionary;
import com.saxatus.aiml.api.utils.StringUtils;

public class AIMLResolver
{

    private final Map<String, String> nonStaticMemory;
    private final Dictionary<String, AIML> list;

    public AIMLResolver(Dictionary<String, AIML> aimlList, Map<String, String> nonStaticMemory)
    {
        this.nonStaticMemory = nonStaticMemory;
        this.list = aimlList;

    }

    public AIML getAIML(String input)
    {
        String topic = nonStaticMemory.getOrDefault("topic", AIML.UNKNOWN)
                        .toUpperCase();
        String that = nonStaticMemory.getOrDefault("that", AIML.UNKNOWN)
                        .toUpperCase();

        List<Set<AIML>> aimlSubLists = Arrays.asList(list.get("_"), list.get(input.split(" ")[0].toUpperCase()),
                        list.get("*"));

        for (Set<AIML> set : aimlSubLists)
        {
            AIML currentAIML = getAIML(input, topic, that, set);
            currentAIML = (currentAIML != null) ? currentAIML : getAIML(input, topic, AIML.UNKNOWN, set);
            currentAIML = (currentAIML != null) ? currentAIML : getAIML(input, AIML.UNKNOWN, that, set);
            currentAIML = (currentAIML != null) ? currentAIML : getAIML(input, AIML.UNKNOWN, AIML.UNKNOWN, set);
            if (currentAIML != null)
                return currentAIML;
        }
        return null;
    }

    private AIML getAIML(String input, String topic, String that, Set<AIML> list)
    {
        if (list == null)
            return null;
        for (AIML aiml : list)
        {
            if (!aiml.hasMatchingTopic(topic) || !aiml.hasMatchingThat(that))
            {
                continue;
            }
            Matcher m = getMatcher(input, aiml);
            if (m.find())
            {
                return aiml;
            }
        }
        return null;
    }

    private Matcher getMatcher(String input, AIML aiml)
    {
        String regex = StringUtils.toRegex(aiml.getPattern());
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(input);
    }

}