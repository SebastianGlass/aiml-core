package com.saxatus.aiml.tags;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class PersonTag extends StarTag
{

    private final Map<String, String> map = new HashMap<String, String>();
    {
        map.put("i", "you");
        map.put("me", "you");
        map.put("my", "your");
        map.put("we", "you");

        map.put("i", "you");
        map.put("you", "me");
        map.put("your", "my");
    }

    private PersonTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "person";

    public static void register()
    {
        TagFactory.addTag(TAG, PersonTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String context;
        if (getNode().getChildNodes()
                        .getLength() == 0)
        {
            context = replaceStars();
        }
        else
        {
            context = handleSubNodes();
        }

        String[] contextList = context.split(" ");
        context = "";
        for (int i = 0; i < contextList.length; i++)
        {
            contextList[i] = map.getOrDefault(contextList[i].toLowerCase(), contextList[i]);
            context += " " + contextList[i];
        }

        return context;
    }

    public String getDebugInformation()
    {
        if (getNode().getChildNodes()
                        .getLength() == 0)
        {
            return TAG + " (" + replaceStars() + ")";
        }
        else
        {
            return TAG;
        }
    }

}
