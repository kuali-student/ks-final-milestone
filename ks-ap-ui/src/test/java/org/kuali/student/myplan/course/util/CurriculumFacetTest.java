package org.kuali.student.myplan.course.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;


public class CurriculumFacetTest {

   /* @Test
    public void testGetFacetItems() throws Exception {
        CurriculumFacet facet = new CurriculumFacet();
        CourseSearchItemImpl course1 = new CourseSearchItem();
        course1.setSubject("A S");
        facet.process(course1);
        CourseSearchItemImpl course2 = new CourseSearchItem();
        course2.setSubject("XYZ");
        facet.process(course2);
        HashMap<String, Map<String,String>> hashMap = new HashMap<String, Map<String,String>>();
        List<EnumeratedValueInfo> enumeratedValueInfoList = new ArrayList<EnumeratedValueInfo>();
        EnumeratedValueInfo enumeratedValueInfo = new EnumeratedValueInfo();
        enumeratedValueInfo.setCode("A S   ");
        enumeratedValueInfo.setAbbrevValue("A S   ");
        enumeratedValueInfo.setValue("AEROSPACE STUDIES (AIR FORCE ROTC)      ");
        enumeratedValueInfo.setEnumerationKey(CourseSearchConstants.SUBJECT_AREA);
        enumeratedValueInfoList.add(enumeratedValueInfo);
        hashMap.put(CourseSearchConstants.SUBJECT_AREA, enumeratedValueInfoList);
        facet.setHashMap(hashMap);
        List<FacetItem> list = facet.getFacetItems();

        assertTrue(list.size() == 2);
        assertEquals(list.get(0).getDisplayName(), "A S");
        assertEquals(list.get(0).getKey(), ";A S;");
        assertEquals(list.get(0).getTitle(), "AEROSPACE STUDIES (AIR FORCE ROTC)");
        assertEquals(list.get(1).getDisplayName(), "XYZ");
        assertEquals(list.get(1).getKey(), ";XYZ;");
        assertEquals(list.get(1).getTitle(), null);

    }*/

    @Test
    public void testProcess() throws Exception {

        CurriculumFacet facet = new CurriculumFacet();
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setSubject("ABC");
        facet.process(course);

        Set<String> keys = course.getCurriculumFacetKeys();

        assertFalse(keys.isEmpty());
        assertEquals(1, keys.size());
        assertTrue(keys.contains(";ABC;"));
    }
}
