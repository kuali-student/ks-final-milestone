package org.kuali.student.ap.coursesearch.util;

import java.util.List;

import org.junit.Test;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreditsFacetTest {
    @Test
    public void testGetFacetItems() throws Exception {
        CreditsFacet facet = new CreditsFacet();

        CourseSearchItemImpl multiple = new CourseSearchItemImpl();
        multiple.setCreditMin(1);
        multiple.setCreditMax(3);
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

        assertTrue( list.size() == 6 );
        assertEquals( list.get( 3 ).getDisplayName(), "5" );
    }

    @Test
    public void testProcessCreditsTypeMultiple() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(1);
        course.setCreditMax(3);
        course.setCreditType(CourseSearchItem.CreditType.Multiple);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKey().contains("1"));
        assertTrue(course.getCreditsFacetKey().contains("3"));
        assertTrue(course.getCreditsFacetKey().length() == 2);
    }

    @Test
    public void testProcessCreditsTypeRange() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(1);
        course.setCreditMax(3);
        course.setCreditType(CourseSearchItem.CreditType.Range);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertTrue(course.getCreditsFacetKey().contains("1"));
        assertTrue(course.getCreditsFacetKey().contains("2"));
        assertTrue(course.getCreditsFacetKey().contains("3"));
        assertTrue(course.getCreditsFacetKey().length() == 3);
    }

    @Test
    public void testProcessCreditsTypeFixed() throws Exception {
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCreditMin(1);
        course.setCreditMax(1);
        course.setCreditType(CourseSearchItem.CreditType.Fixed);

        CreditsFacet facet = new CreditsFacet();
        facet.process(course);
        assertEquals("1", course.getCreditsFacetKey());
    }
}
