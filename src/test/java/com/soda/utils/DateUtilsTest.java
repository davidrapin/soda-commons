package com.soda.utils;

import com.soda.BaseTest;
import org.joda.time.DateTime;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author david rapin
 */
public class DateUtilsTest extends BaseTest
{

    @Test
    public void coverageFix()
    {
        new DateUtils();
    }

    @Test
    public void getMidnight()
    {
        Calendar c = Calendar.getInstance();
        // month index in one more than natural month number
        c.set(2008, 9, 21, 14, 30, 10);
        Date d = c.getTime();

        Date r = DateUtils.getDate(2008, 10, 21);

        assert DateUtils.getMidnight(d).equals(r);

        // ex
        boolean ex = false;
        try
        {
            DateUtils.getMidnight(null);
        }
        catch (Exception e)
        {
            ex = true;
        }
        assert ex;
    }

    @Test
    public void sameYear()
    {
        assert DateUtils.sameYear(DateUtils.getDate(2009, 1, 1), DateUtils.getDate(2009, 12, 31));
        assert DateUtils.sameYear(DateUtils.getDate(2008, 12, 31), DateUtils.getDate(2008, 1, 1));
        assert !DateUtils.sameYear(DateUtils.getDate(2008, 12, 31), DateUtils.getDate(2009, 1, 1));

        // ex
        int ex = 0;
        try { DateUtils.sameYear(new Date(), null); } catch (Exception e) { ex++; }
        try { DateUtils.sameYear(null, new Date()); } catch (Exception e) { ex++; }
        try { DateUtils.sameYear(null, null); } catch (Exception e) { ex++; }
        assert ex == 3;
    }

    @Test
    public void sameDay()
    {
        assert DateUtils.sameDay(DateUtils.getDate(2009, 1, 1), DateUtils.getDate(2009, 1, 1));
        assert DateUtils.sameDay(DateUtils.getDate(2010, 12, 12), DateUtils.getDate(2010, 12, 12));
        assert !DateUtils.sameDay(DateUtils.getDate(2010, 12, 12), DateUtils.getDate(2010, 12, 13));

        // that one is tricky. 2004-02-30 does not exist. it is 2004-03-01.
        assert DateUtils.sameDay(DateUtils.getDate(2004, 2, 30), DateUtils.getDate(2004, 3, 1));

        // ex
        int ex = 0;
        try { DateUtils.sameDay(new Date(), null); } catch (Exception e) { ex++; }
        try { DateUtils.sameDay(null, new Date()); } catch (Exception e) { ex++; }
        try { DateUtils.sameDay(null, null); } catch (Exception e) { ex++; }
        assert ex == 3;
    }

    @Test
    public void beginningOfYear()
    {
        Calendar c = Calendar.getInstance();
        c.set(2008, 9, 21, 14, 30, 10);
        Date d = c.getTime();

        Date r = DateUtils.getDate(2008, 1, 1);

        assert DateUtils.getBeginningOfYear(d).equals(r);
    }

    @Test
    public void beginningOfCurrentYear()
    {
        assert DateUtils.sameYear(new Date(), DateUtils.getBeginningOfYear());
    }

    @Test
    public void dateDiff()
    {
        // set a date 'D'
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(1225636958785L);

        // store: 10 days before 'D'
        Date d1 = c1.getTime();
        c1.add(Calendar.DAY_OF_YEAR, 10);

        // store: 'D'
        Date d2 = c1.getTime();

        // diff in days
        float diff_d = DateUtils.dateDiffInDays(d1, d2);
        // diff in weeks
        float diff_w = DateUtils.dateDiffInWeeks(d1, d2);

        System.out.println("date1:" + d1 + " - date2:" + d2 + " - diff in days:" + diff_d + " - diff in weeks:" + diff_w);

        assert (diff_d == 10F);
        assert (diff_w == 10F / 7F);
        assert DateUtils.distanceToNowInDaysIgnoreTime(new Date()) >= 0;
        assert DateUtils.distanceToNowInDaysIgnoreTime(new Date()) < 0.00001;

        DateTime dt1 = new DateTime(2010, 3, 31, 6, 15, 0, 0);
        DateTime dt2 = new DateTime(2010, 3, 31, 22, 45, 0, 0);
        DateTime dt3 = new DateTime(2010, 4, 1, 3, 0, 0, 0);
        assert DateUtils.dateDiffInDaysIgnoreTime(dt1.toDate(), dt2.toDate()) == 0;
        assert DateUtils.dateDiffInDaysIgnoreTime(dt1.toDate(), dt3.toDate()) == 1;
    }

    @Test
    public void dateDiffIgnoreTime()
    {
        Date d1_a = DateUtils.getDate(2009, 12, 31, 8, 0, 0);
        Date d1_b = DateUtils.getDate(2009, 12, 31, 14, 0, 0);

        Date d2_a = DateUtils.getDate(2010, 1, 1, 7, 0, 0);
        Date d2_b = DateUtils.getDate(2010, 1, 1, 15, 0, 0);

        Date d3 = DateUtils.getDate(2010, 1, 1, 15, 30, 20);
        Date d4 = DateUtils.getDate(2010, 2, 1, 19, 20, 10);

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_a, d1_a) == 0;
        assert DateUtils.dateDiffInDaysIgnoreTime(d1_b, d1_b) == 0;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_a, d2_a) == 0;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_b, d2_b) == 0;

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_a, d1_b) == 0;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_a, d2_b) == 0;

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_a, d2_a) == 1;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_a, d1_a) == -1;

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_a, d2_b) == 1;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_a, d1_b) == -1;

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_b, d2_a) == 1;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_b, d1_a) == -1;

        assert DateUtils.dateDiffInDaysIgnoreTime(d1_b, d2_b) == 1;
        assert DateUtils.dateDiffInDaysIgnoreTime(d2_b, d1_b) == -1;

        assert DateUtils.dateDiffInDaysIgnoreTime(d3, d4) == 31;
        assert DateUtils.dateDiffInDaysIgnoreTime(d4, d3) == -31;
    }

    @Test
    public void mostRecentTest()
    {
        Date d1 = DateUtils.getDate(2010, 1, 31);
        Date d2 = DateUtils.getDate(2010, 1, 31);
        assert DateUtils.getMostRecent(d1, d2).equals(d1);

        d1 = DateUtils.getDate(2010, 1, 31);
        d2 = null;
        assert DateUtils.getMostRecent(d1, d2).equals(d1);
        assert DateUtils.getMostRecent(d2, d1).equals(d1);

        d1 = null;
        d2 = null;
        assert DateUtils.getMostRecent(d1, d2) == null;

        d1 = DateUtils.getDate(2010, 1, 31);
        d2 = DateUtils.getDate(2010, 1, 30);
        assert DateUtils.getMostRecent(d1, d2).equals(d1);

        d1 = DateUtils.getDate(2009, 1, 31);
        d2 = DateUtils.getDate(2010, 1, 31);
        assert DateUtils.getMostRecent(d1, d2).equals(d2);
    }

    @Test
    public void weeksInYear()
    {
        assert DateUtils.weeksInYear(2003) == 52;
        assert DateUtils.weeksInYear(2004) == 53;
        assert DateUtils.weeksInYear(2008) == 52;
        assert DateUtils.weeksInYear(2009) == 53;
    }

    @Test
    public void daysInYear()
    {
        assert DateUtils.daysInYear(2003) == 365;
        assert DateUtils.daysInYear(2004) == 366;
        assert DateUtils.daysInYear(2008) == 366;
        assert DateUtils.daysInYear(2009) == 365;
    }

    @Test
    public void datePush()
    {
        Date d1 = DateUtils.parseDate("31/12/2008");
        Date d2 = DateUtils.newDateFrom(d1, Calendar.DAY_OF_YEAR, 1);
        assert DateUtils.formatDate(d2).equals("01/01/2009");

        d1 = DateUtils.parseDate("28/02/2003");
        d2 = DateUtils.newDateAtDays(d1, 1);
        assert DateUtils.formatDate(d2).equals("01/03/2003");

        d1 = DateUtils.parseDate("28/02/2003");
        d2 = DateUtils.newDateFrom(d1, Calendar.YEAR, 1);
        assert DateUtils.formatDate(d2).equals("28/02/2004");
    }

    @Test
    public void testFormatAndParse()
    {
        String d = "31/12/2003";
        assert DateUtils.formatDate(DateUtils.parseDate(d)).equals(d);
        assert DateUtils.formatDateTime(DateUtils.parseDate(d)).equals(d + " 00:00:00");
        assert DateUtils.parseDate("a2a3a") == null;
    }

    @Test
    public void testDurationToNow()
    {
        DateTime ref = new DateTime();
        DateTime refMin45s = ref.minusSeconds(45);
        DateTime refMin1HourAnd10s = ref.minusHours(1).minusSeconds(10);
        DateTime refMin10Days = ref.minusDays(10);

        assertEquals(DateUtils.durationToDate(ref, refMin10Days), "1 week and 3 days");
        assertEquals(DateUtils.durationToDate(ref, refMin1HourAnd10s), "1 hour and 10 seconds");
        assertEquals(DateUtils.durationToDate(ref, refMin45s), "45 seconds");
        assertEquals(DateUtils.durationToDate(ref, ref), "moments");
    }

    @Test
    public void testDaysSince()
    {
        Date now = new Date();
        Date start = DateUtils.newDateFrom(now, Calendar.DAY_OF_YEAR, -15);
        List<Date> days = DateUtils.daysSince(start);
        int i = 0;

        // all days, plus today
        assert days.size() == 16;

        for (Date check = days.get(0); check.before(now); check = DateUtils.newDateFrom(check, Calendar.DAY_OF_YEAR, 1), i++)
        {
            assert days.get(i).equals(check);
        }
    }

    @Test
    public void ageAtDate()
    {
        Date now = new Date();
        assert DateUtils.getSmallestDateOfBirthForAge(1).before(now);
        assert DateUtils.getHighestDateOfBirthForAge(1).before(now);
        assert DateUtils.getHighestDateOfBirthForAge(0).equals(DateUtils.getMidnight(now));

        // celui qui est né le 28/01/1984 aura 26 ans le 28/01/2010. le 27/01/2010 il a encore 25 ans
        assert DateUtils.getSmallestDateOfBirthForAgeAt(25, DateUtils.getDate(2010, 1, 27)).equals(DateUtils.getDate(1984, 1, 28));
        // celui qui est né le 27/01/1985 a 25 ans le 27/01/2010
        assert DateUtils.getHighestDateOfBirthForAgeAt(25, DateUtils.getDate(2010, 1, 27)).equals(DateUtils.getDate(1985, 1, 27));

        // celui qui est né le 01/08/2004 aura 11 ans le 01/08/2015. le 31/07/2015 il a encore 10 ans
        assert DateUtils.getSmallestDateOfBirthForAgeAt(10, DateUtils.getDate(2015, 7, 31)).equals(DateUtils.getDate(2004, 8, 1));
        // celui qui est né le 31/07/2005 a 10 ans le 31/07/2015
        assert DateUtils.getHighestDateOfBirthForAgeAt(10, DateUtils.getDate(2015, 7, 31)).equals(DateUtils.getDate(2005, 7, 31));
    }
}
