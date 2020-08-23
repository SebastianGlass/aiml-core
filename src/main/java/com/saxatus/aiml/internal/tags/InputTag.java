package com.saxatus.aiml.internal.tags;

import java.util.List;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public class InputTag extends AbstractBotTag
{
    // TODO: Rework
    private InputTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        int index = Integer.parseInt(super.getOptionalAttribute("index", "1"));
        List<String> l = getAIMLHandler().getInputHistory();
        if (l.size() >= index)
            return l.get(l.size() - index);
        return "";
    }

    private static final String TAG = "input";

    public static void register()
    {
        // TagFactory.addTag(TAG, InputTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

}
