package com.saxatus.aiml.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.api.utils.Dictionary;
import com.saxatus.aiml.api.utils.DictionaryFilter;
import com.saxatus.aiml.api.utils.StringUtils;
import com.saxatus.aiml.internal.factory.AIMLDOMFactory;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;
import com.saxatus.aiml.internal.parsing.AIMLResolver;
import com.saxatus.aiml.internal.parsing.TagParameter;

public class AIMLHandlerImpl implements AIMLHandler
{

    private static final Log log = LogFactory.getLog(AIMLHandlerImpl.class);

    private final Dictionary<String, AIML> aimlDict;
    private final Map<String, String> botMemory;
    private final Map<String, String> nonStaticMemory;
    private final List<String> inputs;
    private final List<String> outputs;

    private List<String> thatStars;

    private File learnFile;

    @Inject
    public AIMLHandlerImpl(@Assisted List<AIML> aimls, @Assisted("non-static") Map<String, String> nonStaticMemory,
                    @Assisted("static") Map<String, String> botMemory, @Assisted File learnfile)
    {
        this.botMemory = botMemory;
        this.nonStaticMemory = nonStaticMemory;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.learnFile = learnfile;

        this.aimlDict = aimls.stream()
                        .collect(Dictionary::new, (dict, aiml) -> dict.put(aiml.getPattern()
                                        .split(" ")[0], aiml), (dict, dict2) -> dict.putAll(dict2));

    }

    @Override
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
        AIML aiml = new AIMLResolver(aimlDict, nonStaticMemory).getAIML(input);
        if (aiml == null)
        {
            throw new AIMLNotFoundException(input);
        }
        return aimltoString(aiml, input, real, node);
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

    public Dictionary<String, AIML> getDict()
    {
        return aimlDict;
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
        return new DictionaryFilter(aimlDict).applyTopicFilter(topic)
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

}
