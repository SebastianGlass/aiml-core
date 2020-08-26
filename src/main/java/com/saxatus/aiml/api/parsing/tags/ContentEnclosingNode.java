package com.saxatus.aiml.api.parsing.tags;

import java.util.List;

public interface ContentEnclosingNode<T extends AIMLContentNode> 
{

    List<T> getContent();

    void setContent(List<T> content);

    String getWrappedText(String childContent);

}
