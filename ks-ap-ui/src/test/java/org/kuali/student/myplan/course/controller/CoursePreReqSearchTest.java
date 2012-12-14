package org.kuali.student.myplan.course.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.myplan.course.service.CoursePreReqSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:myplan-test-context.xml"})
public class CoursePreReqSearchTest {

    @Autowired
    private CoursePreReqSearch coursePreReqSearch;

    public CoursePreReqSearch getCoursePreReqSearch() {
        return coursePreReqSearch;
    }

    public void setCoursePreReqSearch(CoursePreReqSearch coursePreReqSearch) {
        this.coursePreReqSearch = coursePreReqSearch;
    }


    @Test
    public void testCoursePreReqSearch() throws Exception {
        System.out.println( "testCoursePreReqSearch" );
        CoursePreReqSearch search = getCoursePreReqSearch();
        List<String> cluList = search.getCoursePreReqBySubject( "A A" );
        assertFalse( cluList.isEmpty() );
    }

    @Test
    public void testCoursePreReqSearchAndRange() throws Exception {
        System.out.println( "testCoursePreReqSearchAndRange" );
        CoursePreReqSearch search = getCoursePreReqSearch();
        List<String> cluList = search.getCoursePreReqBySubjectAndRange( "A A", "5xx" );
        assertFalse( cluList.isEmpty() );
    }

    @Test
    public void testCoursePreReqWithExclusions() throws Exception {
        System.out.println( "testCoursePreReqWithExclusions" );
        CoursePreReqSearch search = getCoursePreReqSearch();
        HashSet<String> excludeSet = new HashSet<String>();
        excludeSet.add( "545" );
        List<String> cluList = search.getCoursePreReqWithExclusions( "A A", "54x", excludeSet );
        assertFalse( cluList.isEmpty() );
    }

}
