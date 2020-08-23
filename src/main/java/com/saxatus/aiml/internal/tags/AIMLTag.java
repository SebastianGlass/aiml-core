package com.saxatus.aiml.internal.tags;

import org.w3c.dom.Node;

import com.saxatus.aiml.internal.factory.TagFactory;
import com.saxatus.aiml.internal.parsing.AIMLParseNode;

public class AIMLTag extends AbstractAIMLTag
{

    protected AIMLTag(Node node, TagFactory factory)
    {
        super(node, factory);
    }

    @Override
    public String handle(AIMLParseNode debugNode)
    {
        super.handle(debugNode);
        return handleSubNodes();
    }

    private static final String TAG = "aiml";

    public static void register()
    {
        TagFactory.addTag(TAG, AIMLTag::new);
    }

    @Override
    public String getTag()
    {
        return TAG;
    }

    @Override
    public String getDebugInformation()
    {
        // TODO Auto-generated method stub
        return TAG + " (" + getFactory().getParameter()
                        .getPattern() + ")";
    }

}
