package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.autogen.controller.ARGUtil;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleComponentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.helper.ActivityOfferingScheduleHelperImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.service.SeatPoolUtilityService;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingMaintainableImpl extends KSMaintainableImpl implements ActivityOfferingMaintainable {

    private static final long serialVersionUID = 1L;
    private transient CourseOfferingService courseOfferingService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient AcademicCalendarService academicCalendarService;
    private transient SchedulingService schedulingService;
    private transient RoomService roomService;
    private transient PopulationService populationService;
    private transient SeatPoolUtilityService seatPoolUtilityService = new SeatPoolUtilityServiceImpl();
    private transient CourseService courseService;
    private transient SearchService searchService;
    private transient LuiService luiService;

    private static final String SCHEDULE_HELPER = "scheduleHelper";

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            disassembleInstructorsWrapper(activityOfferingWrapper.getInstructors(), activityOfferingWrapper.getAoInfo());
            List<SeatPoolDefinitionInfo> seatPools = this.getSeatPoolDefinitions(activityOfferingWrapper.getSeatpools());
            seatPoolUtilityService.updateSeatPoolDefinitionList(seatPools, activityOfferingWrapper.getAoInfo().getId(), contextInfo);

            saveColocatedAOs(activityOfferingWrapper);

            /**
             * Detach the AO from colo schedule if it's not a part of colo anymore
             */
            if (activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && !activityOfferingWrapper.isColocatedAO()){
//   TODOSSR             activityOfferingWrapper.getAoInfo().setScheduleId(null);
//                activityOfferingWrapper.getAoInfo().setIsPartOfColocatedOfferingSet(false);
            }

            ActivityOfferingInfo activityOfferingInfo = null;

            /**
             * Save the AO first before processing schedule. (It's important to save first as scheduleAcivityOffering() service method
             * just accepts the ao id as param and fetches the DTO from the DB.)
             */
            try {
                activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), contextInfo);
                activityOfferingWrapper.setAoInfo(activityOfferingInfo);
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }

            if(activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && !activityOfferingWrapper.isColocatedAO()){
                //if change co-located to un-coloated, no schedules
            } else {
                /**
                 * Even if the user doesnt change any RDL data, here are the scenarios whether we need to process schedules
                 * 1. If the user checks/unchecks the colo checkbox even the user has not changed anything in the schedules
                 * 2. If the user opens an activity after scheduling and without changing any schedule details, submits the doc.
                 * Once user submits a doc after scheduling complemented, all the RDLs should be converted to ADLs
                 */
                if (activityOfferingWrapper.isSchedulesModified() ||
                        (!activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && activityOfferingWrapper.isColocatedAO()) ||
                        (activityOfferingWrapper.isSchedulingCompleted() && !activityOfferingWrapper.getRequestedScheduleComponents().isEmpty())){
                    getScheduleHelper().saveSchedules(activityOfferingWrapper,contextInfo);
                }
            }

            /**
             * Now that the Ao & the schedule has been updated, we need to update the registration groups
             */
            try {
                ARGUtil.getArgServiceAdapter().updateRegistrationGroups(activityOfferingInfo, contextInfo);
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }


            //All the details on the current AO saved successfully.. Now, update the max enrollment on other AOs in the coloset
            try {
                for(ColocatedActivity activity : activityOfferingWrapper.getColocatedActivities()){
                    //If an activity is newly added in this session for colo, delete it's RDLs and ADLs if exists

                    activity.getActivityOfferingInfo().setIsColocated(activityOfferingWrapper.isColocatedAO());
                    if (activityOfferingWrapper.isColocatedAO() && activityOfferingWrapper.isMaxEnrollmentShared()){
                        activity.getActivityOfferingInfo().setMaximumEnrollment(activityOfferingWrapper.getSharedMaxEnrollment());
                    }

                    boolean deleteSchedule = false;
                    if (activityOfferingWrapper.isColocatedAO() && !activity.isAlreadyPersisted()){
                        // SSRTODO: ScheduleId is now a collection
                        //activity.getActivityOfferingInfo().setScheduleId("");
                    }

                    ActivityOfferingInfo updatedAO = getCourseOfferingService().updateActivityOffering(activity.getAoId(), activity.getActivityOfferingInfo(), createContextInfo());
                    activity.setActivityOfferingInfo(updatedAO);

                    if (deleteSchedule){
                        getScheduleHelper().deleteRequestedAndActualSchedules(activity.getActivityOfferingInfo());
                    }
                }
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }
        }
    }

    /**
     *
     * This method handles the logic around the colo saving.
     *
     * <p>If the AO is marked for colocation, following should be done
     *      1.  If max enrollment is shared, update all the AOs in the colo set with the user entered seat details
     *          We save the AOs later once all the save operation on the current AO is done successfully
     *      2.  If max enrollment is maintained individually, just update the enrollment information on
     *          the current AO only.
     *      3. Create/Update Colo Set
     *    If the AO is not marked for colocation and the colo set already exists, just delete the current AO
     *    from the colo set
     * </p>
     *
     * @param wrapper
     */
    protected void saveColocatedAOs(ActivityOfferingWrapper wrapper){

        /*ColocatedOfferingSetInfo coloSet = wrapper.getColocatedOfferingSetInfo();
        *//**
         * Set the effective date and expiration date if it's not already there
         *//*
        if (coloSet.getEffectiveDate() == null){
            coloSet.setEffectiveDate(new Date());
            coloSet.setExpirationDate(DateUtils.addYears(new Date(),1));
        }*/

        if (wrapper.getScheduleRequestSetInfo() == null){
            ScheduleRequestSetInfo set = new ScheduleRequestSetInfo();
            wrapper.setScheduleRequestSetInfo(set);
            set.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
            set.setName("Schedule request set for " + wrapper.getAoInfo().getCourseOfferingCode() + " - " + wrapper.getAoInfo().getActivityCode());
            set.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
            set.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        }

        if (wrapper.isColocatedAO()){
            wrapper.getScheduleRequestSetInfo().getRefObjectIds().clear();

            for (ColocatedActivity activity : wrapper.getColocatedActivities()){
                wrapper.getScheduleRequestSetInfo().getRefObjectIds().add(activity.getAoId());
            }
            wrapper.getScheduleRequestSetInfo().getRefObjectIds().add(wrapper.getAoInfo().getId());

            wrapper.getScheduleRequestSetInfo().setMaxEnrollmentShared(wrapper.isMaxEnrollmentShared());
            if (wrapper.isMaxEnrollmentShared()){
                wrapper.getAoInfo().setMaximumEnrollment(wrapper.getSharedMaxEnrollment());
            } else {
                int totalSeats = 0;
                for (ColocatedActivity activity : wrapper.getEditRenderHelper().getManageSeperateEnrollmentList()){
                    if (activity.getEditRenderHelper().isAllowEnrollmentEdit()){
                        wrapper.getAoInfo().setMaximumEnrollment(activity.getMaxEnrollmentCount());
                    }
                    totalSeats = totalSeats + activity.getMaxEnrollmentCount();
                }
                wrapper.getScheduleRequestSetInfo().setMaximumEnrollment(totalSeats);
            }
            /*try{
                if (StringUtils.isNotBlank(coloSet.getId())){
                    ColocatedOfferingSetInfo updatedColoSet = getCourseOfferingService().updateColocatedOfferingSet(coloSet.getId(),coloSet,createContextInfo());
                    wrapper.setColocatedOfferingSetInfo(updatedColoSet);
                } else {
                    coloSet.setTypeKey(LuiServiceConstants.LUI_SET_COLOCATED_OFFERING_TYPE_KEY);
                    coloSet.setStateKey(LuiServiceConstants.LUI_SET_ACTIVE_STATE_KEY);
                    coloSet.setName("Colo set");
                    ColocatedOfferingSetInfo createdColoSet = getCourseOfferingService().createColocatedOfferingSet(coloSet.getTypeKey(), coloSet, createContextInfo());
                    wrapper.setColocatedOfferingSetInfo(createdColoSet);
                }
            } catch (Exception e){
                throw convertServiceExceptionsToUI(e);
            }*/
        } else {
            //detach AO from colocation
            /*if(wrapper.isPartOfColoSetOnLoadAlready()){
                detachAOFromColocatedSet(wrapper.getAoInfo().getId(), wrapper.getColocatedOfferingSetInfo());
            }*/
        }
    }

    @Override
    public boolean addScheduleRequestComponent(ActivityOfferingWrapper activityOfferingWrapper) {
        return getScheduleHelper().addScheduleRequestComponent(activityOfferingWrapper);
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(info.getCourseOfferingId(), contextInfo);

            // get the format offering
            FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(info.getFormatOfferingId(), contextInfo);
            wrapper.setFormatOffering(formatOfferingInfo);

            // Added for WaitList Tanveer 06/27/2012
            wrapper.setWaitListLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            wrapper.setWaitListTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            wrapper.setHasWaitList(courseOfferingInfo.getHasWaitlist());
            if (!wrapper.getHasWaitList())
                wrapper.setWaitListText("There is no wait list for this offering.");
            if (wrapper.getWaitListLevelTypeKey().equals("Course Offering")) {
                wrapper.setWaitListText("This waitlist is managed at the Course Offering level.");
                wrapper.setToolTipText("There is one waitlist for all Activity Offerings");
            }
            if (wrapper.getWaitListLevelTypeKey().equals("Activity Offering")) {
                wrapper.setWaitListText("This waitlist is managed at the Activity Offering level.");
                wrapper.setToolTipText("Each Activity Offering has its own wait list.");
            }

            // Set the display string (e.g. 'FALL 2020 (9/26/2020 to 12/26/2020)')
            TermInfo term = getAcademicCalendarService().getTerm(info.getTermId(), contextInfo);
            wrapper.setTerm(term);
            if (term != null) {
                wrapper.setTermName(term.getName());
            }
            wrapper.setTermDisplayString(getTermDisplayString(info.getTermId(), term));

            List<TypeInfo> regPeriods = getTypeService().getTypesForGroupType("kuali.milestone.type.group.appt.regperiods", contextInfo);
            List<KeyDateInfo> keyDateInfoList = getAcademicCalendarService().getKeyDatesForTerm(info.getTermId(), contextInfo);
            Date termRegStartDate = null;
            for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                for (TypeInfo regPeriod : regPeriods) {
                    if (keyDateInfo.getTypeKey().equalsIgnoreCase(regPeriod.getKey()) && keyDateInfo.getStartDate() != null) {
                        if (termRegStartDate == null || keyDateInfo.getStartDate().before(termRegStartDate)) {
                            termRegStartDate = keyDateInfo.getStartDate();
                        }
                    }
                }
            }
            wrapper.setTermRegStartDate(termRegStartDate);

            wrapper.setCourseOfferingCode(info.getCourseOfferingCode());
            wrapper.setCourseOfferingTitle(info.getCourseOfferingTitle());

            String sCredits = courseOfferingInfo.getCreditCnt();
            if (sCredits == null) {
                sCredits = "0";
            }
            wrapper.setCredits(sCredits);
            //wrapper.setAbbreviatedActivityCode(info.getActivityCode().toUpperCase().substring(0,3));
            wrapper.setActivityCode(info.getActivityCode());
            wrapper.setAbbreviatedCourseType(getTypeService().getType(info.getTypeKey(), contextInfo).getName().toUpperCase().substring(0, 3));

            //process instructor effort
            assembleInstructorWrapper(info.getInstructors(), wrapper);


            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);

            // allows multiple orgs
            List<String> orgIds = courseOfferingInfo.getUnitsDeploymentOrgIds();
            if(orgIds != null && !orgIds.isEmpty()){
                String orgIDs = "";
                for (String orgId : orgIds) {
                    orgIDs = orgIDs + orgId + ",";
                }
                if (orgIDs.length() > 0) {
                    wrapper.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
                }
            }

            //Set socInfo
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(info.getTermId(), ContextUtils.createDefaultContextInfo());
            if (socIds != null && !socIds.isEmpty()) {
                List<SocInfo> targetSocs = getCourseOfferingSetService().getSocsByIds(socIds, ContextUtils.createDefaultContextInfo());
                for (SocInfo soc: targetSocs) {
                    if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                        wrapper.setSocInfo(soc);
                    }
                }
            }

            //retrieve all the populations for seat pool section client side validation
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(
                    PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY));
            QueryByCriteria criteria = qbcBuilder.build();

            try {
                List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(criteria, createContextInfo());
                if(populationInfoList != null || !populationInfoList.isEmpty()){
                    String populationJSONString = "{\"" + CourseOfferingConstants.POPULATIONS_JSON_ROOT_KEY + "\": {";

                    int index = 0;
                    for (PopulationInfo populationInfo : populationInfoList) {
                        if (index == 0) {
                            populationJSONString = "{\"" + CourseOfferingConstants.POPULATIONS_JSON_ROOT_KEY + "\": {\"" + populationInfo.getId() + "\": \"" + populationInfo.getName() + "\"";
                        } else {
                            populationJSONString += ",\"" + populationInfo.getId() + "\": \"" + populationInfo.getName() + "\"";
                        }
//                        if (index > 0) {
//                            break;
//                        }
                        index++;
                    }
                    populationJSONString += "}}";

                    //populationJSONString = "{\"populations\": {\"049285e2-e309-48a0-87d6-27e3764f4200\": \"Athletic Managers & Trainers\", \"049285e2-e309-48a0-87d6-27e3764f4200\": \"Young Scholars\"}}";

                    wrapper.setPopulationsJSONString(populationJSONString);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            document.getNewMaintainableObject().setDataObject(wrapper);
            document.getOldMaintainableObject().setDataObject(wrapper);
            document.getDocumentHeader().setDocumentDescription("Edit AO - " + info.getActivityCode());
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
            wrapper.setStateName(state.getName());
            TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), contextInfo);
            wrapper.setTypeName(typeInfo.getName());

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(info.getId(), contextInfo);

            //Sort the seatpools by priority order
            Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
                @Override
                public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                    return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
                }
            });

            List<SeatPoolWrapper> seatPoolWrapperList = new ArrayList<SeatPoolWrapper>();

            for (SeatPoolDefinitionInfo seatPoolDefinitionInfo : seatPoolDefinitionInfoList) {
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();

                PopulationInfo pInfo = getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            //try to add an empty line
            if(seatPoolWrapperList.size()==0){
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();
                SeatPoolDefinitionInfo seatPool = new SeatPoolDefinitionInfo();
                seatPool.setProcessingPriority(1);
                spWrapper.setSeatPool(seatPool);
                PopulationInfo seatPoolPopulation = new PopulationInfo();
                spWrapper.setSeatPoolPopulation(seatPoolPopulation);
                seatPoolWrapperList.add(spWrapper);
            }
            wrapper.setSeatpools(seatPoolWrapperList);

            Person user = GlobalVariables.getUserSession().getPerson();

            boolean canOpenView = this.getDocumentDictionaryService().getDocumentAuthorizer(document).canOpen(document,user);

            // Work around, should be fixed with KULRICE-8049
            if (!canOpenView) {
                throw new AuthorizationException(user.getPrincipalName(), "open", null,
                        "User '" + user.getPrincipalName() + "' is not authorized to open view", null);
            }


            //get Course details
            CourseInfo courseInfo = getCourseService().getCourse(courseOfferingInfo.getCourseId(),contextInfo);
            wrapper.setCourse(courseInfo);

            loadColocatedAOs(wrapper);

            getScheduleHelper().loadSchedules(wrapper,contextInfo);

            return wrapper;

        }catch (AuthorizationException ae){
            throw new AuthorizationException(ae.getUserId(), "open", null,
                    "User '" + ae.getUserId() + "' is not authorized to open view", null);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadColocatedAOs(ActivityOfferingWrapper wrapper) throws Exception {

        ActivityOfferingInfo info = wrapper.getAoInfo();
        wrapper.setColocatedOnLoadAlready(info.getIsColocated());

        if (info.getIsColocated()){

            wrapper.setColocatedAO(true);

            List<ScheduleRequestSetInfo> scheduleRequestSets = getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, info.getId(), createContextInfo());
            if(scheduleRequestSets != null && !scheduleRequestSets.isEmpty()){
                ScheduleRequestSetInfo scheduleRequestSetInfo = scheduleRequestSets.get(0);

                //Each AO has only one ScheduleRequestSet for M7-SP2(support full co-location only)
                wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
                wrapper.setMaxEnrollmentShared(scheduleRequestSetInfo.getIsMaxEnrollmentShared());
                if(scheduleRequestSetInfo.getIsMaxEnrollmentShared()) {
                    wrapper.setSharedMaxEnrollment(scheduleRequestSetInfo.getMaximumEnrollment());
                }

                List<String> activityOfferingIds = new ArrayList<String>(scheduleRequestSetInfo.getRefObjectIds());
                activityOfferingIds.remove(info.getId());
                List<ActivityOfferingInfo> aoInfos = getCourseOfferingService().getActivityOfferingsByIds(activityOfferingIds,createContextInfo());

                for (ActivityOfferingInfo dto : aoInfos){
                    ColocatedActivity coloAO = new ColocatedActivity();
                    coloAO.setAoId(dto.getId());
                    if (dto.getMaximumEnrollment() != null){
                        coloAO.setMaxEnrollmentCount(dto.getMaximumEnrollment());
                    }
                    coloAO.setCoId(dto.getCourseOfferingId());
                    coloAO.setActivityOfferingCode(dto.getActivityCode());
                    coloAO.setCourseOfferingCode(dto.getCourseOfferingCode());
                    coloAO.setAlreadyPersisted(true);
                    coloAO.setActivityOfferingInfo(dto);
                    wrapper.getColocatedActivities().add(coloAO);
                    wrapper.getNewScheduleRequest().getColocatedAOs().add(coloAO.getEditRenderHelper().getCode());
                }

                wrapper.getEditRenderHelper().getManageSeperateEnrollmentList().addAll(wrapper.getColocatedActivities());
            }

        }

        ColocatedActivity a = new ColocatedActivity();
        a.setActivityOfferingCode(wrapper.getActivityCode());
        a.setCourseOfferingCode(wrapper.getCourseOfferingCode());

        if (wrapper.getAoInfo().getMaximumEnrollment() != null){
            a.setMaxEnrollmentCount(wrapper.getAoInfo().getMaximumEnrollment());
        }
        a.getEditRenderHelper().setAllowEnrollmentEdit(true);

        wrapper.getEditRenderHelper().getManageSeperateEnrollmentList().add(a);

    }

    @Override
    public void applyDefaultValuesForCollectionLine(View view, Object model, CollectionGroup collectionGroup,
                                                    Object line) {

        super.applyDefaultValuesForCollectionLine(view,model,collectionGroup,line);

        if (line instanceof ColocatedActivity){
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            ColocatedActivity colo = (ColocatedActivity)line;
            colo.getEditRenderHelper().setTermId(activityOfferingWrapper.getTerm().getId());
        }

    }

    /**
     *
     * unwrap seatPoolWrapper. If the seatPoolWrapper is null or contains no seatPools, return null
     *
     * @param seatPoolWrappers list of SeatPoolWrappers to unwrap
     * @return list of SeatPoolDefinitionInfo objects derived from the wrappers
     */
    private List<SeatPoolDefinitionInfo> getSeatPoolDefinitions(List<SeatPoolWrapper> seatPoolWrappers) {

        List<SeatPoolDefinitionInfo> spRet = new ArrayList<SeatPoolDefinitionInfo>();

        if (seatPoolWrappers != null) {
            for (SeatPoolWrapper seatPoolWrapper : seatPoolWrappers) {
                SeatPoolDefinitionInfo seatPool = seatPoolWrapper.getSeatPool();
                seatPool.setPopulationId(seatPoolWrapper.getSeatPoolPopulation().getId());
                //do not add empty SeatPool item(s) to the list
                if (seatPool.getSeatLimit()!=null && seatPool.getPopulationId()!=null){
                    spRet.add(seatPool);
                }

            }
        }

        return spRet;
    }

    private String getTermDisplayString(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            String termType = term.getName();
            formatter.format("%s (%s to %s)", termType, startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    private void assembleInstructorWrapper(List<OfferingInstructorInfo> instructors, ActivityOfferingWrapper wrapper) {
        if (instructors != null && !instructors.isEmpty()) {
            for (OfferingInstructorInfo instructor : instructors) {
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                if (instructor.getPercentageEffort() != null) {
                    instructorWrapper.setsEffort(Integer.toString(instructor.getPercentageEffort().intValue()));
                }
                wrapper.getInstructors().add(instructorWrapper);
            }
        }
    }

    private void disassembleInstructorsWrapper(List<OfferingInstructorWrapper> instructors, ActivityOfferingInfo aoInfo) {
        aoInfo.setInstructors(new ArrayList<OfferingInstructorInfo>());
        if (instructors != null && !instructors.isEmpty()) {
            for (OfferingInstructorWrapper instructor : instructors) {
                aoInfo.getInstructors().add(disassembleInstructorWrapper(instructor));
            }
        }
    }

    private OfferingInstructorInfo disassembleInstructorWrapper(OfferingInstructorWrapper instructor) {
        OfferingInstructorInfo instructorInfo = new OfferingInstructorInfo(instructor.getOfferingInstructorInfo());
        if (!StringUtils.isBlank(instructor.getsEffort())) {
            instructorInfo.setPercentageEffort(new Float(instructor.getsEffort()));
        }


        if (StringUtils.isBlank(instructorInfo.getStateKey())) {
            try {
                StateInfo state = getStateService().getState(LprServiceConstants.TENTATIVE_STATE_KEY, ContextUtils.createDefaultContextInfo());
                instructorInfo.setStateKey(state.getKey());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instructorInfo;
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper) document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), ContextUtils.createDefaultContextInfo());
            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void processAfterDeleteLine(View view, CollectionGroup collectionGroup, Object model, int lineIndex) {
        if (!collectionGroup.getPropertyName().equals("seatpools")) {
            super.processAfterDeleteLine(view, collectionGroup, model, lineIndex);
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine, boolean isValidLine) {
        super.processAfterAddLine(view, collectionGroup, model, addLine, true);

        if (addLine instanceof ScheduleComponentWrapper) {
            ScheduleComponentWrapper scheduleComponentWrapper = (ScheduleComponentWrapper) addLine;
            if ("1".equals(scheduleComponentWrapper.getAddDaysSpecifiedBoolean())) {
                if (null != scheduleComponentWrapper.getAddWeekDayOptions()) {
                    List<String> weekDayLabels = Arrays.asList("Su ", "M ", "T ", "W ", "Th ", "F ", "Sa ");
                    StringBuilder weekDays = new StringBuilder();
                    for (Integer day : scheduleComponentWrapper.getAddWeekDayOptions()) {
                        weekDays.append(weekDayLabels.get(day));
                    }
                    scheduleComponentWrapper.setWeekDays(weekDays.toString());
                }
            } else {
                scheduleComponentWrapper.setWeekDays("To Be Announced");
            }
            if (null != scheduleComponentWrapper.getAddRoomResources()) {
                StringBuilder resources = new StringBuilder();
                for (String resource : scheduleComponentWrapper.getAddRoomResources()) {
                    if (resources.length() > 0) {
                        resources.append(", ");
                    }
                    resources.append(resource);
                }
                scheduleComponentWrapper.setRoomFeatures(resources.toString());
            }
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            activityOfferingWrapper.setSchedulesModified(true);
        } else if (addLine instanceof OfferingInstructorWrapper) {
            // set the person name if it's null, in the case of user-input personell id
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;
            if (instructor.getOfferingInstructorInfo().getPersonName() == null && instructor.getOfferingInstructorInfo().getPersonId() != null) {
                List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructor.getOfferingInstructorInfo().getPersonId());
                if (personList.size() == 1) {
                    instructor.getOfferingInstructorInfo().setPersonName(personList.get(0).getName());
                }
            }
        } else if (addLine instanceof ColocatedActivity && isValidLine) {
            ColocatedActivity colo = (ColocatedActivity)addLine;
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            activityOfferingWrapper.getEditRenderHelper().getManageSeperateEnrollmentList().add(colo);
            activityOfferingWrapper.getNewScheduleRequest().getColocatedAOs().add(colo.getEditRenderHelper().getCode());
        }
    }

    @Override
    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {

        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (addLine instanceof OfferingInstructorWrapper) {   //Personnel
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;

            //check duplication
            List<OfferingInstructorWrapper> instructors = activityOfferingWrapper.getInstructors();
            if (instructors != null && !instructors.isEmpty()) {
                for (OfferingInstructorWrapper thisInst : instructors) {
                    if (instructor.getOfferingInstructorInfo().getPersonId().equals(thisInst.getOfferingInstructorInfo().getPersonId())) {
                        GlobalVariables.getMessageMap().putErrorForSectionId("ao-personnelgroup", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_DUPLICATE, instructor.getOfferingInstructorInfo().getPersonId());
                        return false;
                    }
                }
            }

            //validate ID
            List<Person> lstPerson = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructor.getOfferingInstructorInfo().getPersonId());
            if (lstPerson == null || lstPerson.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId("ao-personnelgroup", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_NOTFOUND, instructor.getOfferingInstructorInfo().getPersonId());
                return false;
            }
        } else if (addLine instanceof SeatPoolWrapper) {   //Seat Pool
            SeatPoolWrapper seatPool = (SeatPoolWrapper) addLine;

            //check if a valid population is entered
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY),
                    PredicateFactory.equalIgnoreCase("name", seatPool.getSeatPoolPopulation().getName())));
            QueryByCriteria criteria = qbcBuilder.build();

            try {
                List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(criteria, createContextInfo());
                if(populationInfoList == null || populationInfoList.isEmpty()){
                    GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", PopulationConstants.POPULATION_MSG_ERROR_POPULATION_NOT_FOUND, seatPool.getSeatPoolPopulation().getName());
                    return false;
                } else {
                    seatPool.getSeatPoolPopulation().setName(populationInfoList.get(0).getName());
                    seatPool.getSeatPoolPopulation().setId(populationInfoList.get(0).getId());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //check duplication
            List<SeatPoolWrapper> pools = activityOfferingWrapper.getSeatpools();
            if (pools != null && !pools.isEmpty()) {
                for (SeatPoolWrapper pool : pools) {
                    if (seatPool.getSeatPoolPopulation().getId().equals(pool.getSeatPoolPopulation().getId())) {
                        GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", ActivityOfferingConstants.MSG_ERROR_SEATPOOL_DUPLICATE, pool.getSeatPoolPopulation().getName());
                        return false;
                    }
                }
            }
        } else if (addLine instanceof ColocatedActivity){
            ColocatedActivity colo = (ColocatedActivity)addLine;

            return validateNewColocatedActivity(colo,activityOfferingWrapper);
        }
        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    protected boolean validateNewColocatedActivity(ColocatedActivity colo,ActivityOfferingWrapper activityOfferingWrapper){

        String groupId = "ActivityOfferingEdit-CoLocatedActivities";

        for (ColocatedActivity activity : activityOfferingWrapper.getColocatedActivities()){
            if (StringUtils.equals(activity.getCourseOfferingCode(),colo.getCourseOfferingCode()) &&
                    StringUtils.equals(activity.getActivityOfferingCode(),colo.getActivityOfferingCode())){
                GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "Duplicate Entry");
                return false;
            }
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("courseOfferingCode", colo.getCourseOfferingCode()),
                PredicateFactory.equalIgnoreCase("atpId", activityOfferingWrapper.getTermId())));
        QueryByCriteria criteria = qbcBuilder.build();

        //Do search. In ideal case, returns one element, which is the desired CO.
        try {
            List<CourseOfferingInfo> courseOfferings = getCourseOfferingService().searchForCourseOfferings(criteria, createContextInfo());
            if (courseOfferings.isEmpty()){
                GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "Invalid Course Offering code");
                return false;
            } else if (courseOfferings.size() > 1){
                GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "More than one course offering exists for the code " + colo.getCourseOfferingCode());
                return false;
            } else {
                colo.setCoId(courseOfferings.get(0).getId());
            }

            List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferings.get(0).getId(),createContextInfo());

            if (activityOfferingInfos.isEmpty()){
                GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "Activity Offerings doesnt exists for " + colo.getCourseOfferingCode());
                return false;
            }

            boolean isAOMatchFound = false;
            for (ActivityOfferingInfo ao : activityOfferingInfos){
                if (StringUtils.equalsIgnoreCase(ao.getActivityCode(),colo.getActivityOfferingCode())){
                    colo.setAoId(ao.getId());
                    if (ao.getMaximumEnrollment() != null){
                        colo.setMaxEnrollmentCount(ao.getMaximumEnrollment());
                    }
                    colo.setActivityOfferingInfo(ao);

                    if (ao.getIsColocated()){
                        GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "This Activity is already part of another colocate set");
                        return false;
                    } else if (StringUtils.equals(ao.getId(),activityOfferingWrapper.getAoInfo().getId())){
                        GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "You are adding the current Activity Offering to the Colocate set.");
                        return false;
                    }
                    isAOMatchFound = true;
                }
            }

            if (!isAOMatchFound){
                GlobalVariables.getMessageMap().putError(groupId, RiceKeyConstants.ERROR_CUSTOM, "Invalid Activity Offering code");
                return false;
            }

            if (isAOMatchFound){
                for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRequestedScheduleComponents()){
                    scheduleWrapper.getColocatedAOs().add(colo.getEditRenderHelper().getCode());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public List<BuildingInfo> retrieveBuildingInfo(String buildingCode){

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.like("buildingCode", StringUtils.upperCase(buildingCode) + "%"));

        QueryByCriteria criteria = qbcBuilder.build();

        try {
            List<BuildingInfo> b = getScheduleHelper().getRoomService().searchForBuildings(criteria,createContextInfo());
            return b;
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
    }

    public List<String> retrieveCourseOfferingCode(String termId,String courseOfferingCode){
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, StringUtils.upperCase(courseOfferingCode));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(false));

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, createContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> courseOfferingCodes = new ArrayList<String>();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for(SearchResultCellInfo cellInfo : row.getCells()){

                if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                    courseOfferingCodes.add(cellInfo.getValue());
                }

            }
        }

        return courseOfferingCodes;
    }

    public void populateColocatedAOs(InputField field, MaintenanceDocumentForm form){

        if (field.isReadOnly()){
            return;
        }

        if (field.getOptionsFinder() == null || ((UifKeyValuesFinderBase)field.getOptionsFinder()).getKeyValues(form).isEmpty()){
            field.getControl().setRender(false);
        } else {
            field.getControl().setRender(true);
        }


    }

    public List<String> retrieveActivityOfferingCode(String termId,String courseOfferingCode, String activityOfferingCode){
        List<String> activityOfferingCodes = new ArrayList<String>();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("courseOfferingCode", StringUtils.upperCase(courseOfferingCode)),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();

        //Do search. In ideal case, returns one element, which is the desired CO.
        List<CourseOfferingInfo> courseOfferings = null;
        try {
            courseOfferings = getCourseOfferingService().searchForCourseOfferings(criteria, createContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!courseOfferings.isEmpty()){
            try {
                List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferings.get(0).getId(),createContextInfo());
                for(ActivityOfferingInfo ao : activityOfferingInfos){
                    if (StringUtils.startsWith(ao.getActivityCode(),StringUtils.upperCase(activityOfferingCode))){
                        activityOfferingCodes.add(ao.getActivityCode());
                    }
                }
                /**
                 * No match found? Display all the AOs
                 */
                if (activityOfferingCodes.isEmpty()){
                    for(ActivityOfferingInfo ao : activityOfferingInfos){
                        activityOfferingCodes.add(ao.getActivityCode());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return activityOfferingCodes;
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorWrapper) {
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;
            instructor.setOfferingInstructorInfo(disassembleInstructorWrapper(instructor));
        }
    }

    @Override
    public void processCollectionDeleteLine(View view, Object model, String collectionPath, int lineIndex) {

        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        if (StringUtils.endsWith(collectionPath, "requestedScheduleComponents")) {
            ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
            wrapper.setSchedulesModified(true);
            ScheduleWrapper scheduleWrapper = wrapper.getRequestedScheduleComponents().remove(lineIndex);
            /*
                If the schedule request already exists in the DB, mark it to delete from DB.
             */
            if (scheduleWrapper.isRequestAlreadySaved()){
                wrapper.getDeletedScheduleComponents().add(scheduleWrapper);
            }
        } else if (StringUtils.endsWith(collectionPath, "colocatedActivities")) {
            ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
            ColocatedActivity deleteCOLO = wrapper.getColocatedActivities().remove(lineIndex);
            wrapper.getEditRenderHelper().getManageSeperateEnrollmentList().remove(deleteCOLO);
            wrapper.getNewScheduleRequest().getColocatedAOs().remove(deleteCOLO.getEditRenderHelper().getCode());
            for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()){
                scheduleWrapper.getColocatedAOs().remove(deleteCOLO.getEditRenderHelper().getCode());
            }
        } else {
            super.processCollectionDeleteLine(view, model, collectionPath, lineIndex);
        }
    }


    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if (stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    private AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }

        return academicCalendarService;
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, PopulationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return populationService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }

    protected ActivityOfferingScheduleHelperImpl getScheduleHelper(){
//        return (ActivityOfferingScheduleHelperImpl)getHelper(SCHEDULE_HELPER);
        return new ActivityOfferingScheduleHelperImpl();
    }

    protected SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    protected LuiService getLuiService() {
        if(luiService == null) {
            luiService = CourseOfferingResourceLoader.loadLuiService();
        }
        return luiService;
    }

    protected SchedulingService getSchedulingService() {
        if(schedulingService == null) {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }
}