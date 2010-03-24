package com.soda.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Date: 24 mars 2010 at 01:47:19
 *
 * @author david
 */
public class RFC822DateParser
{
    public static final SimpleDateFormat rfc822DateFormats[] = new SimpleDateFormat[]
        {
            new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.US),
        };

    public static Date parse(String date)
    {
        if (date == null) return null;
        for (SimpleDateFormat df : rfc822DateFormats)
        {
            Date result;
            try
            {
                result = df.parse(date);
            }
            catch (ParseException e)
            {
                result = null;
            }
            if (result != null) return result;
        }
        return null;
    }
}
