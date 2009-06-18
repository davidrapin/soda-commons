package com.soda.utils;

/**
 * User: david rapin
 * Date: 2 juin 2009
 * Time: 17:09:15
 */
public class MathUtils {

    protected MathUtils() {}

    /**
     * @param min the mininum returned value
     * @param max the maxinum returned value
     * @return a random integer between <code>min</code> and <code>max</code> (both included) 
     */
    public static int randBetween(int min, int max) {
        if (max < min) throw new IllegalArgumentException("'max' < 'min' is not legal");
        return (int) (Math.random()*(max - min) + min);
    }

    /**
     * @param number a (potentially null) number
     * @return <code>0</code> if <code>number</code> is <code>null</code>,
     *         <code>number</code> itself otherwise.
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends Number> T ifNullZero(T number) {
        return (T) ((number == null) ? 0 : number);
    }
}
