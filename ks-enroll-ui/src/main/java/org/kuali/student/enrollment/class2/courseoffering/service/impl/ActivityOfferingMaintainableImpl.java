package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.service.impl.KSMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleComponentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.messenger.util.MessengerConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingMaintainableImpl extends KSMaintainableImpl implements ActivityOfferingMaintainable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ActivityOfferingMaintainableImpl.class);

    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {

            ContextInfo contextInfo = createContextInfo();

            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            disassembleInstructorsWrapper(activityOfferingWrapper.getInstructors(), activityOfferingWrapper.getAoInfo());
            List<SeatPoolDefinitionInfo> seatPools = this.getSeatPoolDefinitions(activityOfferingWrapper.getSeatpools());
            CourseOfferingManagementUtil.getSeatPoolUtilityService().updateSeatPoolDefinitionList(seatPools, activityOfferingWrapper.getAoInfo().getId(), contextInfo);


            processEnrollmentDetail(activityOfferingWrapper);

            ActivityOfferingInfo activityOfferingInfo;

            /**
             * Save the AO first before processing schedule. (It's important to save first as scheduleAcivityOffering() service method
             * just accepts the ao id as param and fetches the DTO from the DB.)
             */
            try {
                // check if subterm is assigned to AO
                String subTermId = activityOfferingWrapper.getSubTermId();
                String aoTermId = activityOfferingWrapper.getAoInfo().getTermId();
                String termId = activityOfferingWrapper.getTerm().getId();
                if (!aoTermId.equals(subTermId)) {
                    if((subTermId == null || StringUtils.isBlank(subTermId))) {
                        if(!aoTermId.equals(termId)) {
                            activityOfferingWrapper.getAoInfo().setTermId(termId);
                        }
                    } else {
                        activityOfferingWrapper.getAoInfo().setTermId(subTermId);
                    }
                }
                activityOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), contextInfo);
                activityOfferingWrapper.setAoInfo(activityOfferingInfo);

            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }

            /**
             * Even if the user doesn't change any RDL data, here are the scenarios where we need to process schedules
             * 1. If the user checks/unchecks the colo checkbox
             * 2. If a new member is added to the colocation
             * 3. If the user opens an activity after scheduling and without changing any schedule details, submits the doc.
             * Once user submits a doc after scheduling complemented, all the RDLs should be converted to ADLs
             */
            if (activityOfferingWrapper.isSchedulesModified() ||
                    (!activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && activityOfferingWrapper.isColocatedAO()) ||
                    (activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && !activityOfferingWrapper.isColocatedAO()) ||
                    (activityOfferingWrapper.isSchedulingCompleted() && !activityOfferingWrapper.getRequestedScheduleComponents().isEmpty())){
                CourseOfferingManagementUtil.getScheduleHelper().saveSchedules(activityOfferingWrapper,contextInfo);
            }

            /**
             * Now that the Ao & the schedule has been updated, we need to update the registration groups
             */
            try {
                CourseOfferingManagementUtil.getCourseOfferingServiceFacade().updateRegistrationGroups(activityOfferingInfo, contextInfo);
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }

            //All the details on the current AO saved successfully.. Now, update the max enrollment on other AOs in the coloset and do WL logic
            try {
                // check if AO has colos
                if (activityOfferingWrapper.isPartOfColoSetOnLoadAlready() && !activityOfferingWrapper.isColocatedAO()){ // un-colocating
                    // create new WL for the un-colocating (edit) AO
                    if (!activityOfferingWrapper.isHasWaitlistCO()) {
                        activityOfferingWrapper.setHasWaitlist(false);
                    }
                    CourseWaitListInfo courseWaitListInfo =  CourseOfferingManagementUtil.getCourseOfferingServiceFacade().createUncolocatedWaitList(activityOfferingWrapper.getCourseWaitListInfo(), activityOfferingWrapper.getWaitListType(), activityOfferingWrapper.isHasWaitlist(), activityOfferingWrapper.isLimitWaitlistSize(), activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo().getFormatOfferingId(), contextInfo);
                    activityOfferingWrapper.setCourseWaitListInfo(courseWaitListInfo);
                } else {
                    HashMap<String, String> aoIdfoIdMap = new HashMap<String, String>();
                    //  Clean up the newly colocated AO's scheduling information
                    for (ColocatedActivity activity : activityOfferingWrapper.getColocatedActivities()){
                        //If an activity is newly added in this session for colo, delete it's RDLs and ADLs if exists
                        activity.getActivityOfferingInfo().setIsColocated(activityOfferingWrapper.isColocatedAO());
                        if (activityOfferingWrapper.isColocatedAO() && activityOfferingWrapper.isMaxEnrollmentShared()){
                            activity.getActivityOfferingInfo().setMaximumEnrollment(activityOfferingWrapper.getSharedMaxEnrollment());
                        }

                        boolean deleteSchedule = false;
                        List<String> deleteScheduleIds = new ArrayList<String>();
                        if (activityOfferingWrapper.isColocatedAO() && !activity.isAlreadyPersisted()){
                            if (!activityOfferingWrapper.isSchedulingCompleted()){
                                deleteScheduleIds.addAll(activity.getActivityOfferingInfo().getScheduleIds());
                                activity.getActivityOfferingInfo().getScheduleIds().clear();
                            }
                            deleteSchedule = true;
                        }

                        ActivityOfferingInfo updatedAO = CourseOfferingManagementUtil.getCourseOfferingService().updateActivityOffering(activity.getAoId(), activity.getActivityOfferingInfo(), contextInfo);
                        activity.setActivityOfferingInfo(updatedAO);

                        if (deleteSchedule){
                            CourseOfferingManagementUtil.getScheduleHelper().deleteRequestedAndActualSchedules(activityOfferingWrapper.getScheduleRequestSetInfo(),updatedAO.getId(),deleteScheduleIds,contextInfo);
                        }

                        // Needed for WL
                        aoIdfoIdMap.put(updatedAO.getId(), updatedAO.getFormatOfferingId());
                        if (activityOfferingWrapper.isHasWaitlistCO() && activityOfferingWrapper.isMaxEnrollmentShared()) {
                            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(activity.getCoId(), contextInfo);
                            if (!courseOfferingInfo.getHasWaitlist()) {
                                activityOfferingWrapper.setHasWaitlistCO(false);
                                activityOfferingWrapper.setHasWaitlist(false);
                            }
                        }
                    }

                    // Updating colo WL
                    CourseWaitListInfo courseWaitListInfo = CourseOfferingManagementUtil.getCourseOfferingServiceFacade().createColocatedWaitList(activityOfferingWrapper.getCourseWaitListInfo(),
                            activityOfferingWrapper.getWaitListType(), activityOfferingWrapper.isHasWaitlist(), activityOfferingWrapper.isLimitWaitlistSize(),
                            activityOfferingWrapper.isColocatedAO(), activityOfferingWrapper.isMaxEnrollmentShared(), aoIdfoIdMap, contextInfo);
                    activityOfferingWrapper.setCourseWaitListInfo(courseWaitListInfo);
                }

                //To retrieve the ExamPeriodId that is on CourseOffering
                String examPeriodId = null;
                try {
                    examPeriodId = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamPeriodId(activityOfferingInfo.getTermId(), contextInfo);
                } catch (DoesNotExistException e) {
                    LOGGER.info("The term " + activityOfferingInfo.getTermId() + " doesn't have an exam period.");
                }

                // generate exam offerings if exam period exists
                if (!StringUtils.isEmpty(examPeriodId)) {
                    CourseOfferingManagementUtil.getExamOfferingServiceFacade().generateFinalExamOfferingForAO(activityOfferingWrapper.getAoInfo(),
                            activityOfferingWrapper.getAoInfo().getTermId(), examPeriodId, new ArrayList<String>(), contextInfo);
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
     * @param wrapper input AO
     */
    protected void processEnrollmentDetail(ActivityOfferingWrapper wrapper){

        if (wrapper.getScheduleRequestSetInfo() == null){
            ScheduleRequestSetInfo set = new ScheduleRequestSetInfo();
            wrapper.setScheduleRequestSetInfo(set);
            set.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
            set.setName("Schedule request set for " + wrapper.getAoInfo().getCourseOfferingCode() + " - " + wrapper.getAoInfo().getActivityCode());
            set.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
            set.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        }

        // KSENROLL-10338 If the isMaxEnrollentShared changes, then need to save the
        // schedule request set data (thus, we set the wrapper's schedulesModified to true)
        Boolean origMaxShared = wrapper.getScheduleRequestSetInfo().getIsMaxEnrollmentShared();
        if (origMaxShared == null ||
                origMaxShared != wrapper.isMaxEnrollmentShared()) {
            wrapper.setSchedulesModified(true);
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
                wrapper.getScheduleRequestSetInfo().setMaximumEnrollment(wrapper.getSharedMaxEnrollment());
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
        } else {
            /**
             * If the current AO is part of the colo set before but now user removed it from the set, then
             * recalculate the enrollment seats if it's managed seperately
             */
            if (wrapper.isPartOfColoSetOnLoadAlready() && !wrapper.getScheduleRequestSetInfo().getIsMaxEnrollmentShared()){
                int totalSeats = 0;
                /**
                 * This is not the best way to do the calculation based on the wrapper as there may be chances user changed
                 * the details at screen before deleting the AO from the coloset.
                 */
                for (ColocatedActivity activity : wrapper.getEditRenderHelper().getManageSeperateEnrollmentList()){
                    if (activity.getEditRenderHelper().isAllowEnrollmentEdit()){
                        wrapper.getAoInfo().setMaximumEnrollment(activity.getMaxEnrollmentCount());
                    }
                    totalSeats = totalSeats + activity.getMaxEnrollmentCount();
                }
                wrapper.getScheduleRequestSetInfo().setMaximumEnrollment(totalSeats);
            }
        }
    }

    @Override
    public boolean addScheduleRequestComponent(ActivityOfferingWrapper activityOfferingWrapper) {
        return CourseOfferingManagementUtil.getScheduleHelper().addScheduleRequestComponent(activityOfferingWrapper);
    }

    public List<String> getEndTimes(String days,String startTime,String termTypeKey) throws Exception{
        return CourseOfferingManagementUtil.getScheduleHelper().getEndTimes(days,startTime,termTypeKey);
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo info = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);

            boolean isCentralSchedulingCoordinator = isSchedulingCoordinator();

            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info, isCentralSchedulingCoordinator);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(info.getCourseOfferingId(), contextInfo);

            // get state and type
            StateInfo state = CourseOfferingManagementUtil.getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
            wrapper.setStateName(state.getName());
            TypeInfo typeInfo = CourseOfferingManagementUtil.getTypeService().getType(wrapper.getAoInfo().getTypeKey(), contextInfo);
            wrapper.setTypeName(typeInfo.getName());

            // get the format offering
            FormatOfferingInfo formatOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(info.getFormatOfferingId(), contextInfo);
            wrapper.setFormatOffering(formatOfferingInfo);

            //check to see if there is crosslisting in CO
            if (courseOfferingInfo.getCrossListings() != null && !courseOfferingInfo.getCrossListings().isEmpty()) {
                StringBuilder builder = new StringBuilder();
                for (CourseOfferingCrossListingInfo crossListing : courseOfferingInfo.getCrossListings()){
                    builder.append(crossListing.getCode() + ", ");
                }
                wrapper.setCrossListedCourseCodes(StringUtils.removeEnd(builder.toString(), ", "));
            }

            // Set the display string (e.g. 'FALL 2020 (9/26/2020 to 12/26/2020)')
            // Now have to deal with subterms: have to check if it's subterm or term
            TermInfo term;
            TermInfo subTerm = null;
            int firstTerm = 0;
            wrapper.setHasSubTerms(false);
            wrapper.setSubTermName("None");
            wrapper.setSubTermId("");
            TermInfo termTemp = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(info.getTermId(), contextInfo);
            List<TypeTypeRelationInfo> terms = CourseOfferingManagementUtil.getTypeService().getTypeTypeRelationsByRelatedTypeAndType(termTemp.getTypeKey(), TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, contextInfo);
            if (terms == null || terms.isEmpty()) {
                term = new TermInfo(termTemp);
            } else {
                //Handle Subterms
                subTerm = new TermInfo(termTemp);
                term = CourseOfferingManagementUtil.getAcademicCalendarService().getContainingTerms(info.getTermId(), contextInfo).get(firstTerm);
                wrapper.setSubTermId(subTerm.getId());
                TypeInfo subTermType = CourseOfferingManagementUtil.getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                wrapper.setSubTermName(subTermType.getName());
            }

            wrapper.setTerm(term);
            if (term != null) {
                wrapper.setTermName(term.getName());
            }
            wrapper.setTermDisplayString(getTermDisplayString(info.getTermId(), term));
            if (subTerm!=null) {
                wrapper.setTermStartEndDate(getTermStartEndDate(subTerm));
            } else {
                wrapper.setTermStartEndDate(getTermStartEndDate(term));
            }

            //Find available sub-terms for term
            List<TermInfo> availableSubTerms = CourseOfferingManagementUtil.getAcademicCalendarService().getIncludedTermsInTerm(term.getId(), contextInfo);
            //Now setup start/end date for all subterms to support subterm changes on the screen
            Map<String, String> subTermDates = new HashMap<String, String>();
            subTermDates.put("none", getTermStartEndDate(term));
            for (TermInfo availSubTerm : availableSubTerms) {
                if (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(availSubTerm.getStateKey())) {
                    subTermDates.put(availSubTerm.getId(), this.getTermStartEndDate(availSubTerm));
                    wrapper.setHasSubTerms(true);
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            wrapper.setSubTermDatesJsonString(mapper.writeValueAsString(subTermDates));
            // end subterms

            //Find the earliest registration period and set the term registration start to that date
            List<TypeInfo> regPeriods = CourseOfferingManagementUtil.getTypeService().getTypesForGroupType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_GROUP_TYPE_KEY, contextInfo);
            List<KeyDateInfo> keyDateInfoList = CourseOfferingManagementUtil.getAcademicCalendarService().getKeyDatesForTerm(info.getTermId(), contextInfo);
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
            wrapper.setActivityCode(info.getActivityCode());
            wrapper.setAbbreviatedCourseType(typeInfo.getName().toUpperCase().substring(0, 3));

            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);

            // allows multiple orgs
            List<String> orgIds = courseOfferingInfo.getUnitsDeploymentOrgIds();
            if(orgIds != null && !orgIds.isEmpty()){
                StringBuilder orgIDs = new StringBuilder("");
                for (String orgId : orgIds) {
                    orgIDs.append(orgId).append(",");
                }
                if (orgIDs.length() > 0) {
                    wrapper.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
                }
            }

            //Set socInfo
            SocInfo soc = CourseOfferingSetUtil.getMainSocForTermId(term.getId(), ContextUtils.createDefaultContextInfo());
            if (soc != null) {
                wrapper.setSocInfo(soc);
            }
            wrapper.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(wrapper.getTerm(), wrapper.getSocInfo(),
                    CourseOfferingManagementUtil.getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), contextInfo));

            //retrieve all the populations for seat pool section client side validation
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY));
            QueryByCriteria criteria = qbcBuilder.build();

            try {
                List<PopulationInfo> populationInfoList = CourseOfferingManagementUtil.getPopulationService().searchForPopulations(criteria, createContextInfo());
                if(populationInfoList != null && !populationInfoList.isEmpty()){
                    String populationJSONString = "{\"" + CourseOfferingConstants.POPULATIONS_JSON_ROOT_KEY + "\": {";

                    int index = 0;
                    for (PopulationInfo populationInfo : populationInfoList) {
                        if (index == 0) {
                            populationJSONString = "{\"" + CourseOfferingConstants.POPULATIONS_JSON_ROOT_KEY + "\": {\"" + populationInfo.getId() + "\": \"" + populationInfo.getName() + "\"";
                        } else {
                            populationJSONString += ",\"" + populationInfo.getId() + "\": \"" + populationInfo.getName() + "\"";
                        }
                        index++;
                    }
                    populationJSONString += "}}";
                    wrapper.setPopulationsJSONString(populationJSONString);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            document.getNewMaintainableObject().setDataObject(wrapper);
            document.getOldMaintainableObject().setDataObject(wrapper);
            document.getDocumentHeader().setDocumentDescription("Edit AO - " + info.getActivityCode());

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(info.getId(), contextInfo);

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

                PopulationInfo pInfo = CourseOfferingManagementUtil.getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            wrapper.setHasSeatpools(true);
            //try to add an empty line
            if(seatPoolWrapperList.isEmpty()){
                wrapper.setHasSeatpools(false);
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
            CourseInfo courseInfo = CourseOfferingManagementUtil.getCourseService().getCourse(courseOfferingInfo.getCourseId(),contextInfo);
            wrapper.setCourse(courseInfo);

            loadColocatedAOs(wrapper);

            // get Waitlist
            wrapper.setHasWaitlistCO(courseOfferingInfo.getHasWaitlist());
            List<CourseWaitListInfo> courseWaitLists = CourseOfferingManagementUtil.getCourseWaitListService().getCourseWaitListsByActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID),contextInfo);

            if(courseWaitLists != null && courseWaitLists.size() > 0){
                int temp = 0;
                CourseWaitListInfo courseWaitListInfo = courseWaitLists.get(temp);
                wrapper.setCourseWaitListInfo(courseWaitListInfo);
                wrapper.updateWaitListType();
                if(courseWaitListInfo.getMaxSize() != null) {
                    wrapper.setLimitWaitlistSize(true);
                } else {
                    wrapper.setLimitWaitlistSize(false);
                }

                //looks like in inquiry view.xml we are using HasWaitlist for the "Waitlist active" field
                wrapper.setHasWaitlist(false);
                if (CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY.equals(courseWaitListInfo.getStateKey())){
                    wrapper.setHasWaitlist(true);
                }

                // checking if co-located AND share enrollement, and if so whether other CO WLs are inactive
                if(courseWaitListInfo.getActivityOfferingIds().size() > 1) {
                    if (wrapper.isHasWaitlistCO() && wrapper.isColocatedAO() && wrapper.isMaxEnrollmentShared()) {
                        for (String activityOfferingId : courseWaitListInfo.getActivityOfferingIds()) {
                            qbcBuilder.setPredicates(PredicateFactory.and(
                                    PredicateFactory.like("aoid", activityOfferingId),
                                    PredicateFactory.equalIgnoreCase("atpId", wrapper.getTerm().getId())));
                            criteria = qbcBuilder.build();
                            List<String> courseOfferingIds = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);
                            for (String courseOfferingId : courseOfferingIds) {
                                CourseOfferingInfo coloCourseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(courseOfferingId, contextInfo);
                                if (!coloCourseOfferingInfo.getHasWaitlist()) {
                                    wrapper.setHasWaitlistCO(false);
                                    wrapper.setHasWaitlist(false);
                                }
                            }
                            if (!wrapper.isHasWaitlistCO()) {
                                break;
                            }
                        }
                    }
                }
            }

            CourseOfferingManagementUtil.getScheduleHelper().loadSchedules(wrapper,contextInfo);

            loadNavigationDetails(wrapper);

            initializeForTimeSlot(wrapper);
            return wrapper;

        }catch (AuthorizationException ae){
            throw new AuthorizationException(ae.getUserId(), "open", null,
                    "User '" + ae.getUserId() + "' is not authorized to open view", null);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Finds the type type relation for the term type and set it at the form for later use.
     * This is used whenever we fetch the timeslots for a term type
     *
     * @param wrapper
     * @throws Exception
     */
    protected void initializeForTimeSlot(ActivityOfferingWrapper wrapper) throws Exception{
        List<TypeTypeRelationInfo> typeTypeRelationInfos = CourseOfferingManagementUtil.getTypeService().getTypeTypeRelationsByOwnerAndType(wrapper.getTerm().getTypeKey(), TypeServiceConstants.TYPE_TYPE_RELATION_ATP2TIMESLOT_TYPE_KEY,createContextInfo());

        if (typeTypeRelationInfos.isEmpty()){
            return;
        }

        TypeTypeRelationInfo typeTypeRelationInfo = KSCollectionUtils.getRequiredZeroElement(typeTypeRelationInfos);
        wrapper.setTimeSlotType(typeTypeRelationInfo.getRelatedTypeKey());

    }

    protected boolean isSchedulingCoordinator() throws Exception{
        RoleService rms = KimApiServiceLocator.getRoleService();

        String principalId = GlobalVariables.getUserSession().getPrincipalId();

        String roleId = rms.getRoleIdByNamespaceCodeAndName("KS-ENR", "KS Schedule Coordinator");

        if (StringUtils.isBlank(roleId)){
            throw new RuntimeException("Couldnt find role 'KS Schedule Coordinator' in 'KS-ENR' namespace");
        }

        List<String> roleIds = new ArrayList<String>();
        roleIds.add(roleId);

        return rms.principalHasRole(principalId, roleIds, null);
    }

    protected void loadColocatedAOs(ActivityOfferingWrapper wrapper) throws Exception {

        ActivityOfferingInfo info = wrapper.getAoInfo();
        wrapper.setColocatedOnLoadAlready(info.getIsColocated());

        int firstRequestSet = 0;

        //  Look for a SchedulingRequestSet. This needs to be loaded even if the AO isn't currently colocated.
        ScheduleRequestSetInfo scheduleRequestSetInfo = null;
        List<ScheduleRequestSetInfo> scheduleRequestSets = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, info.getId(), createContextInfo());
        if (scheduleRequestSets != null && !scheduleRequestSets.isEmpty()) {
            //  Remove this when partial colocation is implemented.
            if (scheduleRequestSets.size() > 1) {
                throw new RuntimeException(String.format("Activity %s:%s [%s] has multiple ScheduleRequestSets. This shouldn't happen.",
                        wrapper.getCourseOfferingCode(), wrapper.getActivityCode(), wrapper.getId()));
            } else {
                scheduleRequestSetInfo = scheduleRequestSets.get(firstRequestSet);
                wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
            }
        }

        if (info.getIsColocated()){

            wrapper.setColocatedAO(true);
            wrapper.setPartOfColoSetOnLoadAlready(true);

            if (scheduleRequestSetInfo != null) {
                wrapper.setMaxEnrollmentShared(scheduleRequestSetInfo.getIsMaxEnrollmentShared());
                if (scheduleRequestSetInfo.getIsMaxEnrollmentShared()) {
                    wrapper.setSharedMaxEnrollment(scheduleRequestSetInfo.getMaximumEnrollment());
                }

                List<String> activityOfferingIds = new ArrayList<String>(scheduleRequestSetInfo.getRefObjectIds());
                activityOfferingIds.remove(info.getId());
                List<ActivityOfferingInfo> aoInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByIds(activityOfferingIds,createContextInfo());

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

    protected void loadNavigationDetails(ActivityOfferingWrapper wrapper) throws Exception {
        List<ActivityOfferingInfo> aos = searchForRelatedAOs(wrapper);

        // Fill previous/next AOs for navigation
        wrapper.getEditRenderHelper().getAoCodes().clear();
        ContextInfo context = createContextInfo();
        for (ActivityOfferingInfo ao : aos){
            TypeInfo typeInfo;
            if (StringUtils.equals(ao.getId(),wrapper.getAoInfo().getId())){
                int index = aos.indexOf(ao);
                if (index > 0){
                    ActivityOfferingInfo prevAO = aos.get(index - 1);
                    wrapper.getEditRenderHelper().setPrevAO(prevAO);
                    typeInfo = CourseOfferingManagementUtil.getTypeService().getType(prevAO.getTypeKey(), context);
                    wrapper.getEditRenderHelper().setPrevAOTypeName(typeInfo.getName());
                } else {
                    wrapper.getEditRenderHelper().setPrevAO(new ActivityOfferingInfo());
                    wrapper.getEditRenderHelper().setPrevAOTypeName(StringUtils.EMPTY);
                }
                if (index < aos.size() - 1){
                    ActivityOfferingInfo nextAO = aos.get(index + 1);
                    wrapper.getEditRenderHelper().setNextAO(nextAO);
                    typeInfo = CourseOfferingManagementUtil.getTypeService().getType(nextAO.getTypeKey(), context);
                    wrapper.getEditRenderHelper().setNextAOTypeName(typeInfo.getName());
                } else {
                    wrapper.getEditRenderHelper().setNextAO(new ActivityOfferingInfo());
                    wrapper.getEditRenderHelper().setNextAOTypeName(StringUtils.EMPTY);
                }
                wrapper.getEditRenderHelper().setSelectedAO(ao.getId());
            }
            typeInfo = CourseOfferingManagementUtil.getTypeService().getType(ao.getTypeKey(), context);
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(ao.getId());
            keyValue.setValue(typeInfo.getName() + " " + ao.getActivityCode());
            wrapper.getEditRenderHelper().getAoCodes().add(keyValue);
        }
    }

    private List<ActivityOfferingInfo> searchForRelatedAOs(ActivityOfferingWrapper wrapper) throws Exception {
        List<ActivityOfferingInfo> result = new ArrayList<ActivityOfferingInfo>();

        // search for id, code, type of all AOs in CO
        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY);
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, wrapper.getAoInfo().getCourseOfferingId());
        List<ActivityOfferingInfo> aos = CourseOfferingViewHelperUtil.loadActivityOfferings(searchRequest);
        if (aos != null) result.addAll(aos);

        return result;
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

    private void disassembleInstructorsWrapper(List<OfferingInstructorWrapper> instructors, ActivityOfferingInfo aoInfo) {
        aoInfo.setInstructors(new ArrayList<OfferingInstructorInfo>());
        if (instructors != null && !instructors.isEmpty()) {
            for (OfferingInstructorWrapper instructor : instructors) {
                if(instructor.getOfferingInstructorInfo() != null && !instructor.getOfferingInstructorInfo().getPersonId().isEmpty())  {
                    aoInfo.getInstructors().add(disassembleInstructorWrapper(instructor));
                }
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
                StateInfo state = CourseOfferingManagementUtil.getStateService().getState(LprServiceConstants.TENTATIVE_STATE_KEY, ContextUtils.createDefaultContextInfo());
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
            StateInfo state = CourseOfferingManagementUtil.getStateService().getState(wrapper.getAoInfo().getStateKey(), ContextUtils.createDefaultContextInfo());
            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void processAfterDeleteLine(View view, CollectionGroup collectionGroup, Object model, int lineIndex) {
        if (!(collectionGroup.getPropertyName().equals("seatpools") || collectionGroup.getPropertyName().equals("instructors"))) {
            super.processAfterDeleteLine(view, collectionGroup, model, lineIndex);
        }  else if(collectionGroup.getPropertyName().equals("instructors")) {
            if (model instanceof MaintenanceDocumentForm) {
                MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
                MaintenanceDocument document = maintenanceForm.getDocument();
                // get the old object's collection
                Collection<Object> oldCollection = ObjectPropertyUtils.getPropertyValue(document.getOldMaintainableObject().getDataObject(),
                        collectionGroup.getPropertyName());
                if(oldCollection.size() -1 >= lineIndex) {
                    super.processAfterDeleteLine(view, collectionGroup, model, lineIndex);
                }
            }
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
            // set the person name if it's null, in the case of user-input personnel id
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;
            if (instructor.getOfferingInstructorInfo().getPersonName() == null && instructor.getOfferingInstructorInfo().getPersonId() != null) {
                List<Person> personList = CourseOfferingViewHelperUtil.getInstructorByPersonId(instructor.getOfferingInstructorInfo().getPersonId());
                int firstPerson = 0;
                if (personList.size() == 1) {
                    instructor.getOfferingInstructorInfo().setPersonName(personList.get(firstPerson).getName());
                }
            }
        } else if (addLine instanceof ColocatedActivity && isValidLine) {
            ColocatedActivity colo = (ColocatedActivity)addLine;
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            activityOfferingWrapper.getEditRenderHelper().getManageSeperateEnrollmentList().add(colo);
            activityOfferingWrapper.getNewScheduleRequest().getColocatedAOs().add(colo.getEditRenderHelper().getCode());

            //  Set this flag so that schedule requests are processed in saveDataObject().
            activityOfferingWrapper.setSchedulesModified(true);
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
            int firstPopulationInfo = 0;

            try {
                List<PopulationInfo> populationInfoList = CourseOfferingManagementUtil.getPopulationService().searchForPopulations(criteria, createContextInfo());
                if(populationInfoList == null || populationInfoList.isEmpty()){
                    GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", PopulationConstants.POPULATION_MSG_ERROR_POPULATION_NOT_FOUND, seatPool.getSeatPoolPopulation().getName());
                    return false;
                } else {
                    seatPool.getSeatPoolPopulation().setName(populationInfoList.get(firstPopulationInfo).getName());
                    seatPool.getSeatPoolPopulation().setId(populationInfoList.get(firstPopulationInfo).getId());
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

        String groupId = "ActivityOfferingEditPage";
        String coCodePropertyPath = "newCollectionLines['document.newMaintainableObject.dataObject.colocatedActivities'].courseOfferingCode";
        String aoCodePropertyPath = "newCollectionLines['document.newMaintainableObject.dataObject.colocatedActivities'].activityOfferingCode";

        for (ColocatedActivity activity : activityOfferingWrapper.getColocatedActivities()){
            if (StringUtils.equals(activity.getCourseOfferingCode(),colo.getCourseOfferingCode()) &&
                    StringUtils.equals(activity.getActivityOfferingCode(),colo.getActivityOfferingCode())){
                GlobalVariables.getMessageMap().putError(groupId, ActivityOfferingConstants.MSG_ERROR_AO_DUPLICATE_COLOCATE);
                return false;
            }
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("courseOfferingCode", colo.getCourseOfferingCode()),
                PredicateFactory.equalIgnoreCase("atpId", activityOfferingWrapper.getTermId())));
        QueryByCriteria criteria = qbcBuilder.build();
        int firstCOInfo = 0;

        //Do search. In ideal case, returns one element, which is the desired CO.
        try {
            List<CourseOfferingInfo> courseOfferings = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferings(criteria, createContextInfo());
            if (courseOfferings.isEmpty()){
                GlobalVariables.getMessageMap().putError(coCodePropertyPath, ActivityOfferingConstants.MSG_ERROR_AO_INVALID_CO_CODE_COLOCATE);
                return false;
            } else if (courseOfferings.size() > 1){
                GlobalVariables.getMessageMap().putError(coCodePropertyPath, ActivityOfferingConstants.MSG_ERROR_AO_MULTIPLE_CO_COLOCATE, colo.getCourseOfferingCode());
                return false;
            } else {
                colo.setCoId(courseOfferings.get(firstCOInfo).getId());
            }

            List<ActivityOfferingInfo> activityOfferingInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferings.get(firstCOInfo).getId(),createContextInfo());

            if (activityOfferingInfos.isEmpty()){
                GlobalVariables.getMessageMap().putError(aoCodePropertyPath, ActivityOfferingConstants.MSG_ERROR_AO_NOT_EXIST_CO_COLOCATE, colo.getCourseOfferingCode());
                return false;
            }

            boolean isAOMatchFound = false;
            for (ActivityOfferingInfo ao : activityOfferingInfos){
                if (StringUtils.equalsIgnoreCase(ao.getActivityCode(), colo.getActivityOfferingCode())){
                    if (ao.getIsColocated()){
                        GlobalVariables.getMessageMap().putError(groupId, ActivityOfferingConstants.MSG_ERROR_AO_PART_COLOCATE);
                        return false;
                    } else if (StringUtils.equals(ao.getId(), activityOfferingWrapper.getAoInfo().getId())){
                        GlobalVariables.getMessageMap().putError(groupId, ActivityOfferingConstants.MSG_ERROR_AO_CURRENT_COLOCATE);
                        return false;
                    } else if (StringUtils.equals(ao.getStateKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)){
                        GlobalVariables.getMessageMap().putError(aoCodePropertyPath, ActivityOfferingConstants.MSG_ERROR_AO_CANCEL_STATE_COLOCATE);
                        return false;
                    }

                    colo.setAoId(ao.getId());
                    if (ao.getMaximumEnrollment() != null){
                        colo.setMaxEnrollmentCount(ao.getMaximumEnrollment());
                    }
                    colo.setActivityOfferingInfo(ao);
                    isAOMatchFound = true;
                }
            }

            if (!isAOMatchFound){
                GlobalVariables.getMessageMap().putError(aoCodePropertyPath, ActivityOfferingConstants.MSG_ERROR_AO_INVALID_AO_CODE_COLOCATE);
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

    private static class SubtermComparator implements Comparator<TypeTypeRelationInfo>, Serializable {
        @Override
        public int compare(TypeTypeRelationInfo o1, TypeTypeRelationInfo o2) {
            String value1 = o1.getId();
            String value2 = o2.getId();

            int result = value1.compareToIgnoreCase(value2);
            return result;
        }
    }

    private String getTermStartEndDate(TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);

        String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
        String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
        formatter.format("%s - %s", startDate, endDate);
        return stringBuilder.toString();
    }

    /**
     * A pass-thru to the building info search in the schedule helper.
     */
    public List<BuildingInfo> retrieveBuildingInfoByCode(String buildingCode) throws Exception {
        return CourseOfferingManagementUtil.getScheduleHelper().retrieveBuildingInfoByCode(buildingCode, false);
    }

    /**
     * Query for course offering codes given a term and dept code.
     *
     * This is used by the course code auto-suggest in the Edit AO colocation section.
     *
     * @param termId The id of the term in which to search.
     * @param partialCode The initial (currently the first 4) characters of a course code.
     * @return A list of course codes that begin with the partial code.
     */
    public List<String> retrieveCourseOfferingCodes(String termId, String partialCode) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, StringUtils.upperCase(partialCode));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(false));

        SearchResultInfo searchResult = null;
        try {
            searchResult = CourseOfferingManagementUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());
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
}
