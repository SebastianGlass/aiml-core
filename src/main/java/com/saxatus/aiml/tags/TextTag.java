package com.saxatus.aiml.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.StringUtils;
import com.saxatus.aiml.factory.TagFactory;
import com.saxatus.aiml.parsing.AIMLParseNode;

public class TextTag extends AbstractAIMLTag
{

    private TextTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "#text";

    public static void register()
    {
        TagFactory.addTag(TAG, TextTag::new);
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
        return getNode().getTextContent();
    }

    @Override
    public String getDebugInformation()
    {
        return TAG + " (" + StringUtils.clearString(getNode().getTextContent()) + ")";
    }

}
