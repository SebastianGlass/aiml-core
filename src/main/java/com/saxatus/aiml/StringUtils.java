package com.saxatus.aiml;

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
        return that.replaceAll("  ", " ")
                        .trim();
    }

    public static String toRegex(String s)
    {
        return "^" + s.replace("?", "\\?")
                        .replace("*", "(.+)")
                        .replace(" ", "\\s")
                        .replace("_", "(.+)") + "$";
    }

}
