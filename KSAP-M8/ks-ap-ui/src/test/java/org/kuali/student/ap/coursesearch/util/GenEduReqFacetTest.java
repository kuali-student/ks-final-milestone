package org.kuali.student.ap.coursesearch.util;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-ap-test-context.xml"})
public class GenEduReqFacetTest {

    @Test
    public void testGetFacetItems() throws Exception {
        GenEduReqFacet facet = new GenEduReqFacet();
        CourseSearchItemImpl course1 = new CourseSearchItemImpl();
        course1.setGenEduReq("ABC");
        facet.process( course1 );
        CourseSearchItemImpl course2 = new CourseSearchItemImpl();
        course2.setGenEduReq("XYZ");
        facet.process( course2 );

        List<FacetItem> list = facet.getFacetItems();

        assertTrue( list.size() == 2 );
        assertEquals( list.get( 0 ).getDisplayName(), "ABC" );
        assertEquals( list.get( 0 ).getKey(), "ABC" );
        assertEquals( list.get( 1 ).getDisplayName(), "XYZ" );
        assertEquals( list.get( 1 ).getKey(), "XYZ" );
    }

    @Test
    public void testProcess() throws Exception {

        GenEduReqFacet facet = new GenEduReqFacet();
		CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setGenEduReq( "ABC" );
        facet.process( course );

        Set<String> keys = course.getGenEduReqFacetKeys();
        assertFalse(keys.isEmpty());
        assertEquals(1, keys.size());
        assertTrue( keys.contains( "ABC" ));
    }
}
