package org.kuali.student.ap.coursesearch.util;

import org.junit.Test;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;


public class CreditsFacetTest {
    @Test
    public void testGetFacetItems() throws Exception {
        CreditsFacet facet = new CreditsFacet();

        CourseSearchItemImpl multiple = new CourseSearchItemImpl();
        multiple.setCreditMin(1);
        multiple.setCreditMax(3);
        multiple.setMultipleCredits(new float[] {1,3});
        multiple.setCreditType(CourseSearchItem.CreditType.Multiple);
        facet.process(multiple);

        CourseSearchItemImpl range = new CourseSearchItemImpl();
        range.setCreditMin(4);
        range.setCreditMax(6);
        range.setCreditType(CourseSearchItem.CreditType.Range);
        facet.process(range);

        CourseSearchItemImpl fixed = new CourseSearchItemImpl();
        fixed.setCreditMin(7);
        fixed.setCreditMax(7);
        fixed.setCreditType(CourseSearchItem.CreditType.Fixed);
        facet.process(fixed);

        List<FacetItem> list = facet.getFacetItems();

        assertEquals(6, list.size());
        assertEquals("5", list.get( 3 ).getDisplayName());
        assertEquals("5.0", list.get( 3 ).getKey());
    }

    @Test
    public void testProcessCreditsTypeMultiple() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
//        course.setCreditMin(1);
//        course.setCreditMax(3);
        course.setCredit("1, 3");
        course.setMultipleCredits(new float[]{1, 3});
        course.setCreditType(CourseSearchItem.CreditType.Multiple);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKeys().contains("1.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.0"));
        assertTrue(course.getCreditsFacetKeys().size() == 2);
    }

    @Test
    public void testProcessCreditsTypeMultiple2() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
//        course.setCreditMin(1);
//        course.setCreditMax(3);
        course.setCredit("1, 3, 3.5");
        course.setMultipleCredits(new float[]{1, 3, (float)3.5});
        course.setCreditType(CourseSearchItem.CreditType.Multiple);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKeys().contains("1.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.5"));
        assertTrue(course.getCreditsFacetKeys().size() == 3);
    }

    @Test
    public void testProcessCreditsTypeMultipleAboveMax() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
//        course.setCreditMin(1);
//        course.setCreditMax(3);
        course.setCredit("1, 3, 3.5, 5.5, 6, 7, 7.5");
        course.setMultipleCredits(new float[]{1, 3, (float)3.5, (float)5.5, 6, 7, (float)7.5});
        course.setCreditType(CourseSearchItem.CreditType.Multiple);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKeys().contains("1.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.5"));
        assertTrue(course.getCreditsFacetKeys().contains("5.5"));
        assertTrue(!course.getCreditsFacetKeys().contains("6.0"));
        assertTrue(course.getCreditsFacetKeys().contains("6+"));
        assertEquals(5, course.getCreditsFacetKeys().size());
    }

    @Test
    public void testProcessCreditsTypeRange() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(1);
        course.setCreditMax(3);
        course.setCreditType(CourseSearchItem.CreditType.Range);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKeys().contains("1.0"));
        assertTrue(course.getCreditsFacetKeys().contains("2.0"));
        assertTrue(course.getCreditsFacetKeys().contains("3.0"));
        assertTrue(course.getCreditsFacetKeys().size() == 3);
    }

    @Test
    public void testProcessCreditsTypeRangeAboveMax() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(4);
        course.setCreditMax(7);
        course.setCreditType(CourseSearchItem.CreditType.Range);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKeys().contains("4.0"));
        assertTrue(course.getCreditsFacetKeys().contains("5.0"));
        assertTrue(course.getCreditsFacetKeys().contains("6+"));
        assertEquals(3, course.getCreditsFacetKeys().size());
    }

    @Test
    public void testProcessCreditsTypeFixed() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(1);
        course.setCreditMax(1);
        course.setCreditType(CourseSearchItem.CreditType.Fixed);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertEquals("1.0", course.getCreditsFacetKey());
    }

    @Test
    public void testProcessCreditsTypeFixedAboveMax() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(9);
        course.setCreditMax(9);
        course.setCreditType(CourseSearchItem.CreditType.Fixed);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertEquals("6+", course.getCreditsFacetKey());
    }
}
