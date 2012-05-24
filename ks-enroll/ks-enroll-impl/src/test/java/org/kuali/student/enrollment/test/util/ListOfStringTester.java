/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class ListOfStringTester {

    public void check(List<String> expectedList, List<String> actualList) {
        if (expectedList.size () != actualList.size ()) {
            this.dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        List<String> expectedSorted = new ArrayList(expectedList);
        Collections.sort(expectedSorted);
        List<String> actualSorted = new ArrayList(actualList);
        Collections.sort(actualSorted);
        for (int i = 0; i < expectedSorted.size(); i++) {
            String expected = expectedSorted.get(i);
            String actual = actualSorted.get(i);
            assertEquals(i + "", expected, actual);
        }
    }

    public void dump (List<String> expectedList, List<String> actualList) {
         System.out.println ("Original List");
        this.dump(expectedList);
        System.out.println ("Updated List");
        this.dump(actualList);
    }
    
    public void dump(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String expected = list.get(i);
            System.out.println(i + ".) " + expected);
        }
    }

}
