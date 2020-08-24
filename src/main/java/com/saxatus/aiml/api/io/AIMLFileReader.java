package com.saxatus.aiml.api.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;

public class AIMLFileReader extends AbstractAIMLFileReader
{

    public AIMLFileReader(File file)
    {
        super(file);
    }

    protected AIMLFileReader(AIMLFileReader aimlFileReader)
    {
        super(aimlFileReader);
    }

    @Override
    public Collection<AIML> provide() throws AIMLCreationException
    {
        if (!this.file.isFile() || !this.file.canRead())
        {
            throw new AIMLCreationException("Can't read file " + file.getAbsolutePath());
        }
        try
        {
            return loadFromFile(this.file);
        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            throw new AIMLCreationException(e);
        }

    }

    @Override
    public AIMLFileReader withBotMemory(Map<String, String> map)
    {
        this.botMemory = map;
        return new AIMLFileReader(this);

    }
}