package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.api.utils.StringUtils;
import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;
import com.saxatus.aiml.internal.parsing.TagRepository;

public class TextTag extends AbstractAIMLTag
{

    private TextTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    private static final String TAG = "#text";

    public static void register()
    {
        TagRepository.addTag(TAG, TextTag::new);
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
