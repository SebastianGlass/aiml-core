package com.saxatus.aiml.api.parsing.tags;

import java.util.List;

public interface ContentEnclosingNode
{

    List<AIMLContentNode> getContent();

    void setContent(List<AIMLContentNode> content);

    String getWrappedText(String childContent);

}
