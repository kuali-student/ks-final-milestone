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
package org.kuali.student.enrollment.class2.autogen.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.autogen.util.ARGToolbarUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGCourseOfferingHandler {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGCourseOfferingHandler.class);

    public static void loadPreviousCO(ARGCourseOfferingManagementForm form) throws Exception {

        CourseOfferingWrapper wrapper = form.getPreviousCourseOfferingWrapper();

        CourseOfferingInfo info = ARGUtil.getCourseOfferingService().getCourseOffering(wrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        wrapper.setCourseOfferingInfo(info);

        form.getCourseOfferingResultList().clear();
        form.setCurrentCourseOfferingWrapper(wrapper);

        form.setInputCode(info.getCourseOfferingCode());

        ARGUtil.getViewHelperService(form).loadActivityOfferingsByCourseOffering(info, form);
        ARGUtil.getViewHelperService(form).loadPreviousAndNextCourseOffering(form);
    }

    public static void loadNextCO(ARGCourseOfferingManagementForm form) throws Exception {

        CourseOfferingWrapper wrapper = form.getNextCourseOfferingWrapper();

        CourseOfferingInfo info = ARGUtil.getCourseOfferingService().getCourseOffering(wrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        wrapper.setCourseOfferingInfo(info);

        form.getCourseOfferingResultList().clear();
        form.setCurrentCourseOfferingWrapper(wrapper);

        form.setInputCode(info.getCourseOfferingCode());

        ARGUtil.getViewHelperService(form).loadActivityOfferingsByCourseOffering(info, form);
        ARGUtil.getViewHelperService(form).loadPreviousAndNextCourseOffering(form);
    }

    public static void copyCourseOfferingCreateCopy(ARGCourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingCopyWrapper copyWrapper = theForm.getCourseOfferingCopyWrapper();
        CourseOfferingInfo courseOfferingInfo = copyWrapper.getCoInfo();
        List<String> optionKeys = new ArrayList<String>();

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

        SocRolloverResultItemInfo item = ARGUtil.getCourseOfferingService().rolloverCourseOffering(
                courseOfferingInfo.getId(),
                copyWrapper.getTermId(),
                optionKeys,
                ContextUtils.createDefaultContextInfo());
        CourseOfferingInfo courseOffering = ARGUtil.getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        ExistingCourseOffering newWrapper = new ExistingCourseOffering(courseOffering);
        newWrapper.setCredits(courseOffering.getCreditCnt());
        newWrapper.setGrading(ARGUtil.getGradingOption(courseOffering.getGradingOptionId()));
        copyWrapper.getExistingOfferingsInCurrentTerm().add(newWrapper);
        // reload the COs
        ARGUtil.reloadCourseOfferings(theForm);
    }

    public static void copyCourseOffering(ARGCourseOfferingManagementForm theForm) throws Exception {
        Object selectedObject = ARGUtil.getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if (selectedObject instanceof CourseOfferingListSectionWrapper) {

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            CourseOfferingInfo courseOfferingInfo = ARGUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());

            // Load activity offerings.
            ARGUtil.getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(courseOfferingInfo, theForm);

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

    public static void loadCOs(ARGCourseOfferingManagementForm form) throws Exception {
        String termId = form.getTermInfo().getId();
        form.setInputCode(form.getSubjectCode());
        ARGUtil.getViewHelperService(form).loadCourseOfferingsByTermAndSubjectCode(termId, form.getSubjectCode(), form);

        String longNameDescr = ARGUtil.getOrgNameDescription(form.getSubjectCode());
        form.setSubjectCodeDescription(longNameDescr);
        //clean up theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        form.setCurrentCourseOfferingWrapper(null);
        ARGToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
    }

    public static String deleteCoConfirmation(ARGCourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOffering = null;

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (!StringUtils.isBlank(selectedCollectionPath)) {
            Object selectedObject = ARGUtil.getSelectedObject(theForm, "deleteCo");
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            theCourseOffering = ARGUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            theForm.getCurrentCourseOfferingWrapper().setCourseOfferingInfo(theCourseOffering);
        }

        //venkat - dont think this happen
        /*if (theCourseOffering == null) {
            theCourseOffering = theForm.getTheCourseOffering();
        }*/
        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        //  Verify the state of the CourseOffering is appropriate for deleting.
        //  FIXME: This logic is duplicated in CoreOfferingEditWrapper.isLegalToDelete().
        String coState = theCourseOffering.getStateKey();
        if (StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)
                || StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY)) {
            LOG.error(String.format("Course offering [%s] cannot not be deleted because the state was [%s].",
                    theCourseOffering.getCourseOfferingCode(), coState));
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                    CourseOfferingConstants.COURSEOFFERING_INVALID_STATE_FOR_DELETE);
            return  CourseOfferingConstants.MANAGE_CO_PAGE;
        }

//        theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());

        // Load activity offerings
        try {
            ARGUtil.getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
            //  Verify AO state.
            if (!ao.isLegalToDelete()) {
                LOG.error(String.format("Course Offering [%s] cannot be deleted because Activity Offering [%s] is in an invalid state for deleting.",
                        theCourseOffering.getCourseOfferingCode(), ao.getActivityCode()));
                GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                        CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                return CourseOfferingConstants.MANAGE_CO_PAGE;
            }
        }

        return CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE;
    }

    public static void deleteBulkCos(ARGCourseOfferingManagementForm theForm) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = theForm.getSelectedCoToDeleteList();
        int checked = 0;
        int enabled = 0;

        for (CourseOfferingListSectionWrapper co : coList) {
            boolean hasDeletion = true;
            if (co.getIsChecked()) {
                checked++;
                CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(co.getCourseOfferingId(), ContextBuilder.loadContextInfo());
            }
        }

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE);
        } else {
            if (enabled == 1) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_N_SUCCESS);
            }
        }

        ARGUtil.reloadCourseOfferings(theForm);
    }

    public static void cancelDeleteBulkCos(ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGUtil.reloadCourseOfferings(theForm);
    }

    public static Properties editTheCO(ARGCourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        return ARGUtil._buildCOURLParameters(theCourseOfferingInfo, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
    }

    public static Properties edit(ARGCourseOfferingManagementForm theForm, CourseOfferingListSectionWrapper coWrapper) throws Exception {
        Properties urlParameters = new Properties();
        CourseOfferingInfo courseOfferingInfo = ARGUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        urlParameters = ARGUtil._buildCOURLParameters(courseOfferingInfo, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        return urlParameters;
    }

    public static Properties view(ARGCourseOfferingManagementForm theForm, CourseOfferingInfo coInfo) throws Exception {
        return ARGUtil._buildCOURLParameters(coInfo, KRADConstants.START_METHOD);
    }

    public static Properties createCourseOffering(ARGCourseOfferingManagementForm theForm) throws Exception {
        String termCode = theForm.getTermCode();
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode", termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");
        return props;
    }

    public static void deleteOneCoWithLink(ARGCourseOfferingManagementForm theForm) throws Exception {
        // get the co and set in the form as selected
        CourseOfferingWrapper currentCourseOfferingWrapper = theForm.getCurrentCourseOfferingWrapper();

        List<CourseOfferingListSectionWrapper> courseOfferingList = theForm.getCourseOfferingResultList();
        for (CourseOfferingListSectionWrapper co : courseOfferingList) {
            if (StringUtils.equals(co.getCourseOfferingId(), currentCourseOfferingWrapper.getCourseOfferingId())) {
                co.setIsChecked(true);
                break;
            }
        }

        ARGUtil.getViewHelperService(theForm).deleteCourseOfferings(theForm);
    }

}
