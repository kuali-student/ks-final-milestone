package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class DevAssemblyTesting {

    @Autowired
    CourseService courseService;

    Set<String> subjectAreaSet = new TreeSet<String>(Arrays.asList(CourseDataGenerator.subjectAreas));
    
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

            // get it fresh from database
            CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId());
            assertNotNull(retrievedCourse);

            // confirm it has the right contents
            assertEquals("323", retrievedCourse.getCode().substring(4));
            assertEquals("323", retrievedCourse.getCourseNumberSuffix());

            assertEquals("courseTitle-15", retrievedCourse.getCourseTitle());
            assertEquals("transcriptTitle-47", retrievedCourse.getTranscriptTitle());

            assertEquals("plain-22", retrievedCourse.getDescr().getPlain());
            assertEquals("formatted-21", retrievedCourse.getDescr().getFormatted());

            assertEquals(2, retrievedCourse.getFormats().size());
            FormatInfo info = retrievedCourse.getFormats().get(0);
            assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getType());
            assertEquals(2, info.getActivities().size());
            assertTrue(info.getActivities().get(1).getActivityType().startsWith("kuali.lu.type.activity."));

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String termOffered = retrievedCourse.getTermsOffered().get(0);
            assertTrue("termsOffered-46".equals(termOffered) || "termsOffered-33".equals(termOffered));

            assertEquals(2, retrievedCourse.getAcademicSubjectOrgs().size());
            String orgId = retrievedCourse.getAcademicSubjectOrgs().get(0);
            assertTrue("academicSubjectOrgs-3".equals(orgId) || "academicSubjectOrgs-4".equals(orgId));

            assertEquals(2, retrievedCourse.getAttributes().size());
            String[] attrKeys = {"attributes-6", "attributes-7"};
            for (String key : attrKeys) {
                String value = retrievedCourse.getAttributes().get(key);
                assertNotNull(value);
                assertEquals(key, value);
            }

            assertEquals(2, retrievedCourse.getCampusLocations().size());
            String campus = retrievedCourse.getCampusLocations().get(1);
            assertTrue(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH.equals(campus) || CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH.equals(campus));

            /*
             * Test LO assertEquals(2, retrievedCourse.getCourseSpecificLOs().size()); LoDisplayInfo info =
             * retrievedCourse.getCourseSpecificLOs().get(0); // TODO - check its contents
             */

            /*
             * assertEquals(2, retrievedCourse.getCrossListings().size()); CourseCrossListingInfo info =
             * retrievedCourse.getCrossListings().get(0); // TODO - check its contents
             */

            assertEquals("department-19", retrievedCourse.getDepartment());

            TimeAmountInfo timeInfo = retrievedCourse.getDuration();
            assertEquals("kuali.atp.duration.Semester", timeInfo.getAtpDurationTypeKey());
            assertEquals(20, timeInfo.getTimeQuantity().intValue());

            // TODO - check effective/expiration dates

            // TODO - check feeInfo

            assertEquals("firstExpectedOffering-26", retrievedCourse.getFirstExpectedOffering());

            // TODO - check joints
            // TODO - check metaInfo

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String atpType = retrievedCourse.getTermsOffered().get(1);
            assertTrue("termsOffered-47".equals(atpType) || "termsOffered-33".equals(atpType));

            CluInstructorInfo instructor = retrievedCourse.getPrimaryInstructor();
            assertEquals("orgId-43", instructor.getOrgId());
            assertEquals("personId-44", instructor.getPersonId());

            assertEquals("draft", retrievedCourse.getState());
            assertTrue(subjectAreaSet.contains(retrievedCourse.getSubjectArea()));

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
            createdCourse.getAcademicSubjectOrgs().add("testOrgId");

            // Delete One Format
            createdCourse.getFormats().remove(0);

            // Delete One Activity from Existing Format
            assertEquals(2, createdCourse.getFormats().get(0).getActivities().size());
            createdCourse.getFormats().get(0).getActivities().remove(0);
            String updActFrmtId = createdCourse.getFormats().get(0).getId();
            
            // Add two New formats
            FormatInfo newFormat = new FormatInfo();
            newFormat.setType(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
            newFormat.setState("DRAFT");
            Map<String, String> attrMap = new HashMap<String, String>();
            attrMap.put("FRMT", "value");
            newFormat.setAttributes(attrMap);
            
            // Add two new activities to new formats
            ActivityInfo newActivity1 = new ActivityInfo();
            newActivity1.setActivityType(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE);
            newActivity1.setState("DRAFT");
            newFormat.getActivities().add(newActivity1);

            ActivityInfo newActivity2 = new ActivityInfo();
            newActivity2.setActivityType(CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE);
            newActivity2.setState("DRAFT");
            newFormat.getActivities().add(newActivity2);

            createdCourse.getFormats().add(newFormat);

            FormatInfo newFormat2 = new FormatInfo();
            newFormat2.setType(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
            newFormat2.setState("DRAFT");
            createdCourse.getFormats().add(newFormat2);

            Map<String, String> attributes = createdCourse.getAttributes();
            attributes.put("testKey", "testValue");
            createdCourse.setAttributes(attributes);

            CourseInfo updatedCourse = courseService.updateCourse(createdCourse);
            assertEquals(initialFormatCount + 1, updatedCourse.getFormats().size());

            for (FormatInfo uFrmt : updatedCourse.getFormats()) {
                // Check to see if activities are added to a new format
                if (uFrmt.getAttributes().containsKey("FRMT")) {
                    assertEquals(2, uFrmt.getActivities().size());
                    String actType = uFrmt.getActivities().get(0).getActivityType();
                    assertTrue(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE.equals(actType) || CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE.equals(actType));
                }
             
                // Check to see if activity is deleted from an existing format
                if(updActFrmtId.equals(uFrmt.getId())) {
                    assertEquals(1, uFrmt.getActivities().size());
                }
            }

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
            } catch (VersionMismatchException e) {
                System.out.println("Correctly received " + e.getMessage());
            } 
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void verifyUpdate(CourseInfo updatedCourse) {
        assertNotNull(updatedCourse);

        assertEquals(1, updatedCourse.getAcademicSubjectOrgs().size());
        assertEquals("testOrgId", updatedCourse.getAcademicSubjectOrgs().get(0));

        assertEquals(3, updatedCourse.getAttributes().size());
        assertNotNull(updatedCourse.getAttributes().get("testKey"));
        assertEquals("testValue", updatedCourse.getAttributes().get("testKey"));
    }

    @Test
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
            } catch (DoesNotExistException e) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCourseIdStateValidation() {
        CourseDataGenerator generator = new CourseDataGenerator();
         try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);

            cInfo.setState("ACTIVE");
            cInfo.setId(null);
            try {
                courseService.createCourse(cInfo);
                fail("Should have thrown data validation exception");
            } catch (DataValidationErrorException e) {} 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    @Test
    public void testDynamicAttributes() {
        CourseDataGenerator generator = new CourseDataGenerator();
         try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
                     
            Map<String, String> attrMap = new HashMap<String, String>();
            attrMap.put("finalExamStatus","GRD");
            attrMap.put("altFinalExamStatusDescr", "Some123description");
            
            cInfo.setAttributes(attrMap);

            cInfo = courseService.createCourse(cInfo);

            // Check in LuService if the attributes are mapped properly
            
            CourseInfo rInfo = courseService.getCourse(cInfo.getId());
            
            assertEquals("GRD", rInfo.getAttributes().get("finalExamStatus"));
            assertEquals("Some123description", rInfo.getAttributes().get("altFinalExamStatusDescr"));

            
            rInfo.getAttributes().put("finalExamStatus", "123");
            
            try {
                courseService.updateCourse(rInfo);
                fail("Should have thrown data validation exception for invalid chars");
            } catch (DataValidationErrorException e) {}
        } catch (Exception e) {
            e.printStackTrace();
        } 
        

    }
    
    
    
    @Test
    public void testCluIsUpdated() {
        
    }
    
	@Test
	public void testGetMetadata(){
		MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
		metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.course.dto.CourseInfo");
            
        Map<String, Metadata> properties = metadata.getProperties();
        assertEquals(properties.size(), 25);
        
        assertTrue(properties.containsKey("state"));
        assertTrue(properties.containsKey("campusLocations"));
           
        assertTrue(properties.containsKey("formats"));
        metadata = properties.get("formats");
        
        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");
               
        properties = metadata.getProperties();
        assertTrue(properties.containsKey("activities"));
        metadata = properties.get("activities");

        properties = metadata.getProperties();
        assertTrue(properties.containsKey("*"));
        metadata = properties.get("*");

        properties = metadata.getProperties();
        assertFalse(properties.containsKey("foo"));
        
        return;
	}
}
