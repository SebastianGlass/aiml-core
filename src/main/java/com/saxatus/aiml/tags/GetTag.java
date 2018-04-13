package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class GetTag extends AbstractBotTag
{

    private static final String TAG = "get";
    private String key;

    private GetTag(Node node, TagFactory factory)
    {
        super(node, factory);
        key = getOptionalAttribute("name", "").toLowerCase();
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        String value = nonStaticMemory.getOrDefault(key, "Unknown");
        return " " + value + " ";
    }

    public static void register()
    {
        TagFactory.addTag(TAG, GetTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String getDebugInformation()
    {
        return TAG + " (" + key + ")";
    }

}
