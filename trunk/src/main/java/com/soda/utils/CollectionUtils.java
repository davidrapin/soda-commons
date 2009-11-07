package com.soda.utils;

import java.util.*;

/**
 * @author david rapin
 */
public class CollectionUtils
{

    protected CollectionUtils() {}

    /**
     *
     * @param objects a list of elements
     * @return true if the given list contains a null value. false if the list is null or if it contains no null element
     */
    public static boolean hasNull(Object... objects)
    {
        if (objects == null) return false;
        for (Object o : objects)
        {
            if (o == null) return true;
        }
        return false;
    }

    public static <T> List<T> grep(final List<T> list, final Condition<T> condition)
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
    public static <T> T getlast(final T[] array)
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

    /**
     * @param list      a list from wich we would like to create a map
     * @param keyReader an attribute reader that will return the object to use as a key for the map for each element
     * @return a map of objects of type <code>V<code> indexed by the objects returned by the <code>keyReader</code>
     */
    public static <K, V> Map<K, V> asMap(List<V> list, AttributeReader<V, K> keyReader)
    {
        if (list == null) return null;
        if (keyReader == null) throw new IllegalArgumentException("'keyReader' should not be null");

        Map<K, V> map = new HashMap<K, V>(list.size());
        for (V item : list)
        {
            map.put(keyReader.getAttribute(item), item);
        }
        return map;
    }

    public static interface AttributeReader<O, V>
    {
        V getAttribute(O object);
    }

    /**
     * a very space+time effectvie way to construct a (read-only) List of a repeated value.
     *
     * @param value the value we want repreated in a list
     * @param times the size of the desired list
     * @return a list containing <code>value</code> <code>times</code> times.
     */
    public static <T> List<T> repeat(final T value, final int times)
    {
        return new AbstractList<T>()
        {
            public T get(int i)
            {
                return value;
            }

            public int size()
            {
                return times;
            }
        };
    }

    /**
     * applies an operation to all items in a list, result the list of results
     *
     * @param sources   the list of source objects to convert
     * @param operation an opteration that takes a source object as an argument and return a result object
     * @return the list of all converted object
     */
    public static <S, T> List<T> map(final List<S> sources, Operation<S, T> operation)
    {
        if (sources == null) return null;
        if (operation == null) throw new IllegalArgumentException("'operation' should not be null");

        List<T> results = new ArrayList<T>();

        for (S source : sources) results.add(operation.applyTo(source));

        return results;
    }

    public static interface Operation<SOURCE, RESULT>
    {
        RESULT applyTo(SOURCE s);
    }

}
