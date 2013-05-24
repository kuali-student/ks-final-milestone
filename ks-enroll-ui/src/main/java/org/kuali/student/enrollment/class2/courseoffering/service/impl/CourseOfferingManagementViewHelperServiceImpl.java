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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ToolbarUtil;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentDisplayInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Deprecated
/**
 * @deprecated
 * @see org.kuali.student.enrollment.class2.autogen.service.impl.ARGCourseOfferingManagementViewHelperServiceImpl
 */
public class CourseOfferingManagementViewHelperServiceImpl extends CO_AO_RG_ViewHelperServiceImpl implements CourseOfferingManagementViewHelperService{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private SearchService searchService = null;

    private CourseService courseService;
    private LRCService lrcService;
    private AtpService atpService;
    private CourseOfferingSetService socService;
    private static PermissionService permissionService;

    /**
     * This method fetches the <code>TermInfo</code> and validate for exact match
     *
     * @param form
     * @throws Exception
     */
    public void populateTerm(CourseOfferingManagementForm form) throws Exception {

        String termCode = form.getTermCode();

        form.getCourseOfferingResultList().clear();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<TermInfo> terms = getAcalService().searchForTerms(criteria, createContextInfo());

        if (terms.isEmpty()){
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
        } else if (terms.size() > 1){
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
        } else {
            form.setTermInfo(terms.get(0));

            // setting term first day of classes
            List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(form.getTermInfo().getId(), createContextInfo());
            Date termClassStartDate = null;
            for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                if (keyDateInfo.getTypeKey().equalsIgnoreCase(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY) && keyDateInfo.getStartDate() != null) {
                    termClassStartDate = keyDateInfo.getStartDate();
                    break;
                }
            }
            form.setTermClassStartDate(termClassStartDate);
        }

    }

    /**
     * This method loads all the course offerings for a term and course code.
     *
     * @param termId
     * @param courseCode
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, CourseOfferingManagementForm form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        if (form.getCourseOfferingResultList().isEmpty()){
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Code", courseCode, termId);
        }

    }

    /**
     * This method loads all the course offerings for a term and subject area/code.
     *
     * @param termId term id
     * @param subjectCode subject area
     * @param form course offering management form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, CourseOfferingManagementForm form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, subjectCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        if (form.getCourseOfferingResultList().isEmpty()){
            LOG.error("Error: Can't find any Course Offering for a Subject Code: " + subjectCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Subject Code", subjectCode, termId);
        }

    }

    /**
     * This method fetches all the course offerings for a term and course/subject code.
     *
     * @see CourseOfferingManagementSearchImpl Actual CO search happens here
     * @param searchRequest
     * @param form
     * @throws Exception
     */
    protected void loadCourseOfferings(SearchRequestInfo searchRequest,CourseOfferingManagementForm form) throws Exception {

        ContextInfo contextInfo = createContextInfo();

        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);

        form.getCourseOfferingResultList().clear();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingListSectionWrapper coListWrapper = new CourseOfferingListSectionWrapper();

            for(SearchResultCellInfo cellInfo : row.getCells()){

                String value = StringUtils.EMPTY;
                if(cellInfo.getValue() != null)  {
                    value = new String(cellInfo.getValue());
                }

                if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCode(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingDesc(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.STATE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingStateKey(value);
                    coListWrapper.setCourseOfferingStateDisplay(getStateInfo(value).getName());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionKey(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingGradingOptionKey(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION_NAME.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingGradingOptionDisplay(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION_NAME.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionDisplay(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DEPLOYMENT_ORG_ID.equals(cellInfo.getKey())){
                    coListWrapper.setAdminOrg(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingId(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.SUBJECT_AREA.equals(cellInfo.getKey())){
                    coListWrapper.setSubjectArea(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())){
                    coListWrapper.setCrossListed(BooleanUtils.toBoolean(value));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())){
                    coListWrapper.setAlternateCOCodes(Arrays.asList(StringUtils.split(value, ",")));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_CODE.equals(cellInfo.getKey())){
                   coListWrapper.setOwnerCode(value);
               }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_ALIASES.equals(cellInfo.getKey())){
                    coListWrapper.setOwnerAliases(Arrays.asList(StringUtils.split(value, ",")));
               }

            }
            form.getCourseOfferingResultList().add(coListWrapper);
        }

        setSocStateKeys(form);
    }


    /**
     * This method loads the previous and next course offerings for navigation purpose.
     *
     * @param form
     */
    public void loadPreviousAndNextCourseOffering(CourseOfferingManagementForm form){
        try{
            ContextInfo contextInfo = createContextInfo();

            /**
             * Get all the course offerings for a term and subject area.
             */
            SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, form.getSubjectCode());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, form.getTermInfo().getId());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

            SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);
            List<CourseOfferingWrapper> availableCOs = new ArrayList<CourseOfferingWrapper>();

            for (SearchResultRowInfo row : searchResult.getRows()) {

                String courseOfferingCode = "";
                String courseOfferingId = "";
                String courseOfferingDesc = "";
                boolean isCrossListed = false;
                List<String> alternateCodes = null;

                for(SearchResultCellInfo cellInfo : row.getCells()){

                    String value = StringUtils.EMPTY;
                    if(cellInfo.getValue() != null)  {
                        value = new String(cellInfo.getValue());
                    }

                    if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                        courseOfferingCode = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                        courseOfferingId = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                        courseOfferingDesc = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())){
                        isCrossListed = BooleanUtils.toBoolean(value);
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())){
                        alternateCodes = Arrays.asList(StringUtils.split(value, ","));
                    }

                }

                CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(isCrossListed,courseOfferingCode,courseOfferingDesc,alternateCodes,courseOfferingId);
                availableCOs.add(coWrapper);
            }

            /**
             * Find the current course offering index and set the previous and next course offerings if exists.
             */
            for (CourseOfferingWrapper coWrapper : availableCOs) {
                if (StringUtils.equals(coWrapper.getCourseOfferingCode(),form.getCurrentCourseOfferingWrapper().getCourseOfferingCode())){
                    int currentIndex = availableCOs.indexOf(coWrapper);
                    form.setInputCode(coWrapper.getCourseOfferingCode());
                    if (currentIndex > 0){
                        form.setPreviousCourseOfferingWrapper(availableCOs.get(currentIndex-1));
                    }else{
                        form.setPreviousCourseOfferingWrapper(null);
                    }
                    if (currentIndex < availableCOs.size()-1){
                        form.setNextCourseOfferingWrapper(availableCOs.get(currentIndex+1));
                    }else{
                        form.setNextCourseOfferingWrapper(null);
                    }
                    break;
                }
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createActivityOfferings(String formatId, String activityId, int noOfActivityOfferings, CourseOfferingManagementForm form){
        String termcode;
        FormatInfo format = null;
        CourseInfo course;
        CourseOfferingInfo courseOffering = form.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        ContextInfo contextInfo = createContextInfo();

        // Get the format object for the id selected
        try {
            course = getCourseService().getCourse(courseOffering.getCourseId(), contextInfo);
            for (FormatInfo f : course.getFormats()) {
                if(f.getId().equals(formatId)) {
                    format = f;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // find the format offering object for the selected format
        FormatOfferingInfo formatOfferingInfo = null;
        try {
            List<FormatOfferingInfo> courseOfferingFOs = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOffering.getId(), contextInfo);
            for(FormatOfferingInfo fo : courseOfferingFOs) {
                if (fo.getFormatId().equals(formatId)) {
                    formatOfferingInfo = fo;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // find the Activity object that matches the activity id selected
        ActivityInfo activity = null;
        //FindBugs: getActivities() null check is in FormatInfo
        List<ActivityInfo> activities = format.getActivities();
        for (ActivityInfo info : activities) {
            if (StringUtils.equals(activityId, info.getId())) {
                activity = info;
            }
        }

        // Get the matching activity offering type for the selected activity
        TypeInfo activityOfferingType;
        try {
            List<TypeInfo> types = getTypeService().getAllowedTypesForType(activity.getTypeKey(), contextInfo);
            // only one AO type should be mapped to each Activity type
            if(types.size() > 1) {
                throw new RuntimeException("More than one allowed type is matched to activity type of: " + activity.getTypeKey());
            }
            if(types.isEmpty()){
                throw new RuntimeException("No Clu to Lui type mapping found in TypeService for: " + activity.getTypeKey());
            }

            activityOfferingType = types.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            AtpInfo termAtp = getAtpService().getAtp(courseOffering.getTermId(), contextInfo);
            termcode = termAtp.getCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i=0;i<noOfActivityOfferings;i++){
            ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
            aoInfo.setActivityId(activityId);
            aoInfo.setFormatOfferingId(formatOfferingInfo.getId());
            aoInfo.setTypeKey(activityOfferingType.getKey());
            aoInfo.setCourseOfferingId(courseOffering.getId());
            aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
            aoInfo.setTermId(courseOffering.getTermId());
            aoInfo.setTermCode(termcode);
            aoInfo.setFormatOfferingName(formatOfferingInfo.getName());
            aoInfo.setCourseOfferingTitle(courseOffering.getCourseOfferingTitle());
            aoInfo.setCourseOfferingCode(courseOffering.getCourseOfferingCode());

            try {
                ActivityOfferingInfo activityOfferingInfo = _getCourseOfferingService().createActivityOffering(formatOfferingInfo.getId(), activityId, activityOfferingType.getKey(), aoInfo, contextInfo);
                ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOfferingInfo);
                StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
                wrapper.setStateName(state.getName());
                TypeInfo typeInfo = getTypeInfo(wrapper.getAoInfo().getTypeKey());
                wrapper.setTypeName(typeInfo.getName());
                form.getActivityWrapperList().add(wrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        if(noOfActivityOfferings == 1){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_1_SUCCESS);
        } else {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_N_SUCCESS);
        }
    }

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception{
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
            activityOfferingWrapperList = new ArrayList<ActivityOfferingWrapper>(activityOfferingInfoList.size());

            for (ActivityOfferingInfo info : activityOfferingInfoList) {
                ActivityOfferingWrapper aoWrapper = convertAOInfoToWrapper(info);
                activityOfferingWrapperList.add(aoWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not load AOs for course offering [%s].", courseOfferingId), e);
        }
        form.setActivityWrapperList(activityOfferingWrapperList);
    }

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId (String courseOfferingId, CourseOfferingManagementForm form) throws Exception{
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
            activityOfferingWrapperList = new ArrayList<ActivityOfferingWrapper>(activityOfferingInfoList.size());

            for (ActivityOfferingInfo info : activityOfferingInfoList) {
                ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(info);
                activityOfferingWrapperList.add(aoWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not load AOs for course offering [%s].", courseOfferingId), e);
        }
        return activityOfferingWrapperList;
    }

    public void approveCourseOfferings(CourseOfferingManagementForm form) throws Exception{
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(CourseOfferingListSectionWrapper co : coList) {
            if(co.getIsChecked()){
                checked++;
                 if(co.isEnableApproveButton()) {
                     enabled++;
                     List<ActivityOfferingWrapper> aos = getActivityOfferingsByCourseOfferingId(co.getCourseOfferingId(), form);
                     if(aos != null && !aos.isEmpty()){
                         ToolbarUtil.processAoToolbarForUser(aos, form);
                         for(ActivityOfferingWrapper ao : aos){
                            if(ao.isEnableApproveButton()){
                                getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            }
                        }
                    }
                }
            }
         }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void deleteCourseOfferings(CourseOfferingManagementForm form) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        int checked = 0;
        int enabled = 0;
        int totalCosToDelete = 0;
        int totalColocatedCos = 0;
        int totalColocatedAos = 0;
        boolean iscolocated = false;
        int totalCrossListedCosToDelete = 0;
        int totalJointDefinedCosToDelete = 0;

        form.setNumOfColocatedCosToDelete(0);
        form.setNumOfColocatedAosToDelete(0);
        form.setNumOfCrossListedCosToDelete(0);
        form.setNumOfJointDefinedCosToDelete(0);

        List<CourseOfferingListSectionWrapper> qualifiedToDeleteList = form.getSelectedCoToDeleteList();
        qualifiedToDeleteList.clear();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        int totalAos = 0;
        form.setCrossListedCO(false);
        form.setColocatedCO(false);
        for (CourseOfferingListSectionWrapper co : coList) {
            boolean hasDeletion = true;
            if (co.getIsChecked()) {
                checked++;
                totalCosToDelete++;
                iscolocated = false;

                List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(co.getCourseOfferingId(), contextInfo);
                List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCourseOffering(co.getCourseOfferingId(), contextInfo);

                co.setCoHasAoToDelete(true);
                if (aoDisplayInfoList.isEmpty()) {
                    co.setCoHasAoToDelete(false);
                }
                co.setColocated(false);

                if (aoDisplayInfoList != null && !aoDisplayInfoList.isEmpty()) {
                    for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {

                        ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
                        aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);
                        aoDisplayWrapper.setActivityOfferingCode(aoDisplayInfo.getActivityOfferingCode());
                        // Adding Information (icons)
                        String information = "";
                        if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                            information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
                        }
                        aoDisplayWrapper.setInformation(information);

                        if (aoDisplayInfo.getScheduleDisplay() != null && !aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()) {
                            //TODO handle TBA state
                            //ScheduleComponentDisplay scheduleComponentDisplay = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().get(0);
                            List<ScheduleComponentDisplayInfo> scheduleComponentDisplays = (List<ScheduleComponentDisplayInfo>) aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays();
                            for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleComponentDisplays) {
                                if (scheduleComponentDisplay.getBuilding() != null) {
                                    aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                                }
                                if (scheduleComponentDisplay.getRoom() != null) {
                                    aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode(), true);
                                }
                                if (!scheduleComponentDisplay.getTimeSlots().isEmpty()) {
                                    if (scheduleComponentDisplay.getTimeSlots().get(0).getStartTime() != null) {
                                        aoDisplayWrapper.setStartTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime().getMilliSeconds()), true);
                                    }
                                    if (scheduleComponentDisplay.getTimeSlots().get(0).getEndTime() != null) {
                                        aoDisplayWrapper.setEndTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime().getMilliSeconds()), true);
                                    }
                                    aoDisplayWrapper.setDaysDisplayName(getDays(scheduleComponentDisplay.getTimeSlots().get(0).getWeekdays()), true);
                                }
                            }
                        }
                        // if ao is colocated AO add colocated info
                        if(isColocatedAo(aoDisplayInfo.getActivityOfferingCode(), aoInfoList))  {
                            String colocateInfo = CourseOfferingViewHelperUtil.createColocatedDisplayData(getAoInfo(aoDisplayInfo.getActivityOfferingCode(), aoInfoList), contextInfo);
                            aoDisplayWrapper.setColocatedAoInfo(colocateInfo);
                            co.setColocated(true);
                            co.setColocatedCoCode(colocateInfo);
                            form.setColocatedCO(true);
                            totalColocatedAos++;
                            iscolocated = true;
                        }
                        co.getAoToBeDeletedList().add(aoDisplayWrapper);
                    }

                    totalAos = totalAos + co.getAoToBeDeletedList().size();
                    co.setCrossListed(false);
                    if (co.getAlternateCOCodes() != null && co.getAlternateCOCodes().size() > 0) {
                        co.setCrossListed(true);
                        form.setCrossListedCO(true);
                        totalCrossListedCosToDelete++;
                    }
                }
                co.setJointDefined(false);

                if(iscolocated) {
                    totalColocatedCos++;
                } else {
                    // verify whether this CO is joint-defined or not
                    String jointDefinedCodes = getJointDefinedInfo(co);
                    if(!jointDefinedCodes.isEmpty()) {
                        co.setJointDefinedCoCode(jointDefinedCodes);
                        co.setJointDefined(true);
                        totalJointDefinedCosToDelete++;
                    }
                }
                qualifiedToDeleteList.add(co);

                if (hasDeletion) {
                    enabled++;
                }
            }
        }
        form.setNumOfCrossListedCosToDelete(totalCrossListedCosToDelete);
        form.setNumOfJointDefinedCosToDelete(totalJointDefinedCosToDelete);
        if(totalColocatedCos == totalCosToDelete) {
            form.setColocatedCoOnly(true);
            form.setNumOfColocatedCosToDelete(totalColocatedCos);
        }
        if(totalJointDefinedCosToDelete >= 1)  {
            form.setJointDefinedCo(true);
        }
        form.setNumOfColocatedAosToDelete(totalColocatedAos);
        form.setTotalAOsToBeDeleted(totalAos);
    }

    public void approveActivityOfferings(CourseOfferingManagementForm form) throws Exception{
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(ActivityOfferingWrapper ao : aoList) {
            if(ao.getIsChecked()){
                checked++;
                 if(ao.isEnableApproveButton()) {
                     enabled++;
                    getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                 }
            }
         }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void draftActivityOfferings(CourseOfferingManagementForm form) throws Exception{
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(ActivityOfferingWrapper ao : aoList) {
             if(ao.getIsChecked()){
                 checked++;
                 if(ao.isEnableDraftButton()) {
                     enabled++;
                    getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
                 }
             }
         }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_N_SUCCESS);
            }
        }

    }
    /**
     * @param aoList The list of AOs to evaluate.
     * @param selectedAction The state change action to perform.
     * @throws Exception
     */
    public void changeActivityOfferingsState(List<ActivityOfferingWrapper> aoList, CourseOfferingInfo courseOfferingInfo, String selectedAction) throws Exception {
        ContextInfo contextInfo = createContextInfo();
        StateInfo draftState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
        StateInfo approvedState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);

        boolean hasBadStateWarning = false, hasStateChangedAO = false, isDraftAction = false;
        String messageKeyWarn, messageKeyError;

        //  Setup feedback message keys.
        if (StringUtils.equals(CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION, selectedAction)) {
            isDraftAction = true;
            messageKeyWarn = CourseOfferingConstants.COURSEOFFERING_SET_TO_DRAFT_SOME_AOS_UPATED;
            messageKeyError = CourseOfferingConstants.COURSEOFFERING_SET_TO_DRAFT_NO_AOS_UPDATED;
        } else {
            messageKeyWarn = CourseOfferingConstants.COURSEOFFERING_APPROVE_FOR_SCHEDULING_SOME_AOS_UPDATED;
            messageKeyError = CourseOfferingConstants.COURSEOFFERING_APPROVE_FOR_SCHEDULING_NO_AOS_UPDATED;
        }

        for (ActivityOfferingWrapper wrapper : aoList) {
            //  Only evaluate items that were selected/checked in the UI.
            if (wrapper.getIsChecked()) {
                //  If the action is "Set as Draft" then the current state of the AO must be "Approved".
                if (isDraftAction) {
                    if (StringUtils.equals(wrapper.getAoInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
                        wrapper.getAoInfo().setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                        wrapper.setStateName(draftState.getName());
                        getCourseOfferingService().changeActivityOfferingState(wrapper.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                    } else {
                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
                    }
                    //  If the action is "Approve for Scheduling" then AO state must be "Draft"
                } else {
                    if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, wrapper.getAoInfo().getStateKey())) {
                        wrapper.getAoInfo().setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
                        wrapper.setStateName(approvedState.getName());
                        getCourseOfferingService().changeActivityOfferingState(wrapper.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                    } else {
                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
                    }
                }
            }
        }

        //  Set feedback message.
        if ( ! hasStateChangedAO) {
            GlobalVariables.getMessageMap().putError("selectedOfferingAction", messageKeyError);
        } else {
            if (hasBadStateWarning) {
                GlobalVariables.getMessageMap().putWarning("selectedOfferingAction", messageKeyWarn);
            }
        }
    }

    /**
     *  Same as markCourseOfferingsForScheduling() but defaults isChecked() == true.
     *  @param coWrappers, viewId, socStateKey provides list of CourseOfferings, viewId, socState.
     */
    public void  markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers, String viewId, String socStateKey) throws Exception {
        markCourseOfferingsForScheduling(coWrappers, viewId, socStateKey, true);
    }

    /**
     *  Examines a List of CourseOffering wrappers and changes the state of each "checked" AO (meaning the
     *  CO was selected on the UI) from "Draft" to "Approved". If the AO has a state other than "Draft" the AO is ignored.
     *  Also, changes the state of the CourseOffering if appropriate.
     *
     * @param coWrappers, viewId, socStateKey provides list of CourseOfferings, viewId, socState.
     * @param checkedOnly True if the CO wrapper isChecked() flag should be respected.
     */
    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers, String viewId, String socStateKey, boolean checkedOnly) throws Exception {
        boolean hasAOWarning = false, hasStateChangedAO = false, hasOrgWarning = false;
        ContextInfo contextInfo = createContextInfo();
        for (CourseOfferingListSectionWrapper coWrapper : coWrappers) {
            if (coWrapper.getIsChecked() || ! checkedOnly) {
                // Checking if the person is authorized to approve
                Map<String,String> permissionDetails = new HashMap<String,String>();
                Map<String,String> roleQualifications = new HashMap<String,String>();
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), contextInfo);
                List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
                String orgIDs = "";
                if(orgIds != null && !orgIds.isEmpty()){
                    for (String orgId : orgIds) {
                        orgIDs = orgIDs + orgId + ",";
                    }
                }
                boolean canApproveAOs = true;
                if (orgIDs.length() > 0) {
                    String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

                    roleQualifications.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length()-1));

                    permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, viewId);
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveSubj");

                    String socState = socStateKey==null?null:socStateKey.substring(socStateKey.lastIndexOf('.')+1);
                    permissionDetails.put("socState", socState);

                    canApproveAOs = getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications);
                }

                if (!canApproveAOs) {  // if can't approve AOs for all COs (because they are in a different org)
                    if (!hasOrgWarning) hasOrgWarning = true;
                    continue;
                } else {
                    List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coWrapper.getCourseOfferingId(),contextInfo);
                    if (activityOfferingInfos.size() == 0) {
                        if ( ! hasAOWarning) hasAOWarning = true;
                        continue;
                    }
                    // Iterate through the AOs and state change Draft -> Approved.
                    for (ActivityOfferingInfo activityOfferingInfo : activityOfferingInfos) {
                        boolean isAOStateDraft = StringUtils.equals(activityOfferingInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                        if (isAOStateDraft) {
                            StatusInfo statusInfo = getCourseOfferingService().changeActivityOfferingState(activityOfferingInfo.getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            if (!statusInfo.getIsSuccess()){
                                GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSE_OFFERING_STATE_CHANGE_ERROR,coWrapper.getCourseOfferingCode(),statusInfo.getMessage());
                            }
                            //  Flag if any AOs can be state changed. This affects the error message whi.
                            if (statusInfo.getIsSuccess()){
                                hasStateChangedAO = true;
                            }
                        } else {
                            //  Flag if any AOs are not in a valid state for approval.
                            if ( ! hasAOWarning) hasAOWarning = true;
                        }
                    }
                }
            }
        }
        //  Set feedback messages.
        if (!hasStateChangedAO) {
            GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_NONE_APPROVED);
        } else {
            if (hasAOWarning) {
                GlobalVariables.getMessageMap().putWarning("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_WITH_AO_DRAFT_APPROVED_ONLY);
            }
            if (hasOrgWarning) {
                GlobalVariables.getMessageMap().putWarning("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_WITH_AO_ORG_APPROVED_ONLY);
            }
        }
    }

    public void setSocStateKeys (CourseOfferingManagementForm form) throws Exception{
        String termCode = form.getTermInfo().getId();
        List<String> socIds = getSocService().getSocIdsByTerm(termCode, createContextInfo());
        if (socIds != null && !socIds.isEmpty()) {
            List<SocInfo> targetSocs = this.getSocService().getSocsByIds(socIds, createContextInfo());
            for (SocInfo soc: targetSocs) {
                if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    form.setSocStateKey(soc.getStateKey());
                    form.setSocSchedulingStateKey(soc.getSchedulingStateKey());
                    return;
                }
            }
        }
    }

    public static String trimTrailing0(String creditValue){
        if (creditValue.indexOf(".0") > 0) {
            return creditValue.substring(0, creditValue.length( )- 2);
        } else {
            return creditValue;
        }
    }

    private boolean isColocatedAo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for(ActivityOfferingInfo ao : aoList) {
            if(StringUtils.equals(aoCode, ao.getActivityCode())) {
                if(ao.getIsPartOfColocatedOfferingSet()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ActivityOfferingInfo getAoInfo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for(ActivityOfferingInfo ao : aoList) {
            if(StringUtils.equals(aoCode, ao.getActivityCode())) {
                if(ao.getIsPartOfColocatedOfferingSet()) {
                    return ao;
                }
            }
        }
        return null;
    }

    private String getJointDefinedInfo(CourseOfferingListSectionWrapper co) {
        if(co == null) return null;

        List<CourseInfo> coInfoList = CourseOfferingViewHelperUtil.getMatchingCoursesFromClu(co.getCourseOfferingCode());
        StringBuffer jointDefinedCodes  = new StringBuffer();

        for(CourseInfo coInfo : coInfoList) {
            List<CourseJointInfo> jointList = coInfo.getJoints();
           if(!jointList.isEmpty() && jointList.size() >= 1 ) {
               for(CourseJointInfo jointInfo : jointList)  {
                   jointDefinedCodes.append(jointInfo.getSubjectArea());
                   jointDefinedCodes.append(jointInfo.getCourseNumberSuffix());
                   jointDefinedCodes.append(" ");
               }

            }
        }

        return jointDefinedCodes.toString();
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    private String millisToTime(Long milliseconds){
        if(milliseconds == null){
            return null;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(cal.getTime());

    }

    private String convertIntoDaysDisplay(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null){
            return sb.toString();
        }

        for(Integer d : intList) {
            sb.append(convertIntoDaysDisplay(d));
        }

        return sb.toString();
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public CourseService getCourseService() {
        if (courseService == null){
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }


    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }
    public AtpService getAtpService() {
        if (atpService == null){
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    public CourseOfferingSetService getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                                                                                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private static PermissionService getPermissionService() {
        if(permissionService == null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
