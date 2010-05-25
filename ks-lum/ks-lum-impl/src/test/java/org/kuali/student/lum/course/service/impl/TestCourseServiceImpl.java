package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-test-context.xml"})
public class TestCourseServiceImpl {
	@Autowired
	CourseService courseService;
	
	@Test 
	public void testGetCourse() {
		assertNotNull(courseService);
	}
	
	@Test 
	public void testUpdateCourse() {
		
	}
	
	@Test 
	public void testDeleteCourse() {
		
	}
	
	@Test
	public void testCreateCourse(){
		
	}
}
