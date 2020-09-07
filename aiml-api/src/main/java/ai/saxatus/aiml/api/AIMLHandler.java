package ai.saxatus.aiml.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import ai.saxatus.aiml.api.parsing.AIMLNotFoundException;

public interface AIMLHandler
{

    AIMLResponse getAnswer(String input);

    AIMLResponse getAnswer(String input, String real) throws AIMLNotFoundException;

    Map<String, String> getStaticMemory();

    Map<String, String> getNonStaticMemory();

    List<String> getInputHistory();

    File getLearnFile();

    void setThatStar(List<String> replacements);

    List<String> getThatStar();

    List<String> getOutputHistory();

    AIMLHandler increaseDepth();

    void resetDepth();

    void setNonStaticMemory(Map<String, String> memory);

}