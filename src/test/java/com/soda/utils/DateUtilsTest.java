package com.soda.utils;

import com.soda.BaseTest;
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
    public void coverageFix() {
        new DateUtils();
    }

    @Test
    public void getMidnight() {
        Calendar c = Calendar.getInstance();
        // month index in one more than natural month number
        c.set(2008, 9, 21, 14, 30, 10);
        Date d = c.getTime();

        Date r = DateUtils.getDate(2008, 10, 21);

        assert DateUtils.getMidnight(d).equals(r);

        // ex
        boolean ex = false;
        try {
            DateUtils.getMidnight(null);
        } catch (Exception e) {
            ex = true;
        }
        assert ex;
    }

    @Test
    public void sameYear() {
        assert DateUtils.sameYear(DateUtils.getDate(2009, 1, 1), DateUtils.getDate(2009, 12, 31));
        assert DateUtils.sameYear(DateUtils.getDate(2008, 12, 31), DateUtils.getDate(2008, 1, 1));
        assert !DateUtils.sameYear(DateUtils.getDate(2008, 12, 31), DateUtils.getDate(2009, 1, 1));

        // ex
        int ex = 0;
        try { DateUtils.sameYear(new Date(), null); } catch (Exception e) { ex++; }
        try { DateUtils.sameYear(null, new Date()); } catch (Exception e) { ex++; }
        try { DateUtils.sameYear(null, null);       } catch (Exception e) { ex++; }
        assert ex == 3;
    }

    @Test
    public void sameDay() {
        assert DateUtils.sameDay(DateUtils.getDate(2009, 1, 1), DateUtils.getDate(2009, 1, 1));
        assert DateUtils.sameDay(DateUtils.getDate(2010, 12, 12), DateUtils.getDate(2010, 12, 12));
        assert !DateUtils.sameDay(DateUtils.getDate(2010, 12, 12), DateUtils.getDate(2010, 12, 13));

        // that one is tricky. 2004-02-30 does not exist. it is 2004-03-01.
        assert DateUtils.sameDay(DateUtils.getDate(2004, 2, 30), DateUtils.getDate(2004, 3, 1));

        // ex
        int ex = 0;
        try { DateUtils.sameDay(new Date(), null); } catch (Exception e) { ex++; }
        try { DateUtils.sameDay(null, new Date()); } catch (Exception e) { ex++; }
        try { DateUtils.sameDay(null, null);       } catch (Exception e) { ex++; }
        assert ex == 3;
    }

    @Test
    public void beginningOfYear() {
        Calendar c = Calendar.getInstance();
        c.set(2008, 9, 21, 14, 30, 10);
        Date d = c.getTime();

        Date r = DateUtils.getDate(2008, 1, 1);

        assert DateUtils.getBeginningOfYear(d).equals(r);
    }

    @Test
    public void beginningOfCurrentYear() {
        assert DateUtils.sameYear(new Date(), DateUtils.getBeginningOfYear());
    }

    @Test()
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
        float diff_d = DateUtils.dateDiffInDays(d1,d2);
        // diff in weeks
        float diff_w = DateUtils.dateDiffInWeeks(d1,d2);

        System.out.println("date1:" + d1 + " - date2:" + d2 + " - diff in days:" + diff_d + " - diff in weeks:" + diff_w );

        assert (diff_d == 10F);
        assert (diff_w == 10F/7F);
    }

    @Test()
    public void weeksInYear()
    {
        assert DateUtils.weeksInYear(2003) == 52;
        assert DateUtils.weeksInYear(2004) == 53;
        assert DateUtils.weeksInYear(2008) == 52;
        assert DateUtils.weeksInYear(2009) == 53;
    }

    @Test()
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
        Date now = new Date();
        Date d = DateUtils.newDateFrom(now, Calendar.DAY_OF_YEAR, -10);
        assert DateUtils.durationToNow(d).equals("1 week and 3 days");
        assert DateUtils.durationToNow(now).equals("moments");
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

        for (Date check = days.get(0); check.before(now);  check = DateUtils.newDateFrom(check, Calendar.DAY_OF_YEAR, 1), i++)
        {
            assert days.get(i).equals(check);
        }
    }
}
