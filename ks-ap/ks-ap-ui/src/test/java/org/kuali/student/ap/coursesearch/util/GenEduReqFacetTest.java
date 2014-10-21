package org.kuali.student.ap.coursesearch.util;

import java.util.ArrayList;
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
public class GenEduReqFacetTest {

    @Test
    public void testGetFacetItems() throws Exception {
        GenEduReqFacet facet = new GenEduReqFacet();
        CourseSearchItemImpl course1 = new CourseSearchItemImpl();
        List temp1 = new ArrayList<String>();
        temp1.add("ABC");
        course1.setGenEduReqs(temp1);
        facet.process( course1 );
        CourseSearchItemImpl course2 = new CourseSearchItemImpl();
        List temp2 = new ArrayList<String>();
        temp2.add("XYZ");
        course2.setGenEduReqs(temp2);
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
        List temp1 = new ArrayList<String>();
        temp1.add("ABC");
        course.setGenEduReqs( temp1 );
        facet.process( course );

        Set<String> keys = course.getGenEduReqFacetKeys();
        assertTrue(!keys.isEmpty());
        assertEquals(1, keys.size());
        assertTrue( keys.contains( "ABC" ));
    }
}
