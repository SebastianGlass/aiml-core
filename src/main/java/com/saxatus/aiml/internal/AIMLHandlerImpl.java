package com.saxatus.aiml.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLDictionaryFilter;
import com.saxatus.aiml.api.parsing.AIMLNotFoundException;
import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.api.parsing.AIMLParser;
import com.saxatus.aiml.api.provider.AIMLParserProvider;
import com.saxatus.aiml.api.utils.Dictionary;
import com.saxatus.aiml.api.utils.StringUtils;
import com.saxatus.aiml.api.utils.XMLUtils;
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

    private final AIMLParserProvider aimlParserProvider;

    @Inject
    public AIMLHandlerImpl(@Assisted List<AIML> aimls, @Assisted("non-static") Map<String, String> nonStaticMemory,
                    @Assisted("static") Map<String, String> botMemory, @Assisted File learnfile,
                    AIMLParserProvider aimlParserProvider)
    {
        this.botMemory = botMemory;
        this.nonStaticMemory = nonStaticMemory;
        this.aimlParserProvider = aimlParserProvider;
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
            Node rootNode = XMLUtils.parseStringToXMLNode(aiml.getTemplate(), "template");
            AIMLParser parser = aimlParserProvider.provideTemplateParser(aiml.getPattern(), input, real, this, node);
            return parser.parse(rootNode).trim();
        }
        catch(IOException | ParserConfigurationException | SAXException e)
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
        String topic = nonStaticMemory.get("topic");
        return new AIMLDictionaryFilter(aimlDict).applyTopicFilter(topic)
                        .getDictionary();
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
