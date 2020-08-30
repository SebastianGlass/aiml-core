package com.saxatus.aiml.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.saxatus.aiml.api.parsing.AIMLNotFoundException;

public interface AIMLHandler
{

    String getAnswer(String input);

    String getAnswer(String input, String real) throws AIMLNotFoundException;

    Map<String, String> getStaticMemory();

    Map<String, String> getNonStaticMemory();

    List<String> getInputHistory();

    File getLearnFile();

    void setThatStar(List<String> replacements);

    List<String> getThatStar();

    List<String> getOutputHistory();

    AIMLHandler increaseDepth();

    void resetDepth();

}