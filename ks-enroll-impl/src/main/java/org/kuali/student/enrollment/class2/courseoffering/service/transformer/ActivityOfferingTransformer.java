package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.krms.impl.util.KrmsRuleManagementCopyMethods;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The structure of this class should be re-evaluated after partial-colocation redesign is completed.  Compare design to
 * the approach taken in {@link CourseOfferingTransformer}, particularly notice that this transformer employs static
 * methods in lieu of instance methods.  ~Brandon Gresham, 6FEB2013
 */
public class ActivityOfferingTransformer {
    private KrmsRuleManagementCopyMethods krmsRuleManagementCopyMethods;

    /**
     * Transform a list of LuiInfos into Activity Offerings. It is the bulk version of lui2Activity transformer
     *
     * @param luiInfos                  the list of LuiInfos
     * @param lprService                the reference of LprService
     * @param schedulingService         the reference of SchedulingService
     * @param context                   information containing the principalId and locale
     *                                  information about the caller of service operation
     * @return a list of ActivityOfferingInfos
     * @throws DoesNotExistException     courseOfferingId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException luiInfos, lprService, schedulingService or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public static List<ActivityOfferingInfo> luis2AOs(List<LuiInfo> luiInfos, LprService lprService, SchedulingService schedulingService, SearchService searchService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        if (luiInfos == null || luiInfos.isEmpty())
            return new ArrayList<ActivityOfferingInfo>(0);

        List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>(luiInfos.size());
        Map<String, List<OfferingInstructorInfo>> luiToInstructorsMap = new HashMap<String, List<OfferingInstructorInfo>>();
        Map<String, OfferingInstructorInfo> lprToInstructorMap = new HashMap<String, OfferingInstructorInfo>();
        List<String> scheduleIdsWithNonTBA = new ArrayList<String>();

        List<String> luiIds = new ArrayList<String>();
        List<String> scheduleIds = new ArrayList<String>();
        for (LuiInfo luiInfo : luiInfos) {
            luiIds.add(luiInfo.getId());
            scheduleIds.addAll(luiInfo.getScheduleIds());
        }

        //Bulk load a list a lprs by a list of lui ids. Cache the results set in a map.
        List<LprInfo> lprs = lprService.getLprsByLuis(luiIds, context);
        List<OfferingInstructorInfo> coInstructors = OfferingInstructorTransformer.lprs2InstructorsBulk(lprs);

        //construct map for lpr to OfferingInstructorInfo
        for (OfferingInstructorInfo coInstructor : coInstructors) {
            lprToInstructorMap.put(coInstructor.getId(), coInstructor);
        }
        //construct map for lui to List<OfferingInstructorInfo>
        for (LprInfo lprInfo : lprs) {
            List<OfferingInstructorInfo> coInstructorList = luiToInstructorsMap.get(lprInfo.getLuiId());
            if (coInstructorList == null) {
                coInstructorList = new ArrayList<OfferingInstructorInfo>();
                luiToInstructorsMap.put(lprInfo.getLuiId(), coInstructorList);
            }
            coInstructorList.add(lprToInstructorMap.get(lprInfo.getId()));
        }

        //Bulk load a list a ScheduleInfos by a list of scheduleIds. Cache the results set in a map.
        if (!scheduleIds.isEmpty()) {
            scheduleIdsWithNonTBA = getScheduleIdsWithNonTBAComponents(scheduleIds, searchService, context);
        }

        Map<String, Boolean> ao2TBAMap = getAo2TBAMap(luiIds, searchService, context);

        for (LuiInfo luiInfo : luiInfos) {
            aoInfos.add(lui2Activity(luiInfo, luiToInstructorsMap, scheduleIdsWithNonTBA, ao2TBAMap, schedulingService, context));
        }


        return aoInfos;
    }

    /**
     * Performs a search to determine a list of AOs and the minimum TBA indicator (if at least one is not TBA then
     * the min TBA indicator will be FALSE
     * @param luiIds list of AO ids
     * @param searchService search service
     * @param context context of the call
     * @return map of AO ids to the minimum TBA indicator
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private static Map<String, Boolean> getAo2TBAMap(List<String> luiIds, SearchService searchService, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        Map<String, Boolean> ao2TBAMap = new HashMap<String, Boolean>();

        //Use search service to perform the search, passing in list of ids, and types
        SearchRequestInfo searchRequest = new SearchRequestInfo(CoreSearchServiceImpl.SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_KEY);
        searchRequest.addParam(CoreSearchServiceImpl.SearchParameters.REF_IDS, luiIds);
        searchRequest.addParam(CoreSearchServiceImpl.SearchParameters.SCH_REQ_SET_TYPE, SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        searchRequest.addParam(CoreSearchServiceImpl.SearchParameters.REF_TYPE, CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);

        SearchResultInfo searchResults = searchService.search(searchRequest,context);

        //Parse the search results and place in a map
        for(SearchResultRowInfo row: searchResults.getRows()){
            String aoId = null;
            Boolean minTBA = null;
            for(SearchResultCellInfo cell:row.getCells()){

                if(CoreSearchServiceImpl.SearchResultColumns.REF_ID.equals(cell.getKey())){
                    aoId = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.MIN_TBA.equals(cell.getKey())){
                    minTBA = Boolean.parseBoolean(cell.getValue());
                }
            }
            ao2TBAMap.put(aoId, minTBA);
        }

        return ao2TBAMap;
    }

    /**
     * Transform a LuiInfo into an Activity Offering. It takes cached maps of luiToLprsMap,
     * scheduleIdsWithNonTBA and luiToScheduleRequestsMap as the params instead of doing
     * service calls inside to retrieve lprs, ScheduleInfo and ScheduleRequestInfos
     *
     * @param lui                           the LuiInfo
     * @param luiToInstructorsMap           the cached map of luiId to OfferingInstructorInfos
     * @param scheduleIdsWithNonTBA         list of scheduleIds withNonTBA
     * @param ao2TBAMap      the list of AOids that have TBA schedule request components
     * @return an ActivityOfferingInfo
     */
    public static ActivityOfferingInfo lui2Activity(LuiInfo lui,
                                                    Map<String, List<OfferingInstructorInfo>> luiToInstructorsMap,
                                                    List<String> scheduleIdsWithNonTBA,
                                                    Map<String, Boolean> ao2TBAMap,
                                                    SchedulingService schedulingService,
                                                    ContextInfo contextInfo ) throws PermissionDeniedException, InvalidParameterException, MissingParameterException, OperationFailedException {
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ao.setId(lui.getId());
        ao.setMeta(lui.getMeta());
        ao.setStateKey(lui.getStateKey());
        ao.setTypeKey(lui.getTypeKey());
        ao.setDescr(lui.getDescr());
        ao.setName(lui.getName());
        ao.setActivityId(lui.getCluId());
        ao.setTermId(lui.getAtpId());
        ao.setMinimumEnrollment(lui.getMinimumEnrollment());
        ao.setMaximumEnrollment(lui.getMaximumEnrollment());
        ao.setScheduleIds(new ArrayList<String>(lui.getScheduleIds()));
        ao.setActivityOfferingURL(lui.getReferenceURL());
        ao.setIsColocated(isColocated(lui, schedulingService, contextInfo));

        if (lui.getOfficialIdentifier() != null){
            ao.setActivityCode(lui.getOfficialIdentifier().getCode());
        }

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = ao.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR.equals(attr.getKey())){
                ao.setIsEvaluated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR.equals(attr.getKey())){
                ao.setIsMaxEnrollmentEstimate(Boolean.valueOf(attr.getValue()));
            } else if( CourseOfferingServiceConstants.IS_AO_APPROVED_FOR_NON_STANDARD_TIME_SLOTS.equals(attr.getKey()) ) {
                ao.setIsApprovedForNonStandardTimeSlots( Boolean.valueOf(attr.getValue()) );
            } else {
                attributes.add(new AttributeInfo(attr));
            }
        }
        ao.setAttributes(attributes);

        // store honors in lu code
        LuCodeInfo luCode = findLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        if (luCode == null) {
            ao.setIsHonorsOffering(false);
        } else {
            ao.setIsHonorsOffering(Boolean.valueOf(luCode.getValue()));
        }

        // build list of OfferingInstructors
        List<OfferingInstructorInfo> instructors = luiToInstructorsMap.get(ao.getId());
        if (instructors != null && !instructors.isEmpty()) {
            ao.setInstructors(instructors);
        }
        // derive the scheduling state

        // if there is an actual schedule tied to the AO, and at least one of the components is not marked TBA, then the AO scheduling state is Scheduled
        if(!ao.getScheduleIds().isEmpty()) {
            ao.setSchedulingStateKey(getSchedulingState(ao, scheduleIdsWithNonTBA));
        }
        else {
            ao.setSchedulingStateKey(getSchedulingStateByScheduleRequest(ao, ao2TBAMap));
        }

        return ao;
    }

    public static void lui2Activity(ActivityOfferingInfo ao, LuiInfo lui, LprService lprService, SchedulingService schedulingService, SearchService searchService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ao.setId(lui.getId());
        ao.setMeta(lui.getMeta());
        ao.setStateKey(lui.getStateKey());
        ao.setTypeKey(lui.getTypeKey());
        ao.setDescr(lui.getDescr());
        ao.setName(lui.getName());
        ao.setActivityId(lui.getCluId());
        ao.setTermId(lui.getAtpId());
        ao.setMinimumEnrollment(lui.getMinimumEnrollment());
        ao.setMaximumEnrollment(lui.getMaximumEnrollment());
        ao.setScheduleIds(new ArrayList<String>(lui.getScheduleIds()));
        ao.setActivityOfferingURL(lui.getReferenceURL());
        ao.setIsColocated(isColocated(lui, schedulingService, context));

        if (lui.getOfficialIdentifier() != null){
            ao.setActivityCode(lui.getOfficialIdentifier().getCode());
        }

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = ao.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR.equals(attr.getKey())){
                ao.setIsEvaluated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR.equals(attr.getKey())){
                ao.setIsMaxEnrollmentEstimate(Boolean.valueOf(attr.getValue()));
            } else if( CourseOfferingServiceConstants.IS_AO_APPROVED_FOR_NON_STANDARD_TIME_SLOTS.equals(attr.getKey()) ) {
                ao.setIsApprovedForNonStandardTimeSlots( Boolean.valueOf(attr.getValue()) );
            } else {
                attributes.add(new AttributeInfo(attr));
            }
        }
        ao.setAttributes(attributes);

        // store honors in lu code
        LuCodeInfo luCode = findLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        if (luCode == null) {
            ao.setIsHonorsOffering(false);
        } else {
            ao.setIsHonorsOffering(Boolean.valueOf(luCode.getValue()));
        }

        // build list of OfferingInstructors
        List<LprInfo> lprs = lprService.getLprsByLui(ao.getId(), context);

        ao.setInstructors(OfferingInstructorTransformer.lprs2Instructors(lprs));

        // derive the scheduling state

        // if there is an actual schedule tied to the AO, and at least one of the components is not marked TBA, then the AO scheduling state is Scheduled
        if(!ao.getScheduleIds().isEmpty()) {
            ao.setSchedulingStateKey(getSchedulingState(ao, searchService, context));
        }
        else {
            ao.setSchedulingStateKey(getSchedulingStateByScheduleRequest(ao, searchService, context));
        }
    }

    public static void activity2Lui (ActivityOfferingInfo ao, LuiInfo lui) {
        lui.setId(ao.getId());
        lui.setTypeKey(ao.getTypeKey());
        lui.setStateKey(ao.getStateKey());
        if (ao.getName() == null) {
            String coCode = ao.getCourseOfferingCode();
            if (coCode == null) {
                coCode = "NOCODE";
            }
            lui.setName(coCode + " AO"); // Makes it easier to track in DB
        } else {
            lui.setName(ao.getName());
        }
        lui.setDescr(ao.getDescr());
        lui.setMeta(ao.getMeta());
        lui.setCluId(ao.getActivityId());
        lui.setAtpId(ao.getTermId());
        lui.setMinimumEnrollment(ao.getMinimumEnrollment());
        lui.setMaximumEnrollment(ao.getMaximumEnrollment());
        lui.setScheduleIds(new ArrayList<String>(ao.getScheduleIds()));
        lui.setReferenceURL(ao.getActivityOfferingURL());

        //Lui Official Identifier
        LuiIdentifierInfo officialIdentifier = new LuiIdentifierInfo();
        officialIdentifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        officialIdentifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        officialIdentifier.setCode(ao.getActivityCode());
        lui.setOfficialIdentifier(officialIdentifier);

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = lui.getAttributes();
        for (Attribute attr : ao.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }

        AttributeInfo isEvaluated = new AttributeInfo();
        isEvaluated.setKey(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR);
        isEvaluated.setValue(String.valueOf(ao.getIsEvaluated()));
        attributes.add(isEvaluated);

        AttributeInfo isMaxEnrollmentEstimate = new AttributeInfo();
        isMaxEnrollmentEstimate.setKey(CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR);
        isMaxEnrollmentEstimate.setValue(String.valueOf(ao.getIsMaxEnrollmentEstimate()));
        attributes.add(isMaxEnrollmentEstimate);

        AttributeInfo isApprovedForNonStandardTimeSlots = new AttributeInfo();
        isApprovedForNonStandardTimeSlots.setKey( CourseOfferingServiceConstants.IS_AO_APPROVED_FOR_NON_STANDARD_TIME_SLOTS );
        isApprovedForNonStandardTimeSlots.setValue( String.valueOf(ao.getIsApprovedForNonStandardTimeSlots()) );
        attributes.add( isApprovedForNonStandardTimeSlots );

        lui.setAttributes(attributes);

        //Honors code
        LuCodeInfo luCode = findAddLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        luCode.setValue(String.valueOf(ao.getIsHonorsOffering()));
    }

    public static LuCodeInfo findLuCode(LuiInfo lui, String typeKey) {
        for (LuCodeInfo info : lui.getLuiCodes()) {
            if (info.getTypeKey().equals(typeKey)) {
                return info;
            }
        }
        return null;
    }

    public static LuCodeInfo findAddLuCode(LuiInfo lui, String typeKey) {
        LuCodeInfo info = findLuCode(lui, typeKey);
        if (info != null) {
            return info;
        }
        info = new LuCodeInfo();
        info.setTypeKey(typeKey);
        lui.getLuiCodes().add(info);
        return info;
    }

    /**
     * Indicates whether or not this Activity Offering is involved in a colocation relationship with other AOs by
     * looking up associated ScheduleRequestSets and checking the ref object Id counts in each. If any of the counts
     * are greater than one then the AO is considered colocated.
     *
     * @param lui
     * @param schedulingService
     * @param context
     * @return
     */
    private static boolean isColocated(LuiInfo lui, SchedulingService schedulingService, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
    {
        if( lui == null ) {
            throw new NullPointerException( "lui cannot be null" );
        }
        if( schedulingService == null ) {
            throw new NullPointerException( "schedulingService cannot be null" );
        }
        if( context == null ) {
            throw new NullPointerException( "context cannot be null" );
        }

        List<ScheduleRequestSetInfo> scheduleRequestSets = schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, lui.getId(), context);
        if(scheduleRequestSets != null && !scheduleRequestSets.isEmpty()){
            for(ScheduleRequestSetInfo srs : scheduleRequestSets){
                if(srs.getRefObjectIds() != null && srs.getRefObjectIds().size() > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String getSchedulingState(ActivityOfferingInfo ao, SearchService searchService, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        if (!getScheduleIdsWithNonTBAComponents(ao.getScheduleIds(), searchService, context).isEmpty()) {
            // If there is at least one non-TBA schedule component, then this must be scheduled
            return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY;
        }

        return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY;
    }

    /**
     * Performs a search. User passes in a list of schedule ids, and the list is filtered for schedule ids that
     * are related to schedule components where at least one of the components is not TBA
     * @param scheduleIds list of schedule ids to query
     * @param searchService search service
     * @param context context of the call
     * @return list of schedule ids that do not have TBA in at least one of the components
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private static List<String> getScheduleIdsWithNonTBAComponents(List<String> scheduleIds, SearchService searchService, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String> scheduleIdsWithNonTBAComponents = new ArrayList<String>();
        SearchRequestInfo searchRequest = new SearchRequestInfo(CoreSearchServiceImpl.SCH_IDS_WITH_NON_TBA_BY_SCH_IDS_SEARCH_KEY);
        searchRequest.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, scheduleIds);
        SearchResultInfo searchResults = searchService.search(searchRequest,context);

        for(SearchResultRowInfo row: searchResults.getRows()){
            for(SearchResultCellInfo cell:row.getCells()){
                if(CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())){
                    scheduleIdsWithNonTBAComponents.add(cell.getValue());
                }
            }
        }

        return scheduleIdsWithNonTBAComponents;
    }

    private static String getSchedulingState(ActivityOfferingInfo ao, List<String> scheduleIdsWithNonTBA) {
        if(CollectionUtils.containsAny(ao.getScheduleIds(), scheduleIdsWithNonTBA)){
            return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY;
        }

        return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY;
    }

    private static String getSchedulingStateByScheduleRequest(ActivityOfferingInfo ao, SearchService searchService, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        // get the schedule request for this AO
        Map<String, Boolean> ao2TBAMap = getAo2TBAMap(Arrays.asList(ao.getId()), searchService, context);
        return getSchedulingStateByScheduleRequest(ao, ao2TBAMap);
    }

    private static String getSchedulingStateByScheduleRequest(ActivityOfferingInfo ao, Map<String, Boolean> ao2TBAMap) {
        if(ao2TBAMap.containsKey(ao.getId()) && !ao2TBAMap.get(ao.getId())) {
            // if there are requests, but at least one is not tba then it is exempt
            return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY;
        }
        //if there are no requests or they are all TBA then it is unscheduled
        return LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY;
    }

    public void copyRulesFromExistingActivityOffering(ActivityOfferingInfo sourceAo,
                                                    ActivityOfferingInfo targetAo,
                                                    List<String> optionKeys)
            throws InvalidParameterException,
            MissingParameterException,
            PermissionDeniedException,
            OperationFailedException {
        if (targetAo.getId() == null) {
            throw new InvalidParameterException("Target ActivityOffering should already have it's id assigned");
        }
        getKrmsRuleManagementCopyMethods().deepCopyReferenceObjectBindingsFromTo(
                CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                sourceAo.getId(),
                CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                targetAo.getId(),
                optionKeys);
    }

    public KrmsRuleManagementCopyMethods getKrmsRuleManagementCopyMethods() {
        return krmsRuleManagementCopyMethods;
    }

    public void setKrmsRuleManagementCopyMethods(KrmsRuleManagementCopyMethods krmsRuleManagementCopyMethods) {
        this.krmsRuleManagementCopyMethods = krmsRuleManagementCopyMethods;
    }

}
