package com.soda.utils;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author david rapin
 */
public class DateUtils
{
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static final long ONE_DAY_IN_MILLIS = 60*60*24*1000;
    public static final long ONE_WEEK_IN_MILLIS = ONE_DAY_IN_MILLIS*7;

    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat dateTimeFormat;


    protected DateUtils() {}

    /**
     * @param date1 a date
     * @param date2 another date
     * @return <code>true</code> of <code>date1</code> and <code>date2</code> are in the same year,
     *         <code>false</code> otherwise.
     */
    public static boolean sameYear(Date date1, Date date2)
    {
        if (date1 == null || date2 == null) throw new IllegalArgumentException("'date1' or 'date2' was null");

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    /**
     * @param date1 a date
     * @param date2 another date
     * @return <code>true</code> of <code>date1</code> and <code>date2</code> are in the same day,
     *         <code>false</code> otherwise.
     */
    public static boolean sameDay(Date date1, Date date2)
    {
        if (date1 == null || date2 == null) throw new IllegalArgumentException("'date1' or 'date2' was null");

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
               && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * @return the first date of the current year (i.e. 01/01/XXXX @ 00:00:00)
     */
    public static Date getBeginningOfYear()
    {
        return getBeginningOfYear(null);
    }

    /**
     * @param date a date (if null, 'now' is assumed)
     * @return the first date of the year of <code>code</code> (i.e. 01/01/date.year @ 00:00:00)
     */
    public static Date getBeginningOfYear(Date date)
    {
        Calendar c = Calendar.getInstance();

        // if date is null, "c" is the current date
        if (date != null) c.setTime(date);

        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @param date a date
     * @return a new date with same day, month and year as <code>date</code>,
     *         but with hours, minutes and seconds set to zero.
     */
    public static Date getMidnight(Date date)
    {
        if (date == null) throw new IllegalArgumentException("'date' was null");

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @param year  a year
     * @param month a month
     * @param day   a day in <code>month</code>
     * @return a new date for the given day/month/year
     */
    public static Date getDate(int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @return the default date format in europe.
     * @param date: the date to format
     */
    public static String formatDate(Date date)
    {
        checkDateFormat();
        return dateFormat.format(date);
    }

    /**
     * @return the default dateTime format in europe.
     * @param date: the date to format
     */
    public static String formatDateTime(Date date)
    {
        if (dateTimeFormat == null)
        {
            dateTimeFormat = new SimpleDateFormat(DATETIME_FORMAT);
        }
        return dateTimeFormat.format(date);
    }


    public static int weeksInYear(int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }

    public static int daysInYear(int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        return c.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static Date newDateAtDays(Date reference, int distance)
    {
        return newDateFrom(reference, Calendar.DAY_OF_YEAR, distance);
    }

    public static Date newDateFrom(Date reference, int field, int distance)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(reference);
        c.add(field, distance);
        return c.getTime();
    }

    public static List<Date> daysSince(Date since)
    {
        List<Date> list = new ArrayList<Date>();
        Date today = getMidnight(new Date());

        for (Date d = getMidnight(since); d.before(today) || d.equals(today); d = newDateFrom(d, Calendar.DAY_OF_YEAR, 1))
        {
            list.add(d);
        }

        return list;
    }

    /**
     * @param date the beginning of the intervalle
     * @return an (english) string representation of duration from <code>date</code> to now.
     */
    public static String durationToNow(Date date)
    {
        Interval interval = new Interval(new DateTime(date), new DateTime());
        Period p = interval.toPeriod();
        //System.out.println(">" + date + " - " + new Date() + " --- " + p.getSeconds());

        String years   = p.getYears()   == 0 ? "" : p.getYears()   == 1 ? "1 year "   : p.getYears()   + " years ";
        String months  = p.getMonths()  == 0 ? "" : p.getMonths()  == 1 ? "1 month "  : p.getMonths()  + " months ";
        String weeks   = p.getWeeks()   == 0 ? "" : p.getWeeks()   == 1 ? "1 week "   : p.getWeeks()   + " weeks ";
        String days    = p.getDays()    == 0 ? "" : p.getDays()    == 1 ? "1 day "    : p.getDays()    + " days ";
        String hours   = p.getHours()   == 0 ? "" : p.getHours()   == 1 ? "1 hour "   : p.getHours()   + " hours ";
        String minutes = p.getMinutes() == 0 ? "" : p.getMinutes() == 1 ? "1 minute " : p.getMinutes() + " minutes ";
        String seconds = p.getSeconds() == 0 ? "" : p.getSeconds() == 1 ? "1 second " : p.getSeconds() + " seconds ";

        List<String> fields = Arrays.asList(years, months, weeks, days, hours, minutes, seconds);
        String result = "";
        Integer lastNonNullField = null;
        for (int i=fields.size()-1; i>=0; i--)
        {
            String field = fields.get(i);
            if (lastNonNullField == null && !field.isEmpty()) lastNonNullField = i;
            result = field + (lastNonNullField != null && lastNonNullField == i+1 ? "and " : "") + result;
        }

        return interval.toDurationMillis() < 1000  ? "moments" : StringUtils.chop(result, ' ');
    }

    /**
     * computes (dateEnd - dateStart) in days
     * @param dateStart the beginning of the intervalle
     * @param dateEnd the end of the inervalle
     * @return the length of the intervalle in days
     */
    public static float dateDiffInDays(Date dateStart, Date dateEnd)
    {
        return ((float) (dateEnd.getTime() - dateStart.getTime()))/ONE_DAY_IN_MILLIS;
    }

    public static float dateDiffInWeeks(Date dateStart, Date dateEnd)
    {
        return ((float) (dateEnd.getTime() - dateStart.getTime()))/ONE_WEEK_IN_MILLIS;
    }

    /**
     * @param date the string representation fo a date, formatted according to DateUtils.DATE_FORMAT
     * @return the converted date obect, or null in case of failure
     */
    public static Date parseDate(String date)
    {
        checkDateFormat();

        try
        {
            return dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    private static void checkDateFormat()
    {
        if (dateFormat == null)
        {
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
    }

    public static Date getSmallestDateOfBirthForAge(int ageInYears)
    {
        return getSmallestDateOfBirthForAgeAt(ageInYears, new Date());
    }

    protected static Date getSmallestDateOfBirthForAgeAt(int ageInYears, Date dateOfComputation)
    {
        if (ageInYears < 0) throw new IllegalArgumentException("'ageInYears' cannot be smaller than zero");
        return getMidnight(new DateTime(dateOfComputation).minusYears(ageInYears + 1).plusDays(1).toDate());
    }

    public static Date getHighestDateOfBirthForAge(int ageInYears)
    {
        return getHighestDateOfBirthForAgeAt(ageInYears, new Date());
    }

    protected static Date getHighestDateOfBirthForAgeAt(int ageInYears, Date dateOfComputation)
    {
        if (ageInYears < 0) throw new IllegalArgumentException("'ageInYears' cannot be smaller than zero");
        return getMidnight(new DateTime(dateOfComputation).minusYears(ageInYears).toDate());
    }
}
