package com.soda.utils;

import org.testng.annotations.Test;

/**
 * @auhor david rapin
 */
public class MemoryUtilsTest
{
    @Test
    public void fixCoverage()
    {
        new MemoryUtils();
    }

    @Test
    public void memoryTest()
    {
        assert MemoryUtils.diagnostic() != null; 
    }
}
