/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.test.util;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class AttributeTester {

    private static final Logger log = LoggerFactory.getLogger(AttributeTester.class);

    public void add2ForCreate(List<AttributeInfo> expected) {
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key1");
        attr.setValue("attribute value1");
        expected.add(attr);
        attr = new AttributeInfo();
        attr.setKey("attribute.key2");
        attr.setValue("attribute value2");
        expected.add(attr);
    }

    public void delete1Update1Add1ForUpdate(List<AttributeInfo> expected) {
        expected.remove(0);
        expected.get(0).setValue("updated value");
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key3");
        attr.setValue("attribute value3");
        expected.add(attr);
    }

    public AttributeInfo toAttribute(String key, String value) {
        if (key == null) {
            return null;
        }
        AttributeInfo actual = new AttributeInfo();
        actual.setKey(key);
        actual.setValue(value);
        return actual;
    }

    public List<AttributeInfo> findAttributes(List<AttributeInfo> attrs, String key) {
        List<AttributeInfo> list = new ArrayList<AttributeInfo>();
        for (AttributeInfo actual : attrs) {
            if (key.equals(actual.getKey())) {
                list.add(actual);
            }
        }
        return list;
    }
    
    
    public void check(List<AttributeInfo> expectedList, List<AttributeInfo> actualList) {
        if (expectedList.size() != actualList.size()) {
            this.dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        List<AttributeInfo> expectedSorted = new ArrayList(expectedList);
        Collections.sort(expectedSorted, new AttributeInfoComparator());
        List<AttributeInfo> actualSorted = new ArrayList(actualList);
        Collections.sort(actualSorted, new AttributeInfoComparator());
        for (int i = 0; i < expectedSorted.size(); i++) {
            AttributeInfo expected = expectedSorted.get(i);
            AttributeInfo actual = actualSorted.get(i);
            if (expected.getId() != null) {
                assertEquals(i + "", expected.getId(), actual.getId());
            }
            
           if (log.isDebugEnabled()) {
            
                Transformer valueTransformer = new Transformer() {

                    @Override
                    public Object transform(Object input) {
                        AttributeInfo attr = (AttributeInfo) input;
                        return attr.getValue();
                    }
                };

                if (!expected.getValue().equals(actual.getValue())) {

                    /*
                     * On miss match print out the values in each collection so we can get
                     * a better sense of why the test failed..
                     */
                    Collection<String> actualValues = CollectionUtils.collect(
                            actualSorted, valueTransformer);
                    Collection<String> expectedValues = CollectionUtils
                            .collect(expectedSorted, valueTransformer);

                    log.debug("expected = "
                            + StringUtils.join(expectedValues, ", ")
                            + ", actual = "
                            + StringUtils.join(actualValues, ", "));

                }
           }
            
            
            assertEquals(i + "", expected.getKey(), actual.getKey());
            assertEquals(i + "", expected.getValue(), actual.getValue());
        }
    }

    public void dump(List<AttributeInfo> expectedList, List<AttributeInfo> actualList) {
        System.out.println("Expected List");
        this.dump(expectedList);
        System.out.println("Actual List");
        this.dump(actualList);
    }

    public void dump(List<AttributeInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            AttributeInfo expected = list.get(i);
            System.out.println(i + ".) " + expected.getKey() + "=" + expected.getValue() + "\t" + expected.getId());
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
