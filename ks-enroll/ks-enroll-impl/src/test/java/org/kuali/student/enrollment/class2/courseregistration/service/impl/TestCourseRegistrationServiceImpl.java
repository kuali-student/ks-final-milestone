package org.kuali.student.enrollment.class2.courseregistration.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.dto.GradeRosterEntryInfo;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:coursereg-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseRegistrationServiceImpl {

    @Resource
    private CourseRegistrationService courseRegService;
    @Resource
    private GradingService crGradingService;
    

    public static String principalId = "123";
    public ContextInfo callContext = null;

    private RegRequestInfo createDummyRegRequest() {
        RegRequestInfo regRequest = new RegRequestInfo();
        regRequest.setRequestorId("Student1");
        regRequest.setTermId(AtpServiceConstants.ATP_SENIOR_YEAR_TERM_1_TYPE_KEY);
        regRequest.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_NEW_STATE_KEY);
        regRequest.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);

        RegRequestItemInfo regRequestItem = new RegRequestItemInfo();
        regRequestItem.setCreditOptionKey("kuali.credit.option.RVG1");
        regRequestItem.setGradingOptionKey("kuali.grading.option.RVG1");
        regRequestItem.setName("TRANS NAME");
        regRequestItem.setOkToHoldList(false);
        regRequestItem.setOkToWaitlist(false);
        regRequestItem.setStudentId("Student1");
        regRequestItem.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);

        regRequestItem.setNewRegGroupId("Lui-1");
        regRequestItem.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        regRequest.getRegRequestItems().add(regRequestItem);

        return regRequest;
    }

    @Before
    public void setUp() throws Exception {
        callContext = new ContextInfo ();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testServiceSetup() {
        assertNotNull(courseRegService);
    }

    
    @Test
    public void testCreateRegRequestFromExisting() {

    }

    @Test
    public void testCreateRegRequest() {

        try {
            RegRequestInfo regRequest = createDummyRegRequest();
            RegRequestInfo regRequestNew = courseRegService.createRegRequest(regRequest, callContext);
            assertNotNull(regRequestNew);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception from service call :" + ex.getMessage());
        }

    }

    @Ignore
    public void testSubmitRegRequest() {

        try {
            RegResponseInfo regResponse = courseRegService.submitRegRequest("LPR-TRANS-1", callContext);
            assertNotNull(regResponse);
            OperationStatusInfo statusInfo = regResponse.getOperationStatus();
            assertNotNull(statusInfo);
            assertEquals("RegRequest operation status is not 'SUCCESS'.", "SUCCESS", statusInfo.getStatus());

            RegRequestInfo regRequest = courseRegService.getRegRequest("LPR-TRANS-1", callContext);
            assertNotNull("Could not find RegRequest 'LPR-TRANS-1'.", regRequest);
            assertEquals("Requestor ID should be 'frank'.", "frank", regRequest.getRequestorId());
            List<RegRequestItemInfo> items = regRequest.getRegRequestItems();
            assertNotNull("RegRequest 'LPR-TRANS-1' has no items.");
            assertEquals("There should be 4 items in the registration request.", 4, items.size());
            ArrayList<String> itemIds = new ArrayList<String> (
                    Arrays.asList("LUI-RG-1", "LUI-CO-1", "LUI-ACT-1", "LUI-ACT-2"));
            for (RegRequestItemInfo item : items) {
                assertTrue("Unexpected NewRegGroupId found in registration items",
                        itemIds.contains(item.getNewRegGroupId()));
                itemIds.remove(item.getNewRegGroupId());  // this item account for; remove
            }

            List<GradeRosterInfo> gradeRosterInfoList = crGradingService.getFinalGradeRostersForCourseOffering("LUI-CO-1", callContext);
            assertNotNull("There are no GradeRosterEntries for 'LUI-CO-1'.", gradeRosterInfoList);
            assertEquals(1,gradeRosterInfoList.size());

            // getGradeRoster is not implemented yet
            //GradeRosterInfo gradeRosterInfo = crGradingService.getGradeRoster("LPR-CO-1-GRADEROSTER", callContext);
            //assertNotNull("GradeRoster 'LPR-CO-1-GRADEROSTER' was not found.", gradeRosterInfo);
            //List<String> gradeRosterEntryIds = gradeRosterInfo.getGradeRosterEntryIds();
            //assertNotNull(gradeRosterEntryIds);

            //List<GradeRosterEntryInfo> gradeRosterEntries = crGradingService.getGradeRosterEntriesByRosterId("LPR-CO-1-GRADEROSTER", callContext);
            //assertNotNull("There are no GradeRosterEntries for 'LPR-CO-1-GRADEROSTER'.", gradeRosterEntries);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    @Ignore
    public void testCheckStudentEligibiltyForCourseOffering() {
        String studentId = "testStudentId1";
        String courseOfferingId = "courseOffering3";

        try {
            List<ValidationResultInfo> passedResults = courseRegService.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);

            assertEquals(1, passedResults.size());
            ValidationResultInfo result = passedResults.get(0);
            assertEquals(result.getLevel(), ValidationResult.ErrorLevel.OK);
        } catch (Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }

        // test with a course offering the student is not eligible for
        studentId = "testStudentId2";
        courseOfferingId = "courseOffering3";

        try {
            List<ValidationResultInfo> failedResults = courseRegService.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);

            assertEquals(1, failedResults.size());
            ValidationResultInfo result = failedResults.get(0);
            assertEquals(result.getLevel(), ValidationResult.ErrorLevel.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }

        // test with an invalid course offering id
        courseOfferingId = "invalidCourse Offering Id";
        List<ValidationResultInfo> shouldBeNull = null;

        try {
            shouldBeNull = courseRegService.checkStudentEligibiltyForCourseOffering(studentId, courseOfferingId, callContext);
            fail("should have thrown an exception in checkStudentEligibiltyForCourseOffering due to invalid course offering id");
        }
        catch(InvalidParameterException ie) {
            assertNull(shouldBeNull);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("call to service method failed due to exception: " + e.getMessage());
        }
    }
}
