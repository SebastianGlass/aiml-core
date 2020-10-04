package ai.saxatus.aiml.internal.parsing.parser;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.w3c.dom.Node;

import com.google.inject.Singleton;

import ai.saxatus.aiml.api.exceptions.AIMLTransformException;
import ai.saxatus.aiml.api.parsing.AIMLTransformer;
import ai.saxatus.aiml.api.parsing.tags.AIMLContentNode;

@Singleton
public class JaxbAIMLTransformer<T extends AIMLContentNode> implements AIMLTransformer<T>
{

    private Unmarshaller jaxbUnmarshaller;

    public JaxbAIMLTransformer()
    {
        Reflections reflections = new Reflections("ai.saxatus");
        List<Class<? extends AIMLContentNode>> classes = reflections.getSubTypesOf(AIMLContentNode.class)
                        .stream()
                        .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers())
                                        && AIMLContentNode.class.isAssignableFrom(clazz))
                        .collect(Collectors.toList());
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[0]));
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        }
        catch(JAXBException e)
        {
            throw new ReflectionsException("Problems with loading AIMLTags", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T transform(Node node) throws AIMLTransformException
    {
        try
        {
            return (T)jaxbUnmarshaller.unmarshal(node);
        }
        catch(JAXBException e)
        {
            throw new AIMLTransformException(e);
        }

    }
}
