package com.saxatus.aiml.api.provider;

import com.saxatus.aiml.api.AIMLHandler;
import com.saxatus.aiml.api.parsing.AIMLParsingSession;
import com.saxatus.aiml.api.parsing.AIMLParsingSession.TagParameter;

public interface AIMLParsingSessionProvider
{
    AIMLParsingSession create(TagParameter parameter, AIMLHandler handler);
}
