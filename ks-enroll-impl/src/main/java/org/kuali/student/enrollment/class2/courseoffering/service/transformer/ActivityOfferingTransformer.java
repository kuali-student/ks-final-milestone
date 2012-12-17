package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityOfferingTransformer {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ActivityOfferingTransformer.class);
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
    public static List<ActivityOfferingInfo> luis2AOs(List<LuiInfo> luiInfos, LprService lprService, SchedulingService schedulingService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        if (luiInfos == null || luiInfos.isEmpty())
            return new ArrayList<ActivityOfferingInfo>(0);

        List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>(luiInfos.size());
        Map<String, List<OfferingInstructorInfo>> luiToInstructorsMap = new HashMap<String, List<OfferingInstructorInfo>>();
        Map<String, List<LprInfo>> luiToLprsMap = new HashMap<String, List<LprInfo>>();
        Map<String, OfferingInstructorInfo> lprToInstructorMap = new HashMap<String, OfferingInstructorInfo>();
        Map<String, ScheduleInfo> scheduleIdToScheduleMap = new HashMap<String, ScheduleInfo>();
        Map<String, List<ScheduleRequestInfo>> luiToScheduleRequestsMap = new HashMap<String, List<ScheduleRequestInfo>>();

        List<String> luiIds = new ArrayList<String>();
        List<String> scheduleIds = new ArrayList<String>();
        for (LuiInfo luiInfo : luiInfos) {
            luiIds.add(luiInfo.getId());
            if (luiInfo.getScheduleId() != null) {
                scheduleIds.add(luiInfo.getScheduleId());
            }
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
        if (scheduleIds != null && !scheduleIds.isEmpty()) {
            List<ScheduleInfo> scheduleInfos = schedulingService.getSchedulesByIds(scheduleIds, context);
            for (ScheduleInfo scheduleInfo : scheduleInfos) {
                scheduleIdToScheduleMap.put(scheduleInfo.getId(), scheduleInfo);
            }
        }

        //Bulk load a list a ScheduleRequestInfos by a list of luiIds. Cache the results set in a map.
        List<ScheduleRequestInfo> scheduleRequestInfos = schedulingService.getScheduleRequestsByRefObjects(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, luiIds, context);
        if (scheduleRequestInfos != null && !scheduleRequestInfos.isEmpty()) {
            for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos) {
                List<ScheduleRequestInfo> scheduleRequestInfoList = luiToScheduleRequestsMap.get(scheduleRequestInfo.getRefObjectId());
                if (scheduleRequestInfoList == null) {
                    scheduleRequestInfoList = new ArrayList<ScheduleRequestInfo>();
                    luiToScheduleRequestsMap.put(scheduleRequestInfo.getRefObjectId(), scheduleRequestInfoList);
                }
                scheduleRequestInfoList.add(scheduleRequestInfo);
            }
        }

        for (LuiInfo luiInfo : luiInfos) {
            aoInfos.add(lui2Activity(luiInfo, luiToInstructorsMap, scheduleIdToScheduleMap, luiToScheduleRequestsMap));
        }


        return aoInfos;
    }

    /**
     * Transform a LuiInfo into an Activity Offering. It takes cached maps of luiToLprsMap,
     * scheduleIdToScheduleMap and luiToScheduleRequestsMap as the params instead of doing
     * service calls inside to retrieve lprs, ScheduleInfo and ScheduleRequestInfos
     *
     * @param lui                           the LuiInfo
     * @param luiToInstructorsMap           the cached map of luiId to OfferingInstructorInfos
     * @param scheduleIdToScheduleMap       the cached map of scheduleId to ScheduleInfo
     * @param luiToScheduleRequestsMap      the cached map of luiId to ScheduleRequestInfos
     * @return an ActivityOfferingInfo
     */
    public static ActivityOfferingInfo lui2Activity(LuiInfo lui,
                                                    Map<String, List<OfferingInstructorInfo>> luiToInstructorsMap,
                                                    Map<String, ScheduleInfo> scheduleIdToScheduleMap,
                                                    Map<String, List<ScheduleRequestInfo>> luiToScheduleRequestsMap) {
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
        ao.setScheduleId(lui.getScheduleId());
        ao.setActivityOfferingURL(lui.getReferenceURL());

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
        if(ao.getScheduleId() != null) {

            ScheduleInfo schedule = scheduleIdToScheduleMap.get(ao.getScheduleId());

            boolean atLeastOneNonTBA = false;
            for (ScheduleComponentInfo componentInfo : schedule.getScheduleComponents()) {
                if (!componentInfo.getIsTBA()) {
                    atLeastOneNonTBA = true;
                    break;
                }
            }

            // if all the schedule components are set as TBA, the AO scheduling state is Scheduled
            // otherwise, it's Unscheduled
            if (atLeastOneNonTBA) {
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY);
            } else {
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY);
            }
        }
        else {
            // get the schedule request for this AO
            List<ScheduleRequestInfo> requests = luiToScheduleRequestsMap.get(ao.getId());

            if(requests == null || requests.isEmpty()) {
                // if there are no requests, the AO scheduling state is Unscheduled
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
            }
            else {
                // Should not be more than one request, grab the first one only
                ScheduleRequestInfo request = requests.get(0);

                // if all the schedule request components are set as TBA, the AO scheduling state is Exempt
                // otherwise, it's Unscheduled
                boolean atLeastOneNonTBA = false;
                for (ScheduleRequestComponentInfo reqComp : request.getScheduleRequestComponents()) {
                    if(!reqComp.getIsTBA()) {
                        atLeastOneNonTBA = true;
                        break;
                    }
                }

                if(atLeastOneNonTBA) {
                    ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
                }
                else {
                    ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY);
                }
            }
        }

        return ao;
    }

    public static void lui2Activity(ActivityOfferingInfo ao, LuiInfo lui, LprService lprService, SchedulingService schedulingService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
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
        ao.setScheduleId(lui.getScheduleId());
        ao.setActivityOfferingURL(lui.getReferenceURL());

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
        if(ao.getScheduleId() != null) {

            ScheduleInfo schedule = schedulingService.getSchedule(ao.getScheduleId(), context);

            boolean atLeastOneNonTBA = false;
            for (ScheduleComponentInfo componentInfo : schedule.getScheduleComponents()) {
                if (!componentInfo.getIsTBA()) {
                    atLeastOneNonTBA = true;
                    break;
                }
            }

            // if all the schedule components are set as TBA, the AO scheduling state is Scheduled
            // otherwise, it's Unscheduled
            if (atLeastOneNonTBA) {
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY);
            } else {
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY);
            }
        }
        else {
            // get the schedule request for this AO
            List<ScheduleRequestInfo> requests = schedulingService.getScheduleRequestsByRefObject(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, ao.getId(), context);

            if(requests.isEmpty()) {
                // if there are no requests, the AO scheduling state is Unscheduled
                ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
            }
            else {
                // Should not be more than one request, grab the first one only
                ScheduleRequestInfo request = requests.get(0);

                // if all the schedule request components are set as TBA, the AO scheduling state is Exempt
                // otherwise, it's Unscheduled
                boolean atLeastOneNonTBA = false;
                for (ScheduleRequestComponentInfo reqComp : request.getScheduleRequestComponents()) {
                    if(!reqComp.getIsTBA()) {
                        atLeastOneNonTBA = true;
                        break;
                    }
                }

                if(atLeastOneNonTBA) {
                    ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
                }
                else {
                    ao.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY);
                }
            }
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
        lui.setScheduleId(ao.getScheduleId());
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

        lui.setAttributes(attributes);

        //Honors code
        LuCodeInfo luCode = findAddLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        luCode.setValue(String.valueOf(ao.getIsHonorsOffering()));
    }


    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        // For instructors we should be using the KIM Principal ID NOT the Entity ID
        searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_ID, personId);
        return getPersonService().findPeople(searchCriteria);
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
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

}
