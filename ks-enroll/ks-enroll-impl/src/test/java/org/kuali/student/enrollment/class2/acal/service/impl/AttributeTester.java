/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.kuali.student.r2.common.dto.AttributeInfo;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class AttributeTester {

    public void compare(List<AttributeInfo> origList, List<AttributeInfo> infoList) {
        if (origList.size () != infoList.size ()) {
            this.dump(origList, infoList);
        }
        assertEquals(origList.size(), infoList.size());
        List<AttributeInfo> origSorted = new ArrayList(origList);
        Collections.sort(origSorted, new AttributeInfoComparator());
        List<AttributeInfo> infoSorted = new ArrayList(infoList);
        Collections.sort(infoSorted, new AttributeInfoComparator());
        for (int i = 0; i < origSorted.size(); i++) {
            AttributeInfo orig = origSorted.get(i);
            AttributeInfo info = infoSorted.get(i);
            if (orig.getId() != null) {
                assertEquals(i + "", orig.getId(), info.getId());
            }
            assertEquals(i + "", orig.getKey(), info.getKey());
            assertEquals(i + "", orig.getValue(), info.getValue());
        }
    }

    public void dump (List<AttributeInfo> origList, List<AttributeInfo> infoList) {
         System.out.println ("Original List");
        this.dump(origList);
        System.out.println ("Updated List");
        this.dump(infoList);
    }
    
    public void dump(List<AttributeInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            AttributeInfo orig = list.get(i);
            System.out.println(i + ".) " + orig.getKey() + "=" + orig.getValue() + "\t" + orig.getId());
        }
    }

    private static class AttributeInfoComparator implements Comparator<AttributeInfo> {

        @Override
        public int compare(AttributeInfo o1, AttributeInfo o2) {
            return calcSortKey(o1).compareTo(calcSortKey(o2));
        }

        private String calcSortKey(AttributeInfo attr) {
            StringBuilder sb = new StringBuilder();
            sb.append(attr.getKey());
            sb.append("\t");
            if (attr.getId() != null) {
                sb.append(attr.getId());
            }
            sb.append("\t");
            if (attr.getValue() != null) {
                sb.append(attr.getValue());
            }
            return sb.toString();
        }
    }
}
