/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupCodeGeneratorFactory;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * @author nwright
 */
public class CourseOfferingServiceBusinessLogicImpl implements CourseOfferingServiceBusinessLogic {

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingServiceBusinessLogicImpl.class);

    @Resource
    private CourseService courseService;

    @Resource
    private AcademicCalendarService acalService;

    @Resource
    private CourseOfferingService coService;

    @Resource
    private RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory;

    @Resource
    private SchedulingService schedulingService;

    @Resource
    private RoomService roomService;

    @Resource
    private CourseOfferingTransformer courseOfferingTransformer;

    @Resource
    private AcademicCalendarServiceFacade acalServiceFacade;

    @Resource
    private RolloverAssist rolloverAssist;

    public RolloverAssist getRolloverAssist() {
        return rolloverAssist;
    }

    public void setRolloverAssist(RolloverAssist rolloverAssist) {
        this.rolloverAssist = rolloverAssist;
    }

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setAcalServiceFacade(AcademicCalendarServiceFacade acalServiceFacade) {
        this.acalServiceFacade = acalServiceFacade;
    }

    public AcademicCalendarServiceFacade getAcalServiceFacade() {
        return acalServiceFacade;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public RegistrationGroupCodeGeneratorFactory getRegistrationCodeGeneratorFactory() {
        return registrationCodeGeneratorFactory;
    }

    public void setRegistrationCodeGeneratorFactory(RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory) {
        this.registrationCodeGeneratorFactory = registrationCodeGeneratorFactory;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public CourseOfferingTransformer getCourseOfferingTransformer() {
        return courseOfferingTransformer;
    }

    public void setCourseOfferingTransformer(CourseOfferingTransformer courseOfferingTransformer) {
        this.courseOfferingTransformer = courseOfferingTransformer;
    }

    
    
    /**
     * Initializes services, if needed
     */
    private void _initServices() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (schedulingService == null) {
            schedulingService = (SchedulingService) GlobalResourceLoader.getService(new QName(SchedulingServiceConstants.NAMESPACE,
                    SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (roomService == null){
            roomService = (RoomService) GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (acalService == null){
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (acalServiceFacade == null) {
            acalServiceFacade = (AcademicCalendarServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acalServiceFacade", "AcademicCalendarServiceFacade"));
        }

        if (rolloverAssist == null) {
            // KSENROLL-8062 Rollover with colocation
            rolloverAssist = (RolloverAssist) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/rolloverAssist", "RolloverAssist"));
        }
    }

    private ActivityOfferingInfo _RCO_createTargetActivityOffering(ActivityOfferingInfo sourceAo, FormatOfferingInfo targetFo,
                                                                   String targetTermId, List<String> optionKeys,
                                                                   ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        ActivityOfferingInfo targetAo = new ActivityOfferingInfo(sourceAo);
        targetAo.setId(null);
        // clear out the ids on the internal sub-objects
        for (AttributeInfo attr : targetAo.getAttributes()) {
            attr.setId(null);
        }
        for (OfferingInstructorInfo instr : targetAo.getInstructors()) {
            instr.setId(null);
        }
        targetAo.setFormatOfferingId(targetFo.getId());
        targetAo.setTermId(targetTermId);
        TermInfo termInfo = acalService.getTerm(targetTermId, context);
        targetAo.setTermCode(termInfo.getCode());
        targetAo.setMeta(null);
        targetAo.setActivityCode(sourceAo.getActivityCode());
        targetAo.setScheduleIds( Collections.EMPTY_LIST );  // target should have no ADLs

        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetAo.getInstructors().clear();
        }
        // Rolled over AO should be in draft state
        targetAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        // The temp context will signal the services to skip over validation of activity offering code
        // TODO: To be removed once COSI is fixed
        ContextInfo tempContext = new ContextInfo(context);
        List<AttributeInfo> attrs = new ArrayList<AttributeInfo>();
        AttributeInfo info = new AttributeInfo("skip.aocode.validation", "true");
        attrs.add(info);
        tempContext.setAttributes(attrs);
        targetAo = coService.createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                targetAo.getTypeKey(), targetAo, tempContext);
        return targetAo;
    }

    private ScheduleRequestSetInfo _RCO_createScheduleRequestSet(String targetAoId, ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        ScheduleRequestSetInfo requestSetToSchedule = new ScheduleRequestSetInfo();
        requestSetToSchedule.setTypeKey( SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET );
        requestSetToSchedule.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        requestSetToSchedule.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        // Create a list containing one AO ID
        List<String> targetAoIds = new ArrayList<String>();
        targetAoIds.add(targetAoId);
        // Then add it to the SRS
        requestSetToSchedule.setRefObjectIds(targetAoIds);
        requestSetToSchedule =
                schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                        CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        requestSetToSchedule, context);
        return requestSetToSchedule;
    }

    private void _RCO_createTargetScheduleRequestsFromScheduleIds(List<String> sourceScheduleIds,
                                                                  ActivityOfferingInfo targetAo,
                                                                  String targetScheduleRequestSetId,
                                                                  ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException, ReadOnlyException,
                   DataValidationErrorException {

        for (String sourceSchedId : sourceScheduleIds) {
            // copy source SRCs to target
            ScheduleInfo sourceSchedule = schedulingService.getSchedule(sourceSchedId, context);
            ScheduleRequestInfo targetSchedRequest = SchedulingServiceUtil.scheduleToRequest( sourceSchedule, roomService, context );

            // set name & descr on target
            StringBuilder nameBuilder = new StringBuilder("Schedule request for ");
            nameBuilder.append(targetAo.getCourseOfferingCode()).append(" - ").append(targetAo.getActivityCode());
            targetSchedRequest.setName(nameBuilder.toString());
            targetSchedRequest.setDescr(sourceSchedule.getDescr());

            // create the target SR
            targetSchedRequest.setScheduleRequestSetId(targetScheduleRequestSetId);
            schedulingService.createScheduleRequest(targetSchedRequest.getTypeKey(), targetSchedRequest, context);
        }
    }

    private void _RCO_createTargetScheduleRequestsFromSourceRequests(List<ScheduleRequestInfo> sourceSchedRequests,
                                                                     ActivityOfferingInfo targetAo,
                                                                     String targetScheduleRequestSetId,
                                                                     ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException {

        for (ScheduleRequestInfo sourceSchedRequest : sourceSchedRequests) {
            // copy source SRCs to target
            ScheduleRequestInfo targetSchedRequest = SchedulingServiceUtil.scheduleRequestToScheduleRequest( sourceSchedRequest, context );

            // set name & descr on target
            StringBuilder nameBuilder = new StringBuilder("Schedule request for ");
            nameBuilder.append(targetAo.getCourseOfferingCode()).append(" - ").append(targetAo.getActivityCode());
            targetSchedRequest.setName(nameBuilder.toString());
            targetSchedRequest.setDescr(sourceSchedRequest.getDescr());

            // create the target SR
            targetSchedRequest.setScheduleRequestSetId(targetScheduleRequestSetId);
            schedulingService.createScheduleRequest(targetSchedRequest.getTypeKey(), targetSchedRequest, context);
        }
    }

    /**
     * Rolling over schedule IDs in the source AO as schedule requests in the target AO.
     * rolloverId is used to handle colocation.
     * @param rolloverId Used to access a mapping of source schedule request to a target
     *                   schedule request.  Needed to assist in colocation of rollover
     *                   or copy CO in same term.
     * @param doColocate if true, use the rollover assist.  The effect is
     *                   to keep colocation.  If false, it breaks colocation.
     *                   Breaking colo done when copying CO from a different term.
     */
    private void _RCO_rolloverSrcSchedsToTargetSchedReqs(ActivityOfferingInfo sourceAo,
                                                         ActivityOfferingInfo targetAo,
                                                         String rolloverId,
                                                         boolean doColocate,
                                                         boolean sourceTermSameAsTarget,
                                                         ContextInfo context)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
                   DoesNotExistException, ReadOnlyException, PermissionDeniedException,
                   OperationFailedException {
        // Note: There has already been a check to see if the sourceAo has a valid scheduleId.  If it doesn't,
        //       it's handled by a different private method which copies just the RDLs
        // KSENROLL-8062 Find the SRS from the source term
        List<ScheduleRequestSetInfo> srSets =
                schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                sourceAo.getId(), context);
        for (ScheduleRequestSetInfo sourceSRSet: srSets) {
            String targetSetId = null;
            if (doColocate && sourceTermSameAsTarget) {
                if (sourceSRSet.getRefObjectIds().size() > 1) { // Avoids co-location if size is 1
                    targetSetId = sourceSRSet.getId(); // Reuse the source schedule request set id
                }
            } else if (doColocate) {
                targetSetId = rolloverAssist.getTargetSRSId(rolloverId, sourceSRSet.getId());
            }
            if (targetSetId != null) {
                // A target schedule request set already exists.  Just add the AO id to the co-located
                // and update
                _RCO_addAoIdToTargetSRSAndUpdate(targetSetId, targetAo, context);
            } else {
                ScheduleRequestSetInfo targetScheduleRequestSet =
                        _RCO_createScheduleRequestSet(targetAo.getId(), context);
                // Use rollover assist to set the mapping between the source
                if (doColocate) {
                    rolloverAssist.mapSourceSRSIdToTargetSRSId(rolloverId, sourceSRSet.getId(), targetScheduleRequestSet.getId());
                }
                _RCO_createTargetScheduleRequestsFromScheduleIds(sourceAo.getScheduleIds(), targetAo,
                        targetScheduleRequestSet.getId(), context);
            }
        }
    }

    // Used in both_RCO_rolloverScheduleToScheduleRequest and _RCO_rolloverSrcSchedReqsToTargetSchedReqs
    private void _RCO_addAoIdToTargetSRSAndUpdate(String targetSetId, ActivityOfferingInfo targetAo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        ScheduleRequestSetInfo targetSet =
                schedulingService.getScheduleRequestSet(targetSetId, context);
        List<String> aoIds = targetSet.getRefObjectIds();
        if (!aoIds.contains(targetAo.getId())) {
            aoIds.add(targetAo.getId());
            // Save co-located info
            try {
                ScheduleRequestSetInfo result =
                    schedulingService.updateScheduleRequestSet(targetSet.getId(), targetSet, context);
            } catch (VersionMismatchException e) {
                // Re-wrap exception
                throw new OperationFailedException(e.getMessage());
            }
        } else {
            // Means we've somehow rolled this AO before...
            LOGGER.warn("In rolloverCO, target AO ID=" + targetAo.getId() + " seen before--should not happen");
        }
    }

    // Also see _RCO_rolloverSrcSchedsToTargetSchedReqs
    private void _RCO_rolloverSrcSchedReqsToTargetSchedReqs(ActivityOfferingInfo sourceAo,
                                                            ActivityOfferingInfo targetAo,
                                                            String rolloverId,
                                                            boolean doColocate,
                                                            boolean sourceTermSameAsTarget,
                                                            ContextInfo context)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
            DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        // KSENROLL-8062 Find the SRS from the source term
        List<ScheduleRequestSetInfo> srSets =
                schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        sourceAo.getId(), context);
        for (ScheduleRequestSetInfo sourceSRSet: srSets) {
            String targetSetId = null; // If this stays null, forces the target SRS to only have one AO ID
            if (doColocate && sourceTermSameAsTarget) {
                if (sourceSRSet.getRefObjectIds().size() > 1) { // Avoids co-location if size is 1
                    targetSetId = sourceSRSet.getId(); // Reuse the source schedule request set id
                }
            } else if (doColocate) {
                targetSetId = rolloverAssist.getTargetSRSId(rolloverId, sourceSRSet.getId());
            }

            if (targetSetId != null) {
                _RCO_addAoIdToTargetSRSAndUpdate(targetSetId, targetAo, context);
            } else {
                List<ScheduleRequestInfo> sourceRequests =
                        schedulingService.getScheduleRequestsByScheduleRequestSet(sourceSRSet.getId(), context);

                ScheduleRequestSetInfo targetScheduleRequestSet =
                        _RCO_createScheduleRequestSet(targetAo.getId(), context);
                // Use rollover assist to set the mapping between the source
                if (doColocate) {
                    rolloverAssist.mapSourceSRSIdToTargetSRSId(rolloverId, sourceSRSet.getId(), targetScheduleRequestSet.getId());
                }
                _RCO_createTargetScheduleRequestsFromSourceRequests(sourceRequests, targetAo,
                        targetScheduleRequestSet.getId(), context);
            }
        }
    }

    private void _RCO_rolloverSeatpools(ActivityOfferingInfo sourceAo, ActivityOfferingInfo targetAo, ContextInfo context) {
        //attach SPs to the AO created
        try {
            List<SeatPoolDefinitionInfo> sourceSPList = coService.getSeatPoolDefinitionsForActivityOffering(sourceAo.getId(), context);
            if (sourceSPList != null && !sourceSPList.isEmpty()) {
                for (SeatPoolDefinitionInfo sourceSP : sourceSPList) {
                    SeatPoolDefinitionInfo targetSP = new SeatPoolDefinitionInfo(sourceSP);
                    targetSP.setId(null);
                    targetSP.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
                    targetSP.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);
                    SeatPoolDefinitionInfo seatPoolCreated = coService.createSeatPoolDefinition(targetSP, context);
                    coService.addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(), targetAo.getId(), context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FormatOfferingInfo _RCO_createTargetFormatOffering(FormatOfferingInfo sourceFo, CourseOfferingInfo targetCo, String targetTermId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        FormatOfferingInfo targetFo = new FormatOfferingInfo(sourceFo);
        targetFo.setId(null);
        // clear out the ids on the internal sub-objects
        for (AttributeInfo attr : targetFo.getAttributes()) {
            attr.setId(null);
        }
        targetFo.setCourseOfferingId(targetCo.getId());
        targetFo.setTermId(targetTermId);
        targetFo.setMeta(null);

        // Rolled over FO should be in planned state
        targetFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        targetFo = this.getCoService().createFormatOffering(targetFo.getCourseOfferingId(), targetFo.getFormatId(),
                targetFo.getTypeKey(), targetFo, context);
        return targetFo;
    }

    private Map<String, List<ActivityOfferingInfo>> _prefetchAOs(List<FormatOfferingInfo> fos, CourseOfferingInfo sourceCo,
                                                                 ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        Map<String, List<ActivityOfferingInfo>> foIdsToAOList = new HashMap<String, List<ActivityOfferingInfo>>();
        for (FormatOfferingInfo sourceFo: fos) {
            // Pass in some context attributes so these values don't need to be looked up again
            List<AttributeInfo> originalContextAttributes = context.getAttributes();
            List<AttributeInfo> newContextAttributes = new ArrayList<AttributeInfo>(originalContextAttributes);
            newContextAttributes.add(new AttributeInfo("FOId", sourceFo.getId()));
            newContextAttributes.add(new AttributeInfo("FOShortName", sourceFo.getShortName()));
            newContextAttributes.add(new AttributeInfo("COId", sourceCo.getId()));
            newContextAttributes.add(new AttributeInfo("COCode", sourceCo.getCourseCode()));
            newContextAttributes.add(new AttributeInfo("COLongName", sourceCo.getCourseOfferingTitle()));
            context.setAttributes(newContextAttributes);

            // Make the call with the additional contextAttributes
            List<ActivityOfferingInfo> aos = this.getCoService().getActivityOfferingsByFormatOffering(sourceFo.getId(), context);

            // Reset the attributes to avoid side affects
            context.setAttributes(originalContextAttributes);

            foIdsToAOList.put(sourceFo.getId(), aos);
        }
        return foIdsToAOList;
    }

    /**
     * Assumes target term is "official" as are its subterms
     * @param foIdsToAoList A map of Format Offering ID to a list of AO infos associated with the FO
     * @param sourceTermId The source term ID of the rollover
     * @param targetTermId The target tern ID of the rollover
     * @param sourceTermIdToTargetTermId Assumes an empty Map<String, String> object.  Filled in as part of
     *               the method from sourceTerm ID to targetTerm Id.
     * @param contextInfo
     * @return true, if the target term has the required subterms
     */
    private boolean
    _createSourceTermIdToTargetTermIdMapping(Map<String, List<ActivityOfferingInfo>> foIdsToAoList,
                                 String sourceTermId, String targetTermId,
                                 Map<String, String> sourceTermIdToTargetTermId,
                                 ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        sourceTermIdToTargetTermId.clear();
        // Note: even if sourceTermId and targetTermId are the same, we still need to create the map
        //       for sourceTermIdToTargetTermId
        Map<String, String> sourceTermTypeToTermId = new HashMap<String, String>(); // Assumes unique subterm types per term type
        TermInfo sourceTerm = acalService.getTerm(sourceTermId, contextInfo);
        sourceTermTypeToTermId.put(sourceTerm.getTypeKey(), sourceTermId);
        // Scan through AOs for subterm IDs
        for (String foId: foIdsToAoList.keySet()) {
            List<ActivityOfferingInfo> aos = foIdsToAoList.get(foId);
            for (ActivityOfferingInfo ao: aos) {
                String termId = ao.getTermId();
                if (sourceTermTypeToTermId.containsValue(termId) || termId.equals(sourceTermId)) {
                    continue;  // Skip it, we've seen this termID before
                }
                TermInfo termInfo = acalService.getTerm(termId, contextInfo);
                String termType = termInfo.getTypeKey();
                sourceTermTypeToTermId.put(termType, termId);
            }
        }
        if (sourceTermTypeToTermId.size() == 1) { // Found no subterms, so we're good (size 1 is parent term)
            return true;
        }
        // Now verify the term types (only go one level deep since, currently, subterms don't
        // have subterms).
        TermInfo targetTerm = acalService.getTerm(targetTermId, contextInfo);
        // Put in the parent source/target term IDs
        sourceTermIdToTargetTermId.put(sourceTermTypeToTermId.get(targetTerm.getTypeKey()), targetTerm.getId());
        List<TermInfo> targetSubterms = acalService.getIncludedTermsInTerm(targetTermId, contextInfo);
        for (TermInfo term: targetSubterms) {
            String sourceTermIdLocal = sourceTermTypeToTermId.get(term.getTypeKey());
            if (sourceTermIdLocal != null) {
                sourceTermIdToTargetTermId.put(sourceTermIdLocal, term.getId()); // Map source term ID to target term ID
            }
        }
        // Valid only if the number of source terms equals the number of target terms and there
        // is a 1-1 mapping
        return sourceTermIdToTargetTermId.size() == sourceTermTypeToTermId.size();
    }


    private String _verifyTermsOfficial(Map<String,String> sourceTermIdToTargetTermId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        for (String targetTermId: sourceTermIdToTargetTermId.values()) {
            TermInfo termInfo = acalService.getTerm(targetTermId, contextInfo);
            if (!termInfo.getStateKey().equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
                return termInfo.getId();
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCoId,
                                                            String targetTermId,
                                                            List<String> optionKeys,
                                                            ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        _initServices();
        // Need to detect
        TermInfo targetTerm = acalService.getTerm(targetTermId, context);
        boolean sourceTermSameAsTarget, isPartOfRolloverSoc = false;

        String rolloverId = context.getAttributeValue(CourseOfferingSetServiceConstants.ROLLOVER_ASSIST_ID_DYNATTR_KEY);
        if (rolloverId == null) {
            // Happens if we aren't doing a rolloverSoc
            rolloverId = rolloverAssist.getRolloverId(); // Create one just for this CO rollover
        } else {
            // Assume we are doing rollover if this ID is being passed.
            isPartOfRolloverSoc = true;
        }
        CourseOfferingInfo sourceCo = coService.getCourseOffering(sourceCoId, context);
        // Determine if source/target term is same
        sourceTermSameAsTarget = sourceCo.getTermId().equals(targetTerm.getId());

        if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_OPTION_KEY)) {
            throw new DataValidationErrorException("Skipped because course offering was cancelled in source term");
        }
        R1CourseServiceHelper helper = new R1CourseServiceHelper(courseService, acalService);

        CourseInfo sourceCourse = helper.getCourse(sourceCo.getCourseId());
        String sourceCourseId = sourceCourse.getId();
        List<CourseInfo> targetCourses = helper.getCoursesForTerm(sourceCourseId, targetTermId, context);
        if (targetCourses.isEmpty()) {
            throw new InvalidParameterException("Skipped because there is no valid version of the course in the target term");
        } else if (targetCourses.size() > 1) {
            throw new InvalidParameterException(
                    "Skipped because there are more than one valid versions of the course in the target term");
        }
        CourseInfo targetCourse = targetCourses.get(0);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.SKIP_IF_ALREADY_EXISTS_OPTION_KEY)) {
            String existingCoId = this._findFirstExistingCourseOfferingIdInTargetTerm(targetCourse.getId(), targetTermId, context);
            if (existingCoId != null) {
                throw new AlreadyExistsException("Skipped because course offering already exists in target term");
            }
        }

        // TODO: Not hard code "Active" but use a constant ... except these are R1 States
        if (optionKeys.contains(CourseOfferingSetServiceConstants.STILL_OFFERABLE_OPTION_KEY)) {
            if (!targetCourse.getStateKey().equals("Active")) {
                throw new InvalidParameterException("skipped because canonical course is no longer active");
            }
        }
        if (optionKeys.contains(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY)) {
            if (!sourceCourse.getId().equals(targetCourse.getId())) {
                throw new InvalidParameterException("skipped because there is a new version of the canonical course");
            }
        }

        // Create the course offering
        CourseOfferingInfo targetCo = _RCO_createTargetCourseOffering(sourceCo, targetTermId, targetCourse, optionKeys, context);
        // Get ready to rollover FOs and AOs
        List<FormatOfferingInfo> foInfos = coService.getFormatOfferingsByCourseOffering(sourceCo.getId(), context);
        // KSENROLL-7795: Prefetch AOs.  Needs to do this because we have to check AO term IDs to find
        // its term type and see if there is a corresponding subterm of the same type in the target term.
        // If not, then, no CO/FO/AO, etc. are rolled over.
        Map<String, List<ActivityOfferingInfo>> foIdsToAOList = _prefetchAOs(foInfos, sourceCo, context);
        // KSENROLL-7795 Verify whether target terms have correct subterms
        Map<String, String> sourceTermIdToTargetTermId = new HashMap<String, String>();
        if (!_createSourceTermIdToTargetTermIdMapping(foIdsToAOList, sourceCo.getTermId(), targetTermId, sourceTermIdToTargetTermId, context)) {
            throw new DataValidationErrorException("Target term does not have subterm types found in AOs");
        }

        // KSENROLL-7795 If option key doesn't exist, validate the target term for official state
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.TARGET_TERM_VALIDATED_OPTION_KEY)) {
            // Put key into constants file soon
            String termId;
            if ((termId = _verifyTermsOfficial(sourceTermIdToTargetTermId, context)) != null) {
                throw new DataValidationErrorException("ERROR: Target (sub)term (" + termId + ") is not official");
            }
        }

        int aoCount = 0;
        for (FormatOfferingInfo sourceFo : foInfos) {
            //TODO FIXME if the  IF_NO_NEW_VERSION_OPTION_KEY is not set and the Course version is different,
            // this call will always fail because the format from the old CO will never match the format on the new Course version
            // Logic will need to be added that reconciles the formats based on activity types
            // cclin -- It's assumed that both the CO/FO have term IDs that are parent terms so targetTermId works
            FormatOfferingInfo targetFo = _RCO_createTargetFormatOffering(sourceFo, targetCo, targetTermId, context);

            Map<String, String> sourceAoIdToTargetAoId = new HashMap<String, String>();
            List<ActivityOfferingInfo> aoList = foIdsToAOList.get(sourceFo.getId());
            for (ActivityOfferingInfo sourceAo : aoList) {
                if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY) &&
                        StringUtils.equals(sourceAo.getTypeKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                    continue;
                }

                sourceAo.setCourseOfferingCode(sourceCo.getCourseOfferingCode());        // courseOfferingCOde is required, but it doesn't seem to get populated by the service call above.

                // Find appropriate target term ID
                String targetTermIdCustom = targetTermId;
                // KSENROLL-7795 If the source AO has a different ID from the CO (which is always a parent term)
                // find the appropriate mapping to the target subterm ID.  Assumption: no subterm type exists
                // more than once for the term.
                if (!sourceAo.getTermId().equals(sourceCo.getTermId())) {
                    // Handle subterm case
                    targetTermIdCustom = sourceTermIdToTargetTermId.get(sourceAo.getTermId());
                }
                ActivityOfferingInfo targetAo =
                        _RCO_createTargetActivityOffering(sourceAo, targetFo, targetTermIdCustom, optionKeys, context);
                sourceAoIdToTargetAoId.put(sourceAo.getId(), targetAo.getId());

                if (!optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
                    boolean doColocate = sourceTermSameAsTarget || isPartOfRolloverSoc;
                    if (_hasAtLeastOneValidScheduleId(sourceAo)) {
                        _RCO_rolloverSrcSchedsToTargetSchedReqs(sourceAo, targetAo, rolloverId, doColocate, sourceTermSameAsTarget, context);
                    } else {
                        // KSNEROLL-6475 Copy RDLs if there are no ADLs from source to target term
                        _RCO_rolloverSrcSchedReqsToTargetSchedReqs(sourceAo, targetAo, rolloverId, doColocate, sourceTermSameAsTarget, context);
                    }
                }
                _RCO_rolloverSeatpools(sourceAo, targetAo, context);

                aoCount++;
            }
            List<ActivityOfferingClusterInfo> targetClusters =
                    _RCO_rolloverActivityOfferingClusters(sourceFo, targetFo, context, sourceAoIdToTargetAoId);
            for (ActivityOfferingClusterInfo cluster: targetClusters) {
                List<BulkStatusInfo> changes = generateRegistrationGroupsForCluster(cluster.getId(), context);
            }
        }
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSourceCourseOfferingId(sourceCoId);
        item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
        item.setStateKey(CourseOfferingSetServiceConstants.CREATED_RESULT_ITEM_STATE_KEY);
        item.setTargetCourseOfferingId(targetCo.getId());
        AttributeInfo aoCountAttr = new AttributeInfo();
        item.getAttributes().add(aoCountAttr);
        aoCountAttr.setKey(CourseOfferingSetServiceConstants.ACTIVITY_OFFERINGS_CREATED_SOC_ITEM_DYNAMIC_ATTRIBUTE);
        aoCountAttr.setValue("" + aoCount);
        return item;
    }

    private boolean _hasAtLeastOneValidScheduleId( ActivityOfferingInfo ao ) {
        if (ao.getScheduleIds() == null || ao.getScheduleIds().isEmpty()) {
            return false;
        }
        String firstId = ao.getScheduleIds().get(0);
        if(StringUtils.isBlank( firstId )) {
            return false;
        }
        return true;
    }

    private CourseOfferingInfo _RCO_createTargetCourseOffering(CourseOfferingInfo sourceCo, String targetTermId, CourseInfo targetCourse, List<String> optionKeys, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        CourseOfferingInfo targetCo = new CourseOfferingInfo(sourceCo);
        targetCo.setId(null);
        // clear out the ids on the internal sub-objects too
        for (OfferingInstructorInfo instr : targetCo.getInstructors()) {
            instr.setId(null);
        }
        for( CourseOfferingCrossListingInfo cross : targetCo.getCrossListings() ) {
            cross.setId(null);
            String crossListedCourseCodesSuffixStripped = StringUtils.stripEnd(cross.getCode(),targetCo.getCourseNumberSuffix());
            cross.setCode(crossListedCourseCodesSuffixStripped);
        }
//        for (RevenueInfo rev : targetCo.getRevenues()) {
//            rev.setId(null);
//        }
//        for (FeeInfo fee : targetCo.getFees()) {
//            fee.setId(null);
//        }
        for (AttributeInfo attr : targetCo.getAttributes()) {
            attr.setId(null);
        }
        targetCo.setTermId(targetTermId);
        targetCo.setMeta(null);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetCo.getInstructors().clear();
        }
        targetCo.setCourseId(targetCourse.getId());
        if (optionKeys.contains(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY)) {
            // copy from cannonical
            courseOfferingTransformer.copyFromCanonical(targetCourse, targetCo, optionKeys, context);
        }
        // Rolled over CO should be in draft state
        targetCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        targetCo = coService.createCourseOffering(targetCo.getCourseId(), targetCo.getTermId(), targetCo.getTypeKey(),
                targetCo, optionKeys, context);
        
        // have to copy rules AFTER CO is created because the link is by the CO id
        if (optionKeys.contains(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY)) {
            // copy rules from cannonical too
            courseOfferingTransformer.copyRulesFromCanonical(targetCourse, targetCo, optionKeys, context);
        } else {
            courseOfferingTransformer.copyRulesFromExistingCourseOffering(sourceCo, targetCo, optionKeys, context);
        }
        return targetCo;
    }

    private ActivityOfferingClusterInfo _RCO_createTargetClusterInfo(ActivityOfferingClusterInfo sourceCluster,
                                                                     String targetFormatOfferingId,
                                                                     Map<String, String> sourceAoIdToTargetAoId) {
        ActivityOfferingClusterInfo targetCluster = new ActivityOfferingClusterInfo();
        targetCluster.setFormatOfferingId(targetFormatOfferingId);
        targetCluster.setPrivateName(sourceCluster.getPrivateName());
        targetCluster.setName(sourceCluster.getName());
        targetCluster.setDescr(sourceCluster.getDescr());
        targetCluster.setTypeKey(sourceCluster.getTypeKey());
        targetCluster.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        // Now copy only the AO Ids that appear in the map (which are the only ones that got rolled over)
        List<ActivityOfferingSetInfo> targetAoSets = new ArrayList<ActivityOfferingSetInfo>();
        for (ActivityOfferingSetInfo sourceSet: sourceCluster.getActivityOfferingSets()) {
            ActivityOfferingSetInfo targetSet = new ActivityOfferingSetInfo();
            targetSet.setActivityOfferingType(sourceSet.getActivityOfferingType());
            List<String> targetAoIds = new ArrayList<String>();
            for (String sourceAoId: sourceSet.getActivityOfferingIds()) {
                if (sourceAoIdToTargetAoId.containsKey(sourceAoId)) {
                    // Only copy target AO Ids if it appears in the map
                    String targetAoIdToAdd = sourceAoIdToTargetAoId.get(sourceAoId);
                    targetAoIds.add(targetAoIdToAdd);
                }
            }
            // Then add the list of Ids to the target AO set
            targetSet.setActivityOfferingIds(targetAoIds);
            // Finally, add this set to the target cluster's lists of AO sets
            targetAoSets.add(targetSet);
        }
        // Finally, set the AO sets
        targetCluster.setActivityOfferingSets(targetAoSets);
        return targetCluster;
    }

    private List<ActivityOfferingClusterInfo>
    _RCO_rolloverActivityOfferingClusters(FormatOfferingInfo sourceFo, FormatOfferingInfo targetFo,
                                                       ContextInfo context,
                                                       Map<String, String> sourceAoIdToTargetAoId)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
                   PermissionDeniedException, OperationFailedException, DataValidationErrorException,
                   ReadOnlyException {

        List<ActivityOfferingClusterInfo> sourceClusterList =
                coService.getActivityOfferingClustersByFormatOffering(sourceFo.getId(), context);
        List<ActivityOfferingClusterInfo> targetClusterList = new ArrayList<ActivityOfferingClusterInfo>();
        for (ActivityOfferingClusterInfo sourceCluster: sourceClusterList) {
            ActivityOfferingClusterInfo targetCluster =
                    _RCO_createTargetClusterInfo(sourceCluster, targetFo.getId(), sourceAoIdToTargetAoId);
            ActivityOfferingClusterInfo created =
                    coService.createActivityOfferingCluster(targetFo.getId(), targetCluster.getTypeKey(), targetCluster, context);
            targetClusterList.add(created);
        }
        return targetClusterList;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys,
                                                                ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException,
                   PermissionDeniedException, VersionMismatchException {

        CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, context);
        CourseInfo course = new R1CourseServiceHelper(courseService, acalService).getCourse(co.getCourseId());
        // copy from canonical
        courseOfferingTransformer.copyFromCanonical(course, co, optionKeys, context);
        try {
            co = coService.updateCourseOffering(courseOfferingId, co, context);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        // TODO: continue traversing down the formats and activities updating from the canonical
        
        // copy rules from canonical
        courseOfferingTransformer.copyRulesFromCanonical(course, co, optionKeys, context);
        return co;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo,
                                                                          List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        CourseInfo course = new R1CourseServiceHelper(courseService, acalService).getCourse(courseOfferingInfo.getCourseId());
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY)) {
            results.addAll(this._compare("CourseTitle", course.getCourseTitle(), courseOfferingInfo.getCourseOfferingTitle(), null));
        }
        results.addAll(this._compare("Code", course.getCode(), courseOfferingInfo.getCourseOfferingCode(), null));

        if (optionKeys.contains(CourseOfferingSetServiceConstants.CREDITS_MATCH_SCHEDULED_HOURS_OPTION_KEY)) {
            results.addAll(compareCreditsToSchedule(course, courseOfferingInfo));
        }
        return results;
    }

    private List<ValidationResultInfo> _compare(String element, String courseValue, String coValue, String message) {
        if (courseValue == null && coValue == null) {
            return Collections.emptyList();
        }
        if (courseValue.equals(coValue)) {
            return Collections.emptyList();
        }
        ValidationResultInfo result = new ValidationResultInfo();
        result.setElement(element);
        result.setLevel(ErrorLevel.ERROR);
        result.setMessage(message);
        return Arrays.asList(result);
    }

    protected List<ValidationResultInfo> compareCreditsToSchedule(CourseInfo course, CourseOfferingInfo co) {
        // TODO: implement this complex logic
        // This is protected because it is explected that implementing instituations will vary widely in this implementation
        return Collections.emptyList();
    }

    private String _findFirstExistingCourseOfferingIdInTargetTerm(String targetCourseId, String targetTermId, ContextInfo context)
            throws DoesNotExistException, OperationFailedException {
        List<CourseOfferingInfo> list;
        try {
            list = coService.getCourseOfferingsByCourseAndTerm(targetCourseId, targetTermId, context);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0).getId();
    }

    /*
    * The core generation logic should work with in the impl as well.
    */
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException,
                   PermissionDeniedException, DataValidationErrorException {

        List<BulkStatusInfo>rgChanges = new ArrayList<BulkStatusInfo>();
        // check for any existing registration groups
        _initServices();

        // verify we are allowed to do this.
        boolean generated = false;
        List<String> aocIdList = coService.getActivityOfferingClustersIdsByFormatOffering(formatOfferingId, contextInfo);

        if (aocIdList.isEmpty()) {
            throw new DoesNotExistException("No ActivityOfferingCluster's exist for formatOfferingId = " + formatOfferingId);
        }

        for (String aocId : aocIdList) {
            try {
                List<BulkStatusInfo> changes = generateRegistrationGroupsForCluster(aocId, contextInfo);
                
                rgChanges.addAll(changes);
            } catch (Exception e) {
                throw new OperationFailedException("formatOfferingId = " + formatOfferingId + ": failed to generate reg groups for activityOfferingClusterId = " + aocId, e);
            }
        }

       
        return rgChanges;
    }

    private void _gRGFC_basicValidate(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
                   PermissionDeniedException, DataValidationErrorException {

        AOClusterVerifyResultsInfo result =
                coService.verifyActivityOfferingClusterForGeneration(activityOfferingClusterId, contextInfo);
        List<ValidationResultInfo> resultInfos = result.getValidationResults();
        for (ValidationResultInfo vri: resultInfos) {
            if (vri.isWarn()) {
                throw new DataValidationErrorException("One or more AOsets in the cluster is empty--can't generate reg groups");
            }
        }
    }


    /**
     * Note: The Registration Group Code is what the administrators want to see the reg groups on a per course offering basis.
     * Registration codes, which are 5-digit values assigned to an RG and are unique to a term, is not yet implemented as of M5.
     * @param regGroupCode 4-digit value that uniquely identifies a reg group within a course offering
     * @param activityOfferingPermutation Contains a set of AO IDs that form a registration group
     * @param formatOffering The format offering which the reg group belongs to
     * @param activityOfferingClusterId The cluster id which the AO IDs were selected from
     * @return A reg group (to be perssisted via services)
     */
    private RegistrationGroupInfo _gRGFC_makeRegGroup(String regGroupCode, List<String> activityOfferingPermutation,
                                                FormatOfferingInfo formatOffering, String activityOfferingClusterId) {
        RegistrationGroupInfo rg = new RegistrationGroupInfo();

        List<String> aoIdsList = new ArrayList<String>(activityOfferingPermutation); // convert to list
        rg.setActivityOfferingIds(aoIdsList);
        rg.setCourseOfferingId(formatOffering.getCourseOfferingId());
        rg.setDescr(new RichTextInfo(regGroupCode, regGroupCode));
        rg.setFormatOfferingId(formatOffering.getId());
        rg.setActivityOfferingClusterId(activityOfferingClusterId);
        rg.setIsGenerated(true);
        rg.setName(regGroupCode);
        rg.setRegistrationCode(null);
        rg.setTermId(formatOffering.getTermId());
        rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        return rg;
    }

    private Integer _gRGFC_computeFirstRegGroupCode(List<RegistrationGroupInfo> regGroups, int prefix) {
        List<Integer> rgCodesUsed = new ArrayList<Integer>();
        if (regGroups.isEmpty()) {
            // If no RGs then multiply prefix by 1000 and add 1.  This creates codes like 1001, 2001, 3001, etc.
            // The prefix identifies the reg group
            return prefix * 1000 + 1;
        }
        for (RegistrationGroupInfo rg: regGroups) {
            String regGroupCode = rg.getName(); // The name field stores
            Integer regGroupNum = Integer.parseInt(regGroupCode);
            rgCodesUsed.add(regGroupNum);
        }
        return Collections.max(rgCodesUsed) + 1;
    }


    private boolean _isValidActivityOfferingPermutation(List<String> activityOfferingPermutation) {
        // TODO: In M6 determine if we always make an RG or not, in particular, for AOs that are
        // in the suspended or canceled state.
        return true;
    }

    public static final String FIRST_REG_GROUP_CODE = "firstRegGroupCode";
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        // Initializes coService
        _initServices();
        ActivityOfferingClusterInfo cluster = coService.getActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        // If any of the AO sets is empty, we'll bail out and not generate.  This is more the expected behavior.
        if (_hasEmptyAoSets(cluster)) {
            return new ArrayList<BulkStatusInfo>(); // KSENROLL-6193, KSENROLL-6181
        }

        List<RegistrationGroupInfo> existingRegistrationGroups =
                coService.getRegistrationGroupsByFormatOffering(cluster.getFormatOfferingId(), contextInfo);
        int prefix = 1;
        if (existingRegistrationGroups.isEmpty()) {
            // A bit tedious to fetch all the FOs
            String foId = cluster.getFormatOfferingId();
            FormatOfferingInfo clusterFo = getCoService().getFormatOffering(foId, contextInfo);
            CourseOfferingInfo co = getCoService().getCourseOffering(clusterFo.getCourseOfferingId(), contextInfo);
            List<FormatOfferingInfo> foInfos = getCoService().getFormatOfferingsByCourseOffering(co.getId(), contextInfo);

            try {
                RegistrationGroupCodeUtil.computeRegCodePrefixForFo(foInfos, getCoService(), contextInfo);
                // Refetch the FO
                FormatOfferingInfo fetched = getCoService().getFormatOffering(foId, contextInfo);
                prefix = RegistrationGroupCodeUtil.getRegCodePrefixFromFo(fetched);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("ERROR in generating reg groups (ReadOnlyException) " + e.getMessage());
            } catch (VersionMismatchException e) {
                throw new OperationFailedException("ERROR in generating reg groups (VersionMismatchException) " + e.getMessage());
            }
        }
        Integer firstRegGroupCode = _gRGFC_computeFirstRegGroupCode(existingRegistrationGroups, prefix);

        // Calculate the set of "set of AO IDs" from which to generate reg groups.

        Set<List<String>> regGroupAoIds =
                PermutationCounter.computeMissingRegGroupAoIdsInCluster(cluster, existingRegistrationGroups);

        FormatOfferingInfo fo = coService.getFormatOffering(cluster.getFormatOfferingId(), contextInfo);
        List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByCluster(activityOfferingClusterId, contextInfo);

        // New instance created each time if desired
        RegistrationGroupCodeGenerator generator =
                registrationCodeGeneratorFactory.makeCodeGenerator();
        Map<String, Object> keyValues = null;
        if (firstRegGroupCode != null) {
            keyValues = new HashMap<String, Object>();
            keyValues.put(FIRST_REG_GROUP_CODE, firstRegGroupCode);
        }
        generator.initializeGenerator(coService, fo, contextInfo, keyValues);

        //Sort the lists in order to be in sequence with RGIDs when generating
        //Sort aoList
        if(aoList.size()>1){
            Collections.sort(aoList, new Comparator<ActivityOfferingInfo>() {
                @Override
                public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
                    if (o1.getActivityCode() != null && o2.getActivityCode() != null
                            && !o1.getActivityCode().equals("") && !o2.getActivityCode().equals("")){
                        return o1.getActivityCode().compareTo(o2.getActivityCode());
                    } else {
                        return -1;
                    }
                }
            });
        }
        //Sort AO IDs within the regGroupAoIds list's arrays
        ArrayList<List<ActivityOfferingInfo>> regGroupAoInfosSorted = new ArrayList<List<ActivityOfferingInfo>>();
        for (List<String> aoIDs : regGroupAoIds) {
            List<ActivityOfferingInfo> aoListSorted = new ArrayList<ActivityOfferingInfo>();
            //loop aoIDs and find the related AO that will store the activityCode to be sorted on
            for (String aoIDinaoIDs: aoIDs){
                //find the matching ID and associate it to the AOInfo
                for (ActivityOfferingInfo aoInfo :  aoList) {
                    if (aoInfo.getId().equals(aoIDinaoIDs)) {
                        aoListSorted.add(aoInfo); //create a list of AOInfos to be sorted
                        break;
                    }
                }
            }
            if (aoListSorted.size() > 1) {  //sort if more than 1 AO in the set
                //Sort aoListSorted based on activityCode
                Collections.sort(aoListSorted, new Comparator<ActivityOfferingInfo>() {
                    @Override
                    public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
                        if (o1.getActivityCode() != null && o2.getActivityCode() != null
                                && !o1.getActivityCode().equals("") && !o2.getActivityCode().equals("")){
                            return o1.getActivityCode().compareTo(o2.getActivityCode());
                        } else {
                            return -1;
                        }
                    }
                });
            }
            regGroupAoInfosSorted.add(aoListSorted);
        }
        //Sort regGroupAoInfosSorted
        Collections.sort(regGroupAoInfosSorted, new Comparator <List<ActivityOfferingInfo>>() {
            @Override
            public int compare(List<ActivityOfferingInfo> o1, List<ActivityOfferingInfo> o2) {
                String o1Code = "";
                String o2Code = "";
                for (ActivityOfferingInfo aoInfo : o1) { //build o1 code
                    o1Code = o1Code + aoInfo.getActivityCode();
                }
                for (ActivityOfferingInfo aoInfo : o2) { //build o2 code
                    o2Code = o2Code + aoInfo.getActivityCode();
                }
                if (o1Code != null && o2Code != null
                        && !o1Code.equals("") && !o2Code.equals("")){
                    return o1Code.compareTo(o2Code);
                } else {
                    return -1;
                }
            }
        });

        // Loop through each set of AO Ids and create a reg group.
        for (List<ActivityOfferingInfo> aoInfoList : regGroupAoInfosSorted) {
            List<String> activityOfferingPermutation = new ArrayList<String>();
            for (ActivityOfferingInfo aoInfo : aoInfoList){
                activityOfferingPermutation.add(aoInfo.getId());
            }

            if (!_isValidActivityOfferingPermutation(activityOfferingPermutation)) {
                continue;
            }
            String regGroupCode = generator.generateRegistrationGroupCode(fo, aoList, null);
            RegistrationGroupInfo rg = _gRGFC_makeRegGroup(regGroupCode, activityOfferingPermutation, fo, cluster.getId());

            try {
                RegistrationGroupInfo rgInfo = coService.createRegistrationGroup(cluster.getFormatOfferingId(), cluster.getId(), LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg, contextInfo);
                BulkStatusInfo status  = new BulkStatusInfo();
                status.setId(rgInfo.getId());
                status.setSuccess(Boolean.TRUE);
                status.setMessage("Created Registration Group");
                rgChanges.add(status);

                // Now determine if this registration group is in a valid state
                List<ValidationResultInfo> validations =
                        coService.verifyRegistrationGroup(rgInfo.getId(), contextInfo);

                for (ValidationResultInfo validation: validations) {
                    if (validation.isWarn()) {
                        // If any validation is an error, then make this invalid
                        coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, contextInfo);
                        break;
                    }
                }

                _gRGFC_changeClusterRegistrationGroupState(rgInfo, contextInfo);

            } catch (DataValidationErrorException e) {
                throw new OperationFailedException("Failed to validate registration group", e);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Failed to write registration group", e);
            }
        }

        return rgChanges;
    }

    // Returns true if a cluster has one (or more) AO sets that is empty.
    private boolean _hasEmptyAoSets(ActivityOfferingClusterInfo cluster) {
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingIds().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Map<String, List<String>> _extractActivityOfferingMap(List<ActivityOfferingSetInfo> activityOfferingSets) {
        Map<String, List<String>> aoTypeToListMap = new LinkedHashMap<String, List<String>>();

        for (ActivityOfferingSetInfo aoSet : activityOfferingSets) {
            for (String aoId : aoSet.getActivityOfferingIds()) {
                List<String> aoIdList = aoTypeToListMap.get(aoSet.getActivityOfferingType());

                if (aoIdList == null) {
                    aoIdList = new ArrayList<String>();
                    aoTypeToListMap.put(aoSet.getActivityOfferingType(), aoIdList);
                }

                aoIdList.add(aoId);
            }
        }
        return aoTypeToListMap;
    }

    private List<String> extractTypes(List<ActivityOfferingSetInfo> activityOfferingSets) {
        List<String> typeList = new ArrayList<String>();

        for (ActivityOfferingSetInfo activityOfferingSetInfo : activityOfferingSets) {

            typeList.add(activityOfferingSetInfo.getActivityOfferingType());
        }
        return typeList;
    }

    private void _gRGFC_changeClusterRegistrationGroupState(RegistrationGroupInfo regGroupInfo, ContextInfo context) {
        try {
            if (!regGroupInfo.getStateKey().equals(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY)) {
                List<String> aoIds = regGroupInfo.getActivityOfferingIds();
                String regGroupStateKey = LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY;
                for (String aoId : aoIds) {
                    ActivityOfferingInfo aoInfo = coService.getActivityOffering(aoId, context);
                    if (aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY)) {
                        regGroupStateKey = LuiServiceConstants.REGISTRATION_GROUP_SUSPENDED_STATE_KEY;
                        break;
                    } else if (aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                        regGroupStateKey = LuiServiceConstants.REGISTRATION_GROUP_CANCELED_STATE_KEY;
                        break;
                    } else if (!aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
                        regGroupStateKey = LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY;
                        break;
                    }
                }
                if(!regGroupInfo.getStateKey().equals(regGroupStateKey)) {
                    StatusInfo statusInfo = coService.changeRegistrationGroupState(regGroupInfo.getId(), regGroupStateKey, context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}