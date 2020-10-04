package ai.saxatus.aiml.api.exceptions;

public class AIMLNotFoundException extends Exception
{
    private static final long serialVersionUID = -8725115503630805244L;
    private final String input;

    public AIMLNotFoundException(String input)
    {
        super("No AIML found for input: " + input);
        this.input = input;
    }

    public String getInput()
    {
        return input;
    }

}
