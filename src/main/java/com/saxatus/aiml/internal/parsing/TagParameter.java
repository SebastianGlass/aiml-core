package com.saxatus.aiml.internal.parsing;

import java.util.Map;

public class TagParameter
{

    private final String request;
    private final String pattern;
    private final String realInput;
    private final Map<String, String> botMemory;
    private Map<String, String> nonStaticMemory;

    public TagParameter(String request, String pattern, String realInput, Map<String, String> botMemory,
                    Map<String, String> nonStaticMemory)
    {
        this.request = request;
        this.pattern = pattern;
        this.realInput = realInput;
        this.botMemory = botMemory;
        this.nonStaticMemory = nonStaticMemory;

    }

    public String getRequest()
    {
        return request;
    }

    public String getPattern()
    {
        return pattern;
    }

    public String getRealInput()
    {
        return realInput;
    }

    public Map<String, String> getBotMemory()
    {
        return botMemory;
    }

    public Map<String, String> getNonStaticMemory()
    {
        return nonStaticMemory;
    }

}