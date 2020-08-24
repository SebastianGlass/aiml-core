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

import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.factory.AIMLParserFactory;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.utils.Dictionary;
import com.saxatus.aiml.api.utils.DictionaryFilter;
import com.saxatus.aiml.api.utils.StringUtils;
import com.saxatus.aiml.internal.factory.AIMLDOMFactory;
import com.saxatus.aiml.internal.parsing.AIMLNotFoundException;
import com.saxatus.aiml.internal.parsing.AIMLResolver;

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

    private final AIMLParserFactory aimlParserFactory;

    @Inject
    public AIMLHandlerImpl(@Assisted List<AIML> aimls, @Assisted("non-static") Map<String, String> nonStaticMemory,
                    @Assisted("static") Map<String, String> botMemory, @Assisted File learnfile,
                    AIMLParserFactory aimlParserFactory)
    {
        this.botMemory = botMemory;
        this.nonStaticMemory = nonStaticMemory;
        this.aimlParserFactory = aimlParserFactory;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.learnFile = learnfile;

        this.aimlDict = aimls.stream()
                        .collect(Dictionary::new, (dict, aiml) -> dict.put(aiml.getPattern()
                                        .split(" ")[0], aiml), (dict, dict2) -> dict.putAll(dict2));

    }

    @Override
    public String getAnswer(String input, AIMLParseNode node)
    {
        inputs.add(input);
        try
        {
            String answer = getAnswer(StringUtils.clearString(input), input, node);
            nonStaticMemory.put("that", answer);
            outputs.add(answer);
            return answer;
        }
        catch(AIMLNotFoundException e)
        {
            log.warn(e);
            AIML aiml = new AIML("", "<srai>RANDOM PICKUP LINE</srai>", "", "", "", 1);
            return aimltoString(aiml, "", input, node);
        }
    }

    @Override
    public String getAnswer(String input, String real, AIMLParseNode node) throws AIMLNotFoundException
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
            Node rootNode = new AIMLDOMFactory(aiml.getTemplate()).getDocumentRoot();
            AIMLParser parser = aimlParserFactory.createTemplateParser(aiml.getPattern(), input, real, this, node);
            return parser.parse(rootNode);
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

    @Override
    public List<String> getOutputHistory()
    {
        return outputs;
    }

    @Override
    public List<String> getInputHistory()
    {
        return inputs;
    }

    @Override
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

    @Override
    public void setThatStar(List<String> thatStars)
    {
        this.thatStars = thatStars;
    }

    public File getLearnFile()
    {

        return learnFile;
    }

    @Override
    public Map<String, String> getStaticMemory()
    {
        return Maps.newHashMap(botMemory);
    }

    @Override
    public Map<String, String> getNonStaticMemory()
    {
        return nonStaticMemory;
    }

}
