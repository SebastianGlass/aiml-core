package com.saxatus.aiml.internal.parsing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AIMLParseNode implements Serializable
{

    private static final long serialVersionUID = 965624758224323417L;

    public final String tag;
    private final List<AIMLParseNode> children;

    public AIMLParseNode(String tag)
    {
        this.tag = tag;
        this.children = new ArrayList<>(10);
    }

    public void addChild(AIMLParseNode child)
    {
        this.children.add(child);
    }

    public List<AIMLParseNode> getChildren()
    {
        return children;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TAG " + this.tag + ": " + this.children);
        return (builder.toString());
    }

}
