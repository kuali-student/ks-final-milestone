package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class TestCourseServiceImpl {
	@Autowired
	CourseService courseService;
	
	@Test
	public void testCreateCourse(){
		CourseDataGenerator generator = new CourseDataGenerator();
		CourseInfo cInfo = null;
		try {
			assertNotNull(cInfo = generator.getCourseTestData());
			CourseInfo createdCourse = courseService.createCourse(cInfo);
			assertNotNull(createdCourse);
			assertEquals("draft", createdCourse.getState());
			assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testGetCourse() {
		try {
			CourseDataGenerator generator = new CourseDataGenerator();
			CourseInfo cInfo = generator.getCourseTestData();
			assertNotNull(cInfo);
			CourseInfo createdCourse = courseService.createCourse(cInfo);
			assertNotNull(createdCourse);
			assertEquals("draft", createdCourse.getState());
			assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
			CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId());
			assertNotNull(retrievedCourse);
			assertEquals(createdCourse.getDepartment(), retrievedCourse.getDepartment());
			assertEquals(createdCourse.getCourseNumberSuffix(), retrievedCourse.getCourseNumberSuffix());
			assertEquals("draft", retrievedCourse.getState());
			assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getType());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testUpdateCourse() {
		
	}
	
	@Test 
	public void testDeleteCourse() {
		
	}
}
