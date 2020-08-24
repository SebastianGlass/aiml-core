package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class SetTag extends AbstractBotTag
{

    private String key;

    private SetTag(Node node, TagFactory factory)
    {
        super(node, factory);
        key = getOptionalAttribute("name", "");
    }

    private static final String TAG = "set";

    public static void register()
    {
        TagRepository.addTag(TAG, SetTag::new);
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

        String value = handleSubNodes();
        nonStaticMemory.put(key, value);
        return " " + value.replace("  ", " ")
                        .trim() + " ";
    }

    @Override
    public String getDebugInformation()
    {
        return TAG + " (" + key + ")";
    }

}