package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
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
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.jms.JMSException;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CourseRegistrationClientServiceImpl implements CourseRegistrationClientService {

    public static final Logger LOGGER = Logger.getLogger(CourseRegistrationClientServiceImpl.class);

    private ScheduleOfClassesService scheduleOfClassesService;
    private CourseRegistrationService courseRegistrationService;

    @Override
    public Response registerForRegistrationGroup(String userId, String termCode, String courseCode, String regGroupName, String regGroupId, String credits, String gradingOptionId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(registerForRegistrationGroupLocal(userId,termCode,courseCode,regGroupName,regGroupId,credits,gradingOptionId));
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();


    }
    public RegistrationResponseInfo registerForRegistrationGroupLocal(String userId, String termCode, String courseCode, String regGroupName, String regGroupId, String credits, String gradingOptionId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException, ReadOnlyException, AlreadyExistsException, LoginException {
        LOGGER.debug(String.format("REGISTRATION: user[%s] termCode[%s] courseCode[%s] regGroup[%s]", userId, termCode, courseCode, regGroupName));
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        }

        if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("User must be logged in to access this service");
        }

        // get the regGroup
        RegGroupSearchResult rg = getRegGroup(termCode, courseCode, regGroupName, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createAddRegistrationRequest(contextInfo.getPrincipalId(), rg.getTermId(), rg.getRegGroupId(), credits, gradingOptionId);

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        RegistrationResponseInfo registrationResponseInfo = getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);

        return registrationResponseInfo;
      
    }

    /**
     * Based on the input, get the RegGroupSearchResult. There are two ways to find it (termCode, courseCode, regGroupName)
     * or just the regGroupId.
     *
     * @param termCode
     * @param courseCode
     * @param regGroupName
     * @param regGroupId
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private RegGroupSearchResult getRegGroup(String termCode, String courseCode, String regGroupName, String regGroupId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        RegGroupSearchResult rg = null;

        if(!StringUtils.isEmpty(regGroupId)){
            RegistrationGroupInfo rgInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingService().getRegistrationGroup(regGroupId, contextInfo);
            if(rgInfo != null){
                rg = new RegGroupSearchResult();
                rg.setCourseOfferingId(rgInfo.getCourseOfferingId());
                rg.setTermId(rgInfo.getTermId());
                rg.setRegGroupState(rgInfo.getStateKey());
                rg.setRegGroupName(rgInfo.getName());
                rg.setRegGroupId(rgInfo.getId());
                rg.setActivityOfferingIds(rgInfo.getActivityOfferingIds());
            }
        } else {
            // get the registration group
            rg = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termCode, courseCode, regGroupName);
        }
        return rg;
    }


    @Override
    public Response getRegEngineStats() {
        Response.ResponseBuilder response;

        try {
            Map<String, List> stats = getStatsFromRegEngine();
            response = Response.ok(stats);
        } catch (Throwable t) {
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
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    private Map<String, List> getStatsFromRegEngine() throws JMSException {

        // define types of stats to collect
        List<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType> statTypesToRequest = new LinkedList<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType>();
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.BROKER);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.INITIALIZATION_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.VERIFICATION_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.SEAT_CHECK_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.REGISTRATION_ENGINE_STATS);

        // collect the stats
        RegEngineMqStatisticsGenerator generator = new RegEngineMqStatisticsGenerator();
        generator.initiateRequestForStats(statTypesToRequest);

        return generator.getStats();
    }

    /**
     * SEARCH for STUDENT REGISTRATION INFO based on person and termCode *
     */
    @Override
    public List<StudentScheduleCourseResult> searchForScheduleByPersonAndTerm(String userId, String termId, String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if(StringUtils.isEmpty(contextInfo.getPrincipalId())){
            throw new LoginException("User must be logged in to access this service");
        }

        if (StringUtils.isEmpty(userId)) {
            userId = contextInfo.getPrincipalId();
        }

        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        return getRegistrationScheduleByPersonAndTerm(userId, termId, contextInfo);
    }

    /**
     * This method call search service to retrieve registration schedule data for the person
     * @param userId
     * @param termId
     * @return StudentScheduleCourseResults
     * @throws OperationFailedException
     * @throws InvalidParameterException
     **/
    private List<StudentScheduleCourseResult> getRegistrationScheduleByPersonAndTerm(String userId, String termId, ContextInfo contextInfo) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<StudentScheduleCourseResult> studentScheduleCourseResults = new ArrayList<StudentScheduleCourseResult>();
        HashMap<String, StudentScheduleCourseResult> hm = new HashMap<String, StudentScheduleCourseResult>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.REG_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, userId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of registration schedule for person " + userId + " and term " + termId + " failed: ", e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            String luiId = "", masterLuiId = "", personLuiType = "", credits = "",
                    luiCode = "", luiName = "", luiDesc = "", luiType = "",
                    roomCode = "", buildingCode = "", weekdays = "", startTimeMs = "", endTimeMs = "";
            for (SearchResultCellInfo cellInfo : row.getCells()) {
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
     *
     * @param personId Principal ID
     * @return list of student schedule
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    public List<StudentScheduleCourseResult> clearLPRsByPerson(String personId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<LprInfo> lprs;
        List<StudentScheduleCourseResult> studentScheduleCourseResults = new ArrayList<StudentScheduleCourseResult>();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        lprs = CourseRegistrationAndScheduleOfClassesUtil.getLprService().getLprsByPerson(personId, contextInfo);
        for (LprInfo lprInfo : lprs) {
            CourseRegistrationAndScheduleOfClassesUtil.getLprService().deleteLpr(lprInfo.getId(), contextInfo);
        }

        return studentScheduleCourseResults;
    }


    /**
     * This method creates a registration request for the add operation of a single registration group.
     *
     * @param principalId principal id
     * @param termId      term id
     * @param regGroupid  Registration Group id
     * @return registration request
     */
    private RegistrationRequestInfo createAddRegistrationRequest(String principalId, String termId, String regGroupid, String credits, String gradingOptionId) {
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
//        registrationRequestItem.setCredits(new KualiDecimal(credits));
//        registrationRequestItem.setGradingOptionId(gradingOptionId);

        regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);

        return regReqInfo;
    }

    private String verifyRegistrationRequestCreditsGradingOption(CourseOfferingInfo courseOfferingInfo, String credits, String gradingOptionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,  DoesNotExistException {
        int firstValue = 0;

        // checking grading option. If null - just keep it that way
        if (!StringUtils.isEmpty(gradingOptionId) && (courseOfferingInfo.getStudentRegistrationGradingOptions().isEmpty() ||
                    !courseOfferingInfo.getStudentRegistrationGradingOptions().contains(gradingOptionId))) {
                throw new InvalidParameterException("Grading option doesn't match");
        }

        //Lookup the selected credit option and set from persisted values
        if (!courseOfferingInfo.getCreditOptionId().isEmpty()) {
            //Lookup the resultValueGroup Information
            ResultValuesGroupInfo resultValuesGroupInfo = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesGroup(courseOfferingInfo.getCreditOptionId(), contextInfo);
            String typeKey = resultValuesGroupInfo.getTypeKey();

            //Get the actual values
            List<ResultValueInfo> resultValueInfos = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

            if (!resultValueInfos.isEmpty()) {
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    credits = resultValueInfos.get(firstValue).getValue(); // fixed credits
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {  // range
                    if (credits.isEmpty() || (Float.valueOf(credits).floatValue() < Float.valueOf(resultValuesGroupInfo.getResultValueRange().getMinValue()).floatValue()) ||
                            (Float.valueOf(credits).floatValue() > Float.valueOf(resultValuesGroupInfo.getResultValueRange().getMaxValue()).floatValue())) {
                        throw new InvalidParameterException("Credits are incorrect");
                    }
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {  // multiple
                    List<String> creditOptions = new ArrayList<String>();
                    for (ResultValueInfo resultValueInfo : resultValueInfos) {
                        creditOptions.add(resultValueInfo.getValue());
                    }
                    if (credits.isEmpty() || !creditOptions.contains(credits)) {
                        throw new InvalidParameterException("Credits are incorrect");
                    }
                }
            }
        }

        return credits;
    }

    public ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = (ScheduleOfClassesService) GlobalResourceLoader.getService(new QName(ScheduleOfClassesServiceConstants.NAMESPACE, ScheduleOfClassesServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

}


