package com.soda.utils;

import com.soda.BaseTest;
import org.testng.annotations.Test;

import java.util.*;

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

        assert map.size() == 3;
        assert map.get("k4").equals(4);
        assert map.get("k5").equals(5);
        assert map.get("k6").equals(6);
    }

    @Test
    public void asMapTest2()
    {
        List<Integer> l = Arrays.asList(5, 9, 9, 6);

        Map<String, Integer> map = CollectionUtils.asMap(l, new CollectionUtils.AttributeReader<Integer, String>()
        {
            public String getAttribute(Integer object)
            {
                return "k" + object;
            }
        });

        assert map.size() == 3;
        assert map.get("k5").equals(5);
        assert map.get("k9").equals(9);
        assert map.get("k6").equals(6);
    }

    @Test
    public void asListMapTest()
    {
        List<Integer> l = Arrays.asList(5, 9, 9, 90, 6);

        Map<String, List<Integer>> map = CollectionUtils.asListMap(l, new CollectionUtils.AttributeReader<Integer, String>()
        {
            public String getAttribute(Integer object)
            {
                if (object > 10) return "k" + (object / 10);
                return "k" + object;
            }
        });

        assert map.size() == 3;

        assert map.get("k5").size() == 1;
        assert map.get("k5").contains(5);

        assert map.get("k9").size() == 3;
        assert map.get("k9").contains(9);
        assert map.get("k9").contains(90);

        assert map.get("k6").size() == 1;
        assert map.get("k6").contains(6);
    }

    @Test
    public void testIntersection()
    {
        Set<Integer> sa = new HashSet<Integer>();
        Set<Integer> sb = new HashSet<Integer>();

        assert CollectionUtils.intersection(sa, sb).isEmpty();
        assert CollectionUtils.intersection(sa, null).isEmpty();
        assert CollectionUtils.intersection(null, sa).isEmpty();
        assert CollectionUtils.intersection(null, null).isEmpty();

        sa.add(1);
        sa.add(9);
        sa.add(7);
        sa.add(14);
        sa.add(6);
        sa.add(1492);
        sa.add(-800);
        sa.add(3);
        sa.add(256);

        sb.add(1492);  // <-
        sb.add(2);
        sb.add(27);
        sb.add(45);
        sb.add(5);
        sb.add(1); // <-
        sb.add(1);
        sb.add(14);  // <-
        sb.add(6);  // <-

        assert CollectionUtils.intersection(sa, sa).equals(sa);
        assert CollectionUtils.intersection(sb, sb).equals(sb);

        Set<Integer> inter = CollectionUtils.intersection(sa, sb);
        Set<Integer> inter2 = CollectionUtils.intersection(sb, sa);
        assert inter.equals(inter2);

        assert inter.size() == 4;
        assert inter.contains(1);
        assert inter.contains(1492);
        assert inter.contains(14);
        assert inter.contains(6);
    }
}
