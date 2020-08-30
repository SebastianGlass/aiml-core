package com.saxatus.aiml.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

class StrftimeTest
{

    private static final Date DATE = new Date(0);

    @Test
    void testCommonFormats()
    {
        assertEquals("01/01/1970", strftime("%d/%m/%Y").format(DATE));
        assertEquals("00:00", strftime("%H:%M").format(DATE));
        assertEquals("00:00:00", strftime("%T").format(DATE));
    }

    @Test
    void testStrangeInputs()
    {
        assertEquals("noop", strftime("noop").format(DATE));
        assertEquals("n01p", strftime("n%dp").format(DATE));
    }

    @Test
    void testModifies()
    {
        assertEquals("Thu Jan 1 00:00:00 1970", strftime("%c").format(DATE));
        assertEquals("Thu Jan 1 00:00:00 1970", strftime("%Ec").format(DATE));

    }

    private Strftime strftime(String s)
    {
        Strftime strftime = new Strftime(s, Locale.ENGLISH);

        strftime.setTimeZone(TimeZone.getTimeZone("UTC"));
        return strftime;
    }

}
