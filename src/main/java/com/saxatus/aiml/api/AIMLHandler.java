package com.saxatus.aiml.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLNotFoundException;

public interface AIMLHandler
{

    default String getAnswer(String input)
    {
        return getAnswer(input, new AIMLParseNode("root"));
    }

    String getAnswer(String input, AIMLParseNode debugNode);

    String getAnswer(String input, String real, AIMLParseNode debugNode) throws AIMLNotFoundException;

    Map<String, String> getStaticMemory();

    Map<String, String> getNonStaticMemory();

    List<String> getInputHistory();

    File getLearnFile();

    void setThatStar(List<String> replacements);

    List<String> getThatStar();

    List<String> getOutputHistory();

}