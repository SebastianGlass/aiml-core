package com.saxatus.aiml.internal.parsing;

public class AIMLNotFoundException extends Exception
{
    private static final long serialVersionUID = -8725115503630805244L;
    private final String input;

    public AIMLNotFoundException(String input)
    {
        this.input = input;
    }

    public String getInput()
    {
        return input;
    }

    @Override
    public String toString()
    {
        return this.getClass()
                        .getName() + " caused by input: " + input;
    }

}
