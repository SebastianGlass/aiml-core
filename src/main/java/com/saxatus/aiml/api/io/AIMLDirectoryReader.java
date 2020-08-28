package com.saxatus.aiml.api.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.saxatus.aiml.api.parsing.AIML;
import com.saxatus.aiml.api.parsing.AIMLParser;

public class AIMLDirectoryReader extends AbstractAIMLFileReader
{

    private static final Predicate<File> isAIMLFile = f -> f.getName()
                    .endsWith(".aiml");

    public AIMLDirectoryReader(File file)
    {
        super(file);
    }

    protected AIMLDirectoryReader(AIMLDirectoryReader aimlFileReader)
    {
        super(aimlFileReader);
    }

    @Override
    public Collection<AIML> provide(AIMLParser aimlParser) throws AIMLCreationException
    {
        if (!this.file.isDirectory())
        {
            throw new AIMLCreationException("Can't read file " + file.getAbsolutePath());
        }
        return getFilesRecursive(this.file, isAIMLFile).map(file -> loadFromFileOrDoNothing(file, aimlParser))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

    }

    private Collection<AIML> loadFromFileOrDoNothing(File t, AIMLParser aimlParser)
    {
        try
        {
            return loadFromFile(t, aimlParser);
        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            return Collections.emptyList();
        }
    }

    private static Stream<File> getFilesRecursive(File f, Predicate<File> filter)
    {
        if (f.isDirectory())
        {
            return Arrays.stream(f.listFiles())
                            .flatMap(file -> getFilesRecursive(file, filter));

        }
        else if (f.isFile() && filter.test(f))
        {
            return Stream.of(f);
        }
        else
        {
            return Stream.empty();
        }
    }
}