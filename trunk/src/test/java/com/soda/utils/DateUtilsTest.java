package com.soda.utils;

import com.soda.BaseTest;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

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
}
