package com.saxatus.aiml.parsing;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.saxatus.aiml.StringUtils;

public class AIML implements Serializable, Comparable<AIML>
{
    private static final long serialVersionUID = 6143514919411255637L;

    private static final String STAR_REPLACEMENT = "\u1d11e"; // Needs to be a char behind all letters and numbers

    private String pattern;
    private String template;
    private String that;
    private String topic;

    private final String source;
    private final int line;

    public AIML(String pattern, String template, String that, String topic, String source, int line)
    {
        super();
        this.line = line;
        this.pattern = pattern;
        this.template = template;
        this.that = that;
        this.topic = topic;
        this.source = source;
    }

    @Override
    public String toString()
    {
        String s = "AIML [pattern=" + pattern.trim() + ", template=" + template.trim();
        if (that != null)
        {
            s += ", that=" + that.trim();
        }
        if (source != null)
        {
            s += ", src=" + source.trim();
        }
        if (topic != null)
        {
            s += ", topic=" + topic.trim();
        }

        s += ", line=" + line + "]";
        return s;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    public String getThat()
    {
        return that;
    }

    public void setThat(String that)
    {
        this.that = that;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getSource()
    {
        return source;
    }

    String getPatternWithReplacement()
    {
        return this.pattern.replace("*", STAR_REPLACEMENT) + STAR_REPLACEMENT;
    }

    @Override
    public int compareTo(AIML o)
    {
        return new AIMLComparator().compare(this, o);

    }

    @Override
    public int hashCode()
    {
        return Objects.hash(line, pattern, source, template, that, topic);
    }

    public boolean hasMatchingTopic(String topic)
    {
        if (topic.equalsIgnoreCase("Unknown") && getTopic() == null)
            return true;

        return getTopic() != null && topic.equalsIgnoreCase(getTopic());

    }

    public boolean hasMatchingThat(String that)
    {
        if (that == null && getThat() == null)
            return true;
        if (that != null && getThat() != null)
        {
            String regex = StringUtils.toRegex(getThat());
            that = StringUtils.clearString(that);
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(that);
            return m.find();
        }
        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AIML other = (AIML)obj;
        return line == other.line && Objects.equals(pattern, other.pattern) && Objects.equals(source, other.source)
                        && Objects.equals(template, other.template) && Objects.equals(that, other.that)
                        && Objects.equals(topic, other.topic);
    }

    public int getLine()
    {
        return line;
    }
}
