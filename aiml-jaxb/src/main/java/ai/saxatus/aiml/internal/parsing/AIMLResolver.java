package ai.saxatus.aiml.internal.parsing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.utils.Dictionary;
import ai.saxatus.aiml.api.utils.StringUtils;

public class AIMLResolver
{

    private final Map<String, String> nonStaticMemory;
    private final Dictionary<String, AIML> aimlDict;

    public AIMLResolver(Dictionary<String, AIML> aimlDict, Map<String, String> nonStaticMemory)
    {
        this.nonStaticMemory = nonStaticMemory;
        this.aimlDict = aimlDict;

    }

    public AIML getAIML(String input)
    {
        String topic = nonStaticMemory.getOrDefault("topic", AIML.UNKNOWN)
                        .toUpperCase();
        String that = nonStaticMemory.getOrDefault("that", AIML.UNKNOWN)
                        .toUpperCase();

        List<Set<AIML>> aimlSubLists = Arrays.asList(aimlDict.get("_"), aimlDict.get(input.split(" ")[0].toUpperCase()),
                        aimlDict.get("*"));

        return aimlSubLists.stream()
                        .filter(Objects::nonNull)
                        .map(set -> findAIML(input, topic, that, set)
                                        .or(() -> findAIML(input, topic, AIML.UNKNOWN, set))
                                        .or(() -> findAIML(input, AIML.UNKNOWN, that, set))
                                        .or(() -> findAIML(input, AIML.UNKNOWN, AIML.UNKNOWN, set))
                                        .orElse(null))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);

    }

    private Optional<AIML> findAIML(String input, String topic, String that, Set<AIML> list)
    {
        return list.stream()
                        .filter(aiml -> hasMatchingTopic(aiml, topic))
                        .filter(aiml -> hasMatchingThat(aiml, that))
                        .filter(aiml -> hasMatchingInput(aiml, input))
                        .findFirst();
    }

    boolean hasMatchingTopic(AIML aiml, String topic)
    {
        if (topic.equalsIgnoreCase(AIML.UNKNOWN) && aiml.getTopic() == null)
            return true;

        return aiml.getTopic() != null && topic.equalsIgnoreCase(aiml.getTopic());

    }

    boolean hasMatchingThat(AIML aiml, String that)
    {
        if (that.equalsIgnoreCase(AIML.UNKNOWN) && aiml.getThat() == null)
            return true;

        if (!that.equalsIgnoreCase(AIML.UNKNOWN) && aiml.getThat() != null)
        {
            String regex = StringUtils.toRegex(aiml.getThat());
            that = StringUtils.clearString(that);
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(that);
            return m.find();
        }
        return false;
    }

    boolean hasMatchingInput(AIML aiml, String input)
    {
        String regex = StringUtils.toRegex(aiml.getPattern());
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return (m.find());
    }

}