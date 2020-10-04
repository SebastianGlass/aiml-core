package ai.saxatus.aiml.api.io;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.saxatus.aiml.api.exceptions.AIMLCreationException;
import ai.saxatus.aiml.api.parsing.AIML;
import ai.saxatus.aiml.api.provider.AIMLProvider;

public class AIMLDirectoryReader implements AIMLProvider
{

    private static final Predicate<File> isAIMLFile = f -> f.getName()
                    .endsWith(".aiml");

    private File path;

    public AIMLDirectoryReader(File path)
    {
        this.path = path;
    }

    @Override
    public Collection<AIML> provide() throws AIMLCreationException
    {
        if (!this.path.isDirectory())
        {
            throw new AIMLCreationException("Can't read file " + path.getAbsolutePath());
        }
        return getFilesRecursive(this.path, isAIMLFile).map(this::loadFromFileOrDoNothing)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

    }

    private Collection<AIML> loadFromFileOrDoNothing(File t)
    {
        try
        {
            return new AIMLFileReader(t).provide();
        }
        catch(AIMLCreationException e)
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