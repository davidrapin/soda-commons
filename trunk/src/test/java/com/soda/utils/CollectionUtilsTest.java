package com.soda.utils;

import com.soda.BaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author david rapin
 */
public class CollectionUtilsTest extends BaseTest
{

    @Test
    public void coverageFix()
    {
        new CollectionUtils();
    }

    @SuppressWarnings({"NullArgumentToVariableArgMethod"})
    @Test
    public void hasNullTest()
    {
        assert !CollectionUtils.hasNull(null);
        assert CollectionUtils.hasNull(null, null);
        assert !CollectionUtils.hasNull(1, 2, 3);
        assert CollectionUtils.hasNull(1, "bla", 2, "true", 3, null);
        assert CollectionUtils.hasNull(null, 1, "bla", 2, "true", 3);
    }

    @Test
    public void grep()
    {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = CollectionUtils.grep(values, new CollectionUtils.Condition<Integer>()
        {
            public boolean keep(Integer element)
            {
                return element > 4 && element < 6;
            }
        });

        // different instances
        assert values != result;

        // expected content
        assert result.contains(5) && result.size() == 1;
    }

    @Test
    public void arrayContains()
    {
        String list1[] = null;
        String list2[] = new String[0];
        String list3[] = new String[]{"a", "b"};
        String list4[] = new String[4];
        String list5[] = new String[]{"a", "b", null};

        assert CollectionUtils.getlast(list1) == null;
        assert CollectionUtils.getlast(list2) == null;
        assert CollectionUtils.getlast(list3).equals("b");
        assert CollectionUtils.getlast(list4) == null;
        assert CollectionUtils.getlast(list5) == null;
    }

    @Test
    public void listContains()
    {
        List<String> list1 = null;
        List<String> list2 = new ArrayList<String>(0);
        List<String> list3 = Arrays.asList("a", "b");
        List<String> list4 = new ArrayList<String>(4);
        List<String> list5 = Arrays.asList("a", "b", null);

        assert CollectionUtils.getlast(list1) == null;
        assert CollectionUtils.getlast(list2) == null;
        assert CollectionUtils.getlast(list3).equals("b");
        assert CollectionUtils.getlast(list4) == null;
        assert CollectionUtils.getlast(list5) == null;
    }

    @Test
    public void mapTest()
    {
        List<String> l = Arrays.asList("1", "2", "3");
        List<Integer> r = CollectionUtils.map(l, new CollectionUtils.Operation<String, Integer>()
        {
            public Integer applyTo(String s)
            {
                return Integer.valueOf(s) * 2;
            }
        });

        assert CollectionUtils.map(null, null) == null;

        boolean failed = false;
        try { CollectionUtils.map(l, null); }
        catch (Exception e) { failed = true; }
        assert failed;

        assert CollectionUtils.map(null, new CollectionUtils.Operation<Object, Object>()
        {
            public Object applyTo(Object s)
            {
                return 123;
            }
        }) == null;

        assert r.get(0).equals(2);
        assert r.get(1).equals(4);
        assert r.get(2).equals(6);
    }

    @Test
    public void repeatTest()
    {
        List<String> s = CollectionUtils.repeat("456", 100);
        for (String value : s) assert value.equals("456");
    }

    @Test
    public void asMapTest()
    {
        List<Integer> l = Arrays.asList(4, 5, 6);
        Map<String, Integer> map = CollectionUtils.asMap(l, new CollectionUtils.AttributeReader<Integer, String>()
        {
            public String getAttribute(Integer object)
            {
                return "k" + object;
            }
        });

        assert CollectionUtils.asMap(null, null) == null;

        boolean failed = false;
        try { CollectionUtils.asMap(l, null); }
        catch (Exception e) { failed = true; }
        assert failed;

        assert map.get("k4").equals(4);
        assert map.get("k5").equals(5);
        assert map.get("k6").equals(6);
    }
}
