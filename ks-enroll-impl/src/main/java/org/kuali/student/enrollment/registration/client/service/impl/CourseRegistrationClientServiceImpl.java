package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationClientService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.LearningPlanItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationValidationResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleActivityOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleTermResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.enrollment.util.KSIdentityServiceHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRegistrationClientServiceImpl implements CourseRegistrationClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationClientServiceImpl.class);

    private static final String LEARNING_PLAN_NOT_CONFIGURED_MESSAGE_KEY = "kuali.cr.learningplan.message.learningplan.not.configured";

    private LprService lprService;
    private ScheduleOfClassesService scheduleOfClassesService;
    private AcademicPlanService academicPlanService; // this does not live in our module so do not put in our context. use GRL to access.
    private CluService cluService;    // this does not live in our module so do not put in our context. use GRL to access.
    private KSIdentityServiceHelper ksIdentityServiceHelper;

    protected static Comparator<LprTransactionItemInfo> LPR_TRANS_ITEM_CREATE_DATE = new Comparator<LprTransactionItemInfo>() {

        @Override
        public int compare(LprTransactionItemInfo o1, LprTransactionItemInfo o2) {
            int ret = 0;
            try {
                ret = o1.getMeta().getCreateTime().compareTo(o2.getMeta().getCreateTime());
            } catch (NullPointerException ex) {
                LOGGER.error("Error comparing reg request meta data", ex);
            }
            return ret;
        }
    };

    @Override
    public Response createAndSubmitAddCourseRegistrationRequest(String termCode, String courseCode, String regGroupCode, String regGroupId, String credits, String gradingOptionId, boolean okToWaitlist, boolean okToRepeat) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(registerForRegistrationGroupLocal(termCode, courseCode, regGroupCode, regGroupId, credits, gradingOptionId, okToWaitlist, okToRepeat, ContextUtils.createDefaultContextInfo()));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response createAndSubmitDropCourseRegistrationRequest(String masterLprId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(dropRegistrationGroupLocal(ContextUtils.createDefaultContextInfo(), masterLprId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response clearScheduleRS(String termId, String termCode) {
        Response.ResponseBuilder response;
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);

            // in case it errors
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error clearing cart");
            String technicalInfo = String.format("Technical Info:(principalId:[%s] termId:[%s])", contextInfo.getPrincipalId(), termId);
            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);

            if (contextInfo.getPrincipalId() != null && !contextInfo.getPrincipalId().isEmpty()
                    && termId != null && !termId.isEmpty()) {
                StudentScheduleTermResult scheduleResult = getStudentScheduleAndWaitlistedCoursesByTerm(termId, termCode);

                List<StudentScheduleCourseResult> coursesToDrop = new ArrayList<>();
                if (scheduleResult != null) {
                    if (scheduleResult.getRegisteredCourseOfferings() != null) {
                        coursesToDrop.addAll(scheduleResult.getRegisteredCourseOfferings());
                    }

                    if (scheduleResult.getWaitlistCourseOfferings() != null) {
                        coursesToDrop.addAll(scheduleResult.getWaitlistCourseOfferings());
                    }

                    List<String> masterLprIds = new ArrayList<>(coursesToDrop.size());
                    for (StudentScheduleCourseResult courseResult : coursesToDrop) {
                        masterLprIds.add(courseResult.getMasterLprId());
                    }

                    if (!masterLprIds.isEmpty()) {
                        response = Response.ok(dropRegistrationGroupsLocal(contextInfo, masterLprIds, termId));
                    } else {
                        userMessage.setGenericMessage("Could not find any courses in your schedule.");
                        response = getResponse(Response.Status.NOT_FOUND, userMessage);
                    }
                }
            } else {
                userMessage.setGenericMessage("User and term must not be empty when clearing schedule.");
            }


        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * SEARCH for STUDENT REGISTRATION INFO based on person and termCode *
     */
    @Override
    public StudentScheduleTermResult getStudentScheduleAndWaitlistedCoursesByTerm(String termId, String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        String userId = contextInfo.getPrincipalId();

        if (StringUtils.isEmpty(userId)) {
            userId = contextInfo.getPrincipalId();
        }

        if (StringUtils.isEmpty(userId)) {
            throw new LoginException("[CourseRegistrationClientServiceImpl::getStudentScheduleAndWaitlistedCoursesByTerm] User must be logged in to access this service");
        }

        if (StringUtils.isEmpty(termId) && StringUtils.isEmpty(termCode)) {
            termId = "";
        } else {
            termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);
        }

        String entityId = getKsIdentityServiceHelper().getEntityIdByPrincipalId(userId);
        List<StudentScheduleTermResult> studentScheduleTermResults = getRegistrationScheduleByPersonAndTerm(entityId, termId, contextInfo);

        StudentScheduleTermResult studentScheduleTermResult = new StudentScheduleTermResult();

        if (!studentScheduleTermResults.isEmpty()) {
            studentScheduleTermResult = KSCollectionUtils.getOptionalZeroElement(studentScheduleTermResults);
        }
        studentScheduleTermResult.setUserId(userId);
        TermSearchResult termInfo = new TermSearchResult();
        termInfo.setTermId(termId);
        studentScheduleTermResult.setTerm(termInfo);

        return studentScheduleTermResult;
    }

    @Override
    public Response createAndSubmitUpdateCourseRegistrationRequest(String courseCode, String regGroupId, String masterLprId, String termId, String credits, String gradingOptionId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(updateScheduleItem(courseCode, regGroupId, masterLprId, termId, credits, gradingOptionId,
                    ContextUtils.createDefaultContextInfo()));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response createAndSubmitUpdateWaitlistRegistrationRequest(String courseCode, String regGroupId, String masterLprId, String termId, String credits, String gradingOptionId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(updateWaitlistEntry(courseCode, regGroupId, masterLprId, termId, credits, gradingOptionId,
                    ContextUtils.createDefaultContextInfo()));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response getRegistrationStatus(@QueryParam("regReqId") String regReqId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(getRegistrationStatusLocal(regReqId, ContextUtils.createDefaultContextInfo()));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response createAndSubmitDropWaitlistRegistrationRequest(@QueryParam("masterLprId") String masterLprId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(dropFromWaitlistLocal(ContextUtils.createDefaultContextInfo(), masterLprId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    private RegistrationRequestInfo dropFromWaitlistLocal(ContextInfo contextInfo, String masterLprId) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException, AlreadyExistsException, LoginException {

        String userId = contextInfo.getPrincipalId();

        LOGGER.debug("REGISTRATION: user[{}] masterLprId[{}]", userId, masterLprId);


        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        } else if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("[CourseRegistrationClientServiceImpl::registerForRegistrationGroupLocal] User must be logged in to access this service");
        }

        LprInfo masterLpr = getLprService().getLpr(masterLprId, contextInfo);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), masterLpr.getAtpId(), null, masterLprId, null, null, LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_DROP_WAITLIST_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, null, false, false);

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }

    private RegistrationRequestInfo registerForRegistrationGroupLocal(String termCode, String courseCode, String regGroupCode, String regGroupId, String credits, String gradingOptionId, boolean okToWaitlist, boolean okToRepeat, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException, ReadOnlyException, AlreadyExistsException, LoginException {
        LOGGER.debug("REGISTRATION: user[{}] termCode[{}] courseCode[{}] regGroup[{}]",
                contextInfo.getPrincipalId(), termCode, courseCode, regGroupCode);

        // get the regGroup
        RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, termCode, courseCode, regGroupCode, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode, contextInfo);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), rg.getTermId(), rg.getRegGroupId(), null, credits, gradingOptionId, LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, courseCode, okToWaitlist, okToRepeat);

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }

    private RegistrationRequestInfo dropRegistrationGroupLocal(ContextInfo contextInfo, String masterLprId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException, ReadOnlyException, AlreadyExistsException, LoginException {

        String userId = contextInfo.getPrincipalId();

        LOGGER.debug("REGISTRATION: user[{}] masterLprId[{}]", userId, masterLprId);


        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        } else if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("[CourseRegistrationClientServiceImpl::registerForRegistrationGroupLocal]User must be logged in to access this service");
        }

        LprInfo masterLpr = getLprService().getLpr(masterLprId, contextInfo);

        //Create the request object
        RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), masterLpr.getAtpId(), masterLpr.getLuiId(), masterLprId, null, null, LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, null, false, false);

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }


    private RegistrationRequestInfo dropRegistrationGroupsLocal(ContextInfo contextInfo, List<String> masterLprIds, String termId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException, ReadOnlyException, AlreadyExistsException, LoginException {

        String userId = contextInfo.getPrincipalId();

        LOGGER.debug("REGISTRATION: user[{}] masterLprId[{}]", userId, masterLprIds);


        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        } else if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("[CourseRegistrationClientServiceImpl::registerForRegistrationGroupLocal]User must be logged in to access this service");
        }

        List<LprInfo> masterLprs = getLprService().getLprsByIds(masterLprIds, contextInfo);

        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(userId);
        regReqInfo.setTermId(termId); // bad bc we have it from the load call above
        regReqInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);
        regReqInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);


        List<RegistrationRequestItemInfo> regReqItems = new ArrayList<>();
        String entityId = getKsIdentityServiceHelper().getEntityIdByPrincipalId(contextInfo.getPrincipalId());

        for (LprInfo masterLpr : masterLprs) {
            RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(entityId, masterLpr.getLuiId(), masterLpr.getMasterLprId(), null, null, LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, null, false, false);
            regReqItems.add(registrationRequestItem);
        }

        regReqInfo.getRegistrationRequestItems().addAll(regReqItems);

        // persist the request object in the service
        RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }


    /**
     * This method retrieves registration schedule data for the person
     *
     * @param userId Person Id
     * @param termId Term Key
     * @return StudentScheduleCourseResults
     * @throws OperationFailedException
     * @throws InvalidParameterException
     */
    private List<StudentScheduleTermResult> getRegistrationScheduleByPersonAndTerm(String userId, String termId, ContextInfo contextInfo) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.REG_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, userId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);

        return searchRegistrationScheduleByPersonAndTerm(searchRequest, contextInfo);
    }

    /**
     * This method calls the search service to retrieve registration schedule data for the person
     *
     * @param searchRequest Seach request info
     * @return StudentScheduleCourseResults
     * @throws OperationFailedException
     * @throws InvalidParameterException
     */
    private List<StudentScheduleTermResult> searchRegistrationScheduleByPersonAndTerm(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequest);
        String userId = requestHelper.getParamAsString(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID);
        String termId = requestHelper.getParamAsString(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID);

        List<StudentScheduleTermResult> studentScheduleTermResults = new ArrayList<>();
        List<String> activityOfferingList = new ArrayList<>();
        HashMap<String, StudentScheduleCourseResult> hmCourseOffering = new HashMap<>();
        HashMap<String, TermSearchResult> hmTermInfo = new HashMap<>();
        HashMap<String, List<String>> hmTerm = new HashMap<>();

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of registration schedule for person " + userId + " and term " + termId + " failed: ", e);
        }

        if (searchResult.getRows().isEmpty()) {
            // Initialize if no results are found
            TermSearchResult termInfo = new TermSearchResult();
            termInfo.setTermId(termId);
            StudentScheduleTermResult studentScheduleTermResult = new StudentScheduleTermResult();
            studentScheduleTermResult.setTerm(termInfo);
        } else {
            for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
                String atpId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_ID);
                String atpCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_CD);
                String atpName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_NAME);
                String luiId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID);
                String masterLprId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.MASTER_LPR_ID);
                String personLuiType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.PERSON_LUI_TYPE);
                String credits = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS);
                String gradingOptionId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.GRADING_OPTION_ID);
                String crossList = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CROSSLIST);
                String luiCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_CODE);
                String luiName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_NAME);
                String luiDesc = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC);
                String luiType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_TYPE);
                String luiLongName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME);
                String isTBA = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.TBA_IND);
                String roomCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE);
                String buildingCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE);
                String weekdays = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS);
                String startTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS);
                String endTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS);
                String resultValuesGroupKey = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RES_VAL_GROUP_KEY);
                String aoName = (luiName != null && luiName.length() >= 3 ? luiName.substring(0, 1).toUpperCase() + luiName.substring(1).toLowerCase() : "");
                String lprCreateTime = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_CREATETIME);

                // running over the list of results returned. One CO can have multiple AOs
                if (hmCourseOffering.containsKey(masterLprId)) {
                    StudentScheduleCourseResult studentScheduleCourseResult = hmCourseOffering.get(masterLprId);
                    if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY)) {
                        if (!StringUtils.isEmpty(crossList) && !StringUtils.equals(crossList, luiCode)) {
                            studentScheduleCourseResult.setCourseCode(crossList);
                        } else {
                            studentScheduleCourseResult.setCourseCode(luiCode);
                        }
                        studentScheduleCourseResult.setCourseId(luiId);
                        studentScheduleCourseResult.setDescription(luiDesc);
                        studentScheduleCourseResult.setCredits(credits);
                        studentScheduleCourseResult.setGradingOptionId(gradingOptionId);
                        studentScheduleCourseResult.setLongName(luiLongName);
                        studentScheduleCourseResult.setMasterLprId(masterLprId);
                        studentScheduleCourseResult.setCreateTime(lprCreateTime);
                        if (StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY)) {
                            studentScheduleCourseResult.setWaitlisted(true);
                        } else {
                            studentScheduleCourseResult.setWaitlisted(false);
                        }
                        if (resultValuesGroupKey != null && resultValuesGroupKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) {
                            studentScheduleCourseResult.setCreditOptions(getCourseOfferingCreditOptionValues(resultValuesGroupKey, contextInfo));
                        } else {
                            if (!studentScheduleCourseResult.getGradingOptions().containsKey(resultValuesGroupKey)) {
                                String gradingOptionName = CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(resultValuesGroupKey);
                                if (!StringUtils.isEmpty(gradingOptionName)) {
                                    studentScheduleCourseResult.getGradingOptions().put(resultValuesGroupKey, gradingOptionName);
                                }
                            }
                        }
                    } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY)) {
                        studentScheduleCourseResult.setRegGroupCode(luiName);
                        studentScheduleCourseResult.setRegGroupId(luiId);
                    } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY)) {
                        // Scheduling info
                        ActivityOfferingScheduleComponentResult scheduleComponent = CourseRegistrationAndScheduleOfClassesUtil.getActivityOfferingScheduleComponent(isTBA, roomCode, buildingCode,
                                weekdays, startTimeMs, endTimeMs);

                        // have to check if we already have the AO in our list, because we can have multiple schedules for the same AO
                        HashMap<String, StudentScheduleActivityOfferingResult> aoHm = new HashMap<>();
                        for (StudentScheduleActivityOfferingResult activityOfferingResult : studentScheduleCourseResult.getActivityOfferings()) {
                            aoHm.put(activityOfferingResult.getActivityOfferingId(), activityOfferingResult);
                        }
                        if (!aoHm.containsKey(luiId)) {
                            StudentScheduleActivityOfferingResult activityOffering = new StudentScheduleActivityOfferingResult();

                            // AO basic info
                            activityOffering.setActivityOfferingId(luiId);
                            activityOffering.setActivityOfferingTypeName(aoName);
                            activityOffering.setActivityOfferingType(luiType);  // to sort over priorities

                            // adding schedule to AO
                            List<ActivityOfferingScheduleComponentResult> scheduleComponents = new ArrayList<>();
                            scheduleComponents.add(scheduleComponent);
                            activityOffering.setScheduleComponents(scheduleComponents);

                            // adding AO to result
                            if (studentScheduleCourseResult.getActivityOfferings().isEmpty()) {
                                List<StudentScheduleActivityOfferingResult> activityOfferings = new ArrayList<>();
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

                        // adding AOID to the list to search for instructors
                        if (!activityOfferingList.contains(luiId)) {
                            activityOfferingList.add(luiId);
                        }
                    }
                } else {
                    StudentScheduleCourseResult studentScheduleCourseResult = new StudentScheduleCourseResult();
                    if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY)) {
                        if (!StringUtils.isEmpty(crossList) && !StringUtils.equals(crossList, luiCode)) {
                            studentScheduleCourseResult.setCourseCode(crossList);
                        } else {
                            studentScheduleCourseResult.setCourseCode(luiCode);
                        }
                        studentScheduleCourseResult.setCourseId(luiId);
                        studentScheduleCourseResult.setDescription(luiDesc);
                        studentScheduleCourseResult.setCredits(credits);
                        studentScheduleCourseResult.setGradingOptionId(gradingOptionId);
                        studentScheduleCourseResult.setLongName(luiLongName);
                        studentScheduleCourseResult.setMasterLprId(masterLprId);
                        studentScheduleCourseResult.setCreateTime(lprCreateTime);
                        if (StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY)) {
                            studentScheduleCourseResult.setWaitlisted(true);
                        } else {
                            studentScheduleCourseResult.setWaitlisted(false);
                        }
                        if (resultValuesGroupKey != null && resultValuesGroupKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) {
                            studentScheduleCourseResult.setCreditOptions(getCourseOfferingCreditOptionValues(resultValuesGroupKey, contextInfo));
                        } else {
                            studentScheduleCourseResult.setGradingOptions(new HashMap<String, String>());
                            String gradingOptionName = CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(resultValuesGroupKey);
                            if (!StringUtils.isEmpty(gradingOptionName)) {
                                studentScheduleCourseResult.getGradingOptions().put(resultValuesGroupKey, gradingOptionName);
                            }
                        }
                        hmCourseOffering.put(masterLprId, studentScheduleCourseResult);
                    } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY)) {
                        studentScheduleCourseResult.setRegGroupCode(luiName);
                        hmCourseOffering.put(masterLprId, studentScheduleCourseResult);
                    } else if (StringUtils.equals(personLuiType, LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY) || StringUtils.equals(personLuiType, LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY)) {
                        List<StudentScheduleActivityOfferingResult> activityOfferings = new ArrayList<>();
                        StudentScheduleActivityOfferingResult activityOffering = new StudentScheduleActivityOfferingResult();
                        // AO basic info
                        activityOffering.setActivityOfferingId(luiId);
                        activityOffering.setActivityOfferingTypeName(aoName);
                        activityOffering.setActivityOfferingType(luiType);  // to sort over priorities

                        // Scheduling info
                        List<ActivityOfferingScheduleComponentResult> scheduleComponents = new ArrayList<>();
                        ActivityOfferingScheduleComponentResult scheduleComponent = CourseRegistrationAndScheduleOfClassesUtil.getActivityOfferingScheduleComponent(isTBA, roomCode, buildingCode,
                                weekdays, startTimeMs, endTimeMs);
                        scheduleComponents.add(scheduleComponent);
                        activityOffering.setScheduleComponents(scheduleComponents);
                        // End scheduling info

                        activityOfferings.add(activityOffering);
                        studentScheduleCourseResult.setActivityOfferings(activityOfferings);
                        hmCourseOffering.put(masterLprId, studentScheduleCourseResult);

                        // adding AOID to the list to search for instructors
                        if (!activityOfferingList.contains(luiId)) {
                            activityOfferingList.add(luiId);
                        }
                    }
                }

                // Adding all course offerings for the particular term
                if (!hmTerm.containsKey(termId)) {
                    List<String> masterLprIds = new ArrayList<>();
                    masterLprIds.add(masterLprId);
                    hmTerm.put(termId, masterLprIds);
                    TermSearchResult termInfo = new TermSearchResult();
                    termInfo.setTermId(atpId);
                    termInfo.setTermCode(atpCode);
                    termInfo.setTermName(atpName);
                    hmTermInfo.put(termId, termInfo);
                } else {
                    if (!hmTerm.get(termId).contains(masterLprId)) {
                        hmTerm.get(termId).add(masterLprId);
                    }
                }
            }

            // getting instructor info for AOs
            Map<String, List<InstructorSearchResult>> hmAOInstructors = new HashMap<>();
            if (!activityOfferingList.isEmpty()) {
                hmAOInstructors = CourseRegistrationAndScheduleOfClassesUtil.searchForInstructorsByAoIds(activityOfferingList, contextInfo);
            }

            for (Map.Entry<String, List<String>> pair : hmTerm.entrySet()) {
                List<StudentScheduleCourseResult> studentScheduleRegisteredCourseResults = new ArrayList<>();
                List<StudentScheduleCourseResult> studentScheduleWaitlistCourseResults = new ArrayList<>();
                TermSearchResult termInfo = hmTermInfo.get(pair.getKey());
                List<String> masterLuiIds = pair.getValue();
                for (String masterLuiId : masterLuiIds) {
                    StudentScheduleCourseResult studentScheduleCourseResult = hmCourseOffering.get(masterLuiId);
                    if (studentScheduleCourseResult.getActivityOfferings().size() > 1) {
                        CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingReslutList(studentScheduleCourseResult.getActivityOfferings(), contextInfo);
                    }
                    for (StudentScheduleActivityOfferingResult activityOffering : studentScheduleCourseResult.getActivityOfferings()) {
                        if (hmAOInstructors.containsKey(activityOffering.getActivityOfferingId())) {
                            activityOffering.setInstructors(hmAOInstructors.get(activityOffering.getActivityOfferingId()));
                        }
                    }
                    if (studentScheduleCourseResult.isWaitlisted()) {
                        studentScheduleWaitlistCourseResults.add(studentScheduleCourseResult);
                    } else {
                        studentScheduleRegisteredCourseResults.add(studentScheduleCourseResult);
                    }
                }
                StudentScheduleTermResult studentScheduleTermResult = new StudentScheduleTermResult();
                studentScheduleTermResult.setTerm(termInfo);
                studentScheduleTermResult.setRegisteredCourseOfferings(studentScheduleRegisteredCourseResults);
                studentScheduleTermResult.setWaitlistCourseOfferings(studentScheduleWaitlistCourseResults);

                studentScheduleTermResults.add(studentScheduleTermResult);
            }
        }

        return studentScheduleTermResults;
    }

    /**
     * This method creates a registration request for the add operation of a single registration group.
     *
     * @param principalId     principal id
     * @param termId          term id
     * @param regGroupId      Registration Group id
     * @param masterLprId     masterLprId
     * @param credits         credits
     * @param gradingOptionId gradingOptionId
     * @param typeKey         typeKey
     * @param stateKey        stateKey
     * @param reqItemTypeKey  reqItemTypeKey
     * @param reqItemStateKey reqItemStateKey
     * @param okToWaitlist    flag to indicate that the student wants to be added to the waitlist if that option is available
     * @return registration request
     */
    private RegistrationRequestInfo createRegistrationRequest(String principalId, String termId, String regGroupId, String masterLprId, String credits, String gradingOptionId, String typeKey, String stateKey, String reqItemTypeKey, String reqItemStateKey, String courseCode, boolean okToWaitlist, boolean okToRepeat) {
        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(principalId);
        regReqInfo.setTermId(termId); // bad bc we have it from the load call above
        regReqInfo.setTypeKey(typeKey);
        regReqInfo.setStateKey(stateKey);

        String entityId = getKsIdentityServiceHelper().getEntityIdByPrincipalId(principalId);

        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(entityId, regGroupId, masterLprId, credits, gradingOptionId, reqItemTypeKey, reqItemStateKey, courseCode, okToWaitlist, okToRepeat);

        regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);

        return regReqInfo;
    }

    private String verifyRegistrationRequestCreditsGradingOption(CourseOfferingInfo courseOfferingInfo, String credits, String gradingOptionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        int firstValue = 0;

        // checking grading option. If null - just keep it that way
        if (!StringUtils.isEmpty(gradingOptionId) && (courseOfferingInfo.getStudentRegistrationGradingOptions().isEmpty()
                || !courseOfferingInfo.getStudentRegistrationGradingOptions().contains(gradingOptionId)) && !ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, gradingOptionId)) {
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
                switch (typeKey) {
                    case LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED:
                        credits = resultValueInfos.get(firstValue).getValue(); // fixed credits

                        break;
                    case LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE:   // range
                        if (credits.isEmpty() || (Float.valueOf(credits) < Float.valueOf(resultValuesGroupInfo.getResultValueRange().getMinValue()))
                                || (Float.valueOf(credits) > Float.valueOf(resultValuesGroupInfo.getResultValueRange().getMaxValue()))) {
                            throw new InvalidParameterException("Credits are incorrect");
                        }
                        break;
                    case LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE:   // multiple
                        List<String> creditOptions = new ArrayList<>();
                        for (ResultValueInfo resultValueInfo : resultValueInfos) {
                            creditOptions.add(resultValueInfo.getValue());
                        }
                        if (credits.isEmpty() || !creditOptions.contains(credits)) {
                            throw new InvalidParameterException("Credits are incorrect");
                        }
                        break;
                }
            }
        }

        return credits;
    }

    private RegistrationRequestInfo updateScheduleItem(String courseCode, String regGroupId, String masterLprId, String termId, String credits, String gradingOptionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, AlreadyExistsException {

        return updateRegistrationItem(courseCode, regGroupId, masterLprId, termId, credits, gradingOptionId, contextInfo, LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY);

    }

    private RegistrationRequestInfo updateWaitlistEntry(String courseCode, String regGroupId, String masterLprId, String termId, String credits, String gradingOptionId, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException, AlreadyExistsException {

        return updateRegistrationItem(courseCode, regGroupId, masterLprId, termId, credits, gradingOptionId, contextInfo, LprServiceConstants.REQ_ITEM_UPDATE_WAITLIST_TYPE_KEY);

    }

    /*
     * Utility method to update any kind of schedule item (registered, waitlisted, etc)
     */
    private RegistrationRequestInfo updateRegistrationItem(String courseCode, String regGroupId, String masterLprId, String termId, String credits,
                                                           String gradingOptionId, ContextInfo contextInfo, String typeKey)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException,
            AlreadyExistsException {
        RegistrationRequestInfo registrationRequestInfo = new RegistrationRequestInfo();
        String userId = contextInfo.getPrincipalId();

        //Populate Fields for RegRequestInfo object
        registrationRequestInfo.setRequestorId(userId);
        registrationRequestInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        registrationRequestInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);

        //Create Reg Request Item
        String entityId = getKsIdentityServiceHelper().getEntityIdByPrincipalId(contextInfo.getPrincipalId());
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(entityId, regGroupId,
                masterLprId, credits, gradingOptionId, typeKey, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, courseCode, false, false);
        List<RegistrationRequestItemInfo> registrationRequestItemInfos = new ArrayList<>();
        registrationRequestItemInfos.add(registrationRequestItem);
        registrationRequestInfo.setRegistrationRequestItems(registrationRequestItemInfos);
        registrationRequestInfo.setTermId(termId);

        //Create Registration Request
        RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().
                createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, registrationRequestInfo, contextInfo);

        // submit the request to the registration engine.
        CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);

        return newRegReq;
    }

    private List<String> getCourseOfferingCreditOptionValues(String creditOptionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        int firstValue = 0;
        List<String> creditOptions = new ArrayList<>();

        //Lookup the selected credit option and set from persisted values
        if (!creditOptionId.isEmpty()) {
            //Lookup the resultValueGroup Information
            ResultValuesGroupInfo resultValuesGroupInfo = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesGroup(creditOptionId, contextInfo);
            String typeKey = resultValuesGroupInfo.getTypeKey();

            //Get the actual values
            List<ResultValueInfo> resultValueInfos = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

            if (!resultValueInfos.isEmpty()) {
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    creditOptions.add(resultValueInfos.get(firstValue).getValue()); // fixed credits
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) ||
                        typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {  // multiple or range
                    for (ResultValueInfo resultValueInfo : resultValueInfos) {
                        creditOptions.add(resultValueInfo.getValue());
                    }
                }
            }
        }

        return creditOptions;
    }

    /**
     * All info about a registration request can be found in the LprTransaction. get the lpr transaction and transform
     * it into something useful.
     * <p/>
     * This method has been temporarily set as public because the TestCourseRegistrationEngine is relying on it...
     *
     * @param regReqId    registration request id
     * @param contextInfo context of the call
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    public RegistrationResponseResult getRegistrationStatusLocal(String regReqId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        RegistrationResponseResult result = new RegistrationResponseResult();

        LprTransactionInfo lprTransactionInfo = getLprService().getLprTransaction(regReqId, contextInfo);

        result.setRegistrationRequestId(lprTransactionInfo.getId());
        result.setState(lprTransactionInfo.getStateKey());
        result.getStatuses().add(lprTransactionInfo.getStateKey()); // use state for now until we come up with something better

        List<LprTransactionItemInfo> lprTransItems = lprTransactionInfo.getLprTransactionItems();

        // for some reason the users want this in reverse order.
        Collections.sort(lprTransItems, LPR_TRANS_ITEM_CREATE_DATE); // make sure we're sorting by date

        for (LprTransactionItemInfo lprTransactionItemInfo : lprTransItems) {
            RegistrationResponseItemResult resultItem = new RegistrationResponseItemResult();

            resultItem.setRegistrationRequestItemId(lprTransactionItemInfo.getId());
            resultItem.setRegistrationRequestId(lprTransactionInfo.getId());

            resultItem.setState(lprTransactionItemInfo.getStateKey());
            resultItem.setStatus(lprTransactionItemInfo.getStateKey()); // we should be using the result state, but that is currently a boolean and not useful
            resultItem.setNewLuiId(lprTransactionItemInfo.getNewLuiId());
            resultItem.setType(lprTransactionItemInfo.getTypeKey());

            for (ValidationResultInfo validationResult : lprTransactionItemInfo.getValidationResults()) {
                resultItem.getMessages().add(validationResult.getMessage());

                //Translate some "pseudo" states for ease in the ui to indicate if waitlists are available or was added to waitlist, etc.
                if (LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(lprTransactionItemInfo.getStateKey()) &&
                        validationResult.getMessage().contains(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_MESSAGE_KEY)) {
                    resultItem.setState(CourseRegistrationClientService.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY);
                } else if (LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY.equals(lprTransactionItemInfo.getStateKey()) &&
                        validationResult.getMessage().contains(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_WAITLISTED_MESSAGE_KEY)) {
                    resultItem.setState(CourseRegistrationClientService.LPRTRANS_ITEM_WAITLIST_STATE_KEY);
                }
            }

            resultItem.setResultingLprId(lprTransactionItemInfo.getResultingLprId());

            result.getResponseItemResults().add(resultItem);
        }

        return result;
    }

    @Override
    public Response getLearningPlan(String termId, String termCode) {
        Response.ResponseBuilder response;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            if (getAcademicPlanService() == null) {
                return getResponse(Response.Status.NOT_FOUND, new RegistrationValidationResult(LEARNING_PLAN_NOT_CONFIGURED_MESSAGE_KEY)).build();
            }

            String entityId =  getKsIdentityServiceHelper().getEntityIdByPrincipalId(contextInfo.getPrincipalId());

            // get the learning plan for this student
            List<LearningPlanInfo> learningPlans = getAcademicPlanService().getLearningPlansForStudentByType(entityId, AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, contextInfo);

            if (learningPlans == null || learningPlans.isEmpty()) {
                return Response.noContent().build();
            }

            // get the plan items
            List<PlanItemInfo> allPlanItems = new ArrayList<>();
            for (LearningPlanInfo lpi : learningPlans) {
                List<PlanItemInfo> planItemsTemp = getAcademicPlanService().getPlanItemsInPlan(lpi.getId(), contextInfo);
                if (planItemsTemp != null && !planItemsTemp.isEmpty()) {
                    allPlanItems.addAll(planItemsTemp);
                }
            }

            List<String> coursesToLookup = new ArrayList<>();
            List<String> rgsToLookup = new ArrayList<>();

            // get only the plans for this term
            termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);
            List<PlanItemInfo> termPlanItems = new ArrayList<>();
            for (PlanItemInfo planItemInfo : allPlanItems) {
                if (planItemInfo.getPlanTermIds().contains(termId)) {
                    termPlanItems.add(planItemInfo);

                    // add additional lookup ids to their lists
                    if (LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY.equals(planItemInfo.getRefObjectType())) {
                        coursesToLookup.add(planItemInfo.getRefObjectId());
                    } else if (LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY.equals(planItemInfo.getRefObjectType())) {
                        rgsToLookup.add(planItemInfo.getRefObjectId());
                    }
                }
            }

            if (termPlanItems.isEmpty()) {
                return Response.noContent().build();
            }

            // Map of reference objects IDs to their data
            Map<String, LearningPlanItemResult> refObjMap = new HashMap<>();

            // Fetch and build out the ref objects
            refObjMap.putAll(buildLearningPlanCourseItems(coursesToLookup, termId, contextInfo));
            refObjMap.putAll(buildLearningPlanRegGroupItems(rgsToLookup, contextInfo));

            // We now have the set of ref objects but we may have duplicates for courses that have planned RGs
            for (String courseToLookup : coursesToLookup) {
                if (refObjMap.containsKey(courseToLookup)) {
                    for (String rgToLookup : rgsToLookup) {
                        // Remove the course from the refObjMap if any RGs point to the same CLU
                        if (refObjMap.containsKey(rgToLookup) &&
                                refObjMap.get(rgToLookup).getCluId().equals(refObjMap.get(courseToLookup).getCluId())) {
                            refObjMap.remove(courseToLookup);
                            break;
                        }
                    }
                }
            }

            // Now we have all the ref objects for the plans... lets finish populating the data
            List<LearningPlanItemResult> lpResults = new ArrayList<>(termPlanItems.size());
            for (PlanItemInfo planItemInfo : termPlanItems) {
                if (refObjMap.containsKey(planItemInfo.getRefObjectId())) {
                    LearningPlanItemResult lpiResult = refObjMap.get(planItemInfo.getRefObjectId());
                    lpiResult.setCategory(planItemInfo.getCategory().toString());
                    lpiResult.setLearningPlanId(planItemInfo.getLearningPlanId());
                    lpiResult.setPlanItemTermId(termId);
                    lpiResult.setRefObjectId(planItemInfo.getRefObjectId());
                    lpiResult.setRefObjectType(planItemInfo.getRefObjectType());

                    lpResults.add(lpiResult);
                }
            }

            response = Response.ok(lpResults);
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     *
     * Build the LearningPlanItemResult objects for RegGroup plan items
     *
     * @param coursesToLookup List of cluVersionIndIds to lookup
     * @param contextInfo
     * @return Map<String, LearningPlanItemResult> map of refObjId (cluVerIndId) > LPIResult
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private Map<String, LearningPlanItemResult> buildLearningPlanCourseItems (List<String> coursesToLookup, String termId, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        Map<String, LearningPlanItemResult> lpiResults = new HashMap<>();

        if (!coursesToLookup.isEmpty()) {
            // Learning plan gives us the version independent id. We need to convert those into clu Ids
            List<String> cluIds = new ArrayList<>();
            for (String vid : coursesToLookup) {
                VersionDisplayInfo versionInfo = getCluService().getCurrentVersion(
                        CluServiceConstants.CLU_NAMESPACE_URI, vid, contextInfo);

                if (versionInfo != null) {
                    cluIds.add(versionInfo.getId());
                }
            }

            // Get the linked CLUs
            List<CluInfo> cluInfos = getCluService().getClusByIds(cluIds, contextInfo);

            if (cluInfos != null && !cluInfos.isEmpty()) {
                // Create the shell LPIResult objects to be returned out
                for (CluInfo cluInfo : cluInfos) {
                    String vid = cluInfo.getVersion().getVersionIndId();

                    LearningPlanItemResult lpiResult = new LearningPlanItemResult();
                    lpiResult.setRefObjectId(vid);

                    lpiResult.setCluId(cluInfo.getId());
                    lpiResult.setCourseCode(cluInfo.getOfficialIdentifier().getCode());
                    lpiResult.setState(cluInfo.getStateKey());
                    lpiResult.setLongName(cluInfo.getOfficialIdentifier().getLongName());

                    // Load up the current Course Offerings for this CLU
                    List<CourseSearchResult> courseOfferings = getScheduleOfClassesService().searchForCourseOfferingsByTermIdAndCluId(
                            termId, cluInfo.getId(), contextInfo);
                    if (courseOfferings != null) {
                        lpiResult.setCoursesOffered(courseOfferings.size());
                    }

                    lpiResults.put(vid, lpiResult);
                }
            }
        }

        return lpiResults;
    }

    /**
     * Build the LearningPlanItemResult objects for RegGroup plan items
     *
     * @param rgsToLookup List of regGroupIds to lookup
     * @param contextInfo
     * @return Map<String, LearningPlanItemResult> map of refObjId (rgId) > LPIResult
     * @throws IOException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private Map<String, LearningPlanItemResult> buildLearningPlanRegGroupItems (List<String> rgsToLookup, ContextInfo contextInfo) throws IOException, MissingParameterException, InvalidParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        Map<String, LearningPlanItemResult> lpiResults = new HashMap<>();

        if (!rgsToLookup.isEmpty()) {
            for (String rgId : rgsToLookup) {
                RegGroupSearchResult regGroupSearchResult = getScheduleOfClassesService().getRegGroup(rgId, contextInfo);
                if (regGroupSearchResult != null) {
                    // We also need the course offering for the course info
                    CourseSearchResult courseSearchResult = getScheduleOfClassesService().getCourseOfferingById(regGroupSearchResult.getCourseOfferingId(), contextInfo);
                    if (courseSearchResult != null) {
                        LearningPlanItemResult lpiResult = new LearningPlanItemResult();
                        lpiResult.setRefObjectId(rgId);

                        lpiResult.setRegGroupId(rgId);
                        lpiResult.setRegGroupCode(regGroupSearchResult.getRegGroupName());
                        lpiResult.setState(regGroupSearchResult.getRegGroupState());

                        lpiResult.setCluId(courseSearchResult.getCluId());
                        lpiResult.setCourseId(courseSearchResult.getCourseId());
                        lpiResult.setCourseCode(courseSearchResult.getCourseCode());
                        lpiResult.setCreditOptions(courseSearchResult.getCreditOptions());
                        lpiResult.setLongName(courseSearchResult.getLongName());

                        lpiResults.put(rgId, lpiResult);
                    }
                }
            }
        }

        return lpiResults;
    }

    /**
     * The academic plan service is NOT part of ENR so it should* not be injected. Instead, pull it from the GRL if
     * it exists.
     */
    private AcademicPlanService getAcademicPlanService() {
        if (academicPlanService == null) {
            academicPlanService = GlobalResourceLoader.getService(new QName(AcademicPlanServiceConstants.NAMESPACE, AcademicPlanServiceConstants.SERVICE_NAME));
        }
        return academicPlanService;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        this.academicPlanService = academicPlanService;
    }

    /**
     * The CLU service is NOT part of ENR so it should* not be injected. Instead, pull it from the GRL if
     * it exists.
     */
    public CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    protected LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    protected ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = GlobalResourceLoader.getService(ScheduleOfClassesServiceConstants.QNAME);
        }

        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    private static Response.ResponseBuilder getResponse(Response.Status status, Object entity) {
        //The request needs additional options (HTTP status 400 Bad Request)
        Response.ResponseBuilder response = Response.status(status).entity(entity);
        response.header("Access-Control-Allow-Header", "Content-Type");
        response.header("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        return response;
    }

    public KSIdentityServiceHelper getKsIdentityServiceHelper() {
        return ksIdentityServiceHelper;
    }

    public void setKsIdentityServiceHelper(KSIdentityServiceHelper ksIdentityServiceHelper) {
        this.ksIdentityServiceHelper = ksIdentityServiceHelper;
    }
}
