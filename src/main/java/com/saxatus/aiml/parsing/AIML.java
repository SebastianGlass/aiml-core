package com.saxatus.aiml.parsing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AIML implements Comparable<AIML>
{

    private static final Log log = LogFactory.getLog(AIML.class);

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

    @Override
    public int compareTo(AIML o)
    {
        int c = compareTopic(o);
        if (c != 0)
        {
            return c;
        }
        c = compareThat(o);
        if (c != 0)
        {
            return c;
        }
        if (pattern.endsWith("*") && !o.pattern.endsWith("*"))
        {
            return 1;
        }
        if (!pattern.endsWith("*") && o.pattern.endsWith("*"))
        {
            return -1;
        }
        c = (this.pattern.replaceAll("\\*", "�") + "�").compareTo((o.pattern.replaceAll("\\*", "�") + "�"));

        if (c != 0)
        {
            return c;
        }
        if (line == o.line && source.equals(o.source))
        {
            return 0;
        }
        if (source.contains("learned"))
        {
            return -1;
        }
        if (o.source.contains("learned"))
        {
            return 1;
        }
        log.warn("Duplicated AIML Signature: " + this + "," + o);

        return 1;
    }

    private int compareThat(AIML o)
    {
        if (that != null || o.that != null)
        {
            if (that == null)
            {
                return 1;
            }
            if (o.that == null)
            {
                return -1;
            }
            return this.that.compareTo(o.that);

        }
        return 0;
    }

    private int compareTopic(AIML o)
    {
        if (topic != null || o.topic != null)
        {
            if (topic == null)
            {
                return 1;
            }
            if (o.topic == null)
            {
                return -1;
            }
            return this.topic.compareTo(o.topic);

        }
        return 0;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + line;
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((template == null) ? 0 : template.hashCode());
        result = prime * result + ((that == null) ? 0 : that.hashCode());
        result = prime * result + ((topic == null) ? 0 : topic.hashCode());
        return result;
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
        if (line != other.line)
            return false;
        if (pattern == null)
        {
            if (other.pattern != null)
                return false;
        }
        else if (!pattern.equals(other.pattern))
            return false;
        if (source == null)
        {
            if (other.source != null)
                return false;
        }
        else if (!source.equals(other.source))
            return false;
        if (template == null)
        {
            if (other.template != null)
                return false;
        }
        else if (!template.equals(other.template))
            return false;
        if (that == null)
        {
            if (other.that != null)
                return false;
        }
        else if (!that.equals(other.that))
            return false;
        if (topic == null)
        {
            if (other.topic != null)
                return false;
        }
        else if (!topic.equals(other.topic))
            return false;
        return true;
    }

}
