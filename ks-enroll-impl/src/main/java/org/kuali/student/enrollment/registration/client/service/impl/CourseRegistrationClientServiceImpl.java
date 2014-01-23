package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationClientService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleActivityOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleCourseResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.statistics.RegEngineMqStatisticsGenerator;
import org.kuali.student.enrollment.registration.engine.util.MQPerformanceCounter;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by swedev on 1/3/14.
 */
public class CourseRegistrationClientServiceImpl implements CourseRegistrationClientService {

    public static final Logger LOGGER = Logger.getLogger(CourseRegistrationClientServiceImpl.class);

    private ScheduleOfClassesService scheduleOfClassesService;
    private CourseRegistrationService courseRegistrationService;

    @Override
    public RegistrationResponseInfo registerForRegistrationGroupByTermCodeAndCourseCodeAndRegGroupName(String userId, String termCode, String courseCode, String regGroupName, String regGroupId) throws Exception {
        LOGGER.debug(String.format("REGISTRATION: user[%s] termCode[%s] courseCode[%s] regGroup[%s]", userId, termCode, courseCode, regGroupName));
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(!StringUtils.isEmpty(userId)){
            contextInfo.setPrincipalId(userId);
        }

        if(StringUtils.isEmpty(contextInfo.getPrincipalId())){
            throw new LoginException("User must be logged in to access this service");
        }

        // get the regGroup id
        String rgId = getRegGroupId(termCode, courseCode, regGroupName, regGroupId);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createAddRegistrationRequest(contextInfo.getPrincipalId(),
                getScheduleOfClassesService().getTermIdByTermCode(termCode),rgId );

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        RegistrationResponseInfo registrationResponseInfo = getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);

        return registrationResponseInfo;
      
    }

    /**
     * Based on the input, get the regGroupId. if it's passed in, return it, if not, use the other input params to find it.
     * @param termCode
     * @param courseCode
     * @param regGroupName
     * @param regGroupId
     * @return
     * @throws Exception
     */
    private String getRegGroupId(String termCode, String courseCode, String regGroupName, String regGroupId) throws Exception{
        String rgId = "";

        if(!StringUtils.isEmpty(regGroupId)){
            rgId = regGroupId;
        } else {
            // get the registration group
            RegGroupSearchResult regGroupSearchResult = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termCode, courseCode, regGroupName);
            if(regGroupSearchResult != null){
                rgId = regGroupSearchResult.getRegGroupId();
            }
        }
        return rgId;
    }


    @Override
    public Response getRegEngineStats() {
        Response.ResponseBuilder response;

        try {
            Map<String, List> stats = getStatsFromRegEngine();
            response = Response.ok(stats);
        } catch( Throwable t ) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public Response clearRegEngineStats() {

        Response.ResponseBuilder response;

        try {
            // This might not be the best way to do this...
            // I would rather have one point of entry into a singleton but
            // this is incredibly easy.
            MQPerformanceCounter.INSTANCE.clearPerformanceStats();

            response = Response.fromResponse(getRegEngineStats());
        } catch( Throwable t ) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    private Map<String, List> getStatsFromRegEngine() throws Exception {

        // define types of stats to collect
        List<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType> statTypesToRequest = new LinkedList<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType>();
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.BROKER );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.INITIALIZATION_QUEUE );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.VERIFICATION_QUEUE );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.SEAT_CHECK_QUEUE );
        statTypesToRequest.add( RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.REGISTRATION_ENGINE_STATS );

        // collect the stats
        RegEngineMqStatisticsGenerator generator = new RegEngineMqStatisticsGenerator();
        generator.initiateRequestForStats( statTypesToRequest );

        return generator.getStats();
    }

    /** SEARCH for STUDENT REGISTRATION INFO based on person and termCode **/
    @Override
    public List<StudentScheduleCourseResult> searchForScheduleByPersonAndTerm(String personId, String termCode) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(StringUtils.isEmpty(contextInfo.getPrincipalId())){
            throw new LoginException("User must be logged in to access this service");
        }

        if (StringUtils.isEmpty(personId)) {
            personId = contextInfo.getPrincipalId();
        }

        String termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(null, termCode);
        return getRegistrationScheduleByPersonAndTerm(personId, termId, contextInfo);
    }

    /**
     * This method call search service to retrieve registration schedule data for the person
     * @param personId
     * @param termId
     * @return StudentScheduleCourseResults
     **/
    private List<StudentScheduleCourseResult> getRegistrationScheduleByPersonAndTerm(String personId, String termId, ContextInfo contextInfo) throws Exception {
        List<StudentScheduleCourseResult> studentScheduleCourseResults = new ArrayList<StudentScheduleCourseResult>();
        HashMap<String, StudentScheduleCourseResult> hm = new HashMap<String, StudentScheduleCourseResult>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.REG_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, personId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);

        SearchResultInfo searchResult = null;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of registration schedule for person " + personId + " and term " + termId + " failed: ", e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            String luiId = "", masterLuiId = "", personLuiType = "", credits = "",
                    luiCode = "", luiName = "", luiDesc = "", luiType = "",
                    roomCode = "", buildingCode = "", weekdays = "", startTimeMs = "", endTimeMs = "";
            for (SearchResultCellInfo cellInfo : row.getCells()){
                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID.equals(cellInfo.getKey())) {
                    luiId = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.MASTER_LUI_ID.equals(cellInfo.getKey())) {
                    masterLuiId = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.PERSON_LUI_TYPE.equals(cellInfo.getKey())) {
                    personLuiType = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS.equals(cellInfo.getKey())) {
                    credits = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_CODE.equals(cellInfo.getKey())) {
                    luiCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_NAME.equals(cellInfo.getKey())) {
                    luiName = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC.equals(cellInfo.getKey())) {
                    luiDesc = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_TYPE.equals(cellInfo.getKey())) {
                    luiType = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cellInfo.getKey())) {
                    roomCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE.equals(cellInfo.getKey())) {
                    buildingCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cellInfo.getKey())) {
                    weekdays = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS.equals(cellInfo.getKey())) {
                    startTimeMs = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS.equals(cellInfo.getKey())) {
                    endTimeMs = cellInfo.getValue();
                }
            }

            // running over the list of results returned. One CO can have multiple AOs
            if (hm.containsKey(masterLuiId)) {
                StudentScheduleCourseResult studentScheduleCourseResult = hm.get(masterLuiId);
                if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_CO_TYPE_KEY)) {
                    studentScheduleCourseResult.setCourseCode(luiCode);
                    studentScheduleCourseResult.setDescription(luiDesc);
                    studentScheduleCourseResult.setCredits(credits);
                    hm.put(masterLuiId, studentScheduleCourseResult);
                } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_AO_TYPE_KEY)) {
                    // Scheduling info
                    ActivityOfferingScheduleComponentResult scheduleComponent = CourseRegistrationAndScheduleOfClassesUtil.getActivityOfferingScheduleComponent(roomCode, buildingCode,
                            weekdays, startTimeMs, endTimeMs);

                    // have to check if we already have the AO in our list, because we can have multiple schedules for the same AO
                    HashMap<String, StudentScheduleActivityOfferingResult> aoHm = new HashMap<String, StudentScheduleActivityOfferingResult>();
                    for (StudentScheduleActivityOfferingResult activityOfferingResult : studentScheduleCourseResult.getActivityOfferings()) {
                        aoHm.put(activityOfferingResult.getActiviyOfferingId(), activityOfferingResult);
                    }
                    if (!aoHm.containsKey(luiId)) {
                        StudentScheduleActivityOfferingResult activityOffering = new StudentScheduleActivityOfferingResult();

                        // AO basic info
                        activityOffering.setActiviyOfferingId(luiId);
                        activityOffering.setActiviyOfferingTypeShortName(luiName);
                        activityOffering.setActiviyOfferingType(luiType);  // to sort over priorities

                        // adding schedule to AO
                        List<ActivityOfferingScheduleComponentResult> scheduleComponents = new ArrayList<ActivityOfferingScheduleComponentResult>();
                        scheduleComponents.add(scheduleComponent);
                        activityOffering.setScheduleComponents(scheduleComponents);

                        // adding AO to result
                        if (studentScheduleCourseResult.getActivityOfferings().isEmpty()) {
                            List<StudentScheduleActivityOfferingResult> activityOfferings = new ArrayList<StudentScheduleActivityOfferingResult>();
                            activityOfferings.add(activityOffering);
                            studentScheduleCourseResult.setActivityOfferings(activityOfferings);
                        } else {
                            studentScheduleCourseResult.getActivityOfferings().add(activityOffering);
                        }
                    } else {
                        StudentScheduleActivityOfferingResult activityOffering = aoHm.get(luiId);
                        studentScheduleCourseResult.getActivityOfferings().remove(activityOffering);
                        activityOffering.getScheduleComponents().add(scheduleComponent);
                        studentScheduleCourseResult.getActivityOfferings().add(activityOffering);
                    }

                    hm.put(masterLuiId, studentScheduleCourseResult);
                }
            } else {
                StudentScheduleCourseResult studentScheduleCourseResult = new StudentScheduleCourseResult();
                if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_CO_TYPE_KEY)) {
                    studentScheduleCourseResult.setCourseCode(luiCode);
                    studentScheduleCourseResult.setDescription(luiDesc);
                    studentScheduleCourseResult.setCredits(credits);
                    hm.put(masterLuiId, studentScheduleCourseResult);
                } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_AO_TYPE_KEY)) {
                    List<StudentScheduleActivityOfferingResult> activityOfferings = new ArrayList<StudentScheduleActivityOfferingResult>();
                    StudentScheduleActivityOfferingResult activityOffering = new StudentScheduleActivityOfferingResult();
                    // AO basic info
                    activityOffering.setActiviyOfferingId(luiId);
                    activityOffering.setActiviyOfferingTypeShortName(luiName);
                    activityOffering.setActiviyOfferingType(luiType);  // to sort over priorities

                    // Scheduling info
                    List<ActivityOfferingScheduleComponentResult> scheduleComponents = new ArrayList<ActivityOfferingScheduleComponentResult>();
                    ActivityOfferingScheduleComponentResult scheduleComponent = CourseRegistrationAndScheduleOfClassesUtil.getActivityOfferingScheduleComponent(roomCode, buildingCode,
                            weekdays, startTimeMs, endTimeMs);
                    scheduleComponents.add(scheduleComponent);
                    activityOffering.setScheduleComponents(scheduleComponents);
                    // End scheduling info

                    activityOfferings.add(activityOffering);
                    studentScheduleCourseResult.setActivityOfferings(activityOfferings);
                    hm.put(masterLuiId, studentScheduleCourseResult);
                }
            }
        }

        if (!hm.isEmpty()) {
            for (Map.Entry<String, StudentScheduleCourseResult> pair : hm.entrySet()) {
                StudentScheduleCourseResult studentScheduleCourseResult = pair.getValue();
                if (studentScheduleCourseResult.getActivityOfferings().size() > 1) {
                    CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingReslutList(studentScheduleCourseResult.getActivityOfferings(), contextInfo);
                }
                studentScheduleCourseResults.add(studentScheduleCourseResult);
            }
        }

        return studentScheduleCourseResults;
    }

    /**
     * Finds all LPRs for a given personId and deletes them
     * Returns an empty List of StudentScheduleCourseResult
     * @param personId
     * @return
     * @throws Exception
     */

    public List<StudentScheduleCourseResult> clearLPRsByPerson(String personId) throws Exception{
        List<LprInfo> lprs;
        List<StudentScheduleCourseResult> studentScheduleCourseResults = new ArrayList<StudentScheduleCourseResult>();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        lprs = CourseRegistrationAndScheduleOfClassesUtil.getLprService().getLprsByPerson(personId, contextInfo);
        for(LprInfo lprInfo: lprs){
            CourseRegistrationAndScheduleOfClassesUtil.getLprService().deleteLpr(lprInfo.getId(), contextInfo);
        }

        return studentScheduleCourseResults;
    }


    /**
    * This method creates a registration request for the add operation of a single registration group.
    * @param principalId
    * @param termId
    * @param regGroupid
    * @return
    **/
    private RegistrationRequestInfo createAddRegistrationRequest(String principalId, String termId, String regGroupid){
        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(principalId);
        regReqInfo.setTermId(termId); // bad bc we have it from the load call above
        regReqInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY); // new reg request
        regReqInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);

        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        registrationRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        registrationRequestItem.setRegistrationGroupId(regGroupid);
        registrationRequestItem.setPersonId(principalId);

        regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);

        return regReqInfo;
    }

    public ScheduleOfClassesService getScheduleOfClassesService() {

        if (scheduleOfClassesService == null){
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(ScheduleOfClassesServiceConstants.NAMESPACE, ScheduleOfClassesServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null){
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

}


