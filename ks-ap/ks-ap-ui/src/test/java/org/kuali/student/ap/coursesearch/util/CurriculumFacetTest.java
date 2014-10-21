package org.kuali.student.ap.coursesearch.util;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-ap-test-context.xml"})
public class CurriculumFacetTest {

    @Test
    public void testGetFacetItems() throws Exception {

        CurriculumFacet facet = new CurriculumFacet();
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setSubject("MATH");
        facet.process(course);

        CourseSearchItemImpl course2 = new CourseSearchItemImpl();
        course2.setSubject("LATN");
        facet.process(course2);

        List<FacetItem> list = facet.getFacetItems();

        assertTrue(list.size() == 2);
        assertEquals(list.get(0).getDisplayName(), "LATN");
        assertEquals(list.get(0).getKey(), "LATN");
        assertEquals(list.get(0).getTitle(), "Latin");
        assertEquals(list.get(1).getDisplayName(), "MATH");
        assertEquals(list.get(1).getKey(), "MATH");
        assertEquals(list.get(1).getTitle(), "Mathematics");

    }

    @Test
    public void testProcess() throws Exception {

        CurriculumFacet facet = new CurriculumFacet();
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setSubject("MATH");
        facet.process(course);

        Set<String> keys = course.getCurriculumFacetKeys();

        assertTrue(!keys.isEmpty());
        assertEquals(1, keys.size());
        assertTrue(keys.contains("MATH"));
    }
}
