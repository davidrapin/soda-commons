package com.soda.utils;

import java.util.Date;
import java.util.Calendar;

/**
 * User: david rapin
 * Date: 2 juin 2009
 * Time: 16:32:02
 */
public class DateUtils {
    
    protected DateUtils() {}

    /**
     * @param date1 a date
     * @param date2 another date
     * @return <code>true</code> of <code>date1</code> and <code>date2</code> are in the same year,
     *         <code>false</code> otherwise.
     */
    public static boolean sameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) throw new  IllegalArgumentException("'date1' or 'date2' was null");

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
    public static boolean sameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) throw new  IllegalArgumentException("'date1' or 'date2' was null");

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
    public static Date getBeginningOfYear() {
        return getBeginningOfYear(null);
    }

    /**
     * @param date a date (if null, 'now' is assumed)
     * @return the first date of the year of <code>code</code> (i.e. 01/01/date.year @ 00:00:00)
     */
    public static Date getBeginningOfYear(Date date) {
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
    public static Date getMidnight(Date date) {
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
     * @param year a year
     * @param month a month
     * @param day a day in <code>month</code>
     * @return a new date for the given day/month/year
     */
    public static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
