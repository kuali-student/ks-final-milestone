package edu.uw.kuali.student.myplan.tests.unit;

import edu.uw.kuali.student.myplan.util.TermInfoComparator;
import org.junit.Test;
import org.kuali.student.core.atp.dto.AtpTypeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class TermInfoComparatorTest {

    @Test
    public void circularTermListExerciseTerms() {
        AtpTypeInfo t1 = new AtpTypeInfo();
        t1.setId("kuali.uw.atp.type.spring");

        AtpTypeInfo t2 = new AtpTypeInfo();
        t2.setId("kuali.uw.atp.type.winter");

        AtpTypeInfo t3 = new AtpTypeInfo();
        t3.setId("kuali.uw.atp.type.autumn");

        AtpTypeInfo t4 = new AtpTypeInfo();
        t4.setId("kuali.uw.atp.type.summer");

        List<AtpTypeInfo> sorted = new ArrayList<AtpTypeInfo>();
        sorted.add(t3);
        sorted.add(t2);
        sorted.add(t1);
        sorted.add(t4);
        sorted.add(t4);

        List<AtpTypeInfo> mixed = new ArrayList<AtpTypeInfo>();
        mixed.add(t4);
        mixed.add(t1);
        mixed.add(t2);
        mixed.add(t3);
        mixed.add(t4);

        Collections.sort(mixed, new TermInfoComparator());

        assertEquals(mixed, sorted);
    }
}
