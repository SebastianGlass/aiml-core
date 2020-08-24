package com.saxatus.aiml.internal.factory;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.w3c.dom.Node;

import com.google.inject.assistedinject.Assisted;
import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.factory.TagFactory;
import com.saxatus.aiml.api.tags.AIMLParseTag;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class TagFactoryImpl implements TagFactory
{

    private final TagParameter parameter;
    private AIMLHandler handler;
    private static TagRepository tagRepo = new TagRepository();

    @Inject
    public TagFactoryImpl(@Assisted TagParameter parameter, @Assisted @Nullable AIMLHandler handler)
    {
        this.parameter = parameter;
        this.handler = handler;
    }

    public AIMLParseTag createTag(Node node)
    {
        TagSupplier supplier = tagRepo.getByName(node.getNodeName());
        return supplier.supply(node, this);
    }

    public TagParameter getParameter()
    {
        return parameter;
    }

    public AIMLHandler getAIMLHandler()
    {
        return handler;
    }

    public static class TagParameterImpl implements TagParameter
    {

        private final String request;
        private final String pattern;
        private final String realInput;
        private final Map<String, String> botMemory;
        private Map<String, String> nonStaticMemory;

        public TagParameterImpl(String request, String pattern, String realInput, Map<String, String> botMemory,
                        Map<String, String> nonStaticMemory)
        {
            this.request = request;
            this.pattern = pattern;
            this.realInput = realInput;
            this.botMemory = botMemory;
            this.nonStaticMemory = nonStaticMemory;

        }

        @Override
        public String getRequest()
        {
            return request;
        }

        @Override
        public String getPattern()
        {
            return pattern;
        }

        @Override
        public String getRealInput()
        {
            return realInput;
        }

        @Override
        public Map<String, String> getBotMemory()
        {
            return botMemory;
        }

        @Override
        public Map<String, String> getNonStaticMemory()
        {
            return nonStaticMemory;
        }

    }

}
