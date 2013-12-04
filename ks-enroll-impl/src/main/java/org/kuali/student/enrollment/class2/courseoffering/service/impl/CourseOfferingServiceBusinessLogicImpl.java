/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.extender.CourseOfferingServiceExtender;
import org.kuali.student.enrollment.class2.courseoffering.service.extender.CourseOfferingServiceExtenderImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.helper.CourseOfferingServiceRolloverHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupCodeGeneratorFactory;
import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacade;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacadeConstants;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nwright
 */
public class CourseOfferingServiceBusinessLogicImpl implements CourseOfferingServiceBusinessLogic {

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingServiceBusinessLogicImpl.class);

    public static final String FIRST_REG_GROUP_CODE = "firstRegGroupCode";
    // ----------------------------------------------------------------
    @Resource
    private CourseService courseService;

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseService getCourseService() {
        return courseService;
    }
    // ----------------------------------------------------------------
    @Resource
    private AcademicCalendarService acalService;

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }
    // ----------------------------------------------------------------
    @Resource
    private CourseOfferingService coService;

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public CourseOfferingService getCoService() {
        return coService;
    }
    // ----------------------------------------------------------------
    @Resource
    private RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory;

    public void setRegistrationCodeGeneratorFactory(RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory) {
        this.registrationCodeGeneratorFactory = registrationCodeGeneratorFactory;
    }
    // ----------------------------------------------------------------
    @Resource
    private SchedulingService schedulingService;

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    // ----------------------------------------------------------------
    @Resource
    private RoomService roomService;

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
    // ----------------------------------------------------------------
    @Resource
    private CourseOfferingTransformer courseOfferingTransformer;

    public void setCourseOfferingTransformer(CourseOfferingTransformer courseOfferingTransformer) {
        this.courseOfferingTransformer = courseOfferingTransformer;
    }
    // ----------------------------------------------------------------
    @Resource
    private ActivityOfferingTransformer activityOfferingTransformer;


    public ActivityOfferingTransformer getActivityOfferingTransformer() {
        return activityOfferingTransformer;
    }

    public void setActivityOfferingTransformer(ActivityOfferingTransformer activityOfferingTransformer) {
        this.activityOfferingTransformer = activityOfferingTransformer;
    }
    // ----------------------------------------------------------------
    @Resource
    private AcademicCalendarServiceFacade acalServiceFacade;

    public void setAcalServiceFacade(AcademicCalendarServiceFacade acalServiceFacade) {
        this.acalServiceFacade = acalServiceFacade;
    }
    // ----------------------------------------------------------------
    @Resource
    private RolloverAssist rolloverAssist;

    public void setRolloverAssist(RolloverAssist rolloverAssist) {
        this.rolloverAssist = rolloverAssist;
    }
    // ----------------------------------------------------------------
    @Resource
    private CourseWaitListService courseWaitListService;

    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }
    // ----------------------------------------------------------------
    @Resource
    private ExamOfferingServiceFacade examOfferingServiceFacade;

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        return examOfferingServiceFacade;
    }

    public void setExamOfferingServiceFacade(ExamOfferingServiceFacade examOfferingServiceFacade) {
        this.examOfferingServiceFacade = examOfferingServiceFacade;
    }
    // ----------------------------------------------------------------
    @Resource
    private CourseWaitListServiceFacade courseWaitListServiceFacade;

    public CourseWaitListServiceFacade getCourseWaitListServiceFacade() {
        return courseWaitListServiceFacade;
    }

    public void setCourseWaitListServiceFacade(CourseWaitListServiceFacade courseWaitListServiceFacade) {
        this.courseWaitListServiceFacade = courseWaitListServiceFacade;
    }
    // ----------------------------------------------------------------
    @Resource
    private CourseOfferingServiceExtender courseOfferingServiceExtender;

    public CourseOfferingServiceExtender getCourseOfferingServiceExtender() {
        return courseOfferingServiceExtender;
    }

    public void setCourseOfferingServiceExtender(CourseOfferingServiceExtender courseOfferingServiceExtender) {
        this.courseOfferingServiceExtender = courseOfferingServiceExtender;
    }
    // ----------------------------------------------------------------
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

        if (examOfferingServiceFacade == null){
            examOfferingServiceFacade = (ExamOfferingServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/examOfferingServiceFacade","examOfferingServiceFacade"));
        }

        if (courseWaitListServiceFacade == null){
            courseWaitListServiceFacade = (CourseWaitListServiceFacade) GlobalResourceLoader.getService(CourseWaitListServiceFacadeConstants.getQName());
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

    private Map<String, List<ActivityOfferingInfo>> _prefetchAOs(List<FormatOfferingInfo> fos,
                                                                 ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        Map<String, List<ActivityOfferingInfo>> foIdsToAOList = new HashMap<String, List<ActivityOfferingInfo>>();
        for (FormatOfferingInfo sourceFo: fos) {
            // Make the call with the additional contextAttributes
            List<ActivityOfferingInfo> aos = this.getCoService().getActivityOfferingsByFormatOffering(sourceFo.getId(), context);
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
     * @param contextInfo context of call
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
        //Code Changed for JIRA-8997 - SONAR Critical issues - Performance - Inefficient use of keySet iterator instead of entrySet iterator
       for(Map.Entry<String, List<ActivityOfferingInfo>> entry: foIdsToAoList.entrySet()) {
            List<ActivityOfferingInfo> aos = entry.getValue();
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
        // Alas, rollover and copy CO use this code, and they behave differently
        TermInfo targetTerm = acalService.getTerm(targetTermId, context);
        boolean sourceTermSameAsTarget;
        boolean isTrueRollover = false; // true, if this is really part of rollover
        boolean isCopyCourseOffering = false; //  true, if used in copy CO (negation of isTrueRollover)

        String rolloverId = context.getAttributeValue(CourseOfferingSetServiceConstants.ROLLOVER_ASSIST_ID_DYNATTR_KEY);
        if (rolloverId == null) {
            // Happens if we aren't doing a rolloverSoc
            rolloverId = rolloverAssist.getRolloverId(); // Create one just for this CO rollover
            isCopyCourseOffering = true;
            isTrueRollover = false;
        } else {
            // Assume we are doing rollover if this ID is being passed.
            isTrueRollover = true;
            isCopyCourseOffering = false;
        }
        CourseOfferingInfo sourceCo = coService.getCourseOffering(sourceCoId, context);
        // Determine if source/target term is same
        sourceTermSameAsTarget = sourceCo.getTermId().equals(targetTerm.getId());

        if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_OPTION_KEY)) {
            throw new DataValidationErrorException("Skipped because course offering was cancelled in source term");
        }
        R1CourseServiceHelper helper = new R1CourseServiceHelper(courseService, acalService);

        List<CourseInfo> targetCourses = helper.getCoursesForTerm(sourceCo.getCourseId(), targetTermId, context);
        if (targetCourses.isEmpty()) {
            throw new InvalidParameterException("Skipped because there is no valid version of the course in the target term");
        } else if (targetCourses.size() > 1) {
            throw new InvalidParameterException(
                    "Skipped because there are more than one valid versions of the course in the target term");
        }

        CourseInfo targetCourse = KSCollectionUtils.getRequiredZeroElement(targetCourses);
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
            if (!sourceCo.getCourseId().equals(targetCourse.getId())) {
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
        Map<String, List<ActivityOfferingInfo>> foIdsToAOList = _prefetchAOs(foInfos, context);
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

        Map<String, List<ActivityOfferingInfo>> foIdToAoList = new HashMap<String, List<ActivityOfferingInfo>>();
        int aoCount = 0;
        for (FormatOfferingInfo sourceFo : foInfos) {
            //TODO FIXME if the  IF_NO_NEW_VERSION_OPTION_KEY is not set and the Course version is different,
            // this call will always fail because the format from the old CO will never match the format on the new Course version
            // Logic will need to be added that reconciles the formats based on activity types
            // cclin -- It's assumed that both the CO/FO have term IDs that are parent terms so targetTermId works
            FormatOfferingInfo targetFo = _RCO_createTargetFormatOffering(sourceFo, targetCo, targetTermId, context);
            foIdsToAOList.put(targetFo.getId(), new ArrayList<ActivityOfferingInfo>());

            Map<String, String> sourceAoIdToTargetAoId = new HashMap<String, String>();
            List<ActivityOfferingInfo> sourceAoList = foIdsToAOList.get(sourceFo.getId());
            Map<String, ActivityOfferingInfo> targetAoId2Ao = new HashMap<String, ActivityOfferingInfo>();
            for (ActivityOfferingInfo sourceAo : sourceAoList) {
                if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY) &&
                        StringUtils.equals(sourceAo.getTypeKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                    continue;
                }

                // Find appropriate target term ID
                String targetTermIdCustom = targetTermId;
                // KSENROLL-7795 If the source AO has a different ID from the CO (which is always a parent term)
                // find the appropriate mapping to the target subterm ID.  Assumption: no subterm type exists
                // more than once for the term.
                if (!sourceAo.getTermId().equals(sourceCo.getTermId())) {
                    // Handle subterm case
                    targetTermIdCustom = sourceTermIdToTargetTermId.get(sourceAo.getTermId());
                }

                ActivityOfferingInfo targetAo;
                CourseOfferingServiceExtender extender = getCourseOfferingServiceExtender();
                if (sourceTermSameAsTarget || !isTrueRollover) {
                    // KSENROLL-8064: Make behavior of copying an AO the same (other than the option
                    // keys in the if statement above
                    // Note: if source/target term are the same, this is not a true rollover, so it is copy CO
                    // If source/target term are different, but it's not a true rollover, we still run this code
                    // since it's still a copy CO operation --cclin
                    targetAo = extender.copyActivityOffering(CourseOfferingServiceExtenderImpl.COPY_OPERATION_COPY_CO,
                            sourceAo, coService,
                            targetFo, targetTermIdCustom,
                            context, optionKeys);
                    // Need to do this, otherwise mapping of source/target AO clusters fails
                    sourceAoIdToTargetAoId.put(sourceAo.getId(), targetAo.getId());
                } else {
                    // Rollover
                    sourceAo.setCourseOfferingCode(sourceCo.getCourseOfferingCode());        // courseOfferingCOde is required, but it doesn't seem to get populated by the service call above.
                    targetAo = extender.createTargetActivityOfferingForRollover(sourceAo, targetFo, targetTermIdCustom,
                            rolloverAssist, rolloverId, optionKeys, coService, context);
                    sourceAoIdToTargetAoId.put(sourceAo.getId(), targetAo.getId());
                }
                targetAoId2Ao.put(targetAo.getId(), targetAo);
                foIdsToAOList.get(targetFo.getId()).add(targetAo);

                // Waitlist copy/rollover
                List<CourseWaitListInfo> waitListInfos = courseWaitListService.getCourseWaitListsByActivityOffering(sourceAo.getId(), context);
                if (isCopyCourseOffering) {
                    _RCO_copyWaitlistForTargetAO_CopyCO(sourceTermSameAsTarget, rolloverId, waitListInfos, targetAo, targetFo, targetCo, context);
                } else {
                    _RCO_rolloverWaitlistForTargetAo(waitListInfos, rolloverId, targetAo, targetFo, targetCo, context);
                }
                aoCount++;
            }
            List<ActivityOfferingClusterInfo> targetClusters =
                    _RCO_rolloverActivityOfferingClusters(sourceFo, targetFo, context, sourceAoIdToTargetAoId);
            for (ActivityOfferingClusterInfo cluster: targetClusters) {
                List<ActivityOfferingInfo> aosInCluster = _getAosInCluster(cluster, targetAoId2Ao);
                CourseOfferingServiceRolloverHelper.generateRegGroupsForClusterHelper(cluster.getId(), context,
                        coService, registrationCodeGeneratorFactory,
                        true, new ArrayList<RegistrationGroupInfo>(),
                        cluster, targetFo, aosInCluster);
            }
        }
        //process final exam offerings for target course offering
        String examPeriodID = null;

        try{
            examPeriodID = getExamOfferingServiceFacade().getExamPeriodId(targetCo.getTermId(), context);
        } catch (DoesNotExistException e) {
            // Unable to find exam period ID so value remains null
        }

        if (examPeriodID != null) {
            getExamOfferingServiceFacade().generateFinalExamOfferingOptimized(targetCo, targetCo.getTermId(),
                    examPeriodID, optionKeys, context, foIdsToAOList);
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

    private List<ActivityOfferingInfo> _getAosInCluster(ActivityOfferingClusterInfo cluster,
                                                        Map<String, ActivityOfferingInfo> targetAoId2Ao)
            throws OperationFailedException {
        List<ActivityOfferingInfo> aosInCluster = new ArrayList<ActivityOfferingInfo>();
        Set<String> aoIdsSeen = new HashSet<String>(); // Error checking for duplicate AO ids
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            for (String aoId: set.getActivityOfferingIds()) {
                if (!aoIdsSeen.contains(aoId)) {
                    ActivityOfferingInfo targetAo = targetAoId2Ao.get(aoId);
                    if (targetAo == null) {
                        throw new OperationFailedException("Unable to locate target AO");
                    }
                    aosInCluster.add(targetAoId2Ao.get(aoId));
                    aoIdsSeen.add(aoId); // Add to list of "seen" ao ids
                }
            }
        }
        return aosInCluster;
    }


    private void _RCO_createDefaultWaitlistForTargetAo(ActivityOfferingInfo targetAo,
                                                       FormatOfferingInfo targetFo,
                                                       CourseOfferingInfo targetCo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // This is an exceptional case where the source AO lacks a waitlist.  All AOs should have waitlists in
        // the ref data
        CourseWaitListInfo theWaitListInfo =
                getCourseWaitListServiceFacade().createDefaultCourseWaitlist(targetFo.getId(),
                        targetAo.getId(), targetCo.getHasWaitlist(), context);

        courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, theWaitListInfo, context);
    }

    private void _RCO_copyWaitlistForTargetAO_CopyCO(boolean sourceTermSameAsTarget,
                                                     String rolloverId,
                                                     List<CourseWaitListInfo> waitListInfos,
                                                     ActivityOfferingInfo targetAo,
                                                     FormatOfferingInfo targetFo,
                                                     CourseOfferingInfo targetCo,
                                                     ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (waitListInfos.isEmpty()) {
            _RCO_createDefaultWaitlistForTargetAo(targetAo, targetFo, targetCo, context);
        } else {
            if (waitListInfos.size() > 1) {
                LOGGER.warn("Not expecting multiple waitlists attached to the AO");
                throw new OperationFailedException("Not expecting multiple waitlists attached to the AO");
            }
            // Should only be one waitlist
            if (sourceTermSameAsTarget) {
                // Same term, so attempt to share waitlists
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    _RCO_copyWaitlistPossiblySharedForTargetAO(waitListInfo, rolloverId, targetAo, targetFo, context);
                }
            } else {
                // different term so just create one waitlist per AO (splitting shared waitlists)
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    _RCO_copyNonsharedWaitlist(waitListInfo, targetAo, targetFo, context);
                }
            }
        }
    }

    private void _RCO_copySharedWaitlist(CourseWaitListInfo waitListInfo,
                                         String rolloverId,
                                         ActivityOfferingInfo targetAo,
                                         FormatOfferingInfo targetFo,
                                         ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        String waitlistId = waitListInfo.getId();
        String targetWaitlistId = rolloverAssist.getTargetSharedWaitlistId(rolloverId, waitlistId);
        if (targetWaitlistId == null) {
            CourseWaitListInfo createdTargetWaitlist = _RCO_copyNonsharedWaitlist(waitListInfo, targetAo, targetFo, context);
            // Store information that the waitlist is shared
            rolloverAssist.mapSourceSharedWaitlistIdToTargetSharedWaitlistId(rolloverId, waitlistId, createdTargetWaitlist.getId());
        } else {
            // Have already created a target waitlist corresponding to the source, so reused and update
            CourseWaitListInfo targetWaitlist = courseWaitListService.getCourseWaitList(targetWaitlistId, context);
            if (targetWaitlist.getActivityOfferingIds().contains(targetAo.getId())) {
                // Really shouldn't happen
                throw new OperationFailedException("Target waitlist already contains targetAO: " + targetAo.getId());
            } else {
                targetWaitlist.getActivityOfferingIds().add(targetAo.getId());
                if (!targetWaitlist.getFormatOfferingIds().contains(targetFo.getId())) {
                    // Co-location may occur with two AOs belonging to two different COs, thus having
                    // two different FOs
                    targetWaitlist.getFormatOfferingIds().add(targetFo.getId());
                }
                // Update to the database
                try {
                    courseWaitListService.updateCourseWaitList(targetWaitlist.getId(), targetWaitlist, context);
                } catch (VersionMismatchException e) {
                    throw new OperationFailedException("Version mismatch" + e.getMessage(), e);
                }
            }
        }
    }

    private CourseWaitListInfo _RCO_copyNonsharedWaitlist(CourseWaitListInfo waitListInfo,
                                            ActivityOfferingInfo targetAo,
                                            FormatOfferingInfo targetFo,
                                            ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        waitListInfo.setId(null);
        waitListInfo.setActivityOfferingIds(new ArrayList<String>());
        waitListInfo.setFormatOfferingIds(new ArrayList<String>());
        waitListInfo.getActivityOfferingIds().add(targetAo.getId());
        waitListInfo.getFormatOfferingIds().add(targetFo.getId());
        CourseWaitListInfo waitlist = courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY, waitListInfo, context);
        return waitlist;
    }


    private void _RCO_rolloverWaitlistForTargetAo(List<CourseWaitListInfo> waitListInfos,
                                                  String rolloverId,
                                                  ActivityOfferingInfo targetAo,
                                                  FormatOfferingInfo targetFo,
                                                  CourseOfferingInfo targetCo,
                                                  ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (waitListInfos.isEmpty()) {
            _RCO_createDefaultWaitlistForTargetAo(targetAo, targetFo, targetCo, context);
        } else {
            if (waitListInfos.size() > 1) {
                LOGGER.warn("Not expecting multiple waitlists attached to the AO");
                throw new OperationFailedException("Not expecting multiple waitlists attached to the AO");
            }
            // different term so just copy the waitlist across
            for (CourseWaitListInfo waitListInfo : waitListInfos){
                _RCO_copyWaitlistPossiblySharedForTargetAO(waitListInfo, rolloverId, targetAo, targetFo, context);
            }
        }
    }

    /**
     * Copy a waitlist which may or may not be shared for a target AO (can be from rollover or from Copy CO
     * in the same term as the CO).
     */
    private void _RCO_copyWaitlistPossiblySharedForTargetAO(CourseWaitListInfo waitListInfo,
                                                            String rolloverId,
                                                            ActivityOfferingInfo targetAo,
                                                            FormatOfferingInfo targetFo,
                                                            ContextInfo context)
            throws OperationFailedException, DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, PermissionDeniedException, ReadOnlyException {

        if (waitListInfo.getActivityOfferingIds() == null || waitListInfo.getActivityOfferingIds().isEmpty()) {
            throw new OperationFailedException("No AO ids in this waitlist");
        }
        // Must be at least one AO ID
        if (waitListInfo.getActivityOfferingIds().size() == 1) {
            // Non-shared case
            _RCO_copyNonsharedWaitlist(waitListInfo, targetAo, targetFo, context);
        } else {
            _RCO_copySharedWaitlist(waitListInfo, rolloverId, targetAo, targetFo, context);
        }
    }

    private CourseOfferingInfo _RCO_createTargetCourseOffering(CourseOfferingInfo sourceCo,
                                                               String targetTermId,
                                                               CourseInfo targetCourse,
                                                               List<String> optionKeys,
                                                               ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
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
            // TODO: KSENROLL-10856 Is this needed?
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

        // waitlist
        targetCo.setHasWaitlist(sourceCo.getHasWaitlist());
        targetCo.setWaitlistLevelTypeKey(sourceCo.getWaitlistLevelTypeKey());
        targetCo.setWaitlistMaximum(sourceCo.getWaitlistMaximum());
        targetCo.setWaitlistTypeKey(sourceCo.getWaitlistTypeKey());

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
        int firstCourseOfferingInfo = 0;
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
        return list.get(firstCourseOfferingInfo).getId();
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

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Initializes coService
        _initServices();
        return CourseOfferingServiceRolloverHelper.generateRegGroupsForClusterHelper(activityOfferingClusterId,
                contextInfo, coService, registrationCodeGeneratorFactory);
    }
}