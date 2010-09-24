package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.IllegalVersionSequencingException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
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
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
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
    public void testCreateCourse() throws Exception {
     System.out.println ("testCreateCourse");
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = null;
        try {
            assertNotNull(cInfo = generator.getCourseTestData());
            CourseInfo createdCourse = courseService.createCourse(cInfo);
            assertNotNull(createdCourse);
            assertEquals("draft", createdCourse.getState());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getType());
            assertEquals(cInfo.getStartTerm(),createdCourse.getStartTerm());
            assertEquals(cInfo.getEndTerm(),createdCourse.getEndTerm());
        } 
        catch (DataValidationErrorException e)
        {
           dumpValidationErrors (cInfo);
           fail("DataValidationError: " + e.getMessage());         
        }  catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void dumpValidationErrors (CourseInfo cInfo) throws Exception {
      List<ValidationResultInfo> validationResults = courseService.validateCourse ("SYSTEM", cInfo);
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

            assertEquals("courseTitle-12", retrievedCourse.getCourseTitle());
            assertEquals("transcriptTitle-49", retrievedCourse.getTranscriptTitle());

            assertEquals("plain-18", retrievedCourse.getDescr().getPlain());
            assertEquals("formatted-17", retrievedCourse.getDescr().getFormatted());

            assertEquals(2, retrievedCourse.getFormats().size());
            FormatInfo info = retrievedCourse.getFormats().get(0);
            assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getType());
            assertEquals(2, info.getActivities().size());
            assertTrue(info.getActivities().get(1).getActivityType().startsWith("kuali.lu.type.activity."));

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String termOffered = retrievedCourse.getTermsOffered().get(0);

            assertTrue("termsOffered-48".equals(termOffered) || "termsOffered-49".equals(termOffered));

            assertEquals(2, retrievedCourse.getUnitsContentOwner().size());
            String orgId = retrievedCourse.getUnitsContentOwner().get(0);
            assertTrue("unitsContentOwner-53".equals(orgId) || "unitsContentOwner-54".equals(orgId));

            assertEquals(4, retrievedCourse.getAttributes().size());
            String[] attrKeys = {"attributes-3", "attributes-4"};
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

            assertEquals("unitsDeployment-56", retrievedCourse.getUnitsDeployment().get(0));

            TimeAmountInfo timeInfo = retrievedCourse.getDuration();
            assertEquals("kuali.atp.duration.Semester", timeInfo.getAtpDurationTypeKey());
            assertEquals(19, timeInfo.getTimeQuantity().intValue());

            // TODO - check effective/expiration dates

            // TODO - check feeInfo


            // TODO - check joints
            // TODO - check metaInfo

            assertEquals(2, retrievedCourse.getTermsOffered().size());

            String atpType = retrievedCourse.getTermsOffered().get(0);
            CluInstructorInfo instructor = retrievedCourse.getPrimaryInstructor();
                   


            assertTrue("termsOffered-48".equals(atpType) || "termsOffered-49".equals(atpType));

            assertEquals("orgId-42", instructor.getOrgId());
            assertEquals("personId-43", instructor.getPersonId());

            assertEquals("draft", retrievedCourse.getState());
            assertTrue(subjectAreaSet.contains(retrievedCourse.getSubjectArea()));

            assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getType());

            assertEquals(2,retrievedCourse.getCreditOptions().size());
            assertEquals("kuali.creditType.credit.degree.11",retrievedCourse.getCreditOptions().get(0).getId());
            assertEquals("kuali.creditType.credit.degree.11",retrievedCourse.getCreditOptions().get(1).getId());

            assertEquals(2,retrievedCourse.getGradingOptions().size());

            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-31"));
            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-32"));
            
            assertEquals(createdCourse.isPilotCourse(),cInfo.isPilotCourse());
            assertEquals(createdCourse.isSpecialTopicsCourse(), cInfo.isSpecialTopicsCourse());
            
            // TODO - check variotions
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateCourse() throws Exception {
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
            assertEquals("courseTitle-12", createdCourse.getCourseTitle());
            assertEquals(2, createdCourse.getUnitsContentOwner().size());
            assertEquals(4, createdCourse.getAttributes().size());

            // update some fields
            createdCourse.getUnitsContentOwner().clear();
            AdminOrgInfo testCurrOrg = new AdminOrgInfo();
            testCurrOrg.setOrgId("testOrgId");
            testCurrOrg.setType(CourseAssemblerConstants.SUBJECT_ORG);
            createdCourse.getUnitsContentOwner().add("testOrgId");

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
            ResultComponentInfo rsltComp = new ResultComponentInfo();
            rsltComp.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);
            rsltComp.getResultValues().add("1");
            rsltComp.getResultValues().add("3");
            createdCourse.getCreditOptions().add(rsltComp);
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

        assertEquals(1, updatedCourse.getUnitsContentOwner().size());
        assertEquals("testOrgId", updatedCourse.getUnitsContentOwner().get(0));

        assertEquals(5, updatedCourse.getAttributes().size());
        assertNotNull(updatedCourse.getAttributes().get("testKey"));
        assertEquals("testValue", updatedCourse.getAttributes().get("testKey"));
        
        assertEquals(2,updatedCourse.getCreditOptions().size());
//        assertTrue(updatedCourse.getCreditOptions().contains("creditOptions-18"));
//        assertTrue(updatedCourse.getCreditOptions().contains("NewCreditOption"));

        assertEquals(2,updatedCourse.getGradingOptions().size());

        assertTrue(updatedCourse.getGradingOptions().contains("gradingOptions-31"));
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
            
            //CourseInfo rInfo = courseService.getCourse(cInfo.getId());
            
            assertEquals("GRD", cInfo.getAttributes().get("finalExamStatus"));
            assertEquals("Some123description", cInfo.getAttributes().get("altFinalExamStatusDescr"));

        } catch (Exception e) {
            System.out.println ("caught exception: " + e.getClass ().getName ());
            System.out.println ("message: " + e.getMessage ());
            e.printStackTrace (System.out);
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
	
	@Test
	public void testCourseVersioning() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, IllegalVersionSequencingException{
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo);
        
        try{
        	courseService.createNewCourseVersion(createdCourse.getVersionInfo().getVersionIndId(), "test make a new version");
        	assertTrue(false);
        }catch(Exception e){
        	assertTrue(true);
        }
        
        //Make the created the current version
        courseService.setCurrentCourseVersion(createdCourse.getId(), null);
        
        CourseInfo newCourse = null; 
        try{
        	newCourse = courseService.createNewCourseVersion(createdCourse.getVersionInfo().getVersionIndId(), "test make a new version");
        	assertTrue(true);
        }catch(Exception e){
        	assertTrue(false);
        }
        
        
        assertNotNull(newCourse);
        
	}
}
