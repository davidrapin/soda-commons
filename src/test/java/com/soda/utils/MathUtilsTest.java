package com.soda.utils;

import com.soda.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;


/**
 * @author david rapin
 */
public class MathUtilsTest extends BaseTest
{

    @Test
    public void coverageFix() {
        new MathUtils();
    }

    @Test
    public void ifNullZero() {
        // null-number to zero
        assert MathUtils.ifNullZero(null).equals(0);
        // non-null number to same number
        assert MathUtils.ifNullZero(123.456f).equals(123.456f);
    }

    @Test
    public void randBetween() {
        for (int i=0; i<1000; i++) {
            assert MathUtils.randBetween(12, 12) == 12;
            assert MathUtils.randBetween(12, 15) <= 15;
            assert MathUtils.randBetween(12, 15) >= 12;
        }
        for (int i=0; i<1000; i++) {
            assert MathUtils.randBetween(12F, 12F) == 12f;
            assert MathUtils.randBetween(12F, 15F) <= 15f;
            assert MathUtils.randBetween(12F, 15F) >= 12f;
        }
        for (int i=0; i<1000; i++) {
            assert MathUtils.randBetween(12L, 12L) == 12L;
            assert MathUtils.randBetween(12L, 15L) <= 15L;
            assert MathUtils.randBetween(12L, 15L) >= 12L;
        }
        
        try {
            MathUtils.randBetween(21, 12);
            assert false;
        } catch (Exception e) { /* empty */}

        try {
            MathUtils.randBetween(21F, 12F);
            assert false;
        } catch (Exception e) { /* empty */}

        try {
            MathUtils.randBetween(21L, 12L);
            assert false;
        } catch (Exception e) { /* empty */}
    }

    @Test
    public void testConvert()
    {
        List<? extends Class> n = Arrays.asList(Integer.class, Long.class, Double.class, Float.class, Short.class, Byte.class);

        for (Class nc  : n)
        {
            assert nc.isAssignableFrom(MathUtils.getAppropriateValueType(7, nc).getClass());
        }

        assert MathUtils.getAppropriateValueType(null, null) == null;
        assert MathUtils.getAppropriateValueType(1, null) == null;
        assert MathUtils.getAppropriateValueType(1, String.class) == null;
        assert MathUtils.getAppropriateValueType(null, Short.class) == null;
    }

    @Test
    public void testInterval()
    {
        assert  MathUtils.checkRange(1, 1, 3);
        assert  MathUtils.checkRange(2, 1, 3);
        assert  MathUtils.checkRange(3, 1, 3);
        assert !MathUtils.checkRange(0, 1, 3);
        assert !MathUtils.checkRange(4, 1, 3);

        assert !MathUtils.checkStrictRange(0, 1, 3);
        assert  MathUtils.checkStrictRange(2, 1, 3);
        assert !MathUtils.checkStrictRange(1, 1, 3);
        assert !MathUtils.checkStrictRange(3, 1, 3);
        assert !MathUtils.checkStrictRange(4, 1, 3);

        int failures = 0;
        try { MathUtils.checkRange(0, 2, 1); } catch (Exception e) { failures++; }
        try { MathUtils.checkStrictRange(0, 2, 1); } catch (Exception e) { failures++; }
        assert failures == 2;

    }

    @Test
    public void testNextMultipleOfN()
    {
        assertEquals(0, MathUtils.nextMultipleOfN(0, 12));
        assertEquals(12, MathUtils.nextMultipleOfN(10, 12));
        assertEquals(12, MathUtils.nextMultipleOfN(12, 12));
        assertEquals(10, MathUtils.nextMultipleOfN(10, 5));
        assertEquals(15, MathUtils.nextMultipleOfN(11, 5));
        assertEquals(120, MathUtils.nextMultipleOfN(61, 60));

        assertEquals(-96, MathUtils.nextMultipleOfN(-100, 12));
        assertEquals(-48, MathUtils.nextMultipleOfN(-50, 12));
        assertEquals(-12, MathUtils.nextMultipleOfN(-13, 12));
        assertEquals(0, MathUtils.nextMultipleOfN(-5, 12));
    }

}
