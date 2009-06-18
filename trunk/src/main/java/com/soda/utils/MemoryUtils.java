package com.soda.utils;

/**
 * User: david rapin
 * Date: 2 juin 2009
 * Time: 16:32:02
 */
public class MemoryUtils {

    protected MemoryUtils() {}

    /**
     * @return the total amount of memory that is currently used, in bytes
     */
    public static long usedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * @return  the total amount of memory currently available for current
     *          and future objects, measured in bytes.
     */
    public static long allocatedMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     *
     * @return  the maximum amount of memory that the virtual machine will
     *          attempt to use, measured in bytes
     */
    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static String diagnostic() {
        return "used: " + (usedMemory()/1024)
                + "kB - allocated: " + (allocatedMemory()/1024)
                + "kB - max: " + (maxMemory()/1024) + "kB";
    }


}
