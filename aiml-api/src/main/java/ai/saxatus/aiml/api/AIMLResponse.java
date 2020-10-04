package ai.saxatus.aiml.api;

import ai.saxatus.aiml.api.parsing.AIML;

public class AIMLResponse
{

    private final String string;
    private final String resolutionXML;
    private final AIML aiml;

    public AIMLResponse(String string, String resolutionXML)
    {
        this(string, null, resolutionXML);
    }

    public AIMLResponse(String string, AIML aiml, String resolutionXML)
    {
        this.string = string.trim();
        this.aiml = aiml;
        this.resolutionXML = resolutionXML == null ? "" : resolutionXML;
    }

    public String getAnswer()
    {
        return string;
    }

    public String getResolutionXML()
    {
        return resolutionXML;
    }

    public AIML getAiml()
    {
        return aiml;
    }

    @Override
    public String toString()
    {
        return "AIMLResponse [string=" + string + ", resolutionXML=" + resolutionXML + "]";
    }

    public AIMLResponse withAIML(AIML aiml)
    {
        return new AIMLResponse(string, aiml, resolutionXML);
    }

}
