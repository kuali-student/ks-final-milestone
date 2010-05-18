package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-test-context.xml"})
public class TestCourseServiceImpl {

	CourseService courseService;
	
	@Test
	public void testCreate(){
		
	}
}
