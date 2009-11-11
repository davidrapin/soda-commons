package com.soda;

import org.apache.log4j.Logger;

/**
 * @author david rapin
 */
public abstract class BaseTest
{
    private static final Logger log = Logger.getLogger(BaseTest.class);

    /**
     * <p>Logs errors in a nice short way (log the given error and returns false).
     * <p>To be used like follows:<br>
     * <code>assert mayFail() || logError("mayFail() failed");</code>
     *
     * @param error the error to log
     * @return false
     */
    protected boolean logError(String error)
    {
        log.error(error);
        return false;
    }

}
