/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 5/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class provides Maintainable logic for Course Offerings in the Course Offering Edit ui
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditMaintainableImpl extends CourseOfferingMaintainableImpl implements Maintainable {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(CourseOfferingEditMaintainableImpl.class);

    //TODO : implement the functionality for Personnel section and its been delayed now since the backend implementation is not yet ready (06/06/2012). KSENROLL-1375

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper) getDataObject();
            updateCourseOffering(coEditWrapper);
        } else if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION)) {
            CourseOfferingEditWrapper coCreateWrapper = (CourseOfferingEditWrapper) getDataObject();
            CourseOfferingInfo createdCOInfo = createCourseOfferingInfo(coCreateWrapper);
            coCreateWrapper.setCourseOfferingInfo(createdCOInfo);
        } else { //for copy action, report error
            LOG.error(">>>Do not support!");
        }

    }

    private void updateCourseOffering(CourseOfferingEditWrapper coEditWrapper) {
        try {
            // persist format offerings
            updateFormatOfferings(coEditWrapper);

            //persist unitDeploymentOrgIds
            List<String> unitDeploymentOrgIds = new ArrayList<String>();
            for (OrganizationInfoWrapper orgWrapper : coEditWrapper.getOrganizationNames()) {
                if( StringUtils.isNotBlank(orgWrapper.getId())) {
                    unitDeploymentOrgIds.add(orgWrapper.getId());
                }
            }

            CourseOfferingInfo coInfo = coEditWrapper.getCourseOfferingInfo();
            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            // Credit Options (also creates extra-line)
            if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED) &&
                    !coEditWrapper.getCreditOption().getFixedCredit().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateFixedCreditResultValuesGroup(coEditWrapper.getCreditOption().getFixedCredit(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE) &&
                    !coEditWrapper.getCreditOption().getMinCredits().isEmpty() && !coEditWrapper.getCreditOption().getMaxCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateRangeCreditResultValuesGroup(coEditWrapper.getCreditOption().getMinCredits(),
                        coEditWrapper.getCreditOption().getMaxCredits(), calculateIncrement(coEditWrapper.getCreditOption().getAllowedCredits()), LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coEditWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) &&
                    !coEditWrapper.getCreditOption().getCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateMultipleCreditResultValuesGroup(coEditWrapper.getCreditOption().getCredits(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            }

            // CO code
            String courseOfferingCode = coEditWrapper.getCourse().getCode();
            coInfo.setCourseNumberSuffix(StringUtils.upperCase(coInfo.getCourseNumberSuffix()));
            if (!StringUtils.isEmpty(coInfo.getCourseNumberSuffix())) {
                courseOfferingCode += coInfo.getCourseNumberSuffix();
            }
            coInfo.setCourseOfferingCode(courseOfferingCode);

            // Waitlist
            if(coInfo.getHasWaitlist() && !coEditWrapper.getHasWaitlist()) {
                //activate the waitlist
                CourseOfferingManagementUtil.getCourseWaitListServiceFacade().activateActivityOfferingWaitlistsByCourseOffering(coInfo.getId(), coInfo.getTermId(), contextInfo);
            } else if (!coInfo.getHasWaitlist() && coEditWrapper.getHasWaitlist()) {
                //deactivate the waitlist
                CourseOfferingManagementUtil.getCourseWaitListServiceFacade().deactivateActivityOfferingWaitlistsByCourseOffering(coInfo.getId(), contextInfo);
            }

            //TODO REMOVE THIS AFTER KRAD CHECKLISTS ARE FIXED for student registration options
            //determine if audit reg options and pass/fail reg options should be added/removed to/from coInfo
            if (coEditWrapper.getAuditStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            } else if (!coEditWrapper.getAuditStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            }

            if (coEditWrapper.getPassFailStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            } else if (!coEditWrapper.getPassFailStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            }

            updateInstructors(coEditWrapper, coInfo);

            //Save cross lists
            loadCrossListedCOs(coEditWrapper, coInfo);

            // update Final Exam Driver
            setFinalExamDriverAttr(coInfo, coEditWrapper);

            // update Use Final Exam Matrix
            setUseFinalExamMatrix(coInfo, coEditWrapper);

            CourseOfferingManagementUtil.getCourseOfferingService().updateCourseOffering(coInfo.getId(), coInfo, contextInfo);

            // generate exam offerings if exam period exists
            if (!StringUtils.isEmpty(coEditWrapper.getExamPeriodId())) {
                CourseOfferingManagementUtil.getExamOfferingServiceFacade().generateFinalExamOffering(coInfo,
                        coInfo.getTermId(), coEditWrapper.getExamPeriodId(), new ArrayList<String>(), contextInfo);
            }

            // check for changes to states in CO and related FOs (may happen in the case of deleted FOs)
//            CourseOfferingViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(coInfo, contextInfo);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    protected CourseOfferingInfo createCourseOfferingInfo(CourseOfferingEditWrapper coCreateWrapper) {

        try {
            //persist unitDeploymentOrgIds
            List<String> unitDeploymentOrgIds = new ArrayList<String>();
            for (OrganizationInfoWrapper orgWrapper : coCreateWrapper.getOrganizationNames()) {
                unitDeploymentOrgIds.add(orgWrapper.getId());
            }

            CourseOfferingInfo coInfo = coCreateWrapper.getCourseOfferingInfo();
            coInfo.setUnitsDeploymentOrgIds(unitDeploymentOrgIds);

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            // Credit Options (also creates extra-line)
            if (coCreateWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED) &&
                    !coCreateWrapper.getCreditOption().getFixedCredit().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateFixedCreditResultValuesGroup(coCreateWrapper.getCreditOption().getFixedCredit(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coCreateWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE) &&
                    !coCreateWrapper.getCreditOption().getMinCredits().isEmpty() && !coCreateWrapper.getCreditOption().getMaxCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateRangeCreditResultValuesGroup(coCreateWrapper.getCreditOption().getMinCredits(),
                        coCreateWrapper.getCreditOption().getMaxCredits(), calculateIncrement(coCreateWrapper.getCreditOption().getAllowedCredits()), LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (coCreateWrapper.getCreditOption().getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) &&
                    !coCreateWrapper.getCreditOption().getCredits().isEmpty()) {
                ResultValuesGroupInfo rvgInfo = CourseOfferingManagementUtil.getLrcService().getCreateMultipleCreditResultValuesGroup(coCreateWrapper.getCreditOption().getCredits(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, contextInfo);
                coInfo.setCreditOptionId(rvgInfo.getKey());
            }

            // CO code
            List<String> optionKeys = CourseOfferingManagementUtil.getDefaultOptionKeysService().getDefaultOptionKeysForCreateCourseOfferingFromCanonical();
            String courseOfferingCode = coCreateWrapper.getCourse().getCode();
            coInfo.setCourseNumberSuffix(StringUtils.upperCase(coInfo.getCourseNumberSuffix()));
            if (!StringUtils.isEmpty(coInfo.getCourseNumberSuffix())) {
                courseOfferingCode += coInfo.getCourseNumberSuffix();
                optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_CODE_SUFFIX_OPTION_KEY);
            }
            coInfo.setCourseOfferingCode(courseOfferingCode);

            // Waitlist
            if (!coInfo.getHasWaitlist()) {
                coInfo.setWaitlistTypeKey(null);
                coInfo.setWaitlistLevelTypeKey(null);
            }

            //TODO REMOVE THIS AFTER KRAD CHECKLISTS ARE FIXED for student registration options
            //determine if audit reg options and pass/fail reg options should be added/removed to/from coInfo
            if (coCreateWrapper.getAuditStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            } else if (!coCreateWrapper.getAuditStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
            }

            if (coCreateWrapper.getPassFailStudentRegOpts() &&
                    !coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                coInfo.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            } else if (!coCreateWrapper.getPassFailStudentRegOpts() &&
                    coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                coInfo.getStudentRegistrationGradingOptions().remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
            }

            updateInstructors(coCreateWrapper, coInfo);

            //Save cross lists
            loadCrossListedCOs(coCreateWrapper, coInfo);

            // set Final Exam Driver
            setFinalExamDriverAttr(coInfo, coCreateWrapper);

            //set Use Final Exam Matrix
            setUseFinalExamMatrix(coInfo, coCreateWrapper);

            CourseOfferingInfo info = CourseOfferingManagementUtil.getCourseOfferingService().createCourseOffering(coInfo.getCourseId(), coInfo.getTermId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, coInfo, optionKeys, contextInfo);

            coCreateWrapper.setCourseOfferingInfo(info);

            this.updateFormatOfferings(coCreateWrapper);
            // generate exam offerings if exam period exists
            if (!StringUtils.isEmpty(coCreateWrapper.getExamPeriodId())) {
                CourseOfferingManagementUtil.getExamOfferingServiceFacade().generateFinalExamOffering(info, info.getTermId(), coCreateWrapper.getExamPeriodId(), new ArrayList<String>(), contextInfo);
            }

            return info;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateInstructors(CourseOfferingEditWrapper coEditWrapper, CourseOfferingInfo coInfo) {
        List<OfferingInstructorWrapper> instructors = coEditWrapper.getInstructors();
        List<OfferingInstructorInfo> coInstructors = coInfo.getInstructors();
        coInstructors.clear();
        int firstPerson = 0;

        for (OfferingInstructorWrapper instructorWrapper : instructors) {
            if (instructorWrapper != null) {
                OfferingInstructorInfo info = instructorWrapper.getOfferingInstructorInfo();
                if ((info != null) && (info.getPersonId() != null) && !info.getPersonId().isEmpty()) {
                    if (info.getStateKey() == null) {
                        info.setStateKey(LprServiceConstants.TENTATIVE_STATE_KEY);
                    }
                    if (info.getPersonName() == null) {
                        List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(info.getPersonId());
                        if (personList.size() == 1) {
                            info.setPersonName(personList.get(firstPerson).getName());
                        }

                    }
                    coInstructors.add(info);
                }
            }
        }

    }

    private String calculateIncrement(List<String> credits) {
        //Sort the list of credits options by the float value.
        Collections.sort(credits, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return Float.valueOf(o1).compareTo(Float.valueOf(o2));
            }
        });
        // JIRA Fix : KSENROLL-8726. - Validate credit list and throw exception.
        if (credits == null || credits.size() < 2) {
            throw new RuntimeException("Not enough credits to calculate credit increment");
        }
        int firstValue = 0;
        int secondValue = 1;
        //Find the difference between the first two values to get the increment
        return String.valueOf(Float.parseFloat(credits.get(secondValue)) - Float.parseFloat(credits.get(firstValue)));
    }

    private void updateFormatOfferings(CourseOfferingEditWrapper coEditWrapper) {
        try {
            List<FormatOfferingWrapper> formatOfferingList = coEditWrapper.getFormatOfferingList();
            CourseOfferingInfo coInfo = coEditWrapper.getCourseOfferingInfo();
            List<String> currentFOIds = getExistingFormatOfferingIds(coInfo.getId());
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            if (formatOfferingList != null && !formatOfferingList.isEmpty()) {
                FormatOfferingInfo updatedFormatOffering;
                for (FormatOfferingWrapper foWrapper : formatOfferingList) {
                    FormatOfferingInfo formatOfferingInfo = foWrapper.getFormatOfferingInfo();
                    if (StringUtils.isNotBlank(formatOfferingInfo.getFormatId())) {
                        if (formatOfferingInfo.getId() != null &&
                                !formatOfferingInfo.getId().isEmpty() &&
                                currentFOIds.contains(formatOfferingInfo.getId())) {
                            //update FO
                            if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals(CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
                                formatOfferingInfo.setFinalExamLevelTypeKey(null);
                            }
                            // Populate AO types (all FOs should "require" this (less important here since it should
                            // already exist)
                            CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(formatOfferingInfo, coEditWrapper.getCourse(), CourseOfferingManagementUtil.getTypeService(), contextInfo);
                            updatedFormatOffering = CourseOfferingManagementUtil.getCourseOfferingService().updateFormatOffering(formatOfferingInfo.getId(), formatOfferingInfo, contextInfo);
                            currentFOIds.remove(formatOfferingInfo.getId());
                        } else {
                            //create a new FO
                            formatOfferingInfo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
                            formatOfferingInfo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
                            formatOfferingInfo.setDescr(null);
                            formatOfferingInfo.setTermId(coInfo.getTermId());
                            formatOfferingInfo.setCourseOfferingId(coInfo.getId());
                            //We need to set the name to maintain ordinality from CLU. (If we're not setting the name here, service will set the name based on priority order which is not the expected behavior)
                            if (StringUtils.isBlank(formatOfferingInfo.getName())) {
                                String[] foNames = getFormatShortAndLongNames(foWrapper, coEditWrapper.getCourse());
                                formatOfferingInfo.setName(foNames[0]);
                                formatOfferingInfo.setShortName(foNames[1]);

                            }
                            if (coInfo.getFinalExamType() != null && !coInfo.getFinalExamType().equals(CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD)) {
                                formatOfferingInfo.setFinalExamLevelTypeKey(null);
                            }
                            // Populate AO types (all FOs should "require" this
                            CourseOfferingViewHelperUtil.addActivityOfferingTypesToFormatOffering(formatOfferingInfo, coEditWrapper.getCourse(), CourseOfferingManagementUtil.getTypeService(), contextInfo);
                            updatedFormatOffering = CourseOfferingManagementUtil.getCourseOfferingService().createFormatOffering(coInfo.getId(), formatOfferingInfo.getFormatId(), formatOfferingInfo.getTypeKey(), formatOfferingInfo, contextInfo);
                        }
                        foWrapper.setFormatOfferingInfo(updatedFormatOffering);
                    }
                }
            }
            //delete FormatOfferings that have been removed by the user
            if (currentFOIds != null && currentFOIds.size() > 0) {
                for (String formatOfferingId : currentFOIds) {
                    //delete all AOs associated with this FO, then delete FO
                    //Note by bonnie deleteAO invoked in deleteFormatOfferingCascaded seems not completely correct.
                    //I didn't see the code if removing FO-AO relations before deleting AOs....
                    CourseOfferingManagementUtil.getCourseOfferingService().deleteFormatOfferingCascaded(formatOfferingId, contextInfo);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<String> getExistingFormatOfferingIds(String courseOfferingId) throws Exception {
        List<String> formatOfferingIds = new ArrayList<String>();

        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.FO_IDS_BY_CO_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);
        SearchResultInfo searchResult = CourseOfferingManagementUtil.getSearchService().search(sr, null);
        List<SearchResultRowInfo> rows = searchResult.getRows();
        if (!rows.isEmpty()) {
            for (SearchResultRowInfo row: rows) {
                List<SearchResultCellInfo> cells = row.getCells();
                for (SearchResultCellInfo cell: cells) {
                    if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                        formatOfferingIds.add(cell.getValue());
                    }
                }
            }
        }

        return formatOfferingIds;
    }

    @Override
    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorInfo) {
            OfferingInstructorInfo instructorInfo = (OfferingInstructorInfo) addLine;

            //check duplication
            MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
            List<OfferingInstructorInfo> instructors = coEditWrapper.getCourseOfferingInfo().getInstructors();
            if (instructors != null && !instructors.isEmpty()) {
                for (OfferingInstructorInfo thisInst : instructors) {
                    if (instructorInfo.getPersonId().equals(thisInst.getPersonId())) {
                        GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-PersonnelTableSection", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_DUPLICATE, instructorInfo.getPersonId());
                        return false;
                    }
                }
            }

            //validate ID
            List<Person> lstPerson = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
            if (lstPerson == null || lstPerson.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingEdit-PersonnelSection", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_NOTFOUND, instructorInfo.getPersonId());
                return false;
            }
        } else if (addLine instanceof OrganizationInfoWrapper) {
            OrganizationInfoWrapper org = (OrganizationInfoWrapper) addLine;
            if (StringUtils.isEmpty(org.getId())) {
                GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroup.getId(), ActivityOfferingConstants.MSG_ERROR_ORGANIZATION_ID_REQUIRED);
                return false;
            }
        }

        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof FormatOfferingInfo) {
            FormatOfferingInfo newLine = (FormatOfferingInfo) addLine;
            String formatId = newLine.getFormatId();
            MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
            CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
            FormatInfo formatInfo = getFormatInfo(coEditWrapper, formatId);
            // TODO: fix R2 Format to include name and short name
            StringBuilder sb = new StringBuilder();
            for (ActivityInfo activityInfo : formatInfo.getActivities()) {
                String activityTypeKey = activityInfo.getTypeKey();
                String activityName = "";
                try {
                    if (!activityTypeKey.isEmpty()) {
                        activityName = CourseOfferingManagementUtil.getTypeService().getType(activityTypeKey, ContextUtils.createDefaultContextInfo()).getName();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                sb.append(activityName);
                sb.append("/");
            }
            String tempName = sb.toString().substring(0, sb.toString().length() - 1);
            newLine.setName(tempName);
            newLine.setShortName(tempName);
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine, boolean isValidLine) {
        if (addLine instanceof OfferingInstructorInfo) {
            // set the person name if it's null, in the case of user-input personell id
            OfferingInstructorInfo instructorInfo = (OfferingInstructorInfo) addLine;
            if (instructorInfo.getPersonName() == null && instructorInfo.getPersonId() != null) {
                List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructorInfo.getPersonId());
                if (personList.size() == 1) {
                    int firstperson = 0;
                    instructorInfo.setPersonName(personList.get(firstperson).getName());
                }
            }

            // make sure state is not null
            if (instructorInfo.getStateKey() == null) {
                instructorInfo.setStateKey(LprServiceConstants.TENTATIVE_STATE_KEY);
            }
        }
    }

    @Override
    protected void processAfterDeleteLine(View view, CollectionGroup collectionGroup, Object model, int lineIndex) {

        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
        MaintenanceDocument document = maintenanceForm.getDocument();

        if (collectionGroup.getPropertyName().endsWith("instructors")) {
            if (model instanceof MaintenanceDocumentForm) {
                // get the old object's collection
                Collection<Object> oldCollection = ObjectPropertyUtils.getPropertyValue(document.getOldMaintainableObject().getDataObject(),
                        collectionGroup.getPropertyName());
                if (oldCollection.size() - 1 >= lineIndex) {
                    super.processAfterDeleteLine(view, collectionGroup, model, lineIndex);
                }
            }
        } else if (collectionGroup.getPropertyName().endsWith("formatOfferingList")) {
            CourseOfferingEditWrapper coWrapper = (CourseOfferingEditWrapper) document.getNewMaintainableObject().getDataObject();
            populateFormatNames(coWrapper);
        } else {
            super.processAfterDeleteLine(view, collectionGroup, model, lineIndex);
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            if (getDataObject() instanceof CourseOfferingEditWrapper) {

                //0.1 get credit count from CourseInfo
                CourseOfferingInfo coInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(dataObjectKeys.get("courseOfferingInfo.id"), contextInfo);
                CourseInfo courseInfo = CourseOfferingManagementUtil.getCourseService().getCourse(coInfo.getCourseId(), contextInfo);

                //1. set CourseOfferingInfo
                CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);
                formObject.setHasWaitlist(coInfo.getHasWaitlist());

                //2. set CourseInfo
                formObject.setCourse(courseInfo);

                //3. set formatOfferingList
                List<FormatOfferingInfo> formatOfferingList = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(), contextInfo);
                List<FormatOfferingWrapper> foList = new ArrayList<FormatOfferingWrapper>();
                for (FormatOfferingInfo fo : formatOfferingList) {
                    FormatOfferingWrapper wrapper = new FormatOfferingWrapper();
                    wrapper.setFormatOfferingInfo(fo);
                    wrapper.setCourseOfferingWrapper(formObject);

                    //set the reader friendly Grade Roster Level
                    if (!StringUtils.isEmpty(fo.getGradeRosterLevelTypeKey())) {
                        wrapper.setGradeRosterUI(CourseOfferingManagementUtil.getTypeService().getType(fo.getGradeRosterLevelTypeKey(), contextInfo).getName());
                    }
                    //set the reader friendly Final Exam Driver Activity
                    if (!StringUtils.isEmpty(fo.getFinalExamLevelTypeKey()) && StringUtils.equals(formObject.getFinalExamDriver(), LuServiceConstants.LU_EXAM_DRIVER_AO_KEY)) {
                        wrapper.setFinalExamUI(CourseOfferingManagementUtil.getTypeService().getType(fo.getFinalExamLevelTypeKey(), contextInfo).getName());
                    } else {
                        wrapper.setFinalExamUI(" ");
                    }



                    foList.add(wrapper);
                }
                formObject.setFormatOfferingList(foList);

                if (foList.isEmpty()) {
                    FormatOfferingWrapper defaultFO = new FormatOfferingWrapper();
                    defaultFO.getRenderHelper().setNewRow(true);
                    defaultFO.setCourseOfferingWrapper(formObject);
                    formObject.getFormatOfferingList().add(defaultFO);
                }

                //4. Checking if Grading Options should be disabled or not and assign default (if no value)
                //5. Checking if there are any student registration options from CLU for screen display
                List<String> studentRegOptions = new ArrayList<String>();
                List<String> crsGradingOptions = new ArrayList<String>();
                if (coInfo.getCourseId() != null && courseInfo != null) {
                    List<String> gradingOptions = courseInfo.getGradingOptions();
                    Set<String> regOpts = new HashSet<String>(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
                    for (String gradingOption : gradingOptions) {
                        if (regOpts.contains(gradingOption)) {
                            studentRegOptions.add(gradingOption);
                        } else {
                            crsGradingOptions.add(gradingOption);
                        }
                    }
                    //Audit is pulled out into a dynamic attribute on course so map it back
                    if ("true".equals(courseInfo.getAttributeValue(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))) {
                        studentRegOptions.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
                    }
                }

                //TODO REMOVE THIS WHEN KRAD IS FIXED
                if (coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                    formObject.setAuditStudentRegOpts(true);
                } else {
                    formObject.setAuditStudentRegOpts(false);
                }
                if (coInfo.getStudentRegistrationGradingOptions().contains(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                    formObject.setPassFailStudentRegOpts(true);
                } else {
                    formObject.setPassFailStudentRegOpts(false);
                }

                formObject.setStudentRegOptions(studentRegOptions);
                formObject.setCrsGradingOptions(crsGradingOptions);

                //6. Defining Credit Option and if CLU is fixed (then it's disabled)
                boolean creditOptionFixed = false;
                String creditOptionId = coInfo.getCreditOptionId();

                CreditOptionInfo creditOption = new CreditOptionInfo();

                //Grab the Course's credit constraints
                //FindBugs: getCreditOptions() null check is in CourseInfo
                List<ResultValuesGroupInfo> courseCreditOptions = courseInfo.getCreditOptions();
                int firstValue = 0;

                //Lookup the related course's credit constraints and set them on the creditOption
                if (coInfo.getCourseId() != null && !courseCreditOptions.isEmpty()) {
                    ResultValuesGroupInfo resultValuesGroupInfo = courseCreditOptions.get(firstValue);
                    //Check for fixed
                    if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                        if (!resultValuesGroupInfo.getResultValueKeys().isEmpty()) {
                            creditOption.setCourseFixedCredits(CourseOfferingManagementUtil.getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(firstValue), contextInfo).getValue());
                        }
                        //Set the flag
                        creditOptionFixed = true;

                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        creditOption.setFixedCredit(creditOption.getCourseFixedCredits());
                        creditOption.getAllowedCredits().add(creditOption.getCourseFixedCredits());
                    } else {
                        //This is either range or multiple

                        //Copy all the allowed credits and sort so that the multiple checkboxes can be properly displayed
                        List<ResultValueInfo> resultValueInfos = CourseOfferingManagementUtil.getLrcService().getResultValuesForResultValuesGroup(resultValuesGroupInfo.getKey(), contextInfo);
                        for (ResultValueInfo rVI : resultValueInfos) {
                            creditOption.getAllowedCredits().add(rVI.getValue());
                        }
                        Collections.sort(creditOption.getAllowedCredits());

                        if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                            creditOption.setCourseMinCredits(resultValuesGroupInfo.getResultValueRange().getMinValue());
                            creditOption.setCourseMaxCredits(resultValuesGroupInfo.getResultValueRange().getMaxValue());

                            //Default the value
                            creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                            creditOption.setMinCredits(creditOption.getCourseMinCredits());
                            creditOption.setMaxCredits(creditOption.getCourseMaxCredits());
                        } else if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                            //Default the value
                            creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                            creditOption.getCredits().addAll(creditOption.getAllowedCredits());
                        }
                    }
                }

                //Lookup the selected credit option and set from persisted values
                if (creditOptionId != null) {
                    //Lookup the resultValueGroup Information
                    ResultValuesGroupInfo resultValuesGroupInfo = CourseOfferingManagementUtil.getLrcService().getResultValuesGroup(creditOptionId, contextInfo);
                    String typeKey = resultValuesGroupInfo.getTypeKey();

                    //Get the actual values
                    List<ResultValueInfo> resultValueInfos = CourseOfferingManagementUtil.getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

                    if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                        if (!resultValueInfos.isEmpty()) {
                            creditOption.setFixedCredit(resultValueInfos.get(firstValue).getValue());
                        }
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                        creditOption.setMinCredits(resultValuesGroupInfo.getResultValueRange().getMinValue());
                        creditOption.setMaxCredits(resultValuesGroupInfo.getResultValueRange().getMaxValue());
                    } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                        if (!resultValueInfos.isEmpty()) {
                            List<String> credits = new ArrayList<String>();
                            for (ResultValueInfo resultValueInfo : resultValueInfos) {
                                credits.add(resultValueInfo.getValue());
                            }
                            creditOption.setCredits(credits);
                        }
                    }
                }

                formObject.setCreditOption(creditOption);
                formObject.setCreditOptionFixed(creditOptionFixed);

                formObject.setOrganizationNames(new ArrayList<OrganizationInfoWrapper>());

                ArrayList<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();

                if (coInfo.getUnitsDeploymentOrgIds() != null) {
                    for (String orgId : coInfo.getUnitsDeploymentOrgIds()) {
                        OrgInfo orgInfo = CourseOfferingManagementUtil.getOrganizationService().getOrg(orgId, contextInfo);
                        orgList.add(new OrganizationInfoWrapper(orgInfo));
                    }
                }
                formObject.setOrganizationNames(orgList);

                List<String> socIds = CourseOfferingManagementUtil.getCourseOfferingSetService().getSocIdsByTerm(coInfo.getTermId(), ContextUtils.createDefaultContextInfo());
                if (socIds != null && !socIds.isEmpty()) {
                    List<SocInfo> targetSocs = CourseOfferingManagementUtil.getCourseOfferingSetService().getSocsByIds(socIds, ContextUtils.createDefaultContextInfo());
                    for (SocInfo soc : targetSocs) {
                        if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                            formObject.setSocInfo(soc);
                        }
                    }
                }

                setTermPropertiesOnFormObject(formObject, coInfo, contextInfo);
                formObject.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(formObject.getTerm(), formObject.getSocInfo(),
                        CourseOfferingManagementUtil.getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), contextInfo));

                document.getNewMaintainableObject().setDataObject(formObject);
                document.getOldMaintainableObject().setDataObject(formObject);
                document.getDocumentHeader().setDocumentDescription("Edit CO - " + coInfo.getCourseOfferingCode());

                Person user = GlobalVariables.getUserSession().getPerson();

                boolean canOpenView = this.getDocumentDictionaryService().getDocumentAuthorizer(document).canOpen(document, user);

                // Work around, should be fixed with KULRICE-8049
                if (!canOpenView) {
                    throw new AuthorizationException(user.getPrincipalName(), "open", null,
                            "User '" + user.getPrincipalName() + "' is not authorized to open view", null);
                }


                //Cross listing
                for (CourseOfferingCrossListingInfo crossListingInfo : coInfo.getCrossListings()) {
                    formObject.getAlternateCOCodes().add(crossListingInfo.getCode());
                    formObject.getAlternateCourseCodesSuffixStripped().add(crossListingInfo.getCode());
                }

                loadNavigationDetails(formObject);

                //check if the final exam status is the same as the one of CM
                String finalExamTypeCM = "";
                if (!courseInfo.getAttributes().isEmpty()) {
                    for (AttributeInfo info : courseInfo.getAttributes()) {
                        if (info.getKey().equals(CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_KEY)) {
                            finalExamTypeCM = info.getValue();
                        }
                    }
                }
                if (!finalExamTypeCM.isEmpty() && !StringUtils.equals(convertCourseFinalExamTypeToCourseOfferingFinalExamType(finalExamTypeCM), coInfo.getFinalExamType())) {
                    GlobalVariables.getMessageMap().putWarningForSectionId("delivery_and_assessment", CourseOfferingConstants.COURSEOFFERING_MSG_WARNING_FINALEXAMTYPE_DIFF_CM);
                }

                // set the display text for use final exam matrix in read-only view
                if (formObject.isUseFinalExamMatrix()) {
                    formObject.setUseFinalExamMatrixUI(CourseOfferingConstants.COURSEOFFERING_TEXT_USE_FINAL_EXAM_MATRIX);
                } else {
                    formObject.setUseFinalExamMatrixUI(CourseOfferingConstants.COURSEOFFERING_TEXT_NOT_USE_FINAL_EXAM_MATRIX);
                }

                // retrieve the exam period id
                try {
                    String examPeriodId = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamPeriodId(coInfo.getTermId(), contextInfo);
                    if (!StringUtils.isEmpty(examPeriodId)) {
                        formObject.setExamPeriodId(examPeriodId);
                    }
                } catch (DoesNotExistException e) {
                    LOG.warn("The Term " + formObject.getTermName() + " that the course offering " + formObject.getCourseOfferingCode() + " is attached to doesn't have an exam period to create exam offerings.");
                }

                return formObject;
            }
        } catch (AuthorizationException ae) {
            throw new AuthorizationException(ae.getUserId(), "open", null,
                    "User '" + ae.getUserId() + "' is not authorized to open view", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected static String convertCourseFinalExamTypeToCourseOfferingFinalExamType(String courseFinalExamType) {
        String sRet;
        if ("STD".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD;
        } else if ("ALT".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_ALTERNATE;
        } else {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_NONE;
        }
        return sRet;
    }

    private void loadNavigationDetails(CourseOfferingEditWrapper wrapper) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> relatedCOs = searchForRelatedCOs(wrapper);
        int indexOfCurrentCo = buildListOfRelatedCOsAndReturnIndexOfCurrentCO(wrapper, relatedCOs);
        wrapper.getRenderHelper().setSelectedCoCode(wrapper.getId());
        setPreviousToCurrentCO(wrapper, indexOfCurrentCo, relatedCOs);
        setNextToCurrentCO(wrapper, indexOfCurrentCo, relatedCOs);
    }

    private List<CourseOfferingInfo> searchForRelatedCOs(CourseOfferingEditWrapper wrapper) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> result = new ArrayList<CourseOfferingInfo>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, wrapper.getCourse().getSubjectArea());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, wrapper.getTerm().getId());
        List<CourseOfferingInfo> relatedCOs = CourseOfferingViewHelperUtil.loadCourseOfferings(searchRequest);
        if (relatedCOs != null) {
            result.addAll(relatedCOs);
        }

        return result;
    }

    private int buildListOfRelatedCOsAndReturnIndexOfCurrentCO(CourseOfferingEditWrapper currentCO, List<CourseOfferingInfo> relatedCOs) {

        int indexOfCurrentCo = -1;
        for (CourseOfferingInfo coInfo : relatedCOs) {

            // store index of current item if it's the one the user is looking at
            if (currentCO.getId().equals(coInfo.getId())) {
                indexOfCurrentCo = relatedCOs.indexOf(coInfo);
            }

            // store current item in list of related-COs
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(coInfo.getId());
            keyValue.setValue(coInfo.getCourseOfferingCode());
            currentCO.getRenderHelper().getRelatedCOs().add(keyValue);
        }

        return indexOfCurrentCo;
    }

    private void setPreviousToCurrentCO(CourseOfferingEditWrapper currentCO, int indexOfCurrentCO, List<CourseOfferingInfo> relatedCOs) {
        CourseOfferingInfo previousCoInfo = new CourseOfferingInfo();
        if (indexOfCurrentCO > 0) {
            previousCoInfo = relatedCOs.get(indexOfCurrentCO - 1);
        }
        currentCO.getRenderHelper().setPrevCO(previousCoInfo);
    }

    private void setNextToCurrentCO(CourseOfferingEditWrapper currentCO, int indexOfCurrentCO, List<CourseOfferingInfo> relatedCOs) {
        CourseOfferingInfo nextCoInfo = new CourseOfferingInfo();
        if (indexOfCurrentCO < relatedCOs.size() - 1) {
            nextCoInfo = relatedCOs.get(indexOfCurrentCO + 1);
        }
        currentCO.getRenderHelper().setNextCO(nextCoInfo);
    }

    private void setTermPropertiesOnFormObject(CourseOfferingEditWrapper formObject, CourseOfferingInfo coInfo, ContextInfo contextInfo) throws Exception {

        TermInfo termInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(coInfo.getTermId(), contextInfo);
        formObject.setTerm(termInfo);
        formObject.setTermName(termInfo.getName());

        // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
        String termStartDate = new String(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getStartDate()));
        String termEndDate = new String(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(termInfo.getEndDate()));
        StringBuilder termStartEnd = new StringBuilder();
        termStartEnd.append(termInfo.getName());
        termStartEnd.append(" (");
        termStartEnd.append(termStartDate);
        termStartEnd.append(" to ");
        termStartEnd.append(termEndDate);
        termStartEnd.append(")");
        formObject.setTermStartEnd(termStartEnd.toString());
    }

    private FormatInfo getFormatInfo(CourseOfferingEditWrapper courseOfferingEditWrapper, String coFormId) {
        List<FormatInfo> formatInfoList = courseOfferingEditWrapper.getCourse().getFormats();
        for (FormatInfo formatInfo : formatInfoList) {
            if (coFormId.equals(formatInfo.getId())) {
                return formatInfo;
            }
        }
        return null;
    }

    private void setFinalExamDriverAttr(CourseOfferingInfo coInfo, CourseOfferingEditWrapper coEditWrapper) {
        AttributeInfo attributeInfo = this.getAttributeForKey(coInfo, CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
        if (coInfo.getFinalExamType().equals(FinalExam.STANDARD.toString())) {  //driver is only for 'STANDARD'
            if (attributeInfo != null) {
                attributeInfo.setValue(coEditWrapper.getFinalExamDriver());
            } else {
                attributeInfo = createAttribute(CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR, coEditWrapper.getFinalExamDriver());
                coInfo.getAttributes().add(attributeInfo);
            }
        } else {
            if (attributeInfo != null) {
                attributeInfo.setValue("na");
            }
        }
    }

    private void setUseFinalExamMatrix(CourseOfferingInfo coInfo, CourseOfferingEditWrapper coEditWrapper) {
        AttributeInfo attributeInfo = this.getAttributeForKey(coInfo, CourseOfferingServiceConstants.FINAL_EXAM_USE_MATRIX);
        if (coInfo.getFinalExamType().equals(FinalExam.STANDARD.toString())) {  //driver is only for 'STANDARD'
            if (attributeInfo != null) {
                attributeInfo.setValue(String.valueOf(coEditWrapper.isUseFinalExamMatrix()));
            } else {
                attributeInfo = createAttribute(CourseOfferingServiceConstants.FINAL_EXAM_USE_MATRIX, String.valueOf(coEditWrapper.isUseFinalExamMatrix()));
                coInfo.getAttributes().add(attributeInfo);
            }
        } else {
            if (attributeInfo != null) {
                attributeInfo.setValue("false");
            }
        }
    }

    private AttributeInfo getAttributeForKey(CourseOfferingInfo coInfo, String key) {
        for (AttributeInfo info : coInfo.getAttributes()) {
            if (info.getKey().equals(key)) {
                return info;
            }
        }
        return null;
    }

    private AttributeInfo createAttribute(String key, String value) {
        AttributeInfo newAttr = new AttributeInfo();
        newAttr.setKey(key);
        newAttr.setValue(value);
        return newAttr;
    }
}