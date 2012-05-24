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

    public void check(List<String> origList, List<String> infoList) {
        if (origList.size () != infoList.size ()) {
            this.dump(origList, infoList);
        }
        assertEquals(origList.size(), infoList.size());
        List<String> origSorted = new ArrayList(origList);
        Collections.sort(origSorted);
        List<String> infoSorted = new ArrayList(infoList);
        Collections.sort(infoSorted);
        for (int i = 0; i < origSorted.size(); i++) {
            String orig = origSorted.get(i);
            String info = infoSorted.get(i);
            assertEquals(i + "", orig, info);
        }
    }

    public void dump (List<String> origList, List<String> infoList) {
         System.out.println ("Original List");
        this.dump(origList);
        System.out.println ("Updated List");
        this.dump(infoList);
    }
    
    public void dump(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String orig = list.get(i);
            System.out.println(i + ".) " + orig);
        }
    }

}
