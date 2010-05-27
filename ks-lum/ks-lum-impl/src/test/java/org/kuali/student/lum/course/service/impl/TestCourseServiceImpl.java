package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
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
			
			assertEquals(2, retrievedCourse.getAcademicSubjectOrgs().size());
			String orgId = retrievedCourse.getAcademicSubjectOrgs().get(0).getOrgId();
			assertTrue("orgId-3".equals(orgId) || "orgId-4".equals(orgId));
			
			assertEquals(2, retrievedCourse.getAttributes().size());
			String[] attrKeys = { "attributes-6", "attributes-7" };
			for (String key : attrKeys) {
				String value = retrievedCourse.getAttributes().get(key);
				assertNotNull(value);
				assertEquals(key, value);
			}
			
			assertEquals(2, retrievedCourse.getCampusLocations().size());
			String campus = retrievedCourse.getCampusLocations().get(1);
			assertTrue("campusLocations-9".equals(campus) || "campusLocations-10".equals(campus));
			
			assertEquals("subjectArea-32courseNumberSuffix-11", retrievedCourse.getCode());
			assertEquals("courseNumberSuffix-11", retrievedCourse.getCourseNumberSuffix());
			
			/* Test LO
			assertEquals(2, retrievedCourse.getCourseSpecificLOs().size());
			LoDisplayInfo info = retrievedCourse.getCourseSpecificLOs().get(0);
			// TODO - check its contents
			*/
			
			assertEquals("courseTitle-15", retrievedCourse.getCourseTitle());
			
			/*
			assertEquals(2, retrievedCourse.getCrossListings().size());
			CourseCrossListingInfo info = retrievedCourse.getCrossListings().get(0);
			// TODO - check its contents
			 */
			
			assertEquals("department-16", retrievedCourse.getDepartment());
			assertEquals("formatted-18", retrievedCourse.getDescription().getFormatted());
			
			TimeAmountInfo timeInfo = retrievedCourse.getDuration();
			assertEquals("kuali.atp.duration.Semester", timeInfo.getAtpDurationTypeKey());
			assertEquals(20, timeInfo.getTimeQuantity().intValue());
			
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
