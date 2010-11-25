package com.soda.utils;

/**
 * @author david rapin
 */
public class MathUtils
{

    protected MathUtils() {}

    /**
     * @param min the mininum returned value
     * @param max the maxinum returned value
     * @return a random integer between <code>min</code> and <code>max</code> (both included)
     */
    public static int randBetween(int min, int max)
    {
        if (max < min) throw new IllegalArgumentException("'max' < 'min' is not legal");
        return (int) (Math.random() * (max - min) + min);
    }

    public static long randBetween(long min, long max)
    {
        if (max < min) throw new IllegalArgumentException("'max' < 'min' is not legal");
        return (long) (Math.random()*(max - min) + min);
    }

    public static float randBetween(float min, float max)
    {
        if (max < min) throw new IllegalArgumentException("'max' < 'min' is not legal");
        return (float) (Math.random()*(max - min) + min);
    }

    /**
     * @param number a (potentially null) number
     * @return <code>0</code> if <code>number</code> is <code>null</code>,
     *         <code>number</code> itself otherwise.
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends Number> T ifNullZero(T number)
    {
        return (T) ((number == null) ? 0 : number);
    }

    public static boolean checkStrictRange(int value, int min, int max)
    {
        if (min > max) throw new IllegalArgumentException("'min' must be smaller than 'max'");
        return value > min && value < max;
    }

    public static boolean checkRange(int value, int min, int max)
    {
        if (min > max) throw new IllegalArgumentException("'min' must be smaller than 'max'");
        return value >= min && value <= max;
    }

    public static Number getAppropriateValueType(Number value, Class requestedValueType)
    {
        if (value == null || requestedValueType == null) return null;

        if (requestedValueType.equals(Integer.class) || requestedValueType.equals(Integer.TYPE))
        {
            return value.intValue();
        }
        else if (requestedValueType.equals(Long.class) || requestedValueType.equals(Long.TYPE))
        {
            return value.longValue();
        }
        else if (requestedValueType.equals(Double.class) || requestedValueType.equals(Double.TYPE))
        {
            return value.doubleValue();
        }
        else if (requestedValueType.equals(Float.class) || requestedValueType.equals(Float.TYPE))
        {
            return value.floatValue();
        }
        else if (requestedValueType.equals(Short.class) || requestedValueType.equals(Short.TYPE))
        {
            return value.shortValue();
        }
        else if (requestedValueType.equals(Byte.class) || requestedValueType.equals(Byte.TYPE))
        {
            return value.byteValue();
        }
        return null;
    }

    /**
     * @param start the first value to consider
     * @param n the number which we want to find a multiple of
     * @return the next multiple of <code>start</code> that is greater than <code>n</code>.
     */
    public static int nextMultipleOfN(int start, int n)
    {
        if (start % n == 0) return start;
        return start + n - (start % n) - (start > 0 ? 0 : n);
    }
}
