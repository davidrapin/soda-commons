package com.soda.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

/**
 * Date: 24 mars 2010 at 01:51:55
 *
 * @author david
 */
public class RFC822DateParserTest
{
    @Test
    public void test()
    {
        String[] dates = new String[]
            {
                "Thu, 17 Dec 2009 00:03:00 +0100",
                "Wed, 02 Oct 2002 08:00:00 EST",
                "Wed, 02 Oct 2002 13:00:00 GMT",
                "Wed, 02 Oct 2002 15:00:00 +0200",
                "Wed, 04 Jan 2006 19:37 +0200"
            };

        assert RFC822DateParser.parse(dates[0]).equals(new DateTime(2009, 12, 17, 0, 3, 0, 0, DateTimeZone.forOffsetHours(1)).toDate());
        assert RFC822DateParser.parse(dates[1]).equals(new DateTime(2002, 10, 2, 8, 0, 0, 0, DateTimeZone.forID("EST")).toDate());
        assert RFC822DateParser.parse(dates[2]).equals(new DateTime(2002, 10, 2, 13, 0, 0, 0, DateTimeZone.forID("GMT")).toDate());
        assert RFC822DateParser.parse(dates[3]).equals(new DateTime(2002, 10, 2, 15, 0, 0, 0, DateTimeZone.forOffsetHours(2)).toDate());
        assert RFC822DateParser.parse(dates[4]).equals(new DateTime(2006, 1, 4, 19, 37, 0, 0, DateTimeZone.forOffsetHours(2)).toDate());
    }
}
