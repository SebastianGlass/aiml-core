package com.saxatus.aiml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.utils.Dictionary;
import com.saxatus.aiml.internal.factory.AIMLDOMFactory;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.parsing.AIMLResolver;
import com.saxatus.aiml.internal.parsing.TagParameter;
import com.saxatus.aiml.internal.utils.DictionaryFilter;
import com.saxatus.aiml.internal.utils.StringUtils;

public class AIMLHandler
{
    private static final Log log = LogFactory.getLog(AIMLHandler.class);

    private Dictionary<String, AIML> aimlList = new Dictionary<>();

    private Map<String, String> botMemory;
    private Map<String, String> nonStaticMemory;

    private List<String> thatStars;
    private List<String> inputs;
    private List<String> outputs;

    private File learnFile;

    AIMLHandler(List<AIML> aimls, Map<String, String> nonStaticMemory, Map<String, String> botMemory, File learnfile)
    {
        this.botMemory = botMemory;
        this.nonStaticMemory = nonStaticMemory;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.learnFile = learnfile;

        this.aimlList = new Dictionary<>();
        for (AIML aiml2 : aimls)
        {
            aimlList.put(aiml2.getPattern()
                            .split(" ")[0], aiml2);
        }
    }

    public String getAnswer(String input)
    {
        return getAnswer(input, new AIMLParseNode("root"));
    }

    public String getAnswer(String input, AIMLParseNode node)
    {
        inputs.add(input);
        String real = input;
        input = StringUtils.clearString(input);
        try
        {
            String answer = getAIMLResponse(input, real, node);
            nonStaticMemory.put("that", answer);
            outputs.add(answer);
            return answer;
        }
        catch(AIMLNotFoundException e)
        {
            log.warn(e);
            AIML aiml = new AIML("", "<srai>RANDOM PICKUP LINE</srai>", "", "", "", 1);
            return aimltoString(aiml, "", real, node);
        }
    }

    public String getAIMLResponse(String input, String real, AIMLParseNode node) throws AIMLNotFoundException
    {
        AIML aiml = new AIMLResolver(aimlList, nonStaticMemory).getAIML(input);
        if (aiml == null)
        {
            throw new AIMLNotFoundException(input);
        }
        return aimltoString(aiml, input, real, node);
    }

    public Dictionary<String, AIML> getDict()
    {
        return aimlList;
    }

    public List<String> getOutputHistory()
    {
        return outputs;
    }

    public List<String> getInputHistory()
    {
        return inputs;
    }

    public List<String> getThatStar()
    {
        return thatStars;
    }

    public Dictionary<String, AIML> getTopicDict()
    {
        String topic = botMemory.get("topic");
        return new DictionaryFilter(aimlList).applyTopicFilter(topic)
                        .getDict();
    }

    public void setThatStar(List<String> thatStars)
    {
        this.thatStars = thatStars;
    }

    public File getLearnFile()
    {

        return learnFile;
    }

    private String aimltoString(AIML aiml, String input, String real, AIMLParseNode node)
    {
        try
        {
            TagParameter tp = new TagParameter(input, aiml.getPattern(), real, botMemory, nonStaticMemory);
            TagFactory factory = new TagFactory(tp, this);

            Node rootNode = new AIMLDOMFactory(aiml.getTemplate()).getDocumentRoot();
            AIMLParseTag tag = factory.createTag(rootNode);

            return tag.handle(node);
        }
        catch(IOException e)
        {
            return "I've lost track, sorry.";
        }

    }

}
