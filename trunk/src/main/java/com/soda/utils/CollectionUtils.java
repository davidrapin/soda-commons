package com.soda.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * @author david rapin
 */
public class CollectionUtils
{

    protected CollectionUtils() {}

    public static <T> List<T> grep(List<T> list, Condition<T> condition)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list)
        {
            if (condition.keep(element))
            {
                newList.add(element);
            }
        }
        return newList;
    }

    public static interface Condition<U>
    {
        public boolean keep(U element);
    }

    /**
     * @param array an array (may be null or empty)
     * @return the last element of <code>array</code> if any, <code>null</code> otherwise.
     */
    public static <T> T getlast(T[] array)
    {
        return array == null || array.length == 0 ? null : array[array.length - 1];
    }

    /**
     * @param list a list (may be null or empty)
     * @return the last element of <code>list</code> if any, <code>null</code> otherwise.
     */
    public static <T> T getlast(List<T> list)
    {
        return list == null || list.size() == 0 ? null : list.get(list.size() - 1);
    }
}
