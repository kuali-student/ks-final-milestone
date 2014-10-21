/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.*;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * This class is used by the ARGCourseOfferingManagementController to handle AO operations
 *
 * @author Kuali Student Team
 */
public class CourseOfferingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CourseOfferingHandler.class);

    public static void copyCourseOfferingCreateCopy(CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingCopyWrapper copyWrapper = theForm.getCourseOfferingCopyWrapper();
        CourseOfferingInfo courseOfferingInfo = copyWrapper.getCoInfo();
        List<String> optionKeys = CourseOfferingManagementUtil.getDefaultOptionKeysService ().getDefaultOptionKeysForCopySingleCourseOffering();

        if (copyWrapper.isExcludeSchedulingInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        }
        if (copyWrapper.isExcludeInstructorInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        }
        if (copyWrapper.isExcludeCancelledActivityOfferings()) {
            optionKeys.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY);
        }
        //Generate Ids
        optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);

        SocRolloverResultItemInfo item = CourseOfferingManagementUtil.getCourseOfferingService().rolloverCourseOffering(
                courseOfferingInfo.getId(),
                copyWrapper.getTermId(),
                optionKeys,
                ContextUtils.createDefaultContextInfo());
        CourseOfferingInfo courseOffering = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        ExistingCourseOffering newWrapper = new ExistingCourseOffering(courseOffering);
        newWrapper.setCredits(courseOffering.getCreditCnt());
        newWrapper.setGrading(CourseOfferingManagementUtil.getGradingOption(courseOffering.getGradingOptionId()));
        copyWrapper.getExistingOfferingsInCurrentTerm().add(newWrapper);
        // reload the COs
        CourseOfferingManagementUtil.reloadCourseOfferings(theForm);
    }

    public static void copyCourseOffering(CourseOfferingManagementForm theForm) throws Exception {
        Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if (selectedObject instanceof CourseOfferingListSectionWrapper) {

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());

            // Load activity offerings.
            CourseOfferingManagementUtil.getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(courseOfferingInfo, theForm);

            // Create a new CourseOfferingCopyWrapper from the Course Offering information.
            CourseOfferingCopyWrapper coCopyWrapper = new CourseOfferingCopyWrapper();

            // Add items that the page wrapper intends to displaying.
            coCopyWrapper.setCoInfo(courseOfferingInfo);
            coCopyWrapper.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
            coCopyWrapper.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
            coCopyWrapper.setTermId(courseOfferingInfo.getTermId());
            coCopyWrapper.setCreditCount(courseOfferingInfo.getCreditCnt());
            coCopyWrapper.setGradingOption(courseOfferingInfo.getGradingOptionName());
            coCopyWrapper.setStudentRegistrationGradingOptionsList(courseOfferingInfo.getStudentRegistrationGradingOptions());
            coCopyWrapper.setFinalExamType(courseOfferingInfo.getFinalExamType());
            coCopyWrapper.setWaitlistLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            coCopyWrapper.setWaitlistTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            if (courseOfferingInfo.getIsHonorsOffering() != null) {
                coCopyWrapper.setIsHonors(courseOfferingInfo.getIsHonorsOffering());
            } else {
                coCopyWrapper.setIsHonors(false);
            }
            coCopyWrapper.setActivityOfferingWrapperList(theForm.getActivityWrapperList());

            // Add it to the Copy Wrapper List.
            theForm.setCourseOfferingCopyWrapper(coCopyWrapper);
        } else { //TODO log error
            theForm.setCourseOfferingCopyWrapper(null);
        }
    }

    public static void loadCOs(CourseOfferingManagementForm form) throws Exception {
        String termId = form.getTermInfo().getId();
        form.setInputCode(form.getSubjectCode());
        CourseOfferingManagementUtil.getViewHelperService(form).loadCourseOfferingsByTermAndSubjectCode(termId, form.getSubjectCode(), form);

        String longNameDescr = CourseOfferingManagementUtil.getOrgNameDescription(form.getSubjectCode());
        form.setSubjectCodeDescription(longNameDescr);
        //clean up theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        form.setCurrentCourseOfferingWrapper(null);
        CourseOfferingManagementToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
    }

    public static String deleteCoConfirmation(CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOffering = null;

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        if (!StringUtils.isBlank(selectedCollectionPath)) {
            Object selectedObject = CourseOfferingManagementUtil.getSelectedObject(theForm, "deleteCo");
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            theCourseOffering = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            theForm.getCurrentCourseOfferingWrapper().setCourseOfferingInfo(theCourseOffering);
        }

        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        //  Verify the state of the CourseOffering is appropriate for deleting.
        //  FIXME: This logic is duplicated in CoreOfferingEditWrapper.isLegalToDelete().
        String coState = theCourseOffering.getStateKey();
        if (StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)
                || StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY)) {
            LOG.error("Course offering [{}] cannot not be deleted because the state was [{}].",
                    theCourseOffering.getCourseOfferingCode(), coState);
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                    CourseOfferingConstants.COURSEOFFERING_INVALID_STATE_FOR_DELETE);
            return  CourseOfferingConstants.MANAGE_CO_PAGE;
        }

        // Load activity offerings
        try {
            CourseOfferingManagementUtil.getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
            //  Verify AO state.
            if (!ao.isLegalToDelete()) {
                LOG.error("Course Offering [{}] cannot be deleted because Activity Offering [{}] is in an invalid state for deleting.",
                        theCourseOffering.getCourseOfferingCode(), ao.getActivityCode());
                GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                        CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                return CourseOfferingConstants.MANAGE_CO_PAGE;
            }
        }

        return CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE;
    }

    public static void prepareCSRConfirmationView(CourseOfferingManagementForm theForm, String methodToCall, String warningMessage) throws Exception{

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToCSRList();
        CourseOfferingWrapper currentCoWrapper = theForm.getCurrentCourseOfferingWrapper();
        currentCoWrapper.setColocatedAoToCSR(false);

        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;

        selectedIndexList.clear();
        for(ActivityOfferingWrapper ao : aoList) {
            boolean isEnabled = false;

            if (StringUtils.equals(methodToCall, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_CANCEL)) {
                if (ao.isEnableCancelButton() && ao.getIsCheckedByCluster()) {
                    isEnabled = true;
                }
                theForm.setCsrLabel("Cancel");
            } else if (StringUtils.equals(methodToCall, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_SUSPEND)) {
                if (ao.isEnableSuspendButton() && ao.getIsCheckedByCluster()) {
                    isEnabled = true;
                }
                theForm.setCsrLabel("Suspend");
            } else if (StringUtils.equals(methodToCall, ActivityOfferingConstants.ACTIVITYOFFERINGS_ACTION_REINSTATE)) {
                if (ao.isEnableReinstateButton() && ao.getIsCheckedByCluster()) {
                    isEnabled = true;
                    if (StringUtils.equals(ao.getAoInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                        ao.setReinstateStateName("Draft");
                    } else if (StringUtils.equals(ao.getAoInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY)) {
                        if (ao.getAoInfo().getScheduleIds() != null && !ao.getAoInfo().getScheduleIds().isEmpty()) {
                            if (StringUtils.equals(theForm.getSocStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)){
                                ao.setReinstateStateName("Offered");
                            } else if (StringUtils.equals(theForm.getSocStateKey(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY) ||
                                    StringUtils.equals(theForm.getSocStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                                ao.setReinstateStateName("Approved");
                            }
                        } else {
                            ao.setReinstateStateName("Draft");
                        }
                    }
                }
                theForm.setCsrLabel("Reinstate");
            }

            if (isEnabled && ao.getIsCheckedByCluster()) {
                ao.setActivityCode(ao.getAoInfo().getActivityCode());
                selectedIndexList.add(ao);
                if (ao.isColocatedAO()) {
                    currentCoWrapper.setColocatedAoToCSR(true);
                }
                enabled++;
            } else if (ao.getIsCheckedByCluster()){
                checked++;
                if (!bNoDeletion) {
                    bNoDeletion = true;
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            theForm.setSelectedIllegalAOInCSR(false);
            if (bNoDeletion) {
                theForm.setSelectedIllegalAOInCSR(true);
            }
        }

        theForm.setNumIneligibleAOsForCSR(checked);

        if (checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, warningMessage);
        }
    }

    public static void deleteBulkCos(CourseOfferingManagementForm theForm) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = theForm.getSelectedCoToDeleteList();
        int checked = 0;

        for (CourseOfferingListSectionWrapper co : coList) {
            if (co.getIsChecked()) {
                checked++;
                CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(co.getCourseOfferingId(), ContextBuilder.loadContextInfo());
            }
        }

        if (checked > 0) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_N_SUCCESS);
        } else {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_N_SUCCESS);
        }
    }

    public static void cancelDeleteBulkCos(CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementUtil.reloadCourseOfferings(theForm);
    }

    public static Properties editTheCO(CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        return CourseOfferingManagementUtil._buildCOURLParameters(theCourseOfferingInfo, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
    }

    public static Properties edit(@SuppressWarnings("unused") CourseOfferingManagementForm theForm, CourseOfferingListSectionWrapper coWrapper) throws Exception {
        Properties urlParameters;
        CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        urlParameters = CourseOfferingManagementUtil._buildCOURLParameters(courseOfferingInfo, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        return urlParameters;
    }

    public static Properties view(@SuppressWarnings("unused") CourseOfferingManagementForm theForm, CourseOfferingInfo coInfo) throws Exception {
        return CourseOfferingManagementUtil._buildCOURLParameters(coInfo, KRADConstants.START_METHOD);
    }

    public static Properties createCourseOffering(CourseOfferingManagementForm theForm) throws Exception {
        String termCode = theForm.getTermCode();
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode", termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");
        return props;
    }

    public static void deleteOneCoWithLink(CourseOfferingManagementForm theForm) throws Exception {
        // get the co and set in the form as selected
        CourseOfferingWrapper currentCourseOfferingWrapper = theForm.getCurrentCourseOfferingWrapper();

        List<CourseOfferingListSectionWrapper> courseOfferingList = theForm.getCourseOfferingResultList();
        for (CourseOfferingListSectionWrapper co : courseOfferingList) {
            if (StringUtils.equals(co.getCourseOfferingId(), currentCourseOfferingWrapper.getCourseOfferingId())) {
                co.setIsChecked(true);
                break;
            }
        }

        CourseOfferingManagementUtil.getViewHelperService(theForm).deleteCourseOfferings(theForm);
    }

}