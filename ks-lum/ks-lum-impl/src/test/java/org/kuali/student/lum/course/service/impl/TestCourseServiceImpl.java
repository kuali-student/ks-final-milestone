package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseFeeInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;

import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:course-test-context.xml"})
public class TestCourseServiceImpl {
    @Autowired
    CourseService courseService;

    Set<String> subjectAreaSet = new TreeSet<String>(Arrays.asList(CourseDataGenerator.subjectAreas));
    
    @Test
    public void testCreateCourse() {
     System.out.println ("testCreateCourse");
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = null;
        try {
            assertNotNull(cInfo = generator.getCourseTestData());
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);
            assertEquals("draft", createdCourse.getState());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
        } 
        catch (DataValidationErrorException e)
        {
           dumpValidationErrors (cInfo);
           fail("DataValidationError: " + e.getMessage());         
        }  catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void dumpValidationErrors (CourseInfo cInfo) {
      List<ValidationResultInfo> validationResults = null;
      try
      {
       validationResults = courseService.validateCourse ("SYSTEM", cInfo);
       }
      catch (Exception ex)
      {
       System.out.println ("Could not get validation results because: " + ex.getMessage ());
      }
      for (ValidationResultInfo vr : validationResults) {
       System.out.println (vr.getElement () + " " + vr.getMessage ());
      }
    }

    @Test
    public void testGetCourse() {
     System.out.println ("testGetCourse");
        try {
            CourseDataGenerator generator = new CourseDataGenerator();
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            cInfo.setSpecialTopicsCourse(true);
            cInfo.setPilotCourse(true);
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);

            // get it fresh from database
            CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId());
            assertNotNull(retrievedCourse);

            // confirm it has the right contents
            assertEquals("323", retrievedCourse.getCode().substring(4));
            assertEquals("323", retrievedCourse.getCourseNumberSuffix());

            assertEquals("courseTitle-18", retrievedCourse.getCourseTitle());
            assertEquals("transcriptTitle-59", retrievedCourse.getTranscriptTitle());

            assertEquals("plain-28", retrievedCourse.getDescr().getPlain());
            assertEquals("formatted-27", retrievedCourse.getDescr().getFormatted());

            assertEquals(2, retrievedCourse.getFormats().size());
            FormatInfo info = retrievedCourse.getFormats().get(0);
            assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getType());
            assertEquals(2, info.getActivities().size());
            assertTrue(info.getActivities().get(1).getActivityType().startsWith("kuali.lu.type.activity."));

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String termOffered = retrievedCourse.getTermsOffered().get(0);

            assertTrue("termsOffered-47".equals(termOffered) || "termsOffered-58".equals(termOffered));


            assertEquals(2, retrievedCourse.getCurriculumOversightOrgs().size());
            String orgId = retrievedCourse.getCurriculumOversightOrgs().get(0).getOrgId();
            assertTrue("orgId-28".equals(orgId) || "orgId-26".equals(orgId));

            assertEquals(4, retrievedCourse.getAttributes().size());
            String[] attrKeys = {"attributes-9", "attributes-10"};
            for (String key : attrKeys) {
                String value = retrievedCourse.getAttributes().get(key);
                assertNotNull(value);
                assertEquals(key, value);
            }

            assertEquals(2, retrievedCourse.getCampusLocations().size());
            String campus = retrievedCourse.getCampusLocations().get(1);
            assertTrue(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH.equals(campus) || CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH.equals(campus));

            /*
             * Test LO assertEquals(2, retrievedCourse.getCourseSpecificLOs().size()); LoDisplayInfo info =
             * retrievedCourse.getCourseSpecificLOs().get(0); // TODO - check its contents
             */

            /*
             * assertEquals(2, retrievedCourse.getCrossListings().size()); CourseCrossListingInfo info =
             * retrievedCourse.getCrossListings().get(0); // TODO - check its contents
             */

            assertEquals("orgId-10", retrievedCourse.getAdministeringOrgs().get(0).getOrgId());

            TimeAmountInfo timeInfo = retrievedCourse.getDuration();
            assertEquals("kuali.atp.duration.Semester", timeInfo.getAtpDurationTypeKey());
            assertEquals(29, timeInfo.getTimeQuantity().intValue());

            // TODO - check effective/expiration dates

            // TODO - check feeInfo


            // TODO - check joints
            // TODO - check metaInfo

            assertEquals(2, retrievedCourse.getTermsOffered().size());

            String atpType = retrievedCourse.getTermsOffered().get(0);
            CluInstructorInfo instructor = retrievedCourse.getPrimaryInstructor();
                   


            assertTrue("termsOffered-58".equals(atpType) || "termsOffered-51".equals(atpType));

            assertEquals("orgId-52", instructor.getOrgId());
            assertEquals("personId-53", instructor.getPersonId());

            assertEquals("draft", retrievedCourse.getState());
            assertTrue(subjectAreaSet.contains(retrievedCourse.getSubjectArea()));

            assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getType());

            assertEquals(2,retrievedCourse.getCreditOptions().size());
            assertTrue(retrievedCourse.getCreditOptions().contains("creditOptions-21"));
            assertTrue(retrievedCourse.getCreditOptions().contains("creditOptions-22"));

            assertEquals(2,retrievedCourse.getGradingOptions().size());

            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-41"));
            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-42"));
            
            assertTrue(createdCourse.isSpecialTopicsCourse());
            assertTrue(createdCourse.isPilotCourse());
            
            // TODO - check variotions
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateCourse() {
       System.out.println ("testUpdateCourse");

       CourseDataGenerator generator = new CourseDataGenerator();
       CourseInfo cInfo = null;
       CourseInfo retrievedCourse = null;
       CourseInfo updatedCourse = null;
       CourseInfo createdCourse = null;
       try {
        System.out.println ("Getting test data...");
       cInfo = generator.getCourseTestData();
       } catch (Exception ex) {
        ex.printStackTrace();
        fail ("Got exception getting test data:" + ex.getMessage ());
       }

       assertNotNull(cInfo);
       cInfo.setSpecialTopicsCourse(true);
       cInfo.setPilotCourse(true);
       try {
        System.out.println ("creating course...");
         createdCourse = courseService.createCourse(cInfo);
        } catch (DataValidationErrorException e) {
           dumpValidationErrors (cInfo);
           fail("DataValidationError: " + e.getMessage());
        } catch (Exception ex) {
         ex.printStackTrace();
         fail ("failed creating course" + ":" + ex.getMessage ());
        }
            int initialFormatCount = createdCourse.getFormats().size();

            // minimal sanity check
            assertNotNull(createdCourse);
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
            assertEquals("courseTitle-18", createdCourse.getCourseTitle());
            assertEquals(2, createdCourse.getCurriculumOversightOrgs().size());
            assertEquals(4, createdCourse.getAttributes().size());

            // update some fields
            createdCourse.getCurriculumOversightOrgs().clear();
            AdminOrgInfo testCurrOrg = new AdminOrgInfo();
            testCurrOrg.setOrgId("testOrgId");
            testCurrOrg.setType(CourseAssemblerConstants.SUBJECT_ORG);
            createdCourse.getCurriculumOversightOrgs().add(testCurrOrg);

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

            createdCourse.getCreditOptions().remove(1);
            createdCourse.getCreditOptions().add("NewCreditOption");
            createdCourse.getGradingOptions().remove(1);
            createdCourse.getGradingOptions().add("NewGradingOption");
            
            createdCourse.setSpecialTopicsCourse(false);
            createdCourse.setPilotCourse(false);
            
            createdCourse.getCourseSpecificLOs().get(0).getLoInfo().getDesc().setPlain("UPDATED!!!");
            createdCourse.getCourseSpecificLOs().remove(1);
            LoDisplayInfo displayInfo = new LoDisplayInfo();
            displayInfo.setLoInfo(new LoInfo());
            displayInfo.getLoInfo().setDesc(new RichTextInfo());
            createdCourse.getCourseSpecificLOs().add(displayInfo);
            createdCourse.getCourseSpecificLOs().get(1).getLoInfo().getDesc().setPlain("BrandNew!!!");
            createdCourse.getCourseSpecificLOs().get(1).getLoCategoryInfoList().add(new LoCategoryInfo());
            createdCourse.getCourseSpecificLOs().get(1).getLoCategoryInfoList().get(0).setId("category-3");
            
            createdCourse.getFeeJustification().setFormatted("NEWJUSTIFICATION");
            createdCourse.getFees().clear();
            createdCourse.getFees().add(new CourseFeeInfo());
            createdCourse.getFees().get(0).setFeeType("UpdatedFeeType");
            createdCourse.getFees().get(0).getFeeAmounts().clear();
            createdCourse.getFees().get(0).getFeeAmounts().add(new CurrencyAmountInfo());
            createdCourse.getFees().get(0).getFeeAmounts().get(0).setCurrencyQuantity(10);
            createdCourse.getFees().get(0).getFeeAmounts().get(0).setCurrencyTypeKey("PESOS");
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().clear();
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().add(new AffiliatedOrgInfo());
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).setOrgId("NEWORG");
            createdCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).setPercentage(Long.valueOf(99));
            
            
            //Perform the update
            try {
            System.out.println ("updating course...");
            updatedCourse = courseService.updateCourse(createdCourse);
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (createdCourse);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception ex) {
             ex.printStackTrace();
             fail ("failed updating course: " + ex.getMessage ());
            }
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
            try {
            System.out.println ("Getting course again...");
            retrievedCourse = courseService.getCourse(createdCourse.getId());
            } catch (Exception ex) {
             ex.printStackTrace();
             fail ("failed getting course again:" + ex.getMessage ());
            }
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
                System.out.println ("Updating course again trying to get a version mismatch...");
                courseService.updateCourse(retrievedCourse);
                fail("Failed to throw VersionMismatchException");
            } catch (VersionMismatchException e) {
                System.out.println("Correctly received " + e.getMessage());
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (retrievedCourse);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
        	    e.printStackTrace();
             fail(e.getMessage());
            }
    }

    private void verifyUpdate(CourseInfo updatedCourse) {
        assertNotNull(updatedCourse);

        assertEquals(1, updatedCourse.getCurriculumOversightOrgs().size());
        assertEquals("testOrgId", updatedCourse.getCurriculumOversightOrgs().get(0).getOrgId());

        assertEquals(5, updatedCourse.getAttributes().size());
        assertNotNull(updatedCourse.getAttributes().get("testKey"));
        assertEquals("testValue", updatedCourse.getAttributes().get("testKey"));
        
        assertEquals(2,updatedCourse.getCreditOptions().size());
        assertTrue(updatedCourse.getCreditOptions().contains("creditOptions-21"));
        assertTrue(updatedCourse.getCreditOptions().contains("NewCreditOption"));

        assertEquals(2,updatedCourse.getGradingOptions().size());

        assertTrue(updatedCourse.getGradingOptions().contains("gradingOptions-41"));
        assertTrue(updatedCourse.getGradingOptions().contains("NewGradingOption"));
        
        assertFalse(updatedCourse.isSpecialTopicsCourse());
        assertFalse(updatedCourse.isPilotCourse());
        
        
        assertEquals("NEWJUSTIFICATION",updatedCourse.getFeeJustification().getFormatted());
        assertEquals("UpdatedFeeType",updatedCourse.getFees().get(0).getFeeType());
        assertEquals(Integer.valueOf(10),updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyQuantity());
        assertEquals("PESOS",updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyTypeKey());
        assertEquals("NEWORG",updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getOrgId());
        assertEquals(Long.valueOf(99),updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getPercentage());
    }

    @Test
    public void testDeleteCourse() {
       System.out.println ("testDeleteCourse");
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
    public void testCourseDescrRequiredBasedOnState () {
     System.out.println ("testCourseDescrRequiredBasedOnState");
        CourseDataGenerator generator = new CourseDataGenerator();
         try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            cInfo.setState("ACTIVE");
            cInfo.setDescr (null);
            List <ValidationResultInfo> vrs = courseService.validateCourse("SYSTEM", cInfo);
            System.out.println ("validation results state=ACTIVE");
            for (ValidationResultInfo vr : vrs)
            {
             System.out.println (vr.getElement () + " " + vr.getMessage ());
            }
            if (vrs.size () == 0) {
                fail("Should have a validation result requiring description");
            }

            cInfo.setState("DRAFT");
            cInfo.setDescr (null);
            vrs = courseService.validateCourse("SYSTEM", cInfo);
            System.out.println ("validation result state=DRAFT");
            for (ValidationResultInfo vr : vrs)
            {
             System.out.println (vr.getElement () + " " + vr.getMessage ());
            }
            if (vrs.size () > 0) {
                fail("Should not have any validation results");
            }                   
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    @Test
    public void testDynamicAttributes() {
     System.out.println ("testDynamicAttributes");
        CourseDataGenerator generator = new CourseDataGenerator();
         try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
                     
            Map<String, String> attrMap = new HashMap<String, String>();
            attrMap.put("finalExamStatus","GRD");
            attrMap.put("altFinalExamStatusDescr", "Some123description");
            attrMap.put("proposalTitle", "proposalTitle-1");
            attrMap.put("proposalRationale", "proposalRationale");
            
            cInfo.setAttributes(attrMap);

            try {
            cInfo = courseService.createCourse(cInfo);
            } catch (DataValidationErrorException e) {
             dumpValidationErrors (cInfo);
             fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
        	    e.printStackTrace();
             fail("failed creating course:" + e.getMessage());
            }
            // Check in LuService if the attributes are mapped properly
            
            CourseInfo rInfo = courseService.getCourse(cInfo.getId());
            
            assertEquals("GRD", rInfo.getAttributes().get("finalExamStatus"));
            assertEquals("Some123description", rInfo.getAttributes().get("altFinalExamStatusDescr"));

            
            rInfo.getAttributes().put("finalExamStatus", "123");
            
            try {
                courseService.updateCourse(rInfo);
                fail("Should have thrown data validation exception for invalid chars");
            } catch (DataValidationErrorException e) {
             System.out.println ("threw data validaiton exception as expected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail (e.getMessage ());
        } 
        

    }
    
    
    
    @Test
    public void testCluIsUpdated() {
        
    }
    
	@Test
	public void testGetMetadata(){
  System.out.println ("testGetMetadata");
		MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
		metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.course.dto.CourseInfo");
            
        Map<String, Metadata> properties = metadata.getProperties();
        assertTrue(properties.size() > 0);
        
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
