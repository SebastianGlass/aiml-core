package ai.saxatus.aiml.api;

public class AIMLResponse
{

    private final String string;
    private final String resolutionXML;
    private String aimlPattern;

    public AIMLResponse(String string, String resolutionXML)
    {
        this(string, null, resolutionXML);
    }

    public AIMLResponse(String string, String aimlPattern, String resolutionXML)
    {
        this.string = string.trim();
        this.aimlPattern = aimlPattern;
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

    public String getAimlPattern()
    {
        return aimlPattern;
    }

    @Override
    public String toString()
    {
        return "AIMLResponse [string=" + string + ", resolutionXML=" + resolutionXML + "]";
    }

    public AIMLResponse withAIMLPattern(String aiml)
    {
        return new AIMLResponse(string, aiml, resolutionXML);
    }

}
