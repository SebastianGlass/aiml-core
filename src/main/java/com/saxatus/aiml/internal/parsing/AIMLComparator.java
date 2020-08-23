package com.saxatus.aiml.internal.parsing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.internal.utils.StringUtils;

public class AIMLComparator implements Comparator<AIML>
{
    private static final String STAR_REPLACEMENT = "\u1d11e"; // Needs to be a char behind all letters and numbers

    private static final Log log = LogFactory.getLog(AIMLComparator.class);

    private static List<BiFunction<AIML, AIML, Integer>> filterChain = Arrays.asList(AIMLComparator::compareTopic,
                    AIMLComparator::compareThat, AIMLComparator::comparePatternEnding,
                    AIMLComparator::comparePatternRegex, AIMLComparator::compareLearnedState);

    @Override
    public int compare(AIML aiml1, AIML aiml2)
    {
        if (aiml1.getLine() == aiml2.getLine() && aiml1.getSource()
                        .equals(aiml2.getSource()))
        {
            return 0;
        }
        return filterChain.stream()
                        .map(chain -> chain.apply(aiml1, aiml2))
                        .filter(r -> r != 0)
                        .findFirst()
                        .orElseGet(() -> {
                            log.warn("Duplicated AIML Signature: " + aiml1 + "," + aiml2);
                            return aiml2.getLine() - aiml1.getLine();
                        });

    }

    private static int compareLearnedState(AIML aiml1, AIML aiml2)
    {
        int result = (aiml1.getSource()
                        .contains("learned") ? 1 : 0)
                        - (aiml2.getSource()
                                        .contains("learned") ? 1 : 0);
        return -result;
    }

    private static String getPatternWithReplacement(AIML a)
    {
        return a.getPattern()
                        .replace("*", STAR_REPLACEMENT) + STAR_REPLACEMENT;
    }

    private static int comparePatternRegex(AIML aiml1, AIML aiml2)
    {
        return getPatternWithReplacement(aiml1).compareTo(getPatternWithReplacement(aiml2));
    }

    private static int comparePatternEnding(AIML aiml1, AIML aiml2)
    {
        return (aiml1.getPattern()
                        .endsWith("*") ? 1 : 0)
                        - (aiml2.getPattern()
                                        .endsWith("*") ? 1 : 0);
    }

    private static int compareThat(AIML aiml1, AIML aiml2)
    {
        return StringUtils.compareTo(aiml1.getThat(), aiml2.getThat());
    }

    private static int compareTopic(AIML aiml11, AIML aiml2)
    {
        return StringUtils.compareTo(aiml11.getTopic(), aiml2.getTopic());
    }

}
