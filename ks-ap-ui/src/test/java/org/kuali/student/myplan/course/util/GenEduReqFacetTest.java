package org.kuali.student.myplan.course.util;

import org.junit.Test;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.FacetItem;

import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

public class GenEduReqFacetTest {

    @Test
    public void testGetFacetItems() throws Exception {
        GenEduReqFacet facet = new GenEduReqFacet();
        CourseSearchItem course1 = new CourseSearchItem();
        course1.setGenEduReq("ABC");
        facet.process( course1 );
        CourseSearchItem course2 = new CourseSearchItem();
        course2.setGenEduReq("XYZ");
        facet.process( course2 );

        List<FacetItem> list = facet.getFacetItems();

        assertTrue( list.size() == 2 );
        assertEquals( list.get( 0 ).getDisplayName(), "ABC" );
        assertEquals( list.get( 0 ).getKey(), ";ABC;" );
        assertEquals( list.get( 1 ).getDisplayName(), "XYZ" );
        assertEquals( list.get( 1 ).getKey(), ";XYZ;" );
    }

    @Test
    public void testProcess() throws Exception {

        GenEduReqFacet facet = new GenEduReqFacet();
        CourseSearchItem course = new CourseSearchItem();
        course.setGenEduReq( "ABC" );
        facet.process( course );

        Set<String> keys = course.getGenEduReqFacetKeys();

        assertFalse(keys.isEmpty());
        assertEquals(1, keys.size());
        assertTrue( keys.contains( ";;ABC;;" ));
    }
}
