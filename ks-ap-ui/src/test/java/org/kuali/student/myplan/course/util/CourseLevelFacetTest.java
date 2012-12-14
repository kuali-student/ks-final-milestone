package org.kuali.student.myplan.course.util;

import org.junit.Test;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.FacetItem;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CourseLevelFacetTest {
    @Test
    public void testGetFacetItems() throws Exception {
        CourseLevelFacet facet = new CourseLevelFacet();
        CourseSearchItem course500 = new CourseSearchItem();
        course500.setLevel("500");
        facet.process(course500);
        CourseSearchItem course600 = new CourseSearchItem();
        course600.setLevel("600");
        facet.process(course600);

        List<FacetItem> list = facet.getFacetItems();

        assertEquals( list.size(), 2 );
        assertEquals( list.get(0).getDisplayName(), "500" );
        assertEquals( list.get(1).getDisplayName(), "600" );
    }

    @Test
    public void testProcess() throws Exception {
        CourseLevelFacet facet = new CourseLevelFacet();
        CourseSearchItem course = new CourseSearchItem();
        course.setLevel("500");
        facet.process(course);
        assertEquals( course.getCourseLevelFacetKey(), ";500;" );
    }
}
