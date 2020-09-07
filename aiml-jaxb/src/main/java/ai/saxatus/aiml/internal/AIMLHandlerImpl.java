package ai.saxatus.aiml.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import ai.saxatus.aiml.api.AIMLHandler;
import ai.saxatus.aiml.api.AIMLResponse;
import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.parsing.AIMLDictionaryFilter;
import ai.saxatus.aiml.api.parsing.AIMLNotFoundException;
import ai.saxatus.aiml.api.parsing.AIMLParser;
import ai.saxatus.aiml.api.provider.AIMLParserProvider;
import ai.saxatus.aiml.api.utils.Dictionary;
import ai.saxatus.aiml.api.utils.StringUtils;
import ai.saxatus.aiml.api.utils.XMLUtils;
import ai.saxatus.aiml.internal.parsing.AIMLResolver;

public class AIMLHandlerImpl implements AIMLHandler
{

    private static final String BOT_TAG_REGEX = "<BOT NAME=\"(.*)\"\\/>";

    private static final Pattern pattern = Pattern.compile(BOT_TAG_REGEX, Pattern.MULTILINE);

    private static final Log log = LogFactory.getLog(AIMLHandlerImpl.class);

    private final Dictionary<String, AIML> aimlDict;
    private final Map<String, String> botMemory;
    private Map<String, String> nonStaticMemory;
    private final List<String> inputs;
    private final List<String> outputs;

    private List<String> thatStars;

    private File learnFile;

    private final AIMLParserProvider aimlParserProvider;

    private int depth = 0;

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
                        .map(aiml -> aiml.withPattern(replaceBotTagsInPattern(aiml.getPattern(), botMemory)))
                        .collect(Dictionary::new, (dict, aiml) -> dict.put(aiml.getPattern()
                                        .split(" ")[0], aiml), (dict, dict2) -> dict.putAll(dict2));

    }

    static String replaceBotTagsInPattern(String string, Map<String, String> botMemory)
    {

        final Matcher matcher = pattern.matcher(string);

        if (matcher.find())
        {
            return StringUtils.innerTrim(string.replaceAll(BOT_TAG_REGEX, " " + botMemory.get(matcher.group(1)
                            .toLowerCase())
                            .toUpperCase()));
        }
        return string;

    }

    @Override
    public AIMLResponse getAnswer(String input)
    {
        inputs.add(input);
        try
        {
            AIMLResponse answer = getAnswer(StringUtils.clearString(input), input);
            nonStaticMemory.put("that", answer.getAnswer());
            outputs.add(answer.getAnswer());
            return answer;
        }
        catch(AIMLNotFoundException e)
        {
            log.warn(e);
            AIML aiml = new AIML("", "<srai>RANDOM PICKUP LINE</srai>", "", "", "", 1);
            return aimltoString(aiml, "");
        }
    }

    @Override
    public AIMLResponse getAnswer(String input, String real) throws AIMLNotFoundException
    {
        if (this.depth >= 30)
        {
            return new AIMLResponse("To deep senpai uwu", null);
        }
        AIML aiml = new AIMLResolver(aimlDict, nonStaticMemory).getAIML(input);
        log.info(aiml);
        if (aiml == null)
        {
            throw new AIMLNotFoundException(input);
        }
        return aimltoString(aiml, input);
    }

    private AIMLResponse aimltoString(AIML aiml, String input)
    {
        try
        {
            Node rootNode = XMLUtils.parseStringToXMLNode(aiml.getTemplate(), "template");
            AIMLParser parser = aimlParserProvider.provideTemplateParser(aiml.getPattern(), input, this);
            return parser.parse(rootNode)
                            .withAIMLPattern(aiml.getPattern());
        }
        catch(IOException | ParserConfigurationException | SAXException e)
        {
            return new AIMLResponse("I've lost track, sorry.", aiml.getPattern(), null);
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

    @Override
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
    public void setNonStaticMemory(Map<String, String> memory)
    {
        this.nonStaticMemory = memory;
    }

    @Override
    public Map<String, String> getNonStaticMemory()
    {
        return nonStaticMemory;
    }

    @Override
    public AIMLHandler increaseDepth()
    {
        this.depth++;
        return this;
    }

    @Override
    public void resetDepth()
    {
        this.depth = 0;
    }

}
