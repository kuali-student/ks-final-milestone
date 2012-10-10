package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
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
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.common.class1.type.dto.TypeInfo;
import org.kuali.student.r2.common.class1.type.service.TypeService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.*;


public class CourseOfferingManagementViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseOfferingManagementViewHelperService{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;

    private CourseService courseService;
    private TypeService typeService;
    private StateService stateService;
    private LRCService lrcService;
    private SchedulingService schedulingService;
    private RoomService roomService;


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
        List<String> courseOfferingIds = _getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectCode, getContextInfo());

        if(courseOfferingIds.size()>0){
            List<CourseOfferingInfo>   courseOfferingList = _getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds,getContextInfo());
            form.getCourseOfferingEditWrapperList().clear();
            for(CourseOfferingInfo coInfo: courseOfferingList){
                CourseOfferingEditWrapper courseOfferingEditWrapper = new CourseOfferingEditWrapper(coInfo);
                courseOfferingEditWrapper.setGradingOption(getGradingOption(coInfo.getGradingOptionId()));
                StateInfo state = getStateService().getState(coInfo.getStateKey(),getContextInfo());
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
        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = _getCourseOfferingService().searchForCourseOfferingIds(criteria, getContextInfo());

        if(courseOfferingIds.size() > 0){
            List<CourseOfferingInfo> courseOfferingList = _getCourseOfferingService().getCourseOfferingsByIds(courseOfferingIds,getContextInfo());
            form.getCourseOfferingEditWrapperList().clear();
            for(CourseOfferingInfo coInfo: courseOfferingList){
                CourseOfferingEditWrapper courseOfferingEditWrapper = new CourseOfferingEditWrapper(coInfo);
                courseOfferingEditWrapper.setGradingOption(getGradingOption(coInfo.getGradingOptionId()));
                StateInfo state = getStateService().getState(coInfo.getStateKey(),getContextInfo());
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
              ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, getContextInfo());
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
            List<String> coIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(courseOfferingInfo.getTermId(),courseOfferingInfo.getSubjectArea(),getContextInfo());
            List<CourseOfferingInfo> courseOfferingInfos = getCourseOfferingService().getCourseOfferingsByIds(coIds,getContextInfo());

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

        // Get the format object for the id selected
        try {
            course = getCourseService().getCourse(courseOffering.getCourseId(), getContextInfo());
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
            List<FormatOfferingInfo> courseOfferingFOs = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOffering.getId(), getContextInfo());
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
        List<ActivityInfo> activities = format.getActivities();
        for (ActivityInfo info : activities) {
            if (StringUtils.equals(activityId, info.getId())) {
                activity = info;
            }
        }

        // Get the matching activity offering type for the selected activity
        TypeInfo activityOfferingType;
        try {
            List<TypeInfo> types = getTypeService().getAllowedTypesForType(activity.getTypeKey(), getContextInfo());
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
                ActivityOfferingInfo activityOfferingInfo = _getCourseOfferingService().createActivityOffering(formatOfferingInfo.getId(), activityId, activityOfferingType.getKey(), aoInfo, getContextInfo());
                ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOfferingInfo);
                StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), getContextInfo());
                wrapper.setStateName(state.getName());
                TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), getContextInfo());
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
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, getContextInfo());
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
        StateInfo draftState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, getContextInfo());
        StateInfo approvedState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, getContextInfo());

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
                        ActivityOfferingInfo updatedAO = getCourseOfferingService().updateActivityOffering(wrapper.getAoInfo().getId(),wrapper.getAoInfo(),getContextInfo());
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
                        ActivityOfferingInfo updatedAO = getCourseOfferingService().updateActivityOffering(wrapper.getAoInfo().getId(),wrapper.getAoInfo(),getContextInfo());
                        wrapper.setAoInfo(updatedAO);
                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
                    } else {
                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
                    }
                }
            }
        }
        //  Check for changes to states in the related COs and FOs
        ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(courseOfferingInfo, getContextInfo());

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
        for (CourseOfferingEditWrapper coWrapper : coWrappers) {
            if (coWrapper.getIsChecked() || ! checkedOnly) {
                List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coWrapper.getCoInfo().getId(),getContextInfo());
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
                        getCourseOfferingService().updateActivityOffering(activityOfferingInfo.getId(), activityOfferingInfo,getContextInfo());
                    } else {
                        //  Flag if any AOs are not in a valid state for approval.
                        if ( ! hasAOWarning) hasAOWarning = true;
                    }
                }
                // check for changes to states in CO and related FOs
                ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(coWrapper.getCoInfo(), getContextInfo());
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

    //TODO: do we still need this method? need code review, refactor, and clean up
    /*
     * For Manage Registration Group page
     */
    public void validateRegistrationGroupsForFormatOffering (List<RegistrationGroupInfo> rgInfos,
                                                             String formatOfferingId,
                                                             CourseOfferingManagementForm form) throws Exception {
        List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByFormatOffering(
                formatOfferingId, getContextInfo());

        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap =
                constructActivityOfferingTypeToAvailableActivityOfferingMap(aoList);

        List<List<String>> generatedPermutations = new ArrayList<List<String>>();
        List<List<String>> foundList = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(new ArrayList<String>(
                activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
                new ArrayList<String>(),
                activityOfferingTypeToAvailableActivityOfferingMap,
                generatedPermutations);

        
        for (List<String> activityOfferingPermutation : generatedPermutations) {
              for (RegistrationGroupInfo rgInfo : rgInfos){
                  if (hasGeneratedRegGroup(activityOfferingPermutation,rgInfo)){
                      rgInfos.remove(rgInfo);
                      foundList.add(activityOfferingPermutation);
                      break;
                  }
              }
        }
        if (generatedPermutations.size() != foundList.size() )  {
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerFormatSection", CourseOfferingConstants.REGISTRATIONGROUP_MISSING_REGGROUPS);
        }
        if (!rgInfos.isEmpty()){
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerFormatSection", CourseOfferingConstants.REGISTRATIONGROUP_INVALID_REGGROUPS);
        }
    }
    
    public ActivityOfferingWrapper convertAOInfoToWrapper(ActivityOfferingInfo aoInfo) throws Exception{

        ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(aoInfo);

        StateInfo state = getStateService().getState(aoInfo.getStateKey(), getContextInfo());
        aoWrapper.setStateName(state.getName());

        TypeInfo typeInfo = getTypeService().getType(aoInfo.getTypeKey(), getContextInfo());
        aoWrapper.setTypeName(typeInfo.getName());

        FormatOfferingInfo fo = getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), getContextInfo());
        aoWrapper.setFormatOffering(fo);

        OfferingInstructorInfo displayInstructor = ViewHelperUtil.findDisplayInstructor(aoInfo.getInstructors());

        if(displayInstructor != null) {
            aoWrapper.setFirstInstructorDisplayName(displayInstructor.getPersonName());
        }

        //This section is to display either schedule request or actuals. If actuals available, display that instead of request
        if (StringUtils.isNotBlank(aoInfo.getScheduleId())){
            //FIXME: Use display object once we get the TBA with ScheduleComponentDisplay
            /*ScheduleDisplayInfo displayInfo = getSchedulingService().getScheduleDisplay(aoInfo.getScheduleId(),getContextInfo());
            if (!displayInfo.getScheduleComponentDisplays().isEmpty()){
                ScheduleComponentDisplay componentDisplay = displayInfo.getScheduleComponentDisplays().get(0);
                updateScheduleToAOWrapperForDisplay(aoWrapper,Boolean.FALSE,componentDisplay.getRoom(),componentDisplay.getTimeSlots().get(0));

            }*/

            ScheduleInfo scheduleInfo = getSchedulingService().getSchedule(aoInfo.getScheduleId(),getContextInfo());

            if (!scheduleInfo.getScheduleComponents().isEmpty()){

                boolean appendScheduleRowDisplay = false;

                for (ScheduleComponentInfo scheduleComponentInfo : scheduleInfo.getScheduleComponents()) {

                    String roomId = scheduleComponentInfo.getRoomId();
                    TimeSlotInfo timeSlotInfo =  getSchedulingService().getTimeSlot(scheduleComponentInfo.getTimeSlotIds().get(0),getContextInfo());

                    updateScheduleToAOWrapperForDisplay(aoWrapper,scheduleComponentInfo.getIsTBA(),roomId,timeSlotInfo,appendScheduleRowDisplay);

                    if (!appendScheduleRowDisplay){
                        appendScheduleRowDisplay = true;
                    }
                }

            }

        }else{

            List<ScheduleRequestInfo> scheduleRequestInfoList = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoInfo.getId(), getContextInfo());

            if (!scheduleRequestInfoList.isEmpty()){

                boolean appendScheduleRowDisplay = false;

                for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfoList.get(0).getScheduleRequestComponents()) {
                    String roomId = componentInfo.getRoomIds().isEmpty() ? StringUtils.EMPTY : componentInfo.getRoomIds().get(0);
                    TimeSlotInfo timeSlotInfo =  getSchedulingService().getTimeSlot(componentInfo.getTimeSlotIds().get(0),getContextInfo());

                    updateScheduleToAOWrapperForDisplay(aoWrapper,componentInfo.getIsTBA(),roomId,timeSlotInfo,appendScheduleRowDisplay);

                    if (!appendScheduleRowDisplay){
                        appendScheduleRowDisplay = true;
                    }
                }
            }

        }

        return aoWrapper;
    }

    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingWrapper aoWrapper, Boolean isTBA,String roomId,TimeSlotInfo timeSlot,boolean append) throws Exception{
         RoomInfo roomInfo = null;
         if (StringUtils.isNotBlank(roomId)){
            roomInfo = getRoomService().getRoom(roomId, getContextInfo());
         }
         updateScheduleToAOWrapperForDisplay(aoWrapper,isTBA,roomInfo,timeSlot,append);
    }

    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingWrapper aoWrapper, Boolean isTBA, RoomInfo roomInfo,TimeSlotInfo timeSlot,boolean append) throws Exception{

        Calendar calendar = new  GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

        aoWrapper.setTbaDisplayName(isTBA,append);

        if (timeSlot != null) {

            TimeOfDayInfo startTime = timeSlot.getStartTime();
            TimeOfDayInfo endTime = timeSlot.getEndTime();
            List<Integer> days = timeSlot.getWeekdays();

            if (startTime != null && startTime.getMilliSeconds() != null) {
                calendar.setTimeInMillis(startTime.getMilliSeconds());
                aoWrapper.setStartTimeDisplay(format.format(calendar.getTime()),append);
            }

            if (endTime != null && endTime.getMilliSeconds() != null) {
                calendar.setTimeInMillis(endTime.getMilliSeconds());
                aoWrapper.setEndTimeDisplay(format.format(calendar.getTime()),append);
            }

            if (days != null && days.size() > 0) {
                aoWrapper.setDaysDisplayName(getDays(days),append);
            }
        }

        if (roomInfo != null && StringUtils.isNotBlank(roomInfo.getBuildingId())) {
            BuildingInfo buildingInfo = getRoomService().getBuilding(roomInfo.getBuildingId(), getContextInfo());
            aoWrapper.setBuildingName(buildingInfo.getName(),append);
            aoWrapper.setRoomName(roomInfo.getRoomCode(),append);
        }
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
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

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    public SchedulingService getSchedulingService() {
         if(schedulingService == null)  {
             schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
         }
         return schedulingService;
    }

    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

    private Map<String, List<String>> constructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

        for (ActivityOfferingInfo info : aoList) {
            String activityType = info.getTypeKey();
            List<String> activityList = activityOfferingTypeToAvailableActivityOfferingMap
                    .get(activityType);

            if (activityList == null) {
                activityList = new ArrayList<String>();
                activityOfferingTypeToAvailableActivityOfferingMap.put(
                        activityType, activityList);
            }

            activityList.add(info.getId());

        }
        return activityOfferingTypeToAvailableActivityOfferingMap;
    }
    
    private boolean hasGeneratedRegGroup(List<String>activityOfferingPermutation, RegistrationGroupInfo rgInfo){
        boolean isMatched = true;
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<String> foundList = new ArrayList<String>();
        for (String activityOfferingPermutationItem : activityOfferingPermutation){
            for (String aoId: aoIds){
                if (activityOfferingPermutationItem.equals(aoId)){
                    aoIds.remove(aoId);
                    foundList.add(activityOfferingPermutationItem);
                    break;
                }
            }
        }
        if (activityOfferingPermutation.size() != foundList.size() ||!aoIds.isEmpty()  )  {
            isMatched = false;
        }
        return isMatched;        
    }


    private String convertIntoDays(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null) return sb.toString();

        for(Integer d : intList) {
            sb.append(convertIntoDays(d));
        }
        return sb.toString();
    }
}
