package com.saxatus.aiml.api.parsing;

import java.util.Map;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.tags.AIMLParseTag;

public interface AIMLParsingSession
{
    AIMLHandler getAIMLHandler();

    AIMLParseTag createTag(Node node);

    TagParameter getParameter();

    interface TagParameter
    {

        String getRequest();

        String getPattern();

        String getRealInput();

        Map<String, String> getBotMemory();

        Map<String, String> getNonStaticMemory();
    }

}