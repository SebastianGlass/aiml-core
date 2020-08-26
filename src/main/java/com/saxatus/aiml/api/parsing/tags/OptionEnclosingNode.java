package com.saxatus.aiml.api.parsing.tags;

import java.util.List;

public interface OptionEnclosingNode<T extends LiNode>
{

    List<T> getContent();

    void setContent(List<T> content);

    String getWrappedText(String childContent);

}
