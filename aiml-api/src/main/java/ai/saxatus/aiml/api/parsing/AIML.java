package ai.saxatus.aiml.api.parsing;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.saxatus.aiml.api.utils.StringUtils;

public class AIML implements Serializable, Comparable<AIML>
{
    public static final String UNKNOWN = "unknown";

    private static final long serialVersionUID = 6143514919411255637L;

    private final String pattern;
    private final String template;
    private final String that;
    private final String topic;

    private final String source;
    private final int line;

    static AIMLComparator aimlComperator = new AIMLComparator();

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
        if (topic != null)
        {
            s += ", topic=" + topic.trim();
        }
        if (source != null)
        {
            s += ", src=" + source.trim();
        }
        s += ", line=" + line + "]";
        return s;
    }

    public AIML withPattern(String newPattern)
    {
        return new AIML(newPattern, template, that, topic, source, line);
    }

    public String getPattern()
    {
        return pattern;
    }

    public String getTemplate()
    {
        return template;
    }

    public String getThat()
    {
        return that;
    }

    public String getTopic()
    {
        return topic;
    }

    public String getSource()
    {
        return source;
    }

    @Override
    public int compareTo(AIML o)
    {
        return aimlComperator.compare(this, o);

    }

    @Override
    public int hashCode()
    {
        return Objects.hash(line, pattern, source, template, that, topic);
    }

    public boolean hasMatchingTopic(String topic)
    {
        if (topic.equalsIgnoreCase(UNKNOWN) && getTopic() == null)
            return true;

        return getTopic() != null && topic.equalsIgnoreCase(getTopic());

    }

    public boolean hasMatchingThat(String that)
    {
        if (that.equalsIgnoreCase(UNKNOWN) && getThat() == null)
            return true;

        if (!that.equalsIgnoreCase(UNKNOWN) && getThat() != null)
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
