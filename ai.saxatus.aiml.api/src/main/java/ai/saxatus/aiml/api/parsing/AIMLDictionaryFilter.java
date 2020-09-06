package ai.saxatus.aiml.api.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.saxatus.aiml.api.utils.Dictionary;
import ai.saxatus.aiml.api.utils.DictionaryFilter;
import ai.saxatus.aiml.api.utils.StringUtils;

public class AIMLDictionaryFilter extends DictionaryFilter<AIML>
{

    public AIMLDictionaryFilter(Dictionary<String, AIML> d)
    {
        super(d);
    }

    public AIMLDictionaryFilter applyTopicFilter(String topic)
    {
        if (topic == null)
        {
            return this;
        }
        return new AIMLDictionaryFilter(applyFilter(aiml -> aiml.hasMatchingTopic(topic)));

    }

    public AIMLDictionaryFilter applyThatFilter(String that)
    {

        if (that == null)
        {
            return this;
        }
        return new AIMLDictionaryFilter(applyFilter(aiml -> aiml.hasMatchingThat(that)));
    }

    public AIMLDictionaryFilter applyPatternFilter(String pattern)
    {
        if (pattern == null)
        {
            return this;
        }
        return new AIMLDictionaryFilter(applyFilter(aiml -> hasMathingPattern(aiml, pattern)));
    }

    private boolean hasMathingPattern(AIML aiml, String pattern)
    {
        String regex = StringUtils.toRegex(aiml.getPattern());
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pattern);
        return m.find();
    }

}
