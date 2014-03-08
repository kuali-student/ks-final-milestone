package org.kuali.student.enrollment.class2.examoffering.service.transformer;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamOfferingTransformer {

    private LuiService luiService;

    private static final Logger LOG = LoggerFactory.getLogger(ExamOfferingTransformer.class);

    /**
     * Transform a list of LuiInfos into ExamOfferingInfos. It is the bulk version of lui2ExamOffering transformer
     *
     * @param examOfferingIds the list of examOfferingIds which is used to retrieve the list of LuiInfos
     * @param eos             the reference of ExamOfferingInfo list whith points to the transformed ExamOfferingInfo list
     * @param context         information containing the principalId and locale
     *                        information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          ActivityOfferingDisplayInfo is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          courseOfferingIds, cos, stateService, or context is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    public void luis2ExamOfferings(List<String> examOfferingIds, List<ExamOfferingInfo> eos, SchedulingService schedulingService,
                                   ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (examOfferingIds == null || examOfferingIds.isEmpty()) {
            LOG.warn("invalid courseOfferingIds");
            return;
        }

        //Retrieve the luiInfos
        List<LuiInfo> luiInfos = luiService.getLuisByIds(examOfferingIds, context);

        List<String> scheduleIds = new ArrayList<String>();
        for (LuiInfo luiInfo : luiInfos) {
            scheduleIds.addAll(luiInfo.getScheduleIds());
        }

        //Bulk load a list a ScheduleInfos by a list of scheduleIds. Cache the results set in a map.
        Map<String, ScheduleInfo> scheduleIdToScheduleMap = new HashMap<String, ScheduleInfo>();
        if (!scheduleIds.isEmpty()) {
            List<ScheduleInfo> scheduleInfos = schedulingService.getSchedulesByIds(scheduleIds, context);
            for (ScheduleInfo scheduleInfo : scheduleInfos) {
                scheduleIdToScheduleMap.put(scheduleInfo.getId(), scheduleInfo);
            }
        }

        Map<String, List<ScheduleRequestInfo>> luiToScheduleRequestsMap = buildLuiToScheduleRequestsMap(examOfferingIds, schedulingService, context);

        for (LuiInfo luiInfo : luiInfos) {
            ExamOfferingInfo eo = new ExamOfferingInfo();
            lui2ExamOffering(luiInfo, eo, scheduleIdToScheduleMap, luiToScheduleRequestsMap, schedulingService, context);

            eos.add(eo);

        }
    }

    /**
     * Transform a LuiInfo into an ExamOfferingInfo.
     *
     * @param lui the LuiInfo that is transformed into ExamOfferingInfo
     * @param eo  the reference of ExamOfferingInfo that is transformed from LuiInfo
     */
    public void lui2ExamOffering(LuiInfo lui, ExamOfferingInfo eo, Map<String, ScheduleInfo> scheduleIdToScheduleMap,
                                 Map<String, List<ScheduleRequestInfo>> luiToScheduleRequestsMap,
                                 SchedulingService schedulingService, ContextInfo context) {

        eo.setId(lui.getId());
        eo.setTypeKey(lui.getTypeKey());
        eo.setStateKey(lui.getStateKey());
        eo.setDescr(lui.getDescr());
        eo.setMeta(lui.getMeta());
        eo.setName(lui.getOfficialIdentifier().getShortName());

        //These still need to be mapped
        eo.setExamPeriodId(lui.getAtpId());
        eo.setExamId(lui.getCluId());
        for (String scheduleId : lui.getScheduleIds()) {
            eo.setScheduleId(scheduleId);
            break;  //ExamOffering should only have one scheduleId.
        }

        // if there is an actual schedule tied to the AO, and at least one of the components is not marked TBA, then the AO scheduling state is Scheduled
        if (eo.getScheduleId() != null) {
            eo.setSchedulingStateKey(getSchedulingState(eo, scheduleIdToScheduleMap));
        } else {
            eo.setSchedulingStateKey(getSchedulingStateByScheduleRequest(eo, luiToScheduleRequestsMap.get(eo.getId())));
        }

        //Dynamic attributes
        List<AttributeInfo> attributes = eo.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }
        eo.setAttributes(attributes);

        return;
    }

    /**
     * Transform a LuiInfo into an ExamOfferingInfo.
     *
     * @param lui the LuiInfo that is transformed into ExamOfferingInfo
     * @param eo  the reference of ExamOfferingInfo that is transformed from LuiInfo
     */
    public void lui2ExamOffering(LuiInfo lui, ExamOfferingInfo eo, SchedulingService schedulingService, ContextInfo context) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException {

        eo.setId(lui.getId());
        eo.setTypeKey(lui.getTypeKey());
        eo.setStateKey(lui.getStateKey());
        eo.setDescr(lui.getDescr());
        eo.setMeta(lui.getMeta());
        eo.setName(lui.getOfficialIdentifier().getShortName());

        //These still need to be mapped
        eo.setExamPeriodId(lui.getAtpId());
        eo.setExamId(lui.getCluId());
        for (String scheduleId : lui.getScheduleIds()) {
            eo.setScheduleId(scheduleId);
            break;  //ExamOffering should only have one scheduleId.
        }

        // if there is an actual schedule tied to the EO, and at least one of the components is not marked TBA, then the EO scheduling state is Scheduled
        if (eo.getScheduleId() != null) {
            try {
                eo.setSchedulingStateKey(getSchedulingState(eo, schedulingService, context));
            } catch (DoesNotExistException e) {
                throw new OperationFailedException("Unable the retrieve scheduling state.", e);
            }
        } else {
            eo.setSchedulingStateKey(getSchedulingStateByScheduleRequest(eo, schedulingService, context));
        }

        //Dynamic attributes
        List<AttributeInfo> attributes = eo.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }
        eo.setAttributes(attributes);

        return;
    }

    public void examOffering2Lui(ExamOfferingInfo eo, LuiInfo lui, ContextInfo context) {

        lui.setId(eo.getId());
        lui.setTypeKey(eo.getTypeKey());
        lui.setStateKey(eo.getStateKey());
        lui.setDescr(eo.getDescr());
        lui.setMeta(eo.getMeta());

        //These still need to be mapped
        lui.setAtpId(eo.getExamPeriodId());
        lui.setCluId(eo.getExamId());

        lui.getScheduleIds().clear();
        lui.getScheduleIds().add(eo.getScheduleId());

        LuiIdentifierInfo luiIdentifierInfo = lui.getOfficialIdentifier();
        if(luiIdentifierInfo == null){
            luiIdentifierInfo = new LuiIdentifierInfo();
            lui.setOfficialIdentifier(luiIdentifierInfo);
        }
        luiIdentifierInfo.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        luiIdentifierInfo.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        luiIdentifierInfo.setShortName(eo.getName());
        lui.setAttributes(eo.getAttributes());

    }

    private static String getSchedulingState(ExamOfferingInfo eo, SchedulingService schedulingService, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        ScheduleInfo schedule = schedulingService.getSchedule(eo.getScheduleId(), context);

        for (ScheduleComponentInfo componentInfo : schedule.getScheduleComponents()) {
            if (!componentInfo.getIsTBA()) {
                return null; //Should be SCHEDULED_STATE_KEY
            }
        }

        return ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY;
    }

    private static String getSchedulingState(ExamOfferingInfo eo, Map<String, ScheduleInfo> scheduleIdToScheduleMap) {
        ScheduleInfo schedule = scheduleIdToScheduleMap.get(eo.getScheduleId());

        for (ScheduleComponentInfo componentInfo : schedule.getScheduleComponents()) {
            if (!componentInfo.getIsTBA()) {
                return null; //Should be SCHEDULED_STATE_KEY
            }
        }

        return ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY;
    }

    private static String getSchedulingStateByScheduleRequest(ExamOfferingInfo eo, SchedulingService schedulingService, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        // get the schedule request for this AO
        List<ScheduleRequestInfo> requests = schedulingService.getScheduleRequestsByRefObject(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, eo.getId(), context);

        return getSchedulingStateByScheduleRequest(eo, requests);
    }

    private static String getSchedulingStateByScheduleRequest(ExamOfferingInfo eo, List<ScheduleRequestInfo> requests) {
        if (requests == null || requests.isEmpty()) {
            // if there are no requests, the EO scheduling state is Unscheduled
            return ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY;
        }

        for (ScheduleRequestInfo request : requests) {
            // if all the schedule request components are set as TBA, the EO scheduling state is Exempt
            // otherwise, it's Unscheduled
            for (ScheduleRequestComponentInfo reqComp : request.getScheduleRequestComponents()) {
                if (!reqComp.getIsTBA()) {
                    return ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY;
                }
            }
        }

        return ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY;
    }

    /*Bulk load a list a ScheduleRequestInfo objects and return the results set in a Map of ActivityOffering ids to a list of ScheduleRequestInfo objects.*/
    private static Map<String, List<ScheduleRequestInfo>> buildLuiToScheduleRequestsMap(List<String> luiIds, SchedulingService schedulingService, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        Map<String, List<ScheduleRequestInfo>> luiToScheduleRequestsMap = new HashMap<String, List<ScheduleRequestInfo>>();

        if(luiIds != null && !luiIds.isEmpty()){
            for(String luiId: luiIds){
                List<ScheduleRequestInfo> scheduleRequestInfos = schedulingService.getScheduleRequestsByRefObject(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, luiId, context);
                if(scheduleRequestInfos != null && !scheduleRequestInfos.isEmpty()){
                    List<ScheduleRequestInfo> scheduleRequestInfoList = luiToScheduleRequestsMap.get(luiId);
                    if (scheduleRequestInfoList == null) {
                        scheduleRequestInfoList = new ArrayList<ScheduleRequestInfo>();
                        luiToScheduleRequestsMap.put(luiId, scheduleRequestInfoList);
                    }
                    scheduleRequestInfoList.addAll(scheduleRequestInfos);
                }
            }
        }

        return luiToScheduleRequestsMap;
    }

    public void transformEORel2LuiLuiRel(ExamOfferingRelationInfo examOfferingRelationInfo, LuiLuiRelationInfo luiRel, String examOfferingTypeKey){
        if (examOfferingRelationInfo.getId() != null && !examOfferingRelationInfo.getId().equals("")) {
            luiRel.setId(examOfferingRelationInfo.getId());
        }
        String examOfferingId = examOfferingRelationInfo.getExamOfferingId();
        luiRel.setLuiId(examOfferingRelationInfo.getFormatOfferingId());
        luiRel.setRelatedLuiId(examOfferingId);
        RichTextInfo descr = new RichTextInfo();
        luiRel.setName("Fo-Eo-relation");
        descr.setPlain(examOfferingId + "-FO-EO"); // Useful for debugging
        descr.setFormatted(examOfferingId + "-FO-EO)"); // Useful for debugging
        luiRel.setDescr(descr);
        luiRel.setTypeKey(examOfferingTypeKey);
        luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        luiRel.setEffectiveDate(new Date());
        //store AO IDs as attributes
        List<AttributeInfo> attributeInfos = new ArrayList<AttributeInfo>();
        int i = 1; //unique key
        for (String aoId : examOfferingRelationInfo.getActivityOfferingIds()){
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setKey("AO"+i);
            attributeInfo.setValue(aoId);
            attributeInfos.add(attributeInfo);
            i++;
        }
        //store Population IDs as attributes
        i = 1;
        for (String aoId : examOfferingRelationInfo.getPopulationIds()){
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setKey("PO"+i);
            attributeInfo.setValue(aoId);
            attributeInfos.add(attributeInfo);
            i++;
        }
        luiRel.setAttributes(attributeInfos);
    }

    public void transformLuiLuiRel2EORel(LuiLuiRelationInfo luiLuiRelationInfo, ExamOfferingRelationInfo examOfferingRelationInfo){
        List<String> aoIds = new ArrayList<String>();
        List<String> populationIds = new ArrayList<String>();
        examOfferingRelationInfo.setId(luiLuiRelationInfo.getId()); // = examOfferingRelationId
        examOfferingRelationInfo.setExamOfferingId(luiLuiRelationInfo.getRelatedLuiId());
        examOfferingRelationInfo.setFormatOfferingId(luiLuiRelationInfo.getLuiId());
        for (AttributeInfo attributeInfo : luiLuiRelationInfo.getAttributes()){
            if(attributeInfo.getKey().contains("AO")){
                aoIds.add(attributeInfo.getValue());
            } else if (attributeInfo.getKey().contains("PO")){
                populationIds.add(attributeInfo.getValue());
            }
        }
        examOfferingRelationInfo.setActivityOfferingIds(aoIds);
        examOfferingRelationInfo.setPopulationIds(populationIds);
    }

    public LuiService getLuiService() {
        if (luiService == null) {
            luiService = GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

}
