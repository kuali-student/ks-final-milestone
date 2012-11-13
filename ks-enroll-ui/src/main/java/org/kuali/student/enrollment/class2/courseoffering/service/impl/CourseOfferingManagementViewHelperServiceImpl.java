package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourseOfferingManagementViewHelperServiceImpl extends CO_AO_RG_ViewHelperServiceImpl implements CourseOfferingManagementViewHelperService{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;

    private CourseService courseService;
    private LRCService lrcService;


    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        // TODO: How does one get rid of hard-coding "atpCode"?
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        AcademicCalendarService acalService = _getAcalService();
        return acalService.searchForTerms(criteria, new ContextInfo());
    }

    public void loadCourseOfferingsByTermAndSubjectCode (String termId, String subjectCode, CourseOfferingManagementForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        List<String> courseOfferingIds = _getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectCode, contextInfo);

        if(courseOfferingIds.size()>0){
            List<CourseOfferingInfo>   courseOfferingList = _getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds,contextInfo);
            form.getCourseOfferingEditWrapperList().clear();
            for(CourseOfferingInfo coInfo: courseOfferingList){
                CourseOfferingEditWrapper courseOfferingEditWrapper = new CourseOfferingEditWrapper(coInfo);
                courseOfferingEditWrapper.setGradingOption(getGradingOption(coInfo.getGradingOptionId()));
                StateInfo state = getStateService().getState(coInfo.getStateKey(),contextInfo);
                courseOfferingEditWrapper.setStateName(state.getName());
                form.getCourseOfferingEditWrapperList().add(courseOfferingEditWrapper);
            }
        } else {
            LOG.error("Error: Can't find any Course Offering for a Subject Code: "+subjectCode+" in term: "+termId);
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Subject", subjectCode, termId);
            form.getCourseOfferingEditWrapperList().clear();
        }
    }

    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, CourseOfferingManagementForm form) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();

        List<String> courseOfferingIds = _getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo); //David Yin commented out


        if(courseOfferingIds.size() > 0){
            List<CourseOfferingInfo> courseOfferingList = _getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds,contextInfo);
            form.getCourseOfferingEditWrapperList().clear();
            for(CourseOfferingInfo coInfo: courseOfferingList){
                CourseOfferingEditWrapper courseOfferingEditWrapper = new CourseOfferingEditWrapper(coInfo);
                courseOfferingEditWrapper.setGradingOption(getGradingOption(coInfo.getGradingOptionId()));
                StateInfo state = getStateService().getState(coInfo.getStateKey(),contextInfo);
                courseOfferingEditWrapper.setStateName(state.getName());
                form.getCourseOfferingEditWrapperList().add(courseOfferingEditWrapper);
            }
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Code", courseCode, termId);
            form.getCourseOfferingEditWrapperList().clear();
        }
    }

    private String getGradingOption(String gradingOptionId) throws Exception {
        String gradingOption = "";
        if(StringUtils.isNotBlank(gradingOptionId)){
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if(rvg!= null && StringUtils.isNotBlank(rvg.getName())){
                gradingOption = rvg.getName();
            }
        }
        return gradingOption;
    }

    public List<CourseOfferingInfo> findCourseOfferingsByTermAndCourseOfferingCode (String termCode, String courseOfferingCode, CourseOfferingManagementForm form) throws Exception{
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        String termId = null;

        try {
            //1. get termId based on termCode
            if (StringUtils.isNotBlank(termCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
                QueryByCriteria criteria = qbcBuilder.build();

                // Do search.  In ideal case, termList contains one element, which is the desired term.
                List<TermInfo> termList = _getAcalService().searchForTerms(criteria, new ContextInfo());

                if (termList != null  && termList.size()>0 ){
                    // Always get first term
                    termId = termList.get(0).getId();
                    if(termList.size()>1){
                        //logger.warn("AdvanceActivityOfferingLookupableImpl - find more than one term for specified termCode: " + termCode) ;
                        //System.out.println(">>Alert: find more than one term for specified termCode: "+termCode);
                        throw new RuntimeException("Alert: find more than one term for specified termCode: "+termCode);
                    }
                } else {
                    throw new RuntimeException("Error: Does not find a valid term with Term = "+ termCode);
                }
            }

            //get courseOfferingId based on courseOfferingCode and termId
            if (StringUtils.isNotBlank(courseOfferingCode) && StringUtils.isNotBlank(termId)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.equalIgnoreCase(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equalIgnoreCase(CourseOfferingConstants.ATP_ID, termId)));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search. In ideal case, returns one element, which is the desired CO.
                courseOfferings = getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courseOfferings;
    }

    public void loadPreviousAndNextCourseOffering(CourseOfferingManagementForm form, CourseOfferingInfo courseOfferingInfo){
        try{
            List<String> coIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(courseOfferingInfo.getTermId(),courseOfferingInfo.getSubjectArea(), ContextUtils.createDefaultContextInfo());
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByIds(coIds, ContextUtils.createDefaultContextInfo());

            Collections.sort(courseOfferingInfos, new Comparator<CourseOfferingInfo>() {
                @Override
                public int compare(CourseOfferingInfo o1, CourseOfferingInfo o2) {
                    if (o1.getCourseOfferingCode().length() == o2.getCourseOfferingCode().length()) {
                        return o1.getCourseOfferingCode().compareTo(o2.getCourseOfferingCode());
                    } else {
                        return o1.getCourseOfferingCode().length() - o2.getCourseOfferingCode().length();
                    }
                }
            });

            for (CourseOfferingInfo offeringInfo : courseOfferingInfos) {
                if (StringUtils.equals(courseOfferingInfo.getId(),offeringInfo.getId())){
                    int currentIndex = courseOfferingInfos.indexOf(offeringInfo);
                    form.setInputCode(offeringInfo.getCourseOfferingCode());
                    if (currentIndex > 0){
                        form.setPreviousCourseOffering(courseOfferingInfos.get(currentIndex-1));
                    }else{
                        form.setPreviousCourseOffering(null);
                    }
                    if (currentIndex < courseOfferingInfos.size()-1){
                        form.setNextCourseOffering(courseOfferingInfos.get(currentIndex+1));
                    }else{
                        form.setNextCourseOffering(null);
                    }
                    break;
                }
            }

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createActivityOfferings(String formatId, String activityId, int noOfActivityOfferings, CourseOfferingManagementForm form){

        FormatInfo format = null;
        CourseInfo course;
        CourseOfferingInfo courseOffering = form.getTheCourseOffering();

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

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

            activityOfferingType = types.get(0);
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
            try {
                ActivityOfferingInfo activityOfferingInfo = _getCourseOfferingService().createActivityOffering(formatOfferingInfo.getId(), activityId, activityOfferingType.getKey(), aoInfo, contextInfo);
                ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOfferingInfo);
                StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
                wrapper.setStateName(state.getName());
                TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), contextInfo);
                wrapper.setTypeName(typeInfo.getName());
                form.getActivityWrapperList().add(wrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception{
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
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

    /**
     * Performs
     * @param aoList The list of AOs to evaluate.
     * @param selectedAction The state change action to perform.
     * @throws Exception
     */
    public void changeActivityOfferingsState(List<ActivityOfferingWrapper> aoList, CourseOfferingInfo courseOfferingInfo, String selectedAction) throws Exception {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
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
                        ActivityOfferingInfo updatedAO = getCourseOfferingService().updateActivityOffering(wrapper.getAoInfo().getId(),wrapper.getAoInfo(),contextInfo);
                        wrapper.setAoInfo(updatedAO);
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                    } else {
                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
                    }
                    //  If the action is "Approve for Scheduling" then AO state must be "Draft"
                } else {
                    if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, wrapper.getAoInfo().getStateKey())) {
                        wrapper.getAoInfo().setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
                        wrapper.setStateName(approvedState.getName());
                        ActivityOfferingInfo updatedAO = getCourseOfferingService().updateActivityOffering(wrapper.getAoInfo().getId(),wrapper.getAoInfo(),contextInfo);
                        wrapper.setAoInfo(updatedAO);
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                    } else {
                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
                    }
                }
            }
        }
        //  Check for changes to states in the related COs and FOs
        ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(courseOfferingInfo, contextInfo);

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
     *  @param coWrappers The list of CourseOffering wrappers.
     */
    public void  markCourseOfferingsForScheduling(List<CourseOfferingEditWrapper> coWrappers) throws Exception {
        markCourseOfferingsForScheduling(coWrappers, true);
    }

    /**
     *  Examines a List of CourseOffering wrappers and changes the state of each "checked" AO (meaning the
     *  CO was selected on the UI) from "Draft" to "Approved". If the AO has a state other than "Draft" the AO is ignored.
     *  Also, changes the state of the CourseOffering if appropriate.
     *
     * @param coWrappers The list of CourseOfferings.
     * @param checkedOnly True if the CO wrapper isChecked() flag should be respected.
     */
    public void markCourseOfferingsForScheduling(List<CourseOfferingEditWrapper> coWrappers, boolean checkedOnly) throws Exception {
        boolean hasAOWarning = false, hasStateChangedAO = false;
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        for (CourseOfferingEditWrapper coWrapper : coWrappers) {
            if (coWrapper.getIsChecked() || ! checkedOnly) {
                List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coWrapper.getCoInfo().getId(),contextInfo);
                if (activityOfferingInfos.size() == 0) {
                    if ( ! hasAOWarning) hasAOWarning = true;
                    continue;
                }
                // Iterate through the AOs and state change Draft -> Approved.
                for (ActivityOfferingInfo activityOfferingInfo : activityOfferingInfos) {
                    boolean isAOStateDraft = StringUtils.equals(activityOfferingInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                    if (isAOStateDraft) {
                        //  Flag if any AOs can be state changed. This affects the error message whi.
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                        activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
                        getCourseOfferingService().updateActivityOffering(activityOfferingInfo.getId(), activityOfferingInfo,contextInfo);
                    } else {
                        //  Flag if any AOs are not in a valid state for approval.
                        if ( ! hasAOWarning) hasAOWarning = true;
                    }
                }
                // check for changes to states in CO and related FOs
                ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(coWrapper.getCoInfo(), contextInfo);
            }
        }
        //  Set feedback messages.
        if ( ! hasStateChangedAO) {
            GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_NONE_APPROVED);
        } else {
            if (hasAOWarning) {
                GlobalVariables.getMessageMap().putWarning("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_WITH_AO_DRAFT_APPROVED_ONLY);
            }
        }
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private AcademicCalendarService _getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
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
}
