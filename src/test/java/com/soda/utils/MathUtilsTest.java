package com.soda.utils;

import org.testng.annotations.Test;


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
        
        boolean ex = false;
        try {
            MathUtils.randBetween(21, 12);
        } catch (Exception e) {
            ex = true;
        }
        assert ex;
    }

}
