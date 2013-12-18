package org.kuali.student.common.test.util;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Helps create a dynamic list of object tester
 * @author Mezba Mahtab
 */
public class ListOfObjectTester {

    public void check(List<? extends Object> expectedList, List<? extends Object> actualList) {
        if (expectedList.size () != actualList.size ()) {
            this.dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Object expected = expectedList.get(i);
            Object actual = actualList.get(i);
            assertEquals(expected, actual);
        }
    }

    public void dump (List<? extends Object> expectedList, List<? extends Object> actualList) {
        System.out.println ("Original List");
        this.dump(expectedList);
        System.out.println ("Updated List");
        this.dump(actualList);
    }

    public void dump(List<? extends Object> list) {
        for (int i = 0; i < list.size(); i++) {
            String expected = list.get(i).toString();
            System.out.println(i + ".) " + expected);
        }
    }

}
