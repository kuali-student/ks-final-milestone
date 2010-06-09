package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.kuali.student.lum.course.service.impl.CourseDataGenerator;
            
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class TestCourseServiceImpl {
	@Autowired
	CourseService courseService;
	
	@Test
	public void testCreateCourse() {
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
			
			assertEquals("subjectArea-29323", retrievedCourse.getCode());
			assertEquals("323", retrievedCourse.getCourseNumberSuffix());
			
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
			
			// TODO - check effective/expiration dates
			
			// TODO - check feeInfo
			
			assertEquals("firstExpectedOffering-22", retrievedCourse.getFirstExpectedOffering());
			
			assertEquals(2, retrievedCourse.getFormats().size());
			FormatInfo info = retrievedCourse.getFormats().get(0);
			assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getType());
			assertEquals(2, info.getActivities().size());
			assertTrue(info.getActivities().get(1).getActivityType().startsWith("kuali.lu.type.activity."));
			
			// TODO - check joints
			// TODO - check metaInfo
			
			assertEquals(2, retrievedCourse.getTermsOffered().size());
			String atpType = retrievedCourse.getTermsOffered().get(1);
			assertTrue("termsOffered-32".equals(atpType) || "termsOffered-33".equals(atpType));
			
			CluInstructorInfo instructor = retrievedCourse.getPrimaryInstructor();
			assertEquals("orgId-31", instructor.getOrgId());
			assertEquals("personId-32", instructor.getPersonId());
			
			assertEquals("draft", retrievedCourse.getState());
			assertEquals("subjectArea-29", retrievedCourse.getSubjectArea());
			
			// TODO - check termsOffered
			
			assertEquals("transcriptTitle-33", retrievedCourse.getTranscriptTitle());
			assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getType());
			
			// TODO - check variotions
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testUpdateCourse() {
		try {
			CourseDataGenerator generator = new CourseDataGenerator();
			CourseInfo cInfo = generator.getCourseTestData();
			assertNotNull(cInfo);
			CourseInfo createdCourse = courseService.createCourse(cInfo);
			
			int initialFormatCount = createdCourse.getFormats().size();
			
			// minimal sanity check
			assertNotNull(createdCourse);
			assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
			assertEquals("courseTitle-15", createdCourse.getCourseTitle());
			assertEquals(2, createdCourse.getAcademicSubjectOrgs().size());
			assertEquals(2, createdCourse.getAttributes().size());
			
			// update some fields
			createdCourse.getAcademicSubjectOrgs().clear();
			AcademicSubjectOrgInfo newOrg = new AcademicSubjectOrgInfo();
			newOrg.setOrgId("testOrgId");
			createdCourse.getAcademicSubjectOrgs().add(newOrg);
			
			FormatInfo newFormat = new FormatInfo();
			newFormat.setType(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
			newFormat.setState("ACTIVE");
			createdCourse.getFormats().add(newFormat);
			
			Map<String, String> attributes = createdCourse.getAttributes();
			attributes.put("testKey", "testValue");
			createdCourse.setAttributes(attributes);
			
			CourseInfo updatedCourse = courseService.updateCourse(createdCourse);
			assertEquals(initialFormatCount+1, updatedCourse.getFormats().size());
			
			// Test what was returned by updateCourse
			verifyUpdate(updatedCourse);
			
			// Now explicitly get it
			CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId());
			verifyUpdate(retrievedCourse);
			
			// and test for optimistic lock exception
			// NOTE: CourseService.updateCourse(CourseInfo courseInfo) modifies its parameter,
			// as the 'results' BusinessDTORef (our CourseInfo) is simply updated to reflect
			// the new contents of the updated Clu (see the
			// results.getAssembler().assemble(updatedClu, results.getBusinessDTORef(), true);
			// line in CourseServiceMethodInvoker.invokeServiceCalls()
			int currVersion = Integer.parseInt(retrievedCourse.getMetaInfo().getVersionInd());
			if (currVersion > 0) {
				retrievedCourse.getMetaInfo().setVersionInd(Integer.toString(--currVersion));
			}
			try {
			    courseService.updateCourse(retrievedCourse);
				fail("Failed to throw VersionMismatchException");
	        } catch (Exception e) {
	            System.out.println("Correctly received " + e.getMessage());
	        } // TODO - should be a VersionMismatchException
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	private void verifyUpdate(CourseInfo updatedCourse) {
		assertNotNull(updatedCourse);
		
		assertEquals(1, updatedCourse.getAcademicSubjectOrgs().size());
		assertEquals("testOrgId",  updatedCourse.getAcademicSubjectOrgs().get(0).getOrgId());
		
		assertEquals(3, updatedCourse.getAttributes().size());
		assertNotNull(updatedCourse.getAttributes().get("testKey"));
		assertEquals("testValue", updatedCourse.getAttributes().get("testKey"));
    }

    //@Test 
	public void testDeleteCourse() {
		try {
			CourseDataGenerator generator = new CourseDataGenerator();
			CourseInfo cInfo = generator.getCourseTestData();
			assertNotNull(cInfo);
			CourseInfo createdCourse = courseService.createCourse(cInfo);
			assertNotNull(createdCourse);
			assertEquals("draft", createdCourse.getState());
			assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
			String courseId = createdCourse.getId();
			CourseInfo retrievedCourse = courseService.getCourse(courseId);
			assertNotNull(retrievedCourse);
			
			courseService.deleteCourse(courseId);
			try {
				retrievedCourse = courseService.getCourse(courseId);
				fail("Retrieval of deleted course should have thrown exception");
			} catch (Exception e) {} // Should be DoesNotExistException, but we're getting a SOAPFaultException instead
			                         // TODO - fix services to return correct exceptions and empty lists (rather than null)

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testCluIsUpdated() {
	    
	}
}
