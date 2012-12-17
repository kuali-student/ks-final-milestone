package org.kuali.student.lum.course.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementOperator;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:course-test-context.xml"})
public class TestCourseServiceImpl{
   // @Client(value = "org.kuali.student.lum.course.service.impl.CourseServiceImpl", additionalContextFile = "classpath:course-test-context.xml")
    //public CourseService courseService;
    //public StatementService statementService;
    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
    @Autowired
    CourseService courseService;
    @Autowired
    StatementService statementService;

    Set<String> subjectAreaSet = new TreeSet<String>(Arrays.asList(CourseDataGenerator.subjectAreas));
    
    @Test
    public void testCourseServiceSetup() {
        assertNotNull(courseService);
        assertNotNull(statementService);
    }
 
    @Test
    public void testCreateCourse() throws Exception {
        System.out.println("testCreateCourse");
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = null;
        try {
            assertNotNull(cInfo = generator.getCourseTestData());
            CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);
            assertNotNull(createdCourse);
            assertEquals(DtoConstants.STATE_DRAFT, createdCourse.getStateKey());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getTypeKey());
            assertEquals(cInfo.getStartTerm(), createdCourse.getStartTerm());
            assertEquals(cInfo.getEndTerm(), createdCourse.getEndTerm());
        } catch (DataValidationErrorException e) {
            dumpValidationErrors(cInfo);
            fail("DataValidationError: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void dumpValidationErrors(CourseInfo cInfo) throws Exception {
        List<ValidationResultInfo> validationResults = courseService.validateCourse("SYSTEM", cInfo, contextInfo );
        for (ValidationResultInfo vr : validationResults) {
            System.out.println(vr.getElement() + " " + vr.getMessage());
        }
    }

  @Test
    public void testGetCourse() {
        System.out.println("testGetCourse");
        try {
            CourseDataGenerator generator = new CourseDataGenerator();
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            cInfo.setSpecialTopicsCourse(true);
            cInfo.setPilotCourse(true);
            cInfo.setCode("");
            CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);
            assertNotNull(createdCourse);

            // get it fresh from database
            CourseInfo retrievedCourse = courseService.getCourse(createdCourse.getId(), contextInfo);
            assertNotNull(retrievedCourse);

            // confirm it has the right contents
            assertEquals("323", retrievedCourse.getCourseNumberSuffix());

            assertEquals("level-36", retrievedCourse.getLevel());

            assertEquals("courseTitle-12", retrievedCourse.getCourseTitle());
            assertEquals("transcriptTitle-53", retrievedCourse.getTranscriptTitle());

            assertEquals("plain-18", retrievedCourse.getDescr().getPlain());
            assertEquals("formatted-17", retrievedCourse.getDescr().getFormatted());

            assertEquals(2, retrievedCourse.getFormats().size());
            FormatInfo info = retrievedCourse.getFormats().get(0);
            assertEquals("kuali.lu.type.CreditCourseFormatShell", info.getTypeKey());
            assertEquals(2, info.getActivities().size());
           assertTrue(info.getActivities().get(1).getTypeKey().startsWith("kuali.lu.type.activity."));

            assertEquals(2, retrievedCourse.getTermsOffered().size());
            String termOffered = retrievedCourse.getTermsOffered().get(0);

            assertTrue("termsOffered-51".equals(termOffered) || "termsOffered-52".equals(termOffered));

            assertEquals(2, retrievedCourse.getUnitsContentOwner().size());
            String orgId = retrievedCourse.getUnitsContentOwner().get(0);
            assertTrue("unitsContentOwner-57".equals(orgId) || "unitsContentOwner-58".equals(orgId));
            
           
            assertEquals(4, retrievedCourse.getAttributes().size());
            
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("key-4", "value-5");
            attrs.put("key-5", "value-6");
            for (String key : attrs.keySet()) {
                String value = retrievedCourse.getAttributeValue(key);
                assertNotNull(value);
                assertEquals(value, attrs.get(key));
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

            assertEquals("unitsDeployment-61", retrievedCourse.getUnitsDeployment().get(0));

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

            assertTrue("termsOffered-51".equals(atpType) || "termsOffered-52".equals(atpType));

            assertEquals("orgId-46", instructor.getOrgId());
            assertEquals("personId-47", instructor.getPersonId());

            assertEquals(DtoConstants.STATE_DRAFT, retrievedCourse.getStateKey());
            assertTrue(subjectAreaSet.contains(retrievedCourse.getSubjectArea()));

            assertEquals("kuali.lu.type.CreditCourse", retrievedCourse.getTypeKey());
//
//              assertEquals(2, retrievedCourse.getCreditOptions().size());
//            assertEquals("kuali.creditType.credit.degree.11.0", retrievedCourse.getCreditOptions().get(0));
//            assertEquals("kuali.creditType.credit.degree.11.0", retrievedCourse.getCreditOptions().get(1));

            assertEquals(2, retrievedCourse.getGradingOptions().size());

            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-31"));
            assertTrue(retrievedCourse.getGradingOptions().contains("gradingOptions-32"));
            assertEquals(createdCourse.isPilotCourse(), cInfo.isPilotCourse());
            assertEquals(createdCourse.isSpecialTopicsCourse(), cInfo.isSpecialTopicsCourse());

            // TODO - check variotions
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

   @Test
    public void testUpdateCourse() throws Exception {
        System.out.println("testUpdateCourse");

        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = null;
        CourseInfo retrievedCourse = null;
        CourseInfo updatedCourse = null;
        CourseInfo createdCourse = null;
        try {
            System.out.println("Getting test data...");
            cInfo = generator.getCourseTestData();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Got exception getting test data:" + ex.getMessage());
        }

        assertNotNull(cInfo);
        cInfo.setSpecialTopicsCourse(true);
        cInfo.setPilotCourse(true);
        try {
            System.out.println("creating course...");
            createdCourse = courseService.createCourse(cInfo, contextInfo );
        } catch (DataValidationErrorException e) {
            dumpValidationErrors(cInfo);
            fail("DataValidationError: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("failed creating course" + ":" + ex.getMessage());
        }
        int initialFormatCount = createdCourse.getFormats().size();

        // minimal sanity check
        assertNotNull(createdCourse);
        assertEquals("kuali.lu.type.CreditCourse", createdCourse.getTypeKey());
        assertEquals("courseTitle-12", createdCourse.getCourseTitle());
        assertEquals(2, createdCourse.getUnitsContentOwner().size());
        assertEquals(4, createdCourse.getAttributes().size());

        // update some fields
        createdCourse.getUnitsContentOwner().clear();
        AdminOrgInfo testCurrOrg = new AdminOrgInfo();
        testCurrOrg.setOrgId("testOrgId");
        testCurrOrg.setTypeKey(CourseAssemblerConstants.SUBJECT_ORG);
        createdCourse.getUnitsContentOwner().add("testOrgId");

        // Delete One Format
        createdCourse.getFormats().remove(0);

        // Delete One Activity from Existing Format
        assertEquals(2, createdCourse.getFormats().get(0).getActivities().size());
        createdCourse.getFormats().get(0).getActivities().remove(0);
        String updActFrmtId = createdCourse.getFormats().get(0).getId();

        // Add two New formats
        FormatInfo newFormat = new FormatInfo();
        newFormat.setTypeKey(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
        newFormat.setStateKey(DtoConstants.STATE_DRAFT);
        
        TimeAmountInfo timeInfo = new TimeAmountInfo();
        timeInfo.setAtpDurationTypeKey("kuali.atp.duration.Semester");
        timeInfo.setTimeQuantity(12);        
        newFormat.setDuration(timeInfo);
        
        List<String> termsOfferedList = new ArrayList<String>();
        termsOfferedList.add("FALL2010");        
        newFormat.setTermsOffered(termsOfferedList);
        
        AttributeInfo rAttributeInfo= new AttributeInfo();
        rAttributeInfo.setKey("FRMT");
        rAttributeInfo.setValue("value");   
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add(rAttributeInfo);
        newFormat.setAttributes(attributes);

        // Add two new activities to new formats
        ActivityInfo newActivity1 = new ActivityInfo();
        newActivity1.setTypeKey(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE);
        newActivity1.setStateKey(DtoConstants.STATE_DRAFT);
        newFormat.getActivities().add(newActivity1);

        ActivityInfo newActivity2 = new ActivityInfo();
        newActivity2.setTypeKey(CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE);
        newActivity2.setStateKey(DtoConstants.STATE_DRAFT);
        newFormat.getActivities().add(newActivity2);

        createdCourse.getFormats().add(newFormat);

        FormatInfo newFormat2 = new FormatInfo();
        newFormat2.setTypeKey(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
        newFormat2.setStateKey(DtoConstants.STATE_DRAFT);
        createdCourse.getFormats().add(newFormat2);

        //Map<String, String> attributes = createdCourse.getAttributes();
        //attributes.put("testKey", "testValue");
       // createdCourse.setAttributes(attributes);
        
        AttributeInfo rAttributeInfo2 = new AttributeInfo();
        rAttributeInfo2.setKey("testKey");
        rAttributeInfo2.setValue("testValue");   
        List<AttributeInfo> attributes2 =  createdCourse.getAttributes();
        attributes2.add(rAttributeInfo2);
        createdCourse.setAttributes(attributes2);

        createdCourse.getCreditOptions().remove(1);
        ResultValuesGroupInfo rsltComp = new ResultValuesGroupInfo();
        rsltComp.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);
        rsltComp.getResultValueKeys().add("1");
        rsltComp.getResultValueKeys().add("3");
        createdCourse.getCreditOptions().add(rsltComp); 
        createdCourse.getGradingOptions().remove(1);
        createdCourse.getGradingOptions().add("NewGradingOption");

        createdCourse.setSpecialTopicsCourse(false);
        createdCourse.setPilotCourse(false);

        createdCourse.getCourseSpecificLOs().get(0).getLoInfo().getDescr().setPlain("UPDATED!!!");
        createdCourse.getCourseSpecificLOs().remove(1);
        LoDisplayInfo displayInfo = new LoDisplayInfo();
        displayInfo.setLoInfo(new LoInfo());
        displayInfo.getLoInfo().setDescr(new RichTextInfo());
        createdCourse.getCourseSpecificLOs().add(displayInfo);
        createdCourse.getCourseSpecificLOs().get(1).getLoInfo().getDescr().setPlain("BrandNew!!!");
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
        
        createdCourse.setSubjectArea(null);
        createdCourse.setCode("UpdatedCode100");
        createdCourse.setLevel("Level100");        
        
        // Perform the update
        try {
            System.out.println("updating course...");
            updatedCourse = courseService.updateCourse(updActFrmtId, createdCourse, contextInfo);
        } catch (DataValidationErrorException e) {
            dumpValidationErrors(createdCourse);
            fail("DataValidationError: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("failed updating course: " + ex.getMessage());
        }
        assertEquals(initialFormatCount + 1, updatedCourse.getFormats().size());

        for (FormatInfo uFrmt : updatedCourse.getFormats()) {
            // Check to see if activities are added to a new format
            if (uFrmt.getAttributes().contains("FRMT")) {
                assertEquals(2, uFrmt.getActivities().size());
                String actType = uFrmt.getActivities().get(0).getTypeKey();
                assertTrue(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE.equals(actType) || CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE.equals(actType));

                assertEquals(1, uFrmt.getTermsOffered().size());
                assertEquals("FALL2010", uFrmt.getTermsOffered().get(0));
                
                TimeAmountInfo tIfo = uFrmt.getDuration();
                assertNotNull(tIfo);
                assertEquals((int)12, (int) tIfo.getTimeQuantity());
            }

            // Check to see if activity is deleted from an existing format
            if (updActFrmtId.equals(uFrmt.getId())) {
                assertEquals(1, uFrmt.getActivities().size());
            }
        }
        // Test what was returned by updateCourse
        verifyUpdate(updatedCourse);

        // Now explicitly get it
        try {
            System.out.println("Getting course again...");
            retrievedCourse = courseService.getCourse(createdCourse.getId(), contextInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("failed getting course again:" + ex.getMessage());
        }
        verifyUpdate(retrievedCourse);

        // and test for optimistic lock exception
        // NOTE: CourseService.updateCourse(CourseInfo courseInfo) modifies its parameter,
        // as the 'results' BusinessDTORef (our CourseInfo) is simply updated to reflect
        // the new contents of the updated Clu (see the
        // results.getAssembler().assemble(updatedClu, results.getBusinessDTORef(), true);
        // line in CourseServiceMethodInvoker.invokeServiceCalls()
        int currVersion = Integer.parseInt(retrievedCourse.getMeta().getVersionInd());
        if (currVersion > 0) {
            retrievedCourse.getMeta().setVersionInd(Integer.toString(--currVersion));
        }
        try {
            System.out.println("Updating course again trying to get a version mismatch...");
            courseService.updateCourse(updActFrmtId, retrievedCourse, contextInfo);
            fail("Failed to throw VersionMismatchException");
        } catch (VersionMismatchException e) {
            System.out.println("Correctly received " + e.getMessage());
        } catch (DataValidationErrorException e) {
            dumpValidationErrors(retrievedCourse);
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
        assertNotNull(updatedCourse.getAttributes().contains("testKey"));
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("testKey", "testValue");
        for (AttributeInfo atr : updatedCourse.getAttributes()) {
            if (attrs.containsKey(atr.getKey())){
            String value = atr.getValue();
            assertEquals(value, attrs.get(atr.getKey()));
            }
        }            

        assertEquals(2, updatedCourse.getCreditOptions().size());
        // assertTrue(updatedCourse.getCreditOptions().contains("creditOptions-18"));
        // assertTrue(updatedCourse.getCreditOptions().contains("NewCreditOption"));

        assertEquals(2, updatedCourse.getGradingOptions().size());

        assertTrue(updatedCourse.getGradingOptions().contains("gradingOptions-31"));
        assertTrue(updatedCourse.getGradingOptions().contains("NewGradingOption"));

        assertFalse(updatedCourse.isSpecialTopicsCourse());
        assertFalse(updatedCourse.isPilotCourse());

        assertNull(updatedCourse.getSubjectArea());
        assertEquals("UpdatedCode100", updatedCourse.getCode());
        assertEquals("Level100", updatedCourse.getLevel());
        assertEquals("NEWJUSTIFICATION", updatedCourse.getFeeJustification().getFormatted());
        assertEquals("UpdatedFeeType", updatedCourse.getFees().get(0).getFeeType());
        assertEquals(Integer.valueOf(10), updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyQuantity());
        assertEquals("PESOS", updatedCourse.getFees().get(0).getFeeAmounts().get(0).getCurrencyTypeKey());
        assertEquals("NEWORG", updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getOrgId());
        assertEquals(Long.valueOf(99), updatedCourse.getRevenues().get(0).getAffiliatedOrgs().get(0).getPercentage());
    }

    @Test
    public void testDeleteCourse() {
        System.out.println("testDeleteCourse");
        try {
            CourseDataGenerator generator = new CourseDataGenerator();
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);
            assertNotNull(createdCourse);
            assertEquals(DtoConstants.STATE_DRAFT, createdCourse.getStateKey());
            assertEquals("kuali.lu.type.CreditCourse", createdCourse.getTypeKey());
            String courseId = createdCourse.getId();
            CourseInfo retrievedCourse = courseService.getCourse(courseId, contextInfo);
            assertNotNull(retrievedCourse);

            courseService.deleteCourse(courseId, contextInfo);
            try {
                retrievedCourse = courseService.getCourse(courseId, contextInfo);
                fail("Retrieval of deleted course should have thrown exception");
            } catch (DoesNotExistException e) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * 
     * This method tests setting code, attributes in course cross listing
     *
     */
   @Test
    public void testCourseCrossListing() {
        CourseDataGenerator generator = new CourseDataGenerator();
        try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);

           
            CourseCrossListingInfo ccInfo = new CourseCrossListingInfo();
            ccInfo.setCourseNumberSuffix("100");
            ccInfo.setSubjectArea("CHEM");
            
         // Map<String, String> da = new HashMap<String, String>();
         //   da.put("KEY1", "VALUE1");
            
            AttributeInfo rAttributeInfo= new AttributeInfo();
            rAttributeInfo.setKey("KEY1");
            rAttributeInfo.setValue("VALUE1");   
            List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
            attributes.add(rAttributeInfo);
            ccInfo.setAttributes(attributes);
            
            
            CourseCrossListingInfo ccInfo1 = new CourseCrossListingInfo();
            ccInfo1.setCourseNumberSuffix("200");
            ccInfo1.setSubjectArea("MATH");
            ccInfo1.setCode("LIFE042");

            List<CourseCrossListingInfo> ccList = new ArrayList<CourseCrossListingInfo>();
            ccList.add(ccInfo);
            ccList.add(ccInfo1);

            cInfo.setCrossListings(ccList);
            
            try {
                cInfo = courseService.createCourse(cInfo, contextInfo);
            } catch (DataValidationErrorException e) {
                dumpValidationErrors(cInfo);
                fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                fail("failed creating course:" + e.getMessage());
            }
            
            CourseInfo rcInfo = courseService.getCourse(cInfo.getId(), contextInfo);
            
            assertEquals(2,rcInfo.getCrossListings().size());
            
            for(CourseCrossListingInfo rcc : rcInfo.getCrossListings()) {
                
                if("100".equals(rcc.getCourseNumberSuffix())) {
                    Map<String, String> attrs = new HashMap<String, String>();
                    attrs.put("KEY1", "VALUE1");
                    for (AttributeInfo atr : rcc.getAttributes()) {
                        if (attrs.containsKey(atr.getKey())){
                        String value = atr.getValue();
                        assertEquals(value, attrs.get(atr.getKey()));
                        }
                    }            
                  //assertEquals("VALUE1", rcc.getAttributes().contains("KEY1"));
                } else {
                    assertEquals("LIFE042", rcc.getCode());
                }                
            }
            
        } catch (Exception e) {
            System.out.println("caught exception: " + e.getClass().getName());
            System.out.println("message: " + e.getMessage());
            e.printStackTrace(System.out);
            e.printStackTrace();
            fail(e.getMessage());
        }        
            
    }
    
   @Test
    public void testCreditOptions() {
        CourseDataGenerator generator = new CourseDataGenerator();
        try {
            CourseInfo cInfo = generator.getCourseTestData();
            assertNotNull(cInfo);
            
            // Check to see if variable credit with float increment works
            ResultValuesGroupInfo rc1 = new ResultValuesGroupInfo();
            rc1.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE);
            ResultValueRangeInfo resultValueRangeInfo = new ResultValueRangeInfo();
            resultValueRangeInfo.setMinValue("5.0");
            resultValueRangeInfo.setMaxValue("6.0");
            resultValueRangeInfo.setIncrement("0.5");
            rc1.setResultValueRange(resultValueRangeInfo);
            
            // Check to see if variable credit with no increments
            ResultValuesGroupInfo rc2 = new ResultValuesGroupInfo();
            rc2.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE);
            ResultValueRangeInfo resultValueRangeInfo2 = new ResultValueRangeInfo();
            resultValueRangeInfo2.setMinValue("1.0");
            resultValueRangeInfo2.setMaxValue("5.0");
            rc2.setResultValueRange(resultValueRangeInfo2);
            
            // Check to see floating point multiple is accepted
            ResultValuesGroupInfo rc3 = new ResultValuesGroupInfo();
            rc3.setTypeKey(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);
            List<String> rv = new ArrayList<String>();
            rv.add("1.0");
            rv.add("1.5");
            rv.add("2.0");
            rc3.setResultValueKeys(rv);
            

            List<ResultValuesGroupInfo> creditOptions = new ArrayList<ResultValuesGroupInfo>();
            creditOptions.add(rc1);
            creditOptions.add(rc2);
            creditOptions.add(rc3);
                        
            cInfo.setCreditOptions(creditOptions);
                        
            try {
                cInfo = courseService.createCourse(cInfo, contextInfo);
            } catch (DataValidationErrorException e) {
                dumpValidationErrors(cInfo);
                fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                fail("failed creating course:" + e.getMessage());
            }
            
            CourseInfo rcInfo = courseService.getCourse(cInfo.getId(), contextInfo);
            
            List<ResultValuesGroupInfo> co = rcInfo.getCreditOptions(); 
            
            assertEquals(3, co.size());

            // Check to see if multiple was set properly
            for(ResultValuesGroupInfo rc : co) { 
                if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE.equals(rc.getTypeKey())){
                    assertEquals(3, rc.getResultValueKeys().size());
                    assertTrue(rc.getResultValueKeys().contains("1.0"));
                    assertTrue(rc.getResultValueKeys().contains("1.5"));
                    assertTrue(rc.getResultValueKeys().contains("2.0"));
                }
                
                if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE.equals(rc.getTypeKey())){
                    if("kuali.creditType.credit.degree.1.0-5.0".equals(rc.getKey())) {
                        assertEquals(5, rc.getResultValueKeys().size());
                        assertTrue(rc.getResultValueKeys().contains("2.0"));
                    } else {                        
                        assertEquals(3, rc.getResultValueKeys().size());
                        assertTrue(rc.getResultValueKeys().contains("5.5"));
                    }
                }                
            }
                        
            
        } catch (Exception e) {
            System.out.println("caught exception: " + e.getClass().getName());
            System.out.println("message: " + e.getMessage());
            e.printStackTrace(System.out);
            e.printStackTrace();
            fail(e.getMessage());
        }        
    }
    
  //  @Test
    public void testDynamicAttributes() {
        System.out.println("testDynamicAttributes");
        CourseDataGenerator generator = new CourseDataGenerator();
        try {
            CourseInfo cInfo = generator.getCourseTestData();
                        
            assertNotNull(cInfo);

            AttributeInfo rAttributeInfo = new AttributeInfo("finalExamStatus", "GRD");
            AttributeInfo rAttributeInfo2 = new AttributeInfo("altFinalExamStatusDescr", "Some123description");
            AttributeInfo rAttributeInfo3 = new AttributeInfo("proposalTitle", "proposalTitle-1");
            AttributeInfo rAttributeInfo4 = new AttributeInfo("proposalRationale", "proposalRationale");
                        
            List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
            attributes.add(rAttributeInfo);
            attributes.add(rAttributeInfo2);
            attributes.add(rAttributeInfo3);
            attributes.add(rAttributeInfo4);
            cInfo.setAttributes(attributes);

            FormatInfo fInfo = new FormatInfo();
            fInfo.setTypeKey(CourseAssemblerConstants.COURSE_FORMAT_TYPE);
            ActivityInfo aInfo = new ActivityInfo();
            aInfo.setTypeKey(CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE);
            
            //Map<String, String> activityAttrs = new HashMap<String, String>();
           // activityAttrs.put("ACTIVITY_KEY", "ACTIVITY_VALUE");
            //aInfo.setAttributes(attrMap);
            
            AttributeInfo activityAttrs= new AttributeInfo();
            activityAttrs.setKey("ACTIVITY_KEY");
            activityAttrs.setValue("ACTIVITY_VALUE");   
            List<AttributeInfo> attrMap = new ArrayList<AttributeInfo>();
            attrMap.add(rAttributeInfo);
            aInfo.setAttributes(attrMap);
            List<ActivityInfo> activities = new ArrayList<ActivityInfo>();
            activities.add(aInfo);                       
            fInfo.setActivities(activities);
           
            List<FormatInfo> formats = new ArrayList<FormatInfo>();
            formats.add(fInfo);
            
            cInfo.setFormats(formats);
            
            try {
                cInfo = courseService.createCourse(cInfo, contextInfo);
            } catch (DataValidationErrorException e) {
                dumpValidationErrors(cInfo);
                fail("DataValidationError: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                fail("failed creating course:" + e.getMessage());
            }
            // Check in CluService if the attributes are mapped properly

            // CourseInfo rInfo = courseService.getCourse(cInfo.getId());
           
            assertEquals("GRD", cInfo.getAttributes().contains("finalExamStatus"));
            assertEquals("Some123description", cInfo.getAttributes().contains("altFinalExamStatusDescr"));

            
            // Check if the attributes are being set in the activity
            assertEquals("ACTIVITY_VALUE", cInfo.getFormats().get(0).getActivities().get(0).getAttributes().contains("ACTIVITY_KEY"));
            
        } catch (Exception e) {
            System.out.println("caught exception: " + e.getClass().getName());
            System.out.println("message: " + e.getMessage());
            e.printStackTrace(System.out);
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testCluIsUpdated() {

    }

    @Test
    public void testGetMetadata() {
        System.out.println("testGetMetadata");
        MetadataServiceImpl metadataService = new MetadataServiceImpl(courseService);
        metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.r2.lum.course.dto.CourseInfo");

        Map<String, Metadata> properties = metadata.getProperties();
        assertTrue(properties.size() > 0);

        assertTrue(properties.containsKey("stateKey"));
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
    public void testCourseVersioning() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, IllegalVersionSequencingException {
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        CourseInfo newCourse = null;
        try {
            newCourse = courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test make a new version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        assertNotNull(newCourse);
        
        
        // test that creating a new course version copies over statements
        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        StatementTreeViewInfo createdTree =  R1R2ConverterUtil.convert(courseService.createCourseStatement(createdCourse.getId(), R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class) ;
        assertNotNull(createdTree);
        
        CourseInfo newVersion = null;
        
        try {
            newVersion = courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test make a new version for statements", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertNotNull(newVersion);
        

    }

    @Test
    public void testGetCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        String courseId = "COURSE-STMT-1";
        List<StatementTreeViewInfo> courseStatements =  R1R2ConverterUtil.convertLists(courseService.getCourseStatements(courseId, null, null, contextInfo),StatementTreeViewInfo.class);
        assertEquals(2, courseStatements.size());
        for (StatementTreeViewInfo tree : courseStatements) {
            checkTreeView(tree, false);
        }
        
        // test that the proper error message occurs if an invalid Clu id is attempted
        String credentialProgramId = "d02dbbd3-20e2-410d-ab52-1bd6d362748b";
        
        try {
            courseService.getCourseStatements(credentialProgramId, null, null, contextInfo);
            assertTrue(false);
        }
        catch(DoesNotExistException e) {
            // we should reach here, since the exception should trigger
            assertTrue(true);
        }
    }

    @Test
    @Ignore
    // FIXME (kscm-712)
    public void testGetCourseStatement_nl() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        String courseId = "COURSE-STMT-1";
        String nlUsageTypeKey = "KUALI.RULE";
        String language = "en";
        List<StatementTreeViewInfo> courseStatements = R1R2ConverterUtil.convertLists(courseService.getCourseStatements(courseId, nlUsageTypeKey, language, contextInfo),StatementTreeViewInfo.class);
        assertEquals(2, courseStatements.size());
        for (StatementTreeViewInfo tree : courseStatements) {
            checkTreeView(tree, true);
        }
    }

    @Test
    @Ignore   //fixme (kscm-712)
    public void testCreateCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        StatementTreeViewInfo createdTree =  R1R2ConverterUtil.convert(courseService.createCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class);
        assertNotNull(createdTree);
        assertEquals(2, createdTree.getStatements().size());
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateCourseStatement_duplicateTree() throws Exception {
        String courseId = "COURSE-STMT-1";
        String nlUsageTypeKey = "KUALI.RULE";
        String language = "en";
        List<StatementTreeViewInfo> courseStatements = R1R2ConverterUtil.convertLists(courseService.getCourseStatements(courseId, nlUsageTypeKey, language, contextInfo), StatementTreeViewInfo.class);
        R1R2ConverterUtil.convert(courseService.createCourseStatement(courseId, R1R2ConverterUtil.convert(courseStatements.get(0),org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo), StatementTreeViewInfo.class);
       
    }

    @Test(expected = MissingParameterException.class)
    public void testCreateCourseStatement_nullCourseId() throws Exception {

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        @SuppressWarnings("unused")
        StatementTreeViewInfo createdTree = R1R2ConverterUtil.convert(courseService.createCourseStatement(null, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class);
    }

   @Test(expected = MissingParameterException.class)
    public void testCreateCourseStatement_nullTree() throws Exception {
        String courseId = "COURSE-STMT-1";

        @SuppressWarnings("unused")
        StatementTreeViewInfo createdTree = R1R2ConverterUtil.convert(courseService.createCourseStatement(courseId, null, contextInfo),StatementTreeViewInfo.class);
    }

    @Test(expected = DoesNotExistException.class)
    public void testDeleteCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        StatementTreeViewInfo createdTree = R1R2ConverterUtil.convert(courseService.createCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class);
        StatusInfo status = courseService.deleteCourseStatement(courseId, R1R2ConverterUtil.convert(createdTree, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        assertTrue(status.getIsSuccess());
        List<StatementTreeViewInfo> statements = R1R2ConverterUtil.convertLists(courseService.getCourseStatements(courseId, null, null, contextInfo),StatementTreeViewInfo.class);
        for (StatementTreeViewInfo statement : statements) {
            if (statement.getId().equals(createdTree.getId())) {
                fail("StatementTree not deleted from course");
            }
        }
        statementService.getStatementTreeView(createdTree.getId());
    }

    @Test(expected = DoesNotExistException.class)
    public void testDeleteCourseStatement_badTree() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        courseService.deleteCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        
    }

    @Test(expected = DoesNotExistException.class)
    public void testDeleteCourseStatement_badCourse() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        courseService.deleteCourseStatement("xxx", R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        
    }

    @Test(expected = MissingParameterException.class)
    public void testDeleteCourseStatement_nullCourseId() throws Exception {
        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        courseService.deleteCourseStatement(null, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
    }

    @Test(expected = MissingParameterException.class)
    public void testDeleteCourseStatement_nullTreeId() throws Exception {
        courseService.deleteCourseStatement("xxx", null, contextInfo);
    }

    @Test
    public void testUpdateCourseStatement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        StatementTreeViewInfo createdTree = R1R2ConverterUtil.convert(courseService.createCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class) ;

        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDescr(toRichText("REQCOMP-1"));
        rc1.setTypeKey("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDescr(toRichText("REQCOMP-2"));
        rc2.setTypeKey("kuali.reqComponent.type.course.courseset.gpa.min");
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDescr(toRichText("STMT-5"));
        subTree1.setOperator(StatementOperator.AND);
        subTree1.setTypeKey("kuali.statement.type.program.entrance");
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);

        StatementTreeViewInfo oldSubTree1 = createdTree.getStatements().get(0);
        createdTree.getStatements().set(0, subTree1);
        StatementTreeViewInfo updatedTree = R1R2ConverterUtil.convert(courseService.updateCourseStatement(courseId, courseId, R1R2ConverterUtil.convert(createdTree, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo),StatementTreeViewInfo.class) ;
        assertEquals(createdTree.getStatements().get(0).getDescr().getPlain(), updatedTree.getStatements().get(0).getDescr().getPlain());

    }

    @Test
    @Ignore
    // FIXME need a dictionary that defines StatamentTreeViewInfo  (kscm-712)
    public void testValidataCourseStatement() throws Exception {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        courseService.validateCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        List<ValidationResultInfo> validations = courseService.validateCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        assertTrue(isEmpty(validations));
    }

    @Test
    @Ignore
    // FIXME need a dictionary that defines StatamentTreeViewInfo (kscm-712)
    public void testValidataCourseStatement_invalidStatement() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        final String courseId = "COURSE-STMT-1";

        StatementTreeViewInfo statementTreeViewInfo = createStatementTree();
        statementTreeViewInfo.setTypeKey("an.example.of.a.bad.statementType");
        statementTreeViewInfo.getStatements().get(0).setTypeKey("fictional.program");
        statementTreeViewInfo.getStatements().get(0).getReqComponents().set(0, createBadReqComponent());
        List<ValidationResultInfo> validations = courseService.validateCourseStatement(courseId, R1R2ConverterUtil.convert(statementTreeViewInfo, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class), contextInfo);
        assertFalse(isEmpty(validations));
    }

    private static ReqComponentInfo createBadReqComponent() {
        ReqComponentInfo reqCompInfo = new ReqComponentInfo();
        // reqCompInfo.setId("REQCOMP-NL-X");
        reqCompInfo.setId("1234567890123456789012345678901234567890");
        reqCompInfo.setTypeKey("kuali.reqComponent.type.courseList.nof");
        reqCompInfo.setStateKey("Active");

        List<ReqCompFieldInfo> fieldList = new ArrayList<ReqCompFieldInfo>();

        ReqCompFieldInfo field1 = new ReqCompFieldInfo();
        field1.setId("1234567890123456789012345678901234567890");
        field1.setTypeKey("kuali.reqComponent.field.type.operator");
        field1.setValue("-1");
        fieldList.add(field1);

        ReqCompFieldInfo field2 = new ReqCompFieldInfo();
        field2.setId("2");
        field2.setTypeKey("kuali.reqComponent.field.type.operator");
        field2.setValue("greater_than_or_equal_to42");
        fieldList.add(field2);

        ReqCompFieldInfo field3 = new ReqCompFieldInfo();
        field3.setId("3");
        field3.setTypeKey("kuali.reqComponent.field.type.cluSet.id");
        field3.setValue("CLUSET-NL-Y");
        fieldList.add(field3);

        reqCompInfo.setReqCompFields(fieldList);
        return reqCompInfo;
    }

    private static StatementTreeViewInfo createStatementTree() {
        // Statement Tree
        // --------- STMT-1:OR ---------
        // | |
        // STMT-2:AND STMT-3:AND
        // | | | |
        // REQCOMP-1 REQCOMP-2 REQCOMP-3 REQCOMP-4

        List<StatementTreeViewInfo> subStatements = new ArrayList<StatementTreeViewInfo>(3);
        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        List<ReqComponentInfo> reqCompList2 = new ArrayList<ReqComponentInfo>(3);

        // req components
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDescr(toRichText("REQCOMP-1"));
        rc1.setTypeKey("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDescr(toRichText("REQCOMP-2"));
        rc2.setTypeKey("kuali.reqComponent.type.course.courseset.gpa.min");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDescr(toRichText("REQCOMP-3"));
        rc3.setTypeKey("kuali.reqComponent.type.course.courseset.completed.nof");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDescr(toRichText("REQCOMP-4"));
        rc4.setTypeKey("kuali.reqComponent.type.course.permission.instructor.required");

        // statement tree views
      
        
        StatementTreeViewInfo statementTree = new StatementTreeViewInfo();

        statementTree.setDescr(toRichText("STMT-1"));
        statementTree.setOperator(StatementOperator.OR);
        //statementTree.setTypeKey("kuali.statement.type.program.entrance");
        statementTree.setTypeKey("kuali.statement.type.course.academicReadiness.coreq");

        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDescr(toRichText("STMT-2"));
        subTree1.setOperator(StatementOperator.AND);
        //ssubTree1.setType("kuali.statement.type.program.entrance");
        subTree1.setTypeKey("kuali.statement.type.course.recommendedPreparation");

        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTree2.setDescr(toRichText("STMT-3"));
        subTree2.setOperator(StatementOperator.AND);
        //subTree2.setType("kuali.statement.type.program.entrance");
        subTree2.setTypeKey("kuali.statement.type.course.academicReadiness.antireq");

        // construct tree with statements and req components
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);
        reqCompList2.add(rc3);
        reqCompList2.add(rc4);
        subTree2.setReqComponents(reqCompList2);
        subStatements.add(subTree1);
        subStatements.add(subTree2);
        statementTree.setStatements(subStatements);

        return statementTree;
    }

    private static RichTextInfo toRichText(String text) {
        RichTextInfo richTextInfo = new RichTextInfo();
        if (text == null) {
            return null;
        }
        richTextInfo.setPlain(text);
        richTextInfo.setFormatted("<p>" + text + "</p>");
        return richTextInfo;
    }

    private static void checkTreeView(final StatementTreeViewInfo rootTree, final boolean checkNaturalLanguage) {
        assertNotNull(rootTree);
        List<StatementTreeViewInfo> subTreeView = rootTree.getStatements();
        assertNotNull(subTreeView);
        assertEquals(2, subTreeView.size());
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);

        // Check root tree
        assertNotNull(rootTree);
        assertEquals(2, subTreeView.size());
        assertNotNull(subTree1);
        assertNotNull(subTree2);

        // Check reqComps of sub-tree 1
        assertEquals("STMT-TV-2", subTree1.getId());
        assertEquals(2, subTree1.getReqComponents().size());
        assertEquals("REQCOMP-TV-1", subTree1.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-2", subTree1.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
            assertEquals("Student must have completed all of MATH 152, MATH 180", subTree1.getReqComponents().get(0).getNaturalLanguageTranslation());
            assertEquals("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180", subTree1.getReqComponents().get(1).getNaturalLanguageTranslation());
        }

        // Check reqComps of sub-tree 2
        assertEquals("STMT-TV-3", subTree2.getId());
        assertEquals(2, subTree2.getReqComponents().size());
        assertEquals("REQCOMP-TV-3", subTree2.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-4", subTree2.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
            assertEquals("Student must have completed 1 of MATH 152, MATH 180", subTree2.getReqComponents().get(0).getNaturalLanguageTranslation());
            assertEquals("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180", subTree2.getReqComponents().get(1).getNaturalLanguageTranslation());
        }
    }
    
    /**
     * 
     * This method checks for an UnsupportedOperationException to be thrown from the methods in the created list.
     * 
     */
    @Test
    public void testExpectedUnsupported() throws Exception {
        String[] unsupportedOperations = {"getCourseActivities", "getCourseFormats", "getCourseLos"};
        
        Collection<ServiceMethodInvocationData> methods = new ArrayList<ServiceMethodInvocationData>(unsupportedOperations.length);
        for(String s : unsupportedOperations) {
            ServiceMethodInvocationData invocationData = new ServiceMethodInvocationData();
            invocationData.methodName = s;
            invocationData.parameters = new Object[1];
            
            // all the parameter types for these methods are the same
            invocationData.paramterTypes = new Class<?>[] {String.class};
            methods.add(invocationData);
        }
        
        invokeForExpectedException(methods, UnsupportedOperationException.class);
    }

    private class ServiceMethodInvocationData {
        String methodName;
        Object[] parameters;
        Class<?>[] paramterTypes;
    }
    
    private void invokeForExpectedException(Collection<ServiceMethodInvocationData> methods, Class<? extends Exception> expectedExceptionClass) throws Exception {
        for(ServiceMethodInvocationData methodData : methods) {
            Method method = courseService.getClass().getMethod(methodData.methodName, methodData.paramterTypes);
            Throwable expected = null;
            Exception unexpected = null;
            try {
                method.invoke(courseService, methodData.parameters);
            }
            catch(InvocationTargetException ex) {
                if(ex.getCause() != null && ex.getCause().getClass().equals(expectedExceptionClass)) {
                    expected = ex.getCause();
                }
                else {
                    unexpected = ex;
                    unexpected.printStackTrace();
                }
            }
            catch(Exception other) {
                unexpected = other;
            }
            finally {
                assertNotNull("An exception of class: " + expectedExceptionClass.toString() + " was expected, but the method: " + methodData.methodName + " threw this exception: " + unexpected, expected);
            }
        }
    }
    
    @Test
    public void testGetVersionMethodsForInvalidParameters() throws Exception {
        String[] getVersionMethods = {"getVersionBySequenceNumber", "getVersions", "getFirstVersion", "getVersionsInDateRange", "getCurrentVersion", "getCurrentVersionOnDate"};
        
        // build an object array with the appropriate number of arguments for each version method to be called
        Object[][] getVersionParams = {new Object[4], new Object[3], new Object[3], new Object[5], new Object[3], new Object[4]};
        
        // build a class array with the parameter types for each method call
        Class<?>[][] getVersionParamTypes = {{String.class, String.class, Long.class, ContextInfo.class}, // for getVersionBySequenceNumber
                {String.class, String.class, ContextInfo.class}, // for getVersions
                {String.class, String.class, ContextInfo.class}, // for getFirstVersion
                {String.class, String.class, Date.class, Date.class,  ContextInfo.class}, // for getVersionsInDateRange
                {String.class, String.class, ContextInfo.class}, // for getCurrentVersion
                {String.class, String.class, Date.class, ContextInfo.class}}; // for getCurrentVersionOnDate
        
        String badRefObjectTypeURI = "BADBADBAD";
        Collection<ServiceMethodInvocationData> methods = new ArrayList<ServiceMethodInvocationData>(getVersionMethods.length);
        for(int i = 0; i < getVersionMethods.length; i++) {
            ServiceMethodInvocationData invocationData = new ServiceMethodInvocationData();
            invocationData.methodName = getVersionMethods[i];
            
            // set the first parameter of each invocation to the invalid data
            getVersionParams[i][0] = badRefObjectTypeURI;
            
            invocationData.parameters = getVersionParams[i];
            invocationData.paramterTypes = getVersionParamTypes[i];
            
            methods.add(invocationData);
        }
        
        invokeForExpectedException(methods, InvalidParameterException.class);
    }
    
   @Test
    public void testGetCurrentVersion() throws Exception {
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        try {
            courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        VersionDisplayInfo versionInfo = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), contextInfo);
        
        assertNotNull(versionInfo);
        assertEquals(createdCourse.getVersion().getSequenceNumber(),versionInfo.getSequenceNumber());
    }
    
    @Test
    public void testGetCurrentVersionOnDate() throws Exception {
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        VersionDisplayInfo versionInfo = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), contextInfo);
        
        assertNotNull(versionInfo);
        assertEquals(createdCourse.getVersion().getSequenceNumber(),versionInfo.getSequenceNumber());
        
        
        // make a second version of the course, set it to be the current version a month in the future, and ensure that getting today's version gets the one that was created first
        CourseInfo cInfo2 = null;
        try {
            cInfo2 = courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version by date", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        
        // Make the created the current version one month from now
        courseService.setCurrentCourseVersion(cInfo2.getId(), cal.getTime(), contextInfo);
        
        // make sure when we get the current version for today, it still returns the first one created
        versionInfo = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, cInfo2.getVersion().getVersionIndId(),contextInfo);
        
        assertNotNull(versionInfo);
        assertEquals(createdCourse.getVersion().getSequenceNumber(), versionInfo.getSequenceNumber());
    }
    
    @Test
    public void testGetVersions() throws Exception {
        
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        List<VersionDisplayInfo> versions = courseService.getVersions(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), contextInfo);
        
        assertEquals(1, versions.size());
        
        try {
            courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        versions = courseService.getVersions(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), contextInfo);
        
        assertEquals(2, versions.size());
    }
    
    @Test
    public void testGetFirstVersion() throws Exception {
        
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        try {
            courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
       // VersionDisplayInfo firstVersion = courseService.getFirstVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId());
        
        //assertEquals(firstVersion.getSequenceNumber(), createdCourse.getVersion().getSequenceNumber());
    }
    
   @Test
    public void testGetVersionBySequenceNumber() throws Exception {
        
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        CourseInfo version2 = null;
        try {
            version2 = courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        //VersionDisplayInfo secondVersion = courseService.getVersionBySequenceNumber(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), version2.getVersion().getSequenceNumber());
        
        //assertEquals(secondVersion.getSequenceNumber(), version2.getVersion().getSequenceNumber());
    }
    
    @Test
    public void testGetVersionsInDateRange() throws Exception {
        CourseDataGenerator generator = new CourseDataGenerator();
        CourseInfo cInfo = generator.getCourseTestData();
        CourseInfo createdCourse = courseService.createCourse(cInfo, contextInfo);

        VersionDisplayInfo versionInfo = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), contextInfo);
        
        assertNotNull(versionInfo);
        assertEquals(createdCourse.getVersion().getSequenceNumber(),versionInfo.getSequenceNumber());
        
        
        // make a second version of the course, set it to be the current version a month in the future, and ensure that getting today's version gets the one that was created first
        CourseInfo cInfo2 = null;
        try {
            cInfo2 = courseService.createNewCourseVersion(createdCourse.getVersion().getVersionIndId(), "test getting version by date", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        
        // Make the created the current version one month from now
        courseService.setCurrentCourseVersion(cInfo2.getId(), cal.getTime(), contextInfo);
        
        // ensure that when retrieving versions from yesterday to tomorrow, we get only the first created version
        Calendar rangeInstance = Calendar.getInstance();
        rangeInstance.add(Calendar.DATE, -1);
        Date yesterday = rangeInstance.getTime();
        
        rangeInstance.add(Calendar.DATE, 2);
        Date tomorrow = rangeInstance.getTime();
        
        //List<VersionDisplayInfo> versions = courseService.getVersionsInDateRange(CourseServiceConstants.COURSE_NAMESPACE_URI, createdCourse.getVersion().getVersionIndId(), yesterday, tomorrow);
        
        //assertEquals(1, versions.size());
    }
    
}
