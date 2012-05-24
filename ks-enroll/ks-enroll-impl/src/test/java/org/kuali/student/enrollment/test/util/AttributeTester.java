/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.test.util;

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

    public void add2ForCreate(List<AttributeInfo> orig) {
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key1");
        attr.setValue("attribute value1");
        orig.add(attr);
        attr = new AttributeInfo();
        attr.setKey("attribute.key2");
        attr.setValue("attribute value2");
        orig.add(attr);
    }

    public void delete1Update1Add1ForUpdate(List<AttributeInfo> orig) {
        orig.remove(0);
        orig.get(0).setValue("updated value");
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key3");
        attr.setValue("attribute value3");
        orig.add(attr);
    }

    public AttributeInfo toAttribute(String key, String value) {
        if (key == null) {
            return null;
        }
        AttributeInfo info = new AttributeInfo();
        info.setKey(key);
        info.setValue(value);
        return info;
    }

    public List<AttributeInfo> findAttributes(List<AttributeInfo> attrs, String key) {
        List<AttributeInfo> list = new ArrayList<AttributeInfo>();
        for (AttributeInfo info : attrs) {
            if (key.equals(info.getKey())) {
                list.add(info);
            }
        }
        return list;
    }
    
    
    public void check(List<AttributeInfo> origList, List<AttributeInfo> infoList) {
        if (origList.size() != infoList.size()) {
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

    public void dump(List<AttributeInfo> origList, List<AttributeInfo> infoList) {
        System.out.println("Original List");
        this.dump(origList);
        System.out.println("Updated List");
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
