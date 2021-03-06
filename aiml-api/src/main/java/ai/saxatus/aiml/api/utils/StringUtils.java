package ai.saxatus.aiml.api.utils;

public class StringUtils
{

    private static final String[] stringReplacements = { "\\.", "\\?", "!", ",", ";", ":", "\\(", "\\)", "\"", "'",
                    "\t", "\n" };

    public static String clearString(String that)
    {
        for (String s : stringReplacements)
        {
            that = that.replaceAll(s, "");
        }
        return innerTrim(that).replace(" />", "/>");
    }

    public static String innerTrim(String input)
    {
        return input.replace("  ", " ")
                        .trim();
    }

    public static String toRegex(String s)
    {
        return "^" + s.replace("?", "\\?")
                        .replace("*", "(.+)")
                        .replace(" ", "\\s")
                        .replace("_", "(.+)") + "$";
    }

    public static int compareTo(String a, String b)
    {
        if (a == null && b == null)
        {
            return 0;
        }
        if (a == null)
        {
            return 1;
        }
        if (b == null)
        {
            return -1;
        }
        return a.compareTo(b);

    }

    private StringUtils()
    {

    }
}
