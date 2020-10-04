package ai.saxatus.aiml.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Converts dates to strings using the same format specifiers as strftime
 *
 * Note: This does not mimic strftime perfectly. Certain strftime commands, are not supported, and will convert as if
 * they were literals.
 *
 * Certain complicated commands, like those dealing with the week of the year probably don't have exactly the same
 * behavior as strftime.
 *
 * These limitations are due to use SimpleDateTime. If the conversion was done manually, all these limitations could be
 * eliminated.
 *
 * The interface looks like a subset of DateFormat. Maybe someday someone will make this class extend DateFormat.
 *
 * @author Bip Thelin
 * @author Dan Sandberg
 * @version $Revision: 467222 $, $Date: 2006-10-24 05:17:11 +0200 (mar., 24 oct. 2006) $
 */
public class Strftime
{
    protected static Properties translate;
    protected SimpleDateFormat simpleDateFormat;

    /**
     * Initialize our pattern translation
     */
    static
    {
        translate = new Properties();
        translate.put("a", "EEE");
        translate.put("A", "EEEE");
        translate.put("b", "MMM");
        translate.put("B", "MMMM");
        translate.put("c", "EEE MMM d HH:mm:ss yyyy");

        translate.put("d", "dd");
        translate.put("D", "MM/dd/yy");
        translate.put("e", "dd"); // will show as '03' instead of ' 3'
        translate.put("F", "yyyy-MM-dd");
        translate.put("g", "yy");
        translate.put("G", "yyyy");
        translate.put("H", "HH");
        translate.put("h", "MMM");
        translate.put("I", "hh");
        translate.put("j", "DDD");
        translate.put("k", "HH"); // will show as '07' instead of ' 7'
        translate.put("l", "hh"); // will show as '07' instead of ' 7'
        translate.put("m", "MM");
        translate.put("M", "mm");
        translate.put("n", "\n");
        translate.put("p", "a");
        translate.put("P", "a"); // will show as pm instead of PM
        translate.put("r", "hh:mm:ss a");
        translate.put("R", "HH:mm");

        translate.put("S", "ss");
        translate.put("t", "\t");
        translate.put("T", "HH:mm:ss");

        translate.put("V", "ww"); // I'm not sure this is always exactly the same

        translate.put("X", "HH:mm:ss");
        translate.put("x", "MM/dd/yy");
        translate.put("y", "yy");
        translate.put("Y", "yyyy");
        translate.put("Z", "z");
        translate.put("z", "Z");
        translate.put("%", "%");
    }

    /**
     * Create an instance of this date formatting class
     *
     * @see #Strftime( String, Locale )
     */
    public Strftime(String origFormat)
    {
        String convertedFormat = convertDateFormat(origFormat);
        simpleDateFormat = new SimpleDateFormat(convertedFormat);
    }

    /**
     * Create an instance of this date formatting class
     * 
     * @param origFormat
     *            the strftime-style formatting string
     * @param locale
     *            the locale to use for locale-specific conversions
     */
    public Strftime(String origFormat, Locale locale)
    {
        String convertedFormat = convertDateFormat(origFormat);
        simpleDateFormat = new SimpleDateFormat(convertedFormat, locale);
    }

    /**
     * Format the date according to the strftime-style string given in the constructor.
     *
     * @param date
     *            the date to format
     * @return the formatted date
     */
    public String format(Date date)
    {
        return simpleDateFormat.format(date);
    }

    /**
     * Get the timezone used for formatting conversions
     *
     * @return the timezone
     */
    public TimeZone getTimeZone()
    {
        return simpleDateFormat.getTimeZone();
    }

    /**
     * Change the timezone used to format dates
     *
     * @see SimpleDateFormat#setTimeZone
     */
    public void setTimeZone(TimeZone timeZone)
    {
        simpleDateFormat.setTimeZone(timeZone);
    }

    class ConvertionState
    {
        private boolean inside;
        private boolean mark;
        private boolean modifiedCommand;

        public ConvertionState()
        {
            this(false, false, false);
        }

        public ConvertionState(boolean inside, boolean mark, boolean modifiedCommand)
        {
            this.inside = inside;
            this.mark = mark;
            this.modifiedCommand = modifiedCommand;

        }
    }

    /**
     * Search the provided pattern and get the C standard Date/Time formatting rules and convert them to the Java
     * equivalent.
     *
     * @param pattern
     *            The pattern to search
     * @return The modified pattern
     */
    protected String convertDateFormat(String pattern)
    {
        StringBuilder buf = new StringBuilder();
        ConvertionState convertionState = new ConvertionState();

        for (int i = 0; i < pattern.length(); i++)
        {
            char c = pattern.charAt(i);

            if (c == '%' && !convertionState.mark)
            {
                convertionState.mark = true;
            }
            else
            {
                convertionState = parseMark(pattern, buf, convertionState, i, c);
            }
        }

        if (buf.length() > 0)
        {
            char lastChar = buf.charAt(buf.length() - 1);

            if (lastChar != '\'' && convertionState.inside)
            {
                buf.append('\'');
            }
        }
        return buf.toString();
    }

    private ConvertionState parseMark(String pattern, StringBuilder buf, ConvertionState convertionState, int i, char c)
    {
        if (convertionState.mark)
        {
            if (convertionState.modifiedCommand)
            {
                // don't do anything--we just wanted to skip a char
                return new ConvertionState(convertionState.inside, false, false);
            }

            convertionState.inside = translateCommand(buf, pattern, i, convertionState.inside);
            // It's a modifier code
            if (c == 'O' || c == 'E')
            {
                convertionState.modifiedCommand = true;
            }
            else
            {
                convertionState.mark = false;
            }
        }
        else
        {
            if (!convertionState.inside && c != ' ')
            {
                // We start a literal, which we need to quote
                buf.append("'");
                convertionState.inside = true;
            }

            buf.append(c);
        }
        return convertionState;
    }

    protected String quote(String str, boolean insideQuotes)
    {
        String retVal = str;
        if (!insideQuotes)
        {
            retVal = '\'' + retVal + '\'';
        }
        return retVal;
    }

    /**
     * Try to get the Java Date/Time formatting associated with the C standard provided.
     *
     * @param buf
     *            The buffer
     * @param pattern
     *            The date/time pattern
     * @param index
     *            The char index
     * @param oldInside
     *            Flag value
     * @return True if new is inside buffer
     */
    protected boolean translateCommand(StringBuilder buf, String pattern, int index, boolean oldInside)
    {
        char firstChar = pattern.charAt(index);
        boolean newInside = oldInside;

        // O and E are modifiers, they mean to present an alternative representation of the next char
        // we just handle the next char as if the O or E wasn't there
        if (firstChar == 'O' || firstChar == 'E')
        {
            if (index + 1 < pattern.length())
            {
                newInside = translateCommand(buf, pattern, index + 1, oldInside);
            }
            else
            {
                buf.append(quote("%" + firstChar, oldInside));
            }
        }
        else
        {
            String command = translate.getProperty(String.valueOf(firstChar));

            // If we don't find a format, treat it as a literal--That's what apache does
            if (command == null)
            {
                buf.append(quote("%" + firstChar, oldInside));
            }
            else
            {
                // If we were inside quotes, close the quotes
                if (oldInside)
                {
                    buf.append('\'');
                }
                buf.append(command);
                newInside = false;
            }
        }
        return newInside;
    }
}