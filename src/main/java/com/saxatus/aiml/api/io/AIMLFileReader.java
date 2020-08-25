package com.saxatus.aiml.api.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParser;

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
    public Collection<AIML> provide(AIMLParser aimlParser) throws AIMLCreationException
    {
        if (!this.file.isFile() || !this.file.canRead())
        {
            throw new AIMLCreationException("Can't read file " + file.getAbsolutePath());
        }
        try
        {
            return loadFromFile(this.file, aimlParser);
        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            throw new AIMLCreationException(e);
        }

    }
}