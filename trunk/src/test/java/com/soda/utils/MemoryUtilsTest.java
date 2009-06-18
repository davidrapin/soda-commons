package com.soda.utils;

import org.testng.annotations.Test;

/**
 * @author david rapin
 */
public class MemoryUtilsTest extends BaseTest
{

    @Test
    public void coverageFix() {
        new MemoryUtils();
    }

    @Test
    public void diagnostic() {
        assert !MemoryUtils.diagnostic().isEmpty();
    }
}
