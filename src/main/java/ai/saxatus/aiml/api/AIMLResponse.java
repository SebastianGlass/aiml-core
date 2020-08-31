package ai.saxatus.aiml.api;

public class AIMLResponse
{

    private final String string;
    private final String resolutionXML;

    public AIMLResponse(String string, String resolutionXML)
    {
        this.string = string.trim();
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

    @Override
    public String toString()
    {
        return "AIMLResponse [string=" + string + ", resolutionXML=" + resolutionXML + "]";
    }

}
