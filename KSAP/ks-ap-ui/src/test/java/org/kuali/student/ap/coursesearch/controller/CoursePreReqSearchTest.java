package org.kuali.student.ap.coursesearch.controller;

import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.coursesearch.service.CoursePreReqSearch;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-ap-test-context.xml"})
public class CoursePreReqSearchTest {

    public static final String principalId = "student1";
    public ContextInfo context;

    @Before
    public void setUp() {
        context = new ContextInfo();
        context.setPrincipalId(principalId);
    }

    @Resource
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
        List<String> cluList = search.getCoursePreReqBySubject( "PHIL", context );
        assertFalse( cluList.isEmpty() );
    }

    @Test
    public void testCoursePreReqSearchAndRange() throws Exception {
        System.out.println( "testCoursePreReqSearchAndRange" );
        CoursePreReqSearch search = getCoursePreReqSearch();
        List<String> cluList = search.getCoursePreReqBySubjectAndRange( "PHIL", "8xx", context );
        assertFalse( cluList.isEmpty() );
    }

    @Test
    public void testCoursePreReqWithExclusions() throws Exception {
        System.out.println( "testCoursePreReqWithExclusions" );
        CoursePreReqSearch search = getCoursePreReqSearch();
        HashSet<String> excludeSet = new HashSet<String>();
        excludeSet.add( "868" );
        List<String> cluList = search.getCoursePreReqWithExclusions( "PHIL", "8xx", excludeSet, context );
        assertFalse( cluList.isEmpty() );
    }

}
