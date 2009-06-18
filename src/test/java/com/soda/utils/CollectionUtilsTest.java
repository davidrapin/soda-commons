package com.soda.utils;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author david rapin
 */
public class CollectionUtilsTest extends BaseTest
{

    @Test
    public void coverageFix() {
        new CollectionUtils();
    }
    
    @Test
    public void grep() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = CollectionUtils.grep(values, new CollectionUtils.Condition<Integer>() {
            public boolean keep(Integer element) {
                return element > 4 && element < 6;
            }
        });

        // different instances
        assert values != result;

        // expected content
        assert result.contains(5) && result.size() == 1;
    }

    @Test
    public void arrayContains() {
        String list1[] = null;
        String list2[] = new String[0];
        String list3[] = new String[] { "a", "b"};
        String list4[] = new String[4];
        String list5[] = new String[] { "a", "b", null};

        assert CollectionUtils.getlast(list1) == null;
        assert CollectionUtils.getlast(list2) == null;
        assert CollectionUtils.getlast(list3).equals("b");
        assert CollectionUtils.getlast(list4) == null;
        assert CollectionUtils.getlast(list5) == null;
    }

    @Test
    public void listContains() {
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
}
