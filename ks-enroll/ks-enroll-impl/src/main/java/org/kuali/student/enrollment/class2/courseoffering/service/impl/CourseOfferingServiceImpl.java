package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.class2.courseoffering.dao.ActivityOfferingClusterDaoApi;
import org.kuali.student.enrollment.class2.courseoffering.dao.SeatPoolDefinitionDaoApi;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterAttributeEntity;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingSetEntity;
import org.kuali.student.enrollment.class2.courseoffering.model.SeatPoolDefinitionEntity;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.extender.CourseOfferingServiceExtender;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingDisplayTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingDisplayTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.FormatOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.OfferingInstructorTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupTransformer;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelation;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.poc.jsonparser.json.SimpleJsonList;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class CourseOfferingServiceImpl implements CourseOfferingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseOfferingServiceImpl.class);

    private static final String FAILED_TO_UPDATE_LUI_STATE_ERROR_MESSAGE = "Failed to update State";

    private static final String PREDICATE_FACTORY_PATH_FOR_LUITYPE = "luiType";

    private AcademicCalendarService acalService;
    private AtpService atpService;
    private CourseService courseService;
    private CourseWaitListService courseWaitListService;
    private ExamOfferingService examOfferingService;
    private LprService lprService;
    private LRCService lrcService;
    private LuiService luiService;
    private RoomService roomService;
    private SchedulingService schedulingService;
    private SearchService searchService;
    private StateService stateService;
    private TypeService typeService;

    private RegistrationGroupAssembler registrationGroupAssembler;
    private CourseOfferingServiceBusinessLogic businessLogic;
    private CourseOfferingCodeGenerator offeringCodeGenerator;
    private CourseOfferingTransformer courseOfferingTransformer;
    private ActivityOfferingTransformer activityOfferingTransformer;
    private SeatPoolDefinitionDaoApi seatPoolDefinitionDao;
    private ActivityOfferingClusterDaoApi activityOfferingClusterDao;
    private RegistrationGroupTransformer registrationGroupTransformer;
    private StateTransitionsHelper stateTransitionsHelper;
    private CourseOfferingServiceExtender courseOfferingServiceExtender;

    private CriteriaLookupService coCriteriaLookupService;
    private CriteriaLookupService aoCriteriaLookupService;
    private CriteriaLookupService foCriteriaLookupService;
    private CriteriaLookupService rgCriteriaLookupService;
    private CriteriaLookupService spCriteriaLookupService;

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCourseOfferingCascaded(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Cascade delete to the formats
        List<FormatOfferingInfo> fos = getFormatOfferingsByCourseOffering(courseOfferingId, context);
        for (FormatOfferingInfo fo : fos) {
            deleteFormatOfferingCascaded(fo.getId(), context);
        }

        // delete offering instructor lprs for the Course Offering
        deleteLprsByLui(courseOfferingId, context);

        //TODO: Delete all attached other things (EnrollmentFees, org relations, etc.)

        // Delete the CO
        deleteCourseOffering(courseOfferingId, context);

        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<FormatOfferingInfo> formatOfferings = new ArrayList<FormatOfferingInfo>();

        // Find all related luis to the course Offering
        List<LuiInfo> luis = luiService.getRelatedLuisByLuiAndRelationType(courseOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, context);
        for (LuiInfo lui : luis) {
            FormatOfferingInfo formatOffering = new FormatOfferingInfo();
            new FormatOfferingTransformer().lui2Format(lui, formatOffering);
            formatOffering.setCourseOfferingId(courseOfferingId);
            formatOfferings.add(formatOffering);
        }
        return formatOfferings;
    }

    private StatusInfo deleteExamOfferingsFromFo(String formatOfferingId, ContextInfo context) throws PermissionDeniedException,
            MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        List<ExamOfferingRelationInfo> eoRelations =  this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(formatOfferingId, context);
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
            this.getExamOfferingService().deleteExamOffering(eoRelation.getExamOfferingId(), context);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteFormatOfferingCascaded(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Delete dependent activity offerings
        List<ActivityOfferingInfo> aos = getActivityOfferingsByFormatOffering(formatOfferingId, context);
        for (ActivityOfferingInfo ao : aos) {
            deleteActivityOfferingCascaded(ao.getId(), context);
        }

        // TODO: Delete dependent RegistrationGroups
        // Delete the associated exam offerings.
        deleteExamOfferingsFromFo(formatOfferingId, context);

        // Delete the format offering
        try {
            deleteFormatOffering(formatOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException(e);
        }
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
    }

    private void deleteLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("luiId", luiId));

        QueryByCriteria criteria = qbcBuilder.build();

        List<String> lprIds = lprService.searchForLprIds(criteria, context);
        for (String lprId : lprIds) {
            StatusInfo status = lprService.deleteLpr(lprId, context);
            if (!status.getIsSuccess()) {
                throw new OperationFailedException("Error Deleting related LPR with id ( " + lprId + " ), given message was: " + status.getMessage());
            }
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            return luiService.deleteLui(courseOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting course offering", e);
        }
    }


    private void cRGBuildLuiLuiRelationForFormatOfferingRegistrationGroup(LuiInfo lui, String formatOfferingId,
                                                                          String coCode, ContextInfo context)
            throws OperationFailedException, DoesNotExistException, PermissionDeniedException, InvalidParameterException,
            ReadOnlyException, MissingParameterException, DataValidationErrorException {
        LuiLuiRelationInfo luiLuiRelFoRg = new LuiLuiRelationInfo();
        luiLuiRelFoRg.setLuiId(formatOfferingId);
        luiLuiRelFoRg.setName("fo-rg-relation"); // TODO: This fixes a DB required field error--find more meaningful value.
        luiLuiRelFoRg.setRelatedLuiId(lui.getId());

        RichTextInfo descrFoRg = new RichTextInfo();
        descrFoRg.setPlain(coCode + "-FO-RG"); // Useful for debugging
        descrFoRg.setFormatted(coCode + "-FO-RG"); // Useful for debugging
        luiLuiRelFoRg.setDescr(descrFoRg);

        luiLuiRelFoRg.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY);
        luiLuiRelFoRg.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        luiLuiRelFoRg.setEffectiveDate(new Date());

        luiService.createLuiLuiRelation(luiLuiRelFoRg.getLuiId(), luiLuiRelFoRg.getRelatedLuiId(), luiLuiRelFoRg.getTypeKey(), luiLuiRelFoRg, context);
    }

    private void cRGBuildLuiLuiRelationForRegGroupsAndAos(List<String> aoIds, LuiInfo lui, String coCode, ContextInfo context)
        throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {
        for (String aoId : aoIds) {
            LuiLuiRelationInfo luiLuiRelRgAo = new LuiLuiRelationInfo();
            luiLuiRelRgAo.setLuiId(lui.getId());
            luiLuiRelRgAo.setName("rg-ao-relation"); // TODO: This fixes a DB required field error--find more meaningful value.
            luiLuiRelRgAo.setRelatedLuiId(aoId);

            RichTextInfo descrRgAo = new RichTextInfo();
            descrRgAo.setPlain(coCode + "-RG-AO"); // Useful for debugging
            descrRgAo.setFormatted(coCode + "-RG-AO"); // Useful for debugging
            luiLuiRelRgAo.setDescr(descrRgAo);

            luiLuiRelRgAo.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY);
            luiLuiRelRgAo.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
            luiLuiRelRgAo.setEffectiveDate(new Date());
            luiService.createLuiLuiRelation(luiLuiRelRgAo.getLuiId(), luiLuiRelRgAo.getRelatedLuiId(), luiLuiRelRgAo.getTypeKey(), luiLuiRelRgAo, context);
        }
    }


    private void cRGValidateCreateRegistrationGroup(RegistrationGroupInfo registrationGroupInfo,
                                                    String registrationGroupTypeKey,
                                                    FormatOfferingInfo fo)
            throws InvalidParameterException, DataValidationErrorException {

        if (!registrationGroupTypeKey.equals(registrationGroupInfo.getTypeKey())) {
            throw new InvalidParameterException(registrationGroupTypeKey + " does not match the corresponding value in the object " + registrationGroupInfo.getTypeKey());
        }

        if (registrationGroupInfo.getTermId() != null && !registrationGroupInfo.getTermId().equals(fo.getTermId()) ) {
            throw new InvalidParameterException(registrationGroupInfo.getTermId() + " term in the registration group does not match the one in the format offering " + fo.getTermId());
        }

        // TODO: Reg group code validation
        if (registrationGroupInfo.getName() == null) {
            // name stores the reg group code which is different from registration code
            throw new DataValidationErrorException("reg group code is null");
        }
    }

    private void validateLuiIsInValidInitialState( TypeStateEntityInfo lui, String luiLifecycleKey, ContextInfo context )
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException
    {
        List<String> validInitialStates = stateService.getInitialStatesByLifecycle( luiLifecycleKey, context );
        if( !validInitialStates.contains( lui.getStateKey() ) ) {
            throw new DataValidationErrorException( "LUI has invalid initial state(" + lui.getStateKey()
                    + "); must be one of: " + validInitialStates );
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public RegistrationGroupInfo createRegistrationGroup(String formatOfferingId, String activityOfferingClusterId,
                                                         String registrationGroupTypeKey,
                                                         RegistrationGroupInfo registrationGroupInfo,
                                                         ContextInfo context) throws
            DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        validateLuiIsInValidInitialState( registrationGroupInfo, LuiServiceConstants.REGISTRATION_GROUP_LIFECYCLE_KEY, context );

        FormatOfferingInfo fo = this.getFormatOffering(formatOfferingId, context);
        cRGValidateCreateRegistrationGroup(registrationGroupInfo, registrationGroupTypeKey, fo);
        registrationGroupInfo.setTermId(fo.getTermId());

        //Default the initial state to not offered (might need more logic here in the future)
        registrationGroupInfo.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);

        // get the course offering
        CourseOfferingInfo coInfo = this.getCourseOffering(registrationGroupInfo.getCourseOfferingId(), context);
        String coCode = coInfo.getCourseOfferingCode();
        if (coCode == null) {
            coCode = "NOCODE";
        }

        // copy to the lui
        LuiInfo lui = registrationGroupTransformer.rg2Lui(registrationGroupInfo, context);

        String cluId = lui.getCluId();
        String atpId = lui.getAtpId();
        String typeKey = lui.getTypeKey();
        lui = luiService.createLui(cluId, atpId, typeKey, lui, context);

        // build the lui lui relation FO-RG
        cRGBuildLuiLuiRelationForFormatOfferingRegistrationGroup(lui, formatOfferingId, coCode, context);

        // build the lui lui relation RG-AO
        List<String> aoIds = registrationGroupInfo.getActivityOfferingIds();
        cRGBuildLuiLuiRelationForRegGroupsAndAos(aoIds, lui, coCode, context);

        // Everything saved to the DB, now return RG sent back by createLui and transformed by transformer back to caller
        RegistrationGroupInfo rgInfo;
        rgInfo = registrationGroupTransformer.lui2RgOptimized(lui, context, formatOfferingId, aoIds);
        rgInfo.setCourseOfferingId(coInfo.getId());
        rgInfo.setRegistrationCode(registrationGroupInfo.getRegistrationCode());
        return rgInfo;
    }


    @Override
    @Transactional(readOnly = true)
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(courseOfferingId, context);
        CourseOfferingInfo co = new CourseOfferingInfo();

        //Associate instructors to the given CO
        courseOfferingTransformer.lui2CourseOffering(lui, co, context);
        courseOfferingTransformer.assembleInstructors(co, lui.getId(), context, getLprService());

        return co;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseOfferingDisplayInfo getCourseOfferingDisplay(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo coInfo = getCourseOffering(courseOfferingId, context);
        CourseOfferingDisplayInfo displayInfo =
                CourseOfferingDisplayTransformer.co2coDisplay(coInfo, acalService, stateService, typeService, lrcService, context);

        return displayInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> coList = getCourseOfferingsByIds(courseOfferingIds, context);

        return CourseOfferingDisplayTransformer.cos2coDisplays(coList, acalService, stateService, typeService, context);

    }

    @Override
    @Transactional(readOnly = true)
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ActivityOfferingInfo aoInfo = getActivityOffering(activityOfferingId, contextInfo);
        ActivityOfferingDisplayInfo displayInfo =
                ActivityOfferingDisplayTransformer.ao2aoDisplay(aoInfo, schedulingService, stateService, typeService, contextInfo);
        return displayInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(List<String> activityOfferingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // For now, just do it simply
        List<ActivityOfferingDisplayInfo> displayInfos = new ArrayList<ActivityOfferingDisplayInfo>();
        for (String id: activityOfferingIds) {
            ActivityOfferingDisplayInfo displayInfo = getActivityOfferingDisplay(id, contextInfo);
            displayInfos.add(displayInfo);
        }
        return displayInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysForCourseOffering(String courseOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Straight-forward implementation--might not be fully optimized
        List<ActivityOfferingInfo> aoInfos = getActivityOfferingsByCourseOffering(courseOfferingId, contextInfo);
        List<ActivityOfferingDisplayInfo> aoDisplayInfos = ActivityOfferingDisplayTransformer.aos2aoDisplays(aoInfos, schedulingService, stateService, typeService, contextInfo);

        return  aoDisplayInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo>courseOfferings = new ArrayList<CourseOfferingInfo>();
        if(courseOfferingIds != null && !courseOfferingIds.isEmpty()){
            courseOfferingTransformer.luis2CourseOfferings(courseOfferingIds, courseOfferings, context);
        }

        return courseOfferings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> luiIds = luiService.getLuiIdsByClu(courseId, context);
        List<CourseOfferingInfo> results = new ArrayList<CourseOfferingInfo>();
        for (String luiId : luiIds) {
            CourseOfferingInfo co = getCourseOffering(luiId, context);
            results.add(co);
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check the term is valid
        acalService.getTerm(termId, context);

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("cluId", courseId),
                                 PredicateFactory.equal("atpId", termId),
                                 PredicateFactory.equal( PREDICATE_FACTORY_PATH_FOR_LUITYPE, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY));

        QueryByCriteria criteria = qbcBuilder.build();

        List<String> luiIds = luiService.searchForLuiIds(criteria, context);

        return getCourseOfferingsByIds(luiIds, context);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        this.acalService.getTerm(termId, context); // check term exists

        return luiService.getLuiIdsByAtpAndType(termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, context);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal( PREDICATE_FACTORY_PATH_FOR_LUITYPE, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equalIgnoreCase("subjectArea", subjectArea));

        QueryByCriteria criteria = qbcBuilder.build();

        GenericQueryResults<String> results = coCriteriaLookupService.lookupIds(LuiEntity.class, criteria);

        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LprInfo> lprInfos = lprService.getLprsByTypeAndPersonAndAtp(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, instructorId, termId, context);
        List<CourseOfferingInfo> cos = new ArrayList<CourseOfferingInfo>();
        for (LprInfo lprInfo : lprInfos) {
            cos.add(getCourseOffering(lprInfo.getLuiId(), context));
        }
        return cos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	
    	QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
    	
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY),
                PredicateFactory.equal("atpId", termId), PredicateFactory.equal("unitsContentOwner", unitsContentOwnerId)));
        
        QueryByCriteria criteria = qbcBuilder.build();
    			
        List<String> luiIds = luiService.searchForLuiIds(criteria, context);
        
        return luiIds;

    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCourseOfferingIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getValidRolloverOptionKeys(ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCourseOfferingId, String targetTermId, List<String> optionKeys, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return this.businessLogic.rolloverCourseOffering(sourceCourseOfferingId, targetTermId, optionKeys, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey,
                                                   CourseOfferingInfo coInfo,
                                                   List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        // validate params
        if (!courseId.equals(coInfo.getCourseId())) {
            throw new InvalidParameterException(courseId + " does not match the corresponding value in the object " + coInfo.getCourseId());
        }
        if (!termId.equals(coInfo.getTermId())) {
            throw new InvalidParameterException(termId + " does not match the corresponding value in the object " + coInfo.getTermId());
        }
        if (!courseOfferingTypeKey.equals(coInfo.getTypeKey())) {
            throw new InvalidParameterException(courseOfferingTypeKey + " does not match the corresponding value in the object " + coInfo.getTypeKey());
        }

        validateLuiIsInValidInitialState( coInfo, LuiServiceConstants.COURSE_OFFERING_LIFECYCLE_KEY, context );

        // check the term and course
        TermInfo term = acalService.getTerm(termId, context);
        CourseInfo courseInfo = getCourse(courseId);
        // copy from canonical
        courseOfferingTransformer.copyFromCanonical(courseInfo, coInfo, optionKeys, context);
        //generate internal suffix code
        List<CourseOfferingInfo> existingCourseOfferings = findCourseOfferingsByTermAndCourseCode(term.getId(), courseInfo.getCode());
        String internalSufx = offeringCodeGenerator.generateCourseOfferingInternalCode(existingCourseOfferings);
        coInfo.setCourseNumberInternalSuffix(internalSufx);
        if (coInfo.getCourseNumberSuffix() != null && !coInfo.getCourseNumberSuffix().isEmpty()) {
            coInfo.setCourseOfferingCode(courseInfo.getCode() + coInfo.getCourseNumberSuffix());
        }
        if (optionKeys.contains(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY)) {
            coInfo.setCourseNumberSuffix(internalSufx);
            coInfo.setCourseOfferingCode(courseInfo.getCode() + internalSufx);
        }
        // copy to lui
        LuiInfo lui = new LuiInfo();
        courseOfferingTransformer.courseOffering2Lui(coInfo, lui, context);

        //Default the effective date to the start date
        lui.setEffectiveDate(term.getStartDate());

        // create it
        lui = luiService.createLui(courseId, termId, lui.getTypeKey(), lui, context);

        // transform it back to a course offering
        CourseOfferingInfo createdCo = new CourseOfferingInfo();

        courseOfferingTransformer.lui2CourseOffering(lui, createdCo, context);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY)) {
           courseOfferingTransformer.copyRulesFromCanonical(courseInfo, createdCo, optionKeys, context);
        }
        return createdCo;
    }

    private List<CourseOfferingInfo> findCourseOfferingsByTermAndCourseCode(String termId, String courseCode)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        if (StringUtils.isNotBlank(courseCode) && StringUtils.isNotBlank(termId)) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                    PredicateFactory.equal("atpId", termId)));
            QueryByCriteria criteria = qbcBuilder.build();

            //Do search. In ideal case, returns one element, which is the desired CO.
            courseOfferings = searchForCourseOfferings(criteria, new ContextInfo());
        }
        return courseOfferings;
    }

    private CourseInfo getCourse(String courseId) throws DoesNotExistException, OperationFailedException {
        R1CourseServiceHelper helper = new R1CourseServiceHelper(courseService, acalService);

        return helper.getCourse(courseId);
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo coInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        validateThatCourseOfferingIdsMatch( courseOfferingId, coInfo );

        // get the backing lui
        LuiInfo lui = luiService.getLui(courseOfferingId, context);

        //Move this to the validation decorator once we get the validations working
        if (!StringUtils.equals(lui.getStateKey(),coInfo.getStateKey())){
            throw new OperationFailedException("Changing the CourseOffering state is not supported with updateCourseOffering(). Please call changeCourseOfferingState() for state changes.");
        }

        // copy fields and update
        courseOfferingTransformer.courseOffering2Lui(coInfo, lui, context);

        // Update lprs for offering instructors
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("luiId", lui.getId()),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.COURSE_INSTRUCTOR_TYPE_KEYS));
        QueryByCriteria criteria = qbcBuilder.build();
        List<OfferingInstructorInfo> existingLprs = OfferingInstructorTransformer.lprs2Instructors(lprService.searchForLprs(criteria, context));
        // map existing lprs to their person id
        Map<String, OfferingInstructorInfo> existingPersonMap = new HashMap<String, OfferingInstructorInfo>(existingLprs.size());
        for (OfferingInstructorInfo info : existingLprs) {
            if (info.getStateKey() != null && info.getStateKey().equals(LprServiceConstants.DROPPED_STATE_KEY)) {
                continue;
            }
            existingPersonMap.put(info.getPersonId(), info);
        }

        List<OfferingInstructorInfo> createdInstructors = new ArrayList<OfferingInstructorInfo>();
        List<OfferingInstructorInfo> updatedInstructors = new ArrayList<OfferingInstructorInfo>();

        for (OfferingInstructorInfo instructor : coInfo.getInstructors()) {
            // if there is no id, it's a new Lpr
            if (instructor.getId() == null ) {
                createdInstructors.add(instructor);
            }


            // if the Lpr already exists, update it
            else if (existingPersonMap.containsKey(instructor.getPersonId())) {

                // remove the found entry from the existing map, to build the list of existing lprs to delete
                existingPersonMap.remove(instructor.getPersonId());
                updatedInstructors.add(instructor);

            }else{
                instructor.setId(null);
                createdInstructors.add(instructor);

            }
        }

        // the instructor objects remaining in the existing map should be marked for deletion,
        // since they were not found in the current list of instructors
        Collection<OfferingInstructorInfo> deletedInstructors = existingPersonMap.values();

        // create the new lprs
        List<LprInfo> createdLprs = OfferingInstructorTransformer.instructors2Lprs(lui, createdInstructors);
        for (LprInfo lprInfo : createdLprs) {
            lprService.createLpr(lprInfo.getPersonId(), lprInfo.getLuiId(), lprInfo.getTypeKey(), lprInfo, context);
        }

        // update existing lprs
        List<LprInfo> updatedLprs = OfferingInstructorTransformer.instructors2Lprs(lui, updatedInstructors);
        for (LprInfo lprInfo : updatedLprs) {
            lprService.updateLpr(lprInfo.getId(), lprInfo, context);
        }

        // delete removed lprs
        for (OfferingInstructorInfo instructorInfo : deletedInstructors) {
            lprService.deleteLpr(instructorInfo.getId(), context);
        }


        lui = luiService.updateLui(courseOfferingId, lui, context);

        return convertLuiToCourseOffering( lui, context );
    }

    private void validateThatCourseOfferingIdsMatch( String courseOfferingId, CourseOfferingInfo coInfo ) throws InvalidParameterException {

        if (!courseOfferingId.equals(coInfo.getId())) {
            throw new InvalidParameterException(courseOfferingId + " does not match the corresponding value in the object " + coInfo.getId());
        }
    }

    private CourseOfferingInfo convertLuiToCourseOffering( LuiInfo lui, ContextInfo contextInfo ) {

        CourseOfferingInfo co = new CourseOfferingInfo();
        courseOfferingTransformer.lui2CourseOffering(lui, co, contextInfo);

        return co;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId,
                                                                List<String> optionKeys,
                                                                ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        return this.businessLogic.updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo,
                                                             ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo,
                                                                          List<String> optionKeys,
                                                                          ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.businessLogic.validateCourseOfferingFromCanonical(courseOfferingInfo, optionKeys, context);
    }

    @Override
    @Transactional(readOnly = true)
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LuiInfo lui = luiService.getLui(formatOfferingId, context);
        FormatOfferingInfo fo = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, fo);
        LuiInfo coLui = this.findCourseOfferingLui(lui.getId(), context);
        fo.setCourseOfferingId(coLui.getId());
        return fo;
    }

    private LuiInfo findCourseOfferingLui(String formatOfferingId, ContextInfo context)
            throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException {

        int firstCOLuiInfo=0;

        List<LuiInfo> rels = luiService.getLuisByRelatedLuiAndRelationType(formatOfferingId,LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, context);

        if (rels.isEmpty()){
            throw new OperationFailedException("Format offering is not associated with a course offering " + formatOfferingId + " among " + rels.size());
        } else if (rels.size() > 1){
            throw new OperationFailedException("Multiple Format offerings found for an activity offering - " + formatOfferingId);
        } else {
            return rels.get(firstCOLuiInfo);
        }
    }

    private LuiInfo findFormatOfferingLui(String activityOfferingId, ContextInfo context)
            throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException {
;
        int firstFOLuiInfo=0;

        List<LuiInfo> rels = luiService.getLuisByRelatedLuiAndRelationType(activityOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, context);

        if (rels.isEmpty()){
            throw new OperationFailedException("Activity offering not associated with format offering " + activityOfferingId + " among " + rels.size());
        } else if (rels.size() > 1){
            throw new OperationFailedException("Multiple Format offerings found for an activity offering - " + activityOfferingId);
        } else {
            return rels.get(firstFOLuiInfo);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {

        return luiService.deleteLui(formatOfferingId, context);

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
                PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // get the existing
        LuiInfo lui = this.luiService.getLui(formatOfferingId, context);
        // transform and update
        new FormatOfferingTransformer().format2Lui(formatOfferingInfo, lui);
        lui = luiService.updateLui(formatOfferingId, lui, context);
        // rebuild the fo to return it
        FormatOfferingInfo fo = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, fo);
        LuiInfo coLui = this.findCourseOfferingLui(formatOfferingId, context);
        fo.setCourseOfferingId(coLui.getId());
        return fo;
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo,
                                                             ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo foInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {

        // validate params
        if (!courseOfferingId.equals(foInfo.getCourseOfferingId())) {
            throw new InvalidParameterException(courseOfferingId + " does not match the corresponding value in the object " + foInfo.getCourseOfferingId());
        }
        if (!formatId.equals(foInfo.getFormatId())) {
            throw new InvalidParameterException(formatId + " does not match the corresponding value in the object " + foInfo.getFormatId());
        }
        if (!formatOfferingType.equals(foInfo.getTypeKey())) {
            throw new InvalidParameterException(formatOfferingType + " does not match the corresponding value in the object " + foInfo.getTypeKey());
        }

        validateLuiIsInValidInitialState(foInfo, LuiServiceConstants.FORMAT_OFFERING_LIFECYCLE_KEY, context);

        // get the course offering
        CourseOfferingInfo co = this.getCourseOffering(courseOfferingId, context);
        if (foInfo.getTermId() != null && !co.getTermId().equals(foInfo.getTermId())) {
            throw new InvalidParameterException(foInfo.getTermId() + " term in the format offering does not match the one in the course offering " + co.getTermId());
        }
        foInfo.setTermId(co.getTermId());

        // Get existing format offerings (for use in prefix generation)
        List<FormatOfferingInfo> existingFos = getFormatOfferingsByCourseOffering(co.getId(), context);

        // get formatId out of the course
        CourseInfo course = this.getCourse(co.getCourseId()); // make sure it exists
        FormatInfo format = null;
        for (FormatInfo info : course.getFormats()) {
            if (info.getId().equals(formatId)) {
                format = info;
                break;
            }
        }
        if (format == null) {
            throw new OperationFailedException("Error creating format offering. Format does not exist with id " + formatId);
        }
        // Use dynamic attributes to set a prefix for the reg code generation (KSENROLL-6222)
        String prefix = "1";
        try {
            prefix = RegistrationGroupCodeUtil.computeRegCodePrefixForFo(existingFos, this, context);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("ERROR: assigning prefix for FO", e);
        }
        // Set a prefix for this newly created FO
        RegistrationGroupCodeUtil.addRegCodePrefixAttributeToFo(prefix + "", foInfo);

        // copy to lui
        LuiInfo lui = new LuiInfo();

        // Make the name of the FO correct
        generateLuiNameAndDescr(foInfo, context);

        new FormatOfferingTransformer().format2Lui(foInfo, lui);

        lui = luiService.createLui(lui.getCluId(), lui.getAtpId(), lui.getTypeKey(), lui, context);

        // now connect it to the course offering lui
        LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
        luiRel.setLuiId(courseOfferingId);
        luiRel.setName("co-fo-relation"); // TODO: This fixes a DB required field error--find more meaningful value.
        RichTextInfo descr = new RichTextInfo();
        String coCode = co.getCourseOfferingCode();
        if (coCode == null) {
            coCode = "NOCODE";
        }
        descr.setPlain(coCode + "-CO-FO"); // Useful for debugging
        descr.setFormatted(coCode + "-CO-FO"); // Useful for debugging
        luiRel.setDescr(descr);
        luiRel.setRelatedLuiId(lui.getId());
        luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        luiRel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY);
        luiRel.setEffectiveDate(new Date());

        luiRel = luiService.createLuiLuiRelation(luiRel.getLuiId(), luiRel.getRelatedLuiId(), luiRel.getTypeKey(), luiRel, context);

        // rebuild to return it
        FormatOfferingInfo formatOffering = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, formatOffering);
        formatOffering.setCourseOfferingId(luiRel.getLuiId());

        return formatOffering;
    }

    /**
     * Generates a name based on priority sorted names of the AO types within an FO
     *
     * @param foInfo the FO
     * @param context context  @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private void generateLuiNameAndDescr(FormatOfferingInfo foInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        CourseOfferingServiceExtender extender = getCourseOfferingServiceExtender();
        if (foInfo.getName()== null || foInfo.getName().isEmpty()) {
            //Get the activity type keys associated with this format
            List<String> activityTypeKeys = extender.getActivityTypesForFormatId(foInfo.getFormatId(), context);

            //Lookup the types to get the names
            List<TypeInfo> types = typeService.getTypesByKeys(activityTypeKeys, context);
            Collections.sort(types, new Comparator<TypeInfo>() {
                //Sort based on priority, then type key
                @Override
                public int compare(TypeInfo o1, TypeInfo o2) {
                    Integer o1Priority = null;
                    for(AttributeInfo attr:o1.getAttributes()){
                        if(TypeServiceConstants.ACTIVITY_SELECTION_PRIORITY_ATTR.equals(attr.getKey())){
                            o1Priority = Integer.parseInt(attr.getValue());
                            break;
                        }
                    }
                    Integer o2Priority = null;
                    for(AttributeInfo attr:o2.getAttributes()){
                        if(TypeServiceConstants.ACTIVITY_SELECTION_PRIORITY_ATTR.equals(attr.getKey())){
                            o2Priority = Integer.parseInt(attr.getValue());
                            break;
                        }
                    }
                    if(o1Priority == null){
                        if(o2Priority==null){
                            return o1.getKey().compareTo(o2.getKey());
                        }else{
                            return 1; //Having a priority sorts lower than not
                        }
                    }else{
                        if(o2Priority==null){
                            return -1;//having priority sorts lower than not
                        }else{
                            return o1Priority.compareTo(o2Priority);//compare priorities
                        }
                    }
                }
            });

            //build up the name in "Lecture/Lab" or "Lab Only" format
            StringBuilder sb = new StringBuilder();
            StringBuilder shortSb = new StringBuilder();
            for(Iterator<TypeInfo> typeIter = types.iterator();typeIter.hasNext();){
                TypeInfo type = typeIter.next();
                sb.append(type.getName());
                shortSb.append(type.getName().substring(0,Math.min(type.getName().length(),3)).toUpperCase());//Lecutre->LEC lab->LAB
                if(typeIter.hasNext()){
                    sb.append("/");
                    shortSb.append("/");
                }
            }
            if(types.size()==1){
                sb.append(" Only");
            }
            foInfo.setName(sb.toString());
            if(foInfo.getShortName()==null||foInfo.getShortName().isEmpty()){
                foInfo.setShortName(shortSb.toString());
            }
        }
        //Set the description
        if(foInfo.getDescr()==null){
            foInfo.setDescr(new RichTextInfo());
            foInfo.getDescr().setFormatted("Courses with "+foInfo.getName());
            foInfo.getDescr().setPlain("Courses with "+foInfo.getName());
        }
    }


    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsForSeatPoolDefinition(
            String seatPoolDefinitionId,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ActivityOfferingInfo> activityOfferingInfos = new ArrayList<ActivityOfferingInfo>();

        if (StringUtils.isNotBlank(seatPoolDefinitionId)) {
            SeatPoolDefinitionEntity poolEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId);
            if (null == poolEntity) {
                throw new DoesNotExistException(seatPoolDefinitionId);
            }
            String activityOfferingId = poolEntity.getActivityOfferingId();
            ActivityOfferingInfo activityOfferingInfo = getActivityOffering(activityOfferingId, context);

            activityOfferingInfos.add(activityOfferingInfo);
        }

        return activityOfferingInfos;
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return typeService.getType(activityOfferingTypeKey, context);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        try {
            return typeService.getTypesForGroupType(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Invalid group type used to retrieve Activity Offering Types: " + LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return typeService.getAllowedTypesForType(activityTypeKey, context);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LuiInfo lui = luiService.getLui(activityOfferingId, context);
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ActivityOfferingTransformer.lui2Activity(ao, lui, lprService, searchService, context);

        LuiInfo foLui = this.findFormatOfferingLui(ao.getId(), context);
        LuiInfo coLui = this.findCourseOfferingLui(foLui.getId(), context);

        AtpInfo termAtp = getAtpService().getAtp(ao.getTermId(), context);
        
        populateActivityOfferingRelationships(ao, coLui, foLui, termAtp, context);

        return ao;
    }

    private void populateActivityOfferingRelationships(ActivityOfferingInfo ao, LuiInfo luiCO, LuiInfo luiFO, AtpInfo termAtp, ContextInfo context) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
        if (luiCO == null){
            throw new MissingParameterException("LuiInfo dto for CO is null");
        }

        if (luiFO == null){
            throw new MissingParameterException("LuiInfo dto for FO is null");
        }

        String foId = luiFO.getId();
        String foShortName = luiFO.getOfficialIdentifier() == null ? null : luiFO.getOfficialIdentifier().getShortName();
        String coId = luiCO.getId();
        String coCode = luiCO.getOfficialIdentifier().getCode();
        String coLongName = luiCO.getOfficialIdentifier().getLongName();

        ao.setFormatOfferingId(foId);
        ao.setCourseOfferingId(coId);
        ao.setFormatOfferingName(foShortName);
        ao.setCourseOfferingCode(coCode);
        ao.setCourseOfferingTitle(coLongName);

        ao.setTermCode(termAtp.getCode());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> luiIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	
    	if (luiIds == null || luiIds.isEmpty())
    		return new ArrayList<ActivityOfferingInfo>();
    	
    	SearchRequestInfo request = new SearchRequestInfo();
    	
    	request.setSearchKey(ActivityOfferingSearchServiceImpl.CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_KEY);
    	
    	request.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_IDS, luiIds);
    	
    	SearchResultInfo results = searchService.search(request, contextInfo);
    	
    	List<SearchResultRowInfo> rows = results.getRows();
    	
    	Set<String>coIds = new HashSet<String>();
    	Set<String>foIds = new HashSet<String>();
    	Set<String>atpIds = new HashSet<String>();
    	
    	Map<String, String>aoId2foIdMap = new HashMap<String, String>();
    	Map<String, String>aoId2coIdMap = new HashMap<String, String>();
    	
    	Map<String, String>aoId2atpIdMap = new HashMap<String, String>();
    	
    	for (SearchResultRowInfo rowInfo : rows) {
			
    		String coId = null;
    		String foId = null;
    		String aoId = null;
    		
    		String aoTermId = null;
    		
    		for (SearchResultCellInfo cellInfo : rowInfo.getCells()) {
				
    			/*
    			 * Load the CO Id, FO Id, AO Id, and AO Atp Id for the current row.
    			 */
    			if (cellInfo.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_ID))
    				coId = cellInfo.getValue();
    			else if (cellInfo.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID))
    				foId = cellInfo.getValue();
    			else if (cellInfo.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID))
    				aoId = cellInfo.getValue();
    			else if (cellInfo.getKey().equals(ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID))
    				aoTermId = cellInfo.getValue();
    			else
    				throw new OperationFailedException("unknown cell key value: " + cellInfo.getKey());
			}
    		
    		/*
    		 * record the associations of this ao to the co, fo and ao term.
    		 */
    		coIds.add(coId);
    		
    		foIds.add(foId);
    		
    		atpIds.add(aoTermId);
    		
    		aoId2coIdMap.put(aoId, coId);
    		aoId2foIdMap.put(aoId, foId);
    		
    		aoId2atpIdMap.put(aoId, aoTermId);
		}
    	
    	List<String>totalLuiIds = new ArrayList<String>();
    	
    	totalLuiIds.addAll(luiIds);
    	
    	totalLuiIds.addAll (coIds);
    	totalLuiIds.addAll (foIds);
    	
    	List<LuiInfo> totalLiuInfos = getLuiService().getLuisByIds(totalLuiIds, contextInfo);
    	
    	
    	Map<String, LuiInfo>coId2LuiMap = new HashMap<String, LuiInfo>();
    	Map<String, LuiInfo>foId2LuiMap = new HashMap<String, LuiInfo>();
    	
    	List<LuiInfo>aoLuiInfos = new ArrayList<LuiInfo>();
    	
    	for (LuiInfo luiInfo : totalLiuInfos) {
			
    		String typeKey = luiInfo.getTypeKey();
    		
    		if (LuiServiceConstants.COURSE_OFFERING_TYPE_KEY.equals(typeKey)) {
    			// accumulate co Id -> lui map
    			coId2LuiMap.put(luiInfo.getId(), luiInfo);
    		}
    		else if (LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY.equals(typeKey)) {
    			// accumulate fo Id -> lui map
    			foId2LuiMap.put(luiInfo.getId(), luiInfo);
    		}
    		else {
    			// assume its an ao and accumulate the lui
    			aoLuiInfos.add(luiInfo);
    		}
		}
    	
    	
    	List<AtpInfo>atpInfos = getAtpService().getAtpsByIds(new ArrayList<String>(atpIds), contextInfo);
    	
    	Map<String, AtpInfo>atpId2AtpMap = new HashMap<String, AtpInfo>();
    	
    	
    	for (AtpInfo atpInfo : atpInfos) {
			
    		atpId2AtpMap.put(atpInfo.getId(), atpInfo);
		}
    	
    	List<ActivityOfferingInfo> aos = ActivityOfferingTransformer.luis2AOs(aoLuiInfos, getLprService(), getSearchService(), contextInfo);
    	

    	for (ActivityOfferingInfo ao : aos) {
			
    			String coId = aoId2coIdMap.get(ao.getId());
    			
    			String foId = aoId2foIdMap.get(ao.getId());
    	
    			String atpId = aoId2atpIdMap.get(ao.getId());
    			
    			LuiInfo coLui = coId2LuiMap.get(coId);

    			LuiInfo foLui = foId2LuiMap.get(foId);

    			AtpInfo atp = atpId2AtpMap.get(atpId);
    			
                populateActivityOfferingRelationships(ao, coLui, foLui, atp, contextInfo);

        }

        return aos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<String> aoIds = new ArrayList<String>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_AND_FO_IDS_BY_CO_ID_SEARCH_TYPE.getKey());
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, context);
        } catch (Exception e) {
            throw new OperationFailedException ("search failed for courseOfferingId = " + courseOfferingId, e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for(SearchResultCellInfo cellInfo : row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cellInfo.getKey())){
                    aoIds.add(cellInfo.getValue());
                }

            }
        }
        return this.getActivityOfferingsByIds(aoIds,context);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	
        List<ActivityOfferingInfo> activityOfferings = new ArrayList<ActivityOfferingInfo>();

        // Find all related luis to the course Offering
        
        List<LuiInfo> luis = luiService.getRelatedLuisByLuiAndRelationType(formatOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, contextInfo);
        
        List<String>aoIds = new ArrayList<String>();
        
        for (LuiInfo luiInfo : luis) {
			
        	aoIds.add(luiInfo.getId());
		}
        
        
        activityOfferings = getActivityOfferingsByIds(aoIds, contextInfo);

        Collections.sort(activityOfferings, new Comparator<ActivityOfferingInfo>() {
            @Override
            public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
                if (o1.getActivityCode() == null) {
                    return 1;
                } else if (o2.getActivityCode() == null) {
                    return -1;
                } else {
                    return o1.getActivityCode().compareTo(o2.getActivityCode());
                }
            }
        });
        return activityOfferings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(String formatOfferingId,
                                                                                         ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<String> aoIds = new ArrayList<String>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE.getKey());
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.FO_ID, formatOfferingId);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for(SearchResultCellInfo cellInfo : row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cellInfo.getKey())){
                    aoIds.add(cellInfo.getValue());
                }

            }
        }
        return this.getActivityOfferingsByIds(aoIds,contextInfo);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getAllowedTimeSlotIdsForActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

     /**
      * Provides a list of valid {@link TimeSlotInfo}s for a given Activity Offering.
      */
    @Override
    public List<TimeSlotInfo> getAllowedTimeSlotsForActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TimeSlotInfo> getAllowedTimeSlotsByDaysAndStartTimeForActivityOffering(String activityOfferingId, List<Integer> daysOfWeek, TimeOfDayInfo startTime, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Get the ATP id from the AO's LUI.
        LuiInfo aoLui = getLuiService().getLui(activityOfferingId, contextInfo);
        String atpId = aoLui.getAtpId();
        // Get the ATP type key. Using the ATP service since it is available and quicker.
        AtpInfo atpInfo = getAtpService().getAtp(atpId, contextInfo);
        String atpTypeKey = atpInfo.getTypeKey();

        // Get Time Slot types which are related to the ATP. In most cases there should only be one.
        List<TypeTypeRelationInfo> ttRelations =
            getTypeService().getTypeTypeRelationsByOwnerAndType(atpTypeKey, TypeServiceConstants.TYPE_TYPE_RELATION_ATP2TIMESLOT_TYPE_KEY, contextInfo);

        if (ttRelations.isEmpty()) {
            throw new OperationFailedException(String.format("Unable to find time slots because no time slot types are associated with ATP type %s.", atpTypeKey));
        }

        List<String> timeSlotTypeKeys = new ArrayList<String>();
        for (TypeTypeRelationInfo ttRelation : ttRelations) {
            timeSlotTypeKeys.add(ttRelation.getRelatedTypeKey());
        }

        List<TimeSlotInfo> timeSlots = new ArrayList<TimeSlotInfo>();
        for (String tsType : timeSlotTypeKeys) {
            timeSlots.addAll(getSchedulingService().getTimeSlotsByDaysAndStartTime(tsType, daysOfWeek, startTime, contextInfo));
        }

        return timeSlots;
    }

    private ActivityOfferingInfo cAoInitActivityOffering(CourseOfferingInfo co, FormatOfferingInfo fo, LuiInfo lui, LuiLuiRelationInfo luiRel, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ActivityOfferingTransformer.lui2Activity(ao, lui, lprService, searchService, context);
        ao.setFormatOfferingId(luiRel.getLuiId());
        ao.setCourseOfferingId(co.getId());
        ao.setFormatOfferingName(fo.getShortName());
        ao.setCourseOfferingCode(co.getCourseOfferingCode());
        ao.setCourseOfferingTitle(co.getCourseOfferingTitle());
        String aoTermId = ao.getTermId();
        // KSENROLL-7795 Use AtpService to make this quicker since loading ATP is faster than loading
        // a TermInfo.
        AtpInfo termAtp = getAtpService().getAtp(aoTermId, context);
        ao.setTermCode(termAtp.getCode());
        return ao;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
                                                       String activityId,
                                                       String activityOfferingTypeKey,
                                                       ActivityOfferingInfo aoInfo, ContextInfo context)
        throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        // validate params (may throw InvalidParameterException)
        cAoValidateParams(aoInfo, formatOfferingId, activityId, activityOfferingTypeKey);

        validateLuiIsInValidInitialState( aoInfo, LuiServiceConstants.ACTIVITY_OFFERING_LIFECYCLE_KEY, context );

        // get the required objects checking they exist
        FormatOfferingInfo fo = this.getFormatOffering(formatOfferingId, context);
        CourseOfferingInfo co = this.getCourseOffering(fo.getCourseOfferingId(), context);
        if (aoInfo.getTermId() != null) {
            if (!aoInfo.getTermId().equals(fo.getTermId())) {
                // KSENROLL-7795: AO must be a subterm of FO's term.  Verify this is the case
                List<TermInfo> parentTerms = acalService.getContainingTerms(aoInfo.getTermId(), context);
                // KSENROLL-7795 Should just be one parent term given tree assumption about parent-child terms
                if (parentTerms == null || parentTerms.size() != 1) {
                    throw new InvalidParameterException("In createActivityOffering, can only have one parent term for a subterm");
                }
                String parentTermId = KSCollectionUtils.getRequiredZeroElement(parentTerms).getId();
                if (!parentTermId.equals(fo.getTermId())) {
                    // KSENROLL-7795 Throw exception if the parent term ID of the AO term does not match FO's term ID
                    throw new InvalidParameterException(aoInfo.getTermId() + " term in the activity offering is not subterm of format offering term (" + fo.getTermId() + ")");
                }
            }
        } else {
            // KSENROLL-7795 Since AO termID is null, set it to the same as the FO
            aoInfo.setTermId(fo.getTermId());
        }

        // check that the passed in activity code does not already exist for that course offering
        if (context.getAttributeValue("skip.aocode.validation") == null ||
                context.getAttributeValue("skip.aocode.validation").equals("false")) {
            // TODO: This is a hack to avoid setting the AO code for rollover--will be fixed by bigger solution later on.
            cAoSetActivityCodeForAO(aoInfo, co, context);
        }

        // copy to the lui
        LuiInfo lui = new LuiInfo();
        ActivityOfferingTransformer.activity2Lui(aoInfo, lui);

        String cluId = lui.getCluId();
        String atpId = lui.getAtpId();
        String typeKey = lui.getTypeKey();
        lui = luiService.createLui(cluId, atpId, typeKey, lui, context);

        // build LPR(s) for Offering Instructor
        List<LprInfo> lprs = OfferingInstructorTransformer.instructors2Lprs(lui, aoInfo.getInstructors());

        for (LprInfo lprInfo : lprs) {
            lprService.createLpr(lprInfo.getPersonId(), lprInfo.getLuiId(), lprInfo.getTypeKey(), lprInfo, context);
        }

        // now build the lui lui relation
        LuiLuiRelationInfo luiRel = cAoBuildLuiLuiRelation(aoInfo, lui, formatOfferingId, context);

        // Everything saved to the DB, now return AO sent back by createLui and transformed by transformer back to caller
        return cAoInitActivityOffering(co, fo, lui, luiRel, context);

    }

    /**
     * Validate input parameters
     * @param aoInfo The AO to check
     * @param formatOfferingId FO ID for this AO
     * @param activityId CLU ID for the AO
     * @param activityOfferingTypeKey The type key for the AO
     * @throws InvalidParameterException
     */
    private void cAoValidateParams(ActivityOfferingInfo aoInfo,
                                   String formatOfferingId,
                                   String activityId,
                                   String activityOfferingTypeKey) throws InvalidParameterException {
        if (!formatOfferingId.equals(aoInfo.getFormatOfferingId())) {
            throw new InvalidParameterException(formatOfferingId + " does not match the corresponding value in the object " + aoInfo.getFormatOfferingId());
        }
        if (!activityId.equals(aoInfo.getActivityId())) {
            throw new InvalidParameterException(activityId + " does not match the corresponding value in the object " + aoInfo.getActivityId());
        }
        if (!activityOfferingTypeKey.equals(aoInfo.getTypeKey())) {
            throw new InvalidParameterException(activityOfferingTypeKey + " does not match the corresponding value in the object " + aoInfo.getTypeKey());
        }
    }

    private void cAoSetActivityCodeForAO(ActivityOfferingInfo aoInfo, CourseOfferingInfo co, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        CourseOfferingServiceExtender extender = getCourseOfferingServiceExtender();
        // pull the current list of Ao's from the DB.
        Map<String,String> aoMap = extender.computeAoIdToAoCodeMapByCourseOffering(co.getId(), context);
        List<String> aoCodeList = null;
        Collection<String>  coll = aoMap.values();

        if (coll instanceof List) {
            aoCodeList = (List) coll;
        } else {
            aoCodeList = new ArrayList(coll);
        }

        if (aoInfo.getActivityCode() == null) {
            // If there is no activity code, create a new one
            aoInfo.setActivityCode(getNextActivityOfferingCode(co, aoCodeList,context));
        } else {
            for (String existingAoCode : aoCodeList) {
                if (aoInfo.getActivityCode().equals(existingAoCode)) {
                    throw new InvalidParameterException("Activity Offering Code '" + aoInfo.getActivityCode()
                            + "' already exists for course code " + co.getCourseOfferingCode()
                            + " term Id '" + co.getTermId() + "'");
                }
            }
        }
    }

    /**
     *
     * When generating activity codes we need to make sure there are no duplicates. The generator should be thread safe,
     * but that does't matter if the aoList passed into the generator is stale. so, attepmt to get a good code, but if
     * there is a duplicate, try again recursivly.
     *
     * @param coInfo
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    protected String getNextActivityOfferingCode(CourseOfferingInfo coInfo, List<String> existingAoCodes,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String activityCode = "";

        Map<String, Object> generatorProperties = new HashMap<String, Object>();

        generatorProperties.put(CourseOfferingCodeGenerator.COURSE_OFFERING_CODE_KEY, coInfo.getId());
        generatorProperties.put(CourseOfferingCodeGenerator.ACTIVITY_OFFERING_CODE_LIST_KEY, existingAoCodes);

        // get the next activity code based off the current list of activities
        activityCode = offeringCodeGenerator.generateActivityOfferingCode(generatorProperties);

        return activityCode;
    }

    private LuiLuiRelationInfo cAoBuildLuiLuiRelation(ActivityOfferingInfo aoInfo, LuiInfo lui, String formatOfferingId, ContextInfo context)
            throws OperationFailedException, DoesNotExistException,
                PermissionDeniedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {

        LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
        luiRel.setLuiId(formatOfferingId);
        luiRel.setName("fo-ao-relation"); // TODO: This fixes a DB required field error--find more meaningful value.
        luiRel.setRelatedLuiId(lui.getId());
        RichTextInfo descr = new RichTextInfo();
        String coCode = aoInfo.getCourseOfferingCode();
        if (coCode == null) {
            coCode = "NOCODE";
        }
        descr.setPlain(coCode + "-FO-AO"); // Useful for debugging
        descr.setFormatted(coCode + "-FO-AO"); // Useful for debugging
        luiRel.setDescr(descr);
        luiRel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY);
        luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        luiRel.setEffectiveDate(new Date());

        luiRel = luiService.createLuiLuiRelation(luiRel.getLuiId(), luiRel.getRelatedLuiId(), luiRel.getTypeKey(), luiRel, context);

        return luiRel;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ActivityOfferingInfo copyActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseOfferingServiceExtender extender = getCourseOfferingServiceExtender();
        ActivityOfferingInfo sourceAo = getActivityOffering(activityOfferingId, context);
        ActivityOfferingInfo copiedAo = extender.copyActivityOffering(sourceAo, this, context);
        return copiedAo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<ActivityOfferingInfo> generateActivityOfferings(String formatOfferingId, String activityOfferingType, Integer quantity, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId,
                                                       ActivityOfferingInfo activityOfferingInfo,
                                                       ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // validate params
        if (!activityOfferingId.equals(activityOfferingInfo.getId())) {
            throw new InvalidParameterException(activityOfferingId + " does not match the corresponding value in the object " + activityOfferingInfo.getId());
        }
        // get it
        LuiInfo lui = luiService.getLui(activityOfferingId, context);
        // TODO: check that the lui being updated is an activity not another kind of lui

        // copy to lui
        ActivityOfferingTransformer.activity2Lui(activityOfferingInfo, lui);

        // update lui
        lui = luiService.updateLui(activityOfferingId, lui, context);

        // Update lprs for offering instructors
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("luiId", activityOfferingId),
                PredicateFactory.in("personRelationTypeId", LprServiceConstants.COURSE_INSTRUCTOR_TYPE_KEYS));
        QueryByCriteria criteria = qbcBuilder.build();
        List<OfferingInstructorInfo> existingLprs = OfferingInstructorTransformer.lprs2Instructors(lprService.searchForLprs(criteria, context));
        // map existing lprs to their person id
        Map<String, OfferingInstructorInfo> existingPersonMap = new HashMap<String, OfferingInstructorInfo>(existingLprs.size());
        for (OfferingInstructorInfo info : existingLprs) {
            existingPersonMap.put(info.getId(), info);
        }

        List<OfferingInstructorInfo> createdInstructors = new ArrayList<OfferingInstructorInfo>();
        List<OfferingInstructorInfo> updatedInstructors = new ArrayList<OfferingInstructorInfo>();

        for (OfferingInstructorInfo instructor : activityOfferingInfo.getInstructors()) {
            // if there is no id, it's a new Lpr
            if (instructor.getId() == null) {
                createdInstructors.add(instructor);
            }
            // if the Lpr already exists, update it
            else if (existingPersonMap.containsKey(instructor.getId())) {
                updatedInstructors.add(instructor);
                // remove the found entry from the existing map, to build the list of existing lprs to delete
                existingPersonMap.remove(instructor.getId());
            }
        }

        // the instructor objects remaining in the existing map should be marked for deletion,
        // since they were not found in the current list of instructors
        Collection<OfferingInstructorInfo> deletedInstructors = existingPersonMap.values();


        // create the new lprs
        List<LprInfo> createdLprs = OfferingInstructorTransformer.instructors2Lprs(lui, createdInstructors);
        for (LprInfo lprInfo : createdLprs) {
            lprService.createLpr(lprInfo.getPersonId(), lprInfo.getLuiId(), lprInfo.getTypeKey(), lprInfo, context);
        }

        // update existing lprs
        List<LprInfo> updatedLprs = OfferingInstructorTransformer.instructors2Lprs(lui, updatedInstructors);
        for (LprInfo lprInfo : updatedLprs) {
            lprService.updateLpr(lprInfo.getId(), lprInfo, context);
        }

        // delete removed lprs
        for (OfferingInstructorInfo instructorInfo : deletedInstructors) {
            lprService.deleteLpr(instructorInfo.getId(), context);
        }

        // rebuild activity to return it
        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ActivityOfferingTransformer.lui2Activity(ao, lui, lprService, searchService, context);
        FormatOfferingInfo foInfo = this.getFormatOffering(activityOfferingInfo.getFormatOfferingId(), context);
        CourseOfferingInfo coInfo = this.getCourseOffering(foInfo.getCourseOfferingId(), context);
        ao.setFormatOfferingId(foInfo.getId());
        ao.setCourseOfferingId(coInfo.getId());
        ao.setFormatOfferingName(foInfo.getName());
        ao.setCourseOfferingCode(coInfo.getCourseOfferingCode());
        ao.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());
        // KSENROLL-7795 Fetching Atp is more efficient than fetching Term
        AtpInfo term = getAtpService().getAtp(ao.getTermId(), context);
        ao.setTermCode(term.getCode());
        return ao;
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(activityOfferingId, context);

        if (!checkTypeForActivityOfferingType(lui.getTypeKey(), context)) {
            throw new InvalidParameterException("Given lui id ( " + activityOfferingId + " ) is not an Activity Offering");
        }

        //  Clean up ScheduleRequestSets
        try {
            dAoCleanUpScheduleRequestSets(activityOfferingId, context);
        } catch (Exception e) {
            throw new OperationFailedException("Unable to clean up schedule request sets", e);
        }

        try {
            // delete offering instructor lprs for the Activity Offering
            deleteLprsByLui(activityOfferingId, context);

            return luiService.deleteLui(activityOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Error deleting dependent objects", e);
        }
    }

    /**
     * Cleans up ScheduleRequestSets when deleting an ActivityOffering.
     * If the AO being deleted is the last member of the ScheduleRequestSet then the set should be deleted.
     * Otherwise, the AO id should be removed from the set. (This situation would exist for colocated AOs)
     *
     * @param activityOfferingId
     * @param context
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private void dAoCleanUpScheduleRequestSets(String activityOfferingId, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
                VersionMismatchException, ReadOnlyException, DataValidationErrorException, DoesNotExistException {
        List<ScheduleRequestSetInfo> scheduleRequestSets = getSchedulingService()
                .getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityOfferingId, context);
        for (ScheduleRequestSetInfo srs : scheduleRequestSets) {
            //  If there is more than one AO id associated with the SRS then remove this AO's id. Otherwise, delete the set.
            if (srs.getRefObjectIds().size() > 1) {
                srs.getRefObjectIds().remove(activityOfferingId);
                getSchedulingService().updateScheduleRequestSet(srs.getId(), srs, context);
            } else {
                getSchedulingService().deleteScheduleRequestSet(srs.getId(), context);
            }
        }
    }

    private StatusInfo deleteWaitListFromAo(String activityOfferingId, String formatOfferingId, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ReadOnlyException, VersionMismatchException, DataValidationErrorException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        // it is assumed that the list will contain only zero/one element in it.
        List<CourseWaitListInfo> courseWaitListInfos = courseWaitListService.getCourseWaitListsByActivityOffering(activityOfferingId, context);
        for(CourseWaitListInfo courseWaitListInfo : courseWaitListInfos){
            if (courseWaitListInfo.getActivityOfferingIds().size() == 1) {
                courseWaitListService.deleteCourseWaitList(courseWaitListInfo.getId(), context);
            } else {
                // remove deleted AO from shared WL
                courseWaitListInfo.getActivityOfferingIds().remove(activityOfferingId);
                courseWaitListInfo.getFormatOfferingIds().remove(formatOfferingId);
                getCourseWaitListService().updateCourseWaitList(courseWaitListInfo.getId(), courseWaitListInfo, context);
            }
        }
        return status;
    }

    private StatusInfo deleteExamOfferingFromAo(String activityOfferingId, ContextInfo context) throws MissingParameterException,
            InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        List<String> eoRelationIds =  this.getExamOfferingService().getExamOfferingRelationIdsByActivityOffering(activityOfferingId, context);
        for(String eoRelationId : eoRelationIds){
            ExamOfferingRelationInfo eoRelation = this.getExamOfferingService().getExamOfferingRelation(eoRelationId, context);
            if(eoRelation.getActivityOfferingIds().size()==1){
                this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
                this.getExamOfferingService().deleteExamOffering(eoRelation.getExamOfferingId(), context);
            } else {
                eoRelation.getActivityOfferingIds().remove(activityOfferingId);
                try {
                    this.getExamOfferingService().updateExamOfferingRelation(eoRelationId, eoRelation, context);
                } catch (DataValidationErrorException e) {
                    throw new OperationFailedException("Unable to remove activity offering from exam offering relation", e);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Unable to remove activity offering from exam offering relation", e);
                } catch (VersionMismatchException e) {
                    throw new OperationFailedException("Unable to remove activity offering from exam offering relation", e);
                }
            }
        }
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
	public StatusInfo deleteActivityOfferingCascaded(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

    	ActivityOfferingInfo ao = getActivityOffering(activityOfferingId, context);
    	
        List<SeatPoolDefinitionInfo> seatPoolsToDelete = getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
        deleteSeatPoolsFromAo(seatPoolsToDelete, activityOfferingId, context);
        try {
			deleteWaitListFromAo(activityOfferingId, ao.getFormatOfferingId(), context);
		} catch (DataValidationErrorException e) {
			throw new OperationFailedException(e);
		} catch (ReadOnlyException e) {
			throw new OperationFailedException(e);
		} catch (VersionMismatchException e) {
			throw new OperationFailedException(e);
		}
        deleteExamOfferingFromAo(activityOfferingId, context);
        removeActivityOfferingFromAoCluster(activityOfferingId, context);

        return deleteActivityOffering(activityOfferingId, context);

    }

    /* Delete RegGroups attached to the AO */
    private void deleteRegGroupsForAo( String activityOfferingId, ContextInfo context )
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        // Delete RGs attached to this AO
        List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByActivityOffering(activityOfferingId, context);
        if (regGroups != null && !regGroups.isEmpty()) {
            for (RegistrationGroupInfo regGroup : regGroups) {
                deleteRegistrationGroup(regGroup.getId(), context);
            }
        }
    }

    /* Removes the seat pool reference from the AO and then deletes the orphaned seat pool */
    private void deleteSeatPoolsFromAo( List<SeatPoolDefinitionInfo> seatPoolsToDelete, String activityOfferingId, ContextInfo context )
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        for (SeatPoolDefinitionInfo seatPool : seatPoolsToDelete) {
            removeSeatPoolDefinitionFromActivityOffering(seatPool.getId(), activityOfferingId, context);
            deleteSeatPoolDefinition(seatPool.getId(), context);
        }
    }


    /* Removes the AO from AO cluster */
    private void removeActivityOfferingFromAoCluster(String activityOfferingId, ContextInfo context) {

        boolean exceptionThrown = false;
        try {
            ActivityOfferingInfo aoInfo = getActivityOffering(activityOfferingId, context);
            List<ActivityOfferingClusterInfo> aoClusters
                    = getActivityOfferingClustersByFormatOffering(aoInfo.getFormatOfferingId(), context);
            for (ActivityOfferingClusterInfo cluster: aoClusters) {
                // In M5, you'd expect only one AO cluster to contain an AO ID, but just in case it changes,
                // this will check all AO clusters to remove an AO ID.
                List<ActivityOfferingSetInfo> aoSets = cluster.getActivityOfferingSets();
                boolean changed = false;
                for (ActivityOfferingSetInfo set: aoSets) {
                    List<String> aoIds = set.getActivityOfferingIds();
                    if (aoIds.contains(activityOfferingId)) {
                        aoIds.remove(activityOfferingId);
                        changed = true;
                    }
                }
                if (changed) {
                    // Update, but only if an AO has been deleted
                    updateActivityOfferingCluster(aoInfo.getFormatOfferingId(), cluster.getId(), cluster, context);
                }
            }

        } catch (InvalidParameterException e) {
            exceptionThrown = true;
        } catch (ReadOnlyException e) {
            exceptionThrown = true;
        } catch (DoesNotExistException e) {
            exceptionThrown = true;
        } catch (DataValidationErrorException e) {
            exceptionThrown = true;
        } catch (PermissionDeniedException e) {
            exceptionThrown = true;
        } catch (VersionMismatchException e) {
            exceptionThrown = true;
        } catch (OperationFailedException e) {
            exceptionThrown = true;
        } catch (MissingParameterException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            // Avoids catching all exceptions
            LOGGER.warn("Unable to find AO: {}", activityOfferingId);
        }
    }

    /**
     * This implementation is the work-around for M5 that lacks an actual scheduler.
     * The schedule request that is bound to the Activity Offering is directly translated into an actual schedule,
     * which is persisted through the scheduling service.
     *
     * @param activityOfferingId Id of the Activity Offering to be scheduled.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a StatusInfo indicating the operation was successful
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @Override
    @Transactional
    public StatusInfo scheduleActivityOffering(String activityOfferingId,
                                               ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        /**
         * This method essentially has to do the following until we implement external scheduler
         * 1. Know the list of current schedule Ids for an AO
         * 2. Get Schedule Requests for an AO.
         * 3. Convert all the Schedule Requests to Schedule
         * 4. Update AO with the latest schedule Ids
         * 5. If it's a colo, update all the AOs in that set with new sch ids
         * 6. Once updated, delete all the old schedules.
         */
        ActivityOfferingInfo aoInfo = getActivityOffering(activityOfferingId, contextInfo);

        List<ScheduleRequestInfo> requests = getSchedulingService()
                .getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityOfferingId, contextInfo);

        StatusInfo result = new StatusInfo();

        //  Save the list of schedule Ids associated with the AO to be deleted for later.
        List<String> scheduleInfoListToDelete = new ArrayList<String>(aoInfo.getScheduleIds());
        aoInfo.getScheduleIds().clear();

        String aoSchedulingStateKey = LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY;
        if (requests != null && !requests.isEmpty()) {
            for (ScheduleRequestInfo request : requests) {
                ScheduleInfo scheduleInfo = new ScheduleInfo();

                // short cut the submission to the scheduler, and just translate the schedule request components to actuals
                SchedulingServiceUtil.requestToSchedule(request, scheduleInfo,getRoomService(),contextInfo);

                scheduleInfo.setAtpId(aoInfo.getTermId());
                try {
                    ScheduleInfo persistedSchedule = schedulingService.createSchedule(scheduleInfo.getTypeKey(), scheduleInfo, contextInfo);
                    request.setScheduleId(persistedSchedule.getId());
                    schedulingService.updateScheduleRequest(request.getId(), request, contextInfo);
                    aoInfo.getScheduleIds().add(persistedSchedule.getId());

                    if (!persistedSchedule.getScheduleComponents().isEmpty()) {
                        for (ScheduleComponentInfo actualScheduleComponent : persistedSchedule.getScheduleComponents()) {
                            if (!actualScheduleComponent.getIsTBA()) {
                                aoSchedulingStateKey = LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY;
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new OperationFailedException("createSchedule failed due to the following uncaught exception: " + e.getClass().getSimpleName() + " " + e.getMessage(), e);
                }
            }
        } else {
            aoSchedulingStateKey = LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY;
        }


        try {
            aoInfo.setSchedulingStateKey(aoSchedulingStateKey);
            updateActivityOffering(aoInfo.getId(), aoInfo, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result.setSuccess(true);
        result.setMessage("New Schedule Successfully created");

        int firstScheduleRequestInfo=0;

        //  If the AO is colocated then update the related AO's schedule Ids.
        if (aoInfo.getIsColocated() && !requests.isEmpty()){
            // Only processing the SRS for the first schedule request only works for full colocation (An AO only has
            // one SRS). When partial colocation is implemented (and an AO may be associated with multiple SRSs),
            // getScheduleRequestSetsByRefObject(...) will have to be called and the collection of SRSs will have to be
            // iterated through.
            ScheduleRequestSetInfo schSet = getSchedulingService()
                    .getScheduleRequestSet(requests.get(firstScheduleRequestInfo).getScheduleRequestSetId(),contextInfo);
            for (String aoId : schSet.getRefObjectIds()){
                if (!StringUtils.equals(aoId,aoInfo.getId())) {
                    ActivityOfferingInfo colo = getActivityOffering(aoId,contextInfo);
                    colo.getScheduleIds().clear();
                    colo.getScheduleIds().addAll(aoInfo.getScheduleIds());
                    try {
                        colo.setSchedulingStateKey(aoSchedulingStateKey);  //update the colo AO to have the same scheduling state key
                        updateActivityOffering(colo.getId(), colo, contextInfo);
                    } catch (Exception e) {
                        throw new OperationFailedException("Error updating schedule id for the colo activity offering - " + e.getMessage(),e);
                    }
                }
            }
        }

        //  Delete orphaned schedules.
        for(String scheduleId : scheduleInfoListToDelete){
            /**
             * Only delete the schedules if they are not used by other AOs in a colocation. To determine this look up
             * the ScheduleRequests associated with the schedule. Then lookup the SRS associated with the SR. If the
             * list of ref object Ids in the SRS does NOT contain the Id of this AO this is shouldn't be deleted. (This
             * situation can happen when an AO is removed from a colocation)
             */
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.equal("scheduleId", scheduleId));

            QueryByCriteria criteria = qbcBuilder.build();

            List<ScheduleRequestInfo> schInfos = getSchedulingService().searchForScheduleRequests(criteria,contextInfo);

            boolean deleteSchedule = true;
            if (!schInfos.isEmpty()){
                ScheduleRequestSetInfo setInfo = getSchedulingService()
                        .getScheduleRequestSet(schInfos.get(firstScheduleRequestInfo).getScheduleRequestSetId(),contextInfo);
                if (!setInfo.getRefObjectIds().contains(aoInfo.getId())){
                    deleteSchedule = false;
                }
            }

            if (deleteSchedule){
                StatusInfo statusInfo = getSchedulingService().deleteSchedule(scheduleId,contextInfo);
                if (!statusInfo.getIsSuccess()){
                     throw new OperationFailedException("Error deleting schedule" + scheduleId);
                }
            }
        }
        return result;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType,
                                                               ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(registrationGroupId, context);
        if (lui == null) {
            throw new DoesNotExistException("registrationGroupId does not exist: " + registrationGroupId);
        }
        RegistrationGroupInfo rgInfo = registrationGroupTransformer.lui2Rg(lui, context);
        rgInfo.setCourseOfferingId(this.getFormatOffering(rgInfo.getFormatOfferingId(), context).getCourseOfferingId());

        return rgInfo;

    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupsIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        List<LuiInfo> luis = getLuiService().getLuisByIds(registrationGroupsIds, contextInfo);

        for (LuiInfo lui : luis) {
            regGroups.add( registrationGroupTransformer.lui2Rg(lui, contextInfo));
        }

        return regGroups;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> rgs = new ArrayList<RegistrationGroupInfo>();
        List<String> rgIds = new ArrayList<String>();
        List<LuiLuiRelationInfo> relsCOtoFO = luiService.getLuiLuiRelationsByLui(courseOfferingId, context);
        if (relsCOtoFO != null && !relsCOtoFO.isEmpty()) {
            for (LuiLuiRelationInfo relCOtoFO : relsCOtoFO) {
                if (relCOtoFO.getLuiId().equals(courseOfferingId) && relCOtoFO.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY)) {
                    String foId = relCOtoFO.getRelatedLuiId();
                    List<LuiLuiRelationInfo> relsFOtoRG = luiService.getLuiLuiRelationsByLui(foId, context);
                    if (relsFOtoRG != null && !relsFOtoRG.isEmpty()) {
                        for (LuiLuiRelationInfo relFOtoRG : relsFOtoRG) {
                            if (relFOtoRG.getLuiId().equals(foId) && relFOtoRG.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY)) {
                                String luiId = relFOtoRG.getRelatedLuiId();
                                LuiInfo lui = luiService.getLui(luiId, context);
                                if (lui != null && lui.getTypeKey().equals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY) && !rgIds.contains(luiId)) {
                                    rgIds.add(luiId);
                                    rgs.add(getRegistrationGroup(luiId, context));
                                }
                            }
                        }
                    }
                }
            }
        }

        return rgs;
    }

    @Override
    @Transactional(readOnly = true)
    //TODO - lets try to use a single DB call to accomplish this!
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(List<String> activityOfferingIds,
                                                                                  ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();
        Set aoIdSet = new HashSet(activityOfferingIds);
        if (activityOfferingIds != null && !activityOfferingIds.isEmpty()) {
            String firstAoId = KSCollectionUtils.getRequiredZeroElement(activityOfferingIds);
            // Pick an ID to search RGs by
            List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByActivityOffering(firstAoId, context);
            if (regGroups != null) {
                for (RegistrationGroupInfo rgInfo: regGroups) {
                    // Verify that all the AO ids appear in this rgInfo
                    Set rgAoIds = new HashSet(rgInfo.getActivityOfferingIds());
                    if (rgAoIds.containsAll(aoIdSet)) {
                        // Yes, so add it to the list
                        regGroupList.add(rgInfo);
                    }
                }
            }
        }

        return regGroupList;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();

        List<String> rgIds = luiService.getLuiIdsByRelatedLuiAndRelationType(activityOfferingId, LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, context);
        if (rgIds != null && !rgIds.isEmpty()) {
            regGroups.addAll(getRegistrationGroupsByIds(rgIds, context));

            // Now sort based on reg group code order (alphabetical order works fine)
            // TODO: figure out how to write a compare method that makes sense given different code generators.
            Collections.sort(regGroups, new Comparator<RegistrationGroupInfo>() {
                @Override
                public int compare(RegistrationGroupInfo o1, RegistrationGroupInfo o2) {
                    if (o1 == null) {
                        return -1;
                    } else if (o2 == null) {
                        return 1;
                    } else {
                        // We assume <name> stores the registration group code as 4-digit string
                        return o1.getName().compareTo(o2.getName());
                    }
                }
            });
            return regGroups;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        // Find all related luis to the format offering
        List<LuiInfo> luis = luiService.getRelatedLuisByLuiAndRelationType(formatOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);
        for (LuiInfo lui : luis) {
            RegistrationGroupInfo rgInfo = registrationGroupTransformer.lui2Rg(lui, context);
            rgInfo.setCourseOfferingId(this.getFormatOffering(rgInfo.getFormatOfferingId(), context).getCourseOfferingId());
            regGroups.add(rgInfo);
        }

        // Now sort based on reg group code order (alphabetical order works fine)
        // TODO: figure out how to write a compare method that makes sense given different code generators.
        Collections.sort(regGroups, new Comparator<RegistrationGroupInfo>() {
            @Override
            public int compare(RegistrationGroupInfo o1, RegistrationGroupInfo o2) {
                if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                } else {
                    // We assume <name> stores the registration group code as 4-digit string
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        return regGroups;
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        try {
            return businessLogic.generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
        } catch (DoesNotExistException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException("Registration Groups generation has failed! ", e);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return businessLogic.generateRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {

        // validate params
        if (!registrationGroupId.equals(registrationGroupInfo.getId())) {
            throw new InvalidParameterException(registrationGroupId + " does not match the corresponding value in the object " + registrationGroupInfo.getId());
        }

        // get it
        LuiInfo lui = luiService.getLui(registrationGroupId, context);

        // Throw exception if a state change is attempted
        if (!registrationGroupInfo.getStateKey().equals(lui.getStateKey())) {
            throw new ReadOnlyException("state key can only be changed by calling changeRegistrationGroupState");
        }
        //TO DO: Check that the Registration code is unique within a CO. If it is a duplicate, do not change it

        Set<String> existingRelatedLuiIds = new HashSet<String>();
        Set<String> newRelatedLuiIds = new HashSet<String>(registrationGroupInfo.getActivityOfferingIds());

        //Update LLR
        List<LuiLuiRelationInfo> llrs = luiService.getLuiLuiRelationsByLui(registrationGroupId, context);
        for (LuiLuiRelationInfo llr : llrs) {
            if (registrationGroupId.equals(llr.getLuiId()) && LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY.equals(llr.getTypeKey())) {
                String relatedLuiId = llr.getRelatedLuiId();
                existingRelatedLuiIds.add(relatedLuiId);
                if (!newRelatedLuiIds.contains(relatedLuiId)) {
                    luiService.deleteLuiLuiRelation(llr.getId(), context);
                }
            } else if (registrationGroupId.equals(llr.getRelatedLuiId())
                    && LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY.equals(llr.getTypeKey())
                    && !llr.getLuiId().equals(registrationGroupInfo.getFormatOfferingId())) {
                luiService.deleteLuiLuiRelation(llr.getId(), context);
                createLuiLuiRelationForRegGroups(registrationGroupInfo.getFormatOfferingId(), registrationGroupId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);
            }
        }
        // Create relations for added Activity Offerings or Course Offering
        for (String luiId : newRelatedLuiIds) {
            if (!existingRelatedLuiIds.contains(luiId)) {
                createLuiLuiRelationForRegGroups(registrationGroupId, luiId, LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, context);
            }
        }

        LuiInfo regGroupLui = registrationGroupTransformer.rg2Lui(registrationGroupInfo, context);
        LuiInfo updatedRegGroupLui = luiService.updateLui(regGroupLui.getId(), regGroupLui, context);

        // Everything saved to the DB, now return RG sent back by createLui and transformed by transformer back to caller
        RegistrationGroupInfo rgInfo = registrationGroupTransformer.lui2Rg(updatedRegGroupLui, context);
        rgInfo.setCourseOfferingId(registrationGroupInfo.getCourseOfferingId());
        rgInfo.setRegistrationCode(updatedRegGroupLui.getOfficialIdentifier().getCode());
        return rgInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            LuiInfo fetch = luiService.getLui(registrationGroupId, context);
            if (fetch == null) {
                throw new DoesNotExistException("Registration Group, " + registrationGroupId + ", does not exist");
            }
            // Make sure we have correct type before deleting
            if (!LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY.equals(fetch.getTypeKey())) {
                throw new InvalidParameterException("ID, " + registrationGroupId + ", does not have a registration group type");
            }
            return luiService.deleteLui(registrationGroupId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Could not delete LUI '" + registrationGroupId + "'", e);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> deleteRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Quick verification
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();

        try {
            List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByFormatOffering(formatOfferingId, context);
            for (RegistrationGroupInfo regGroup : regGroups) {

                BulkStatusInfo bulkStatus = bulkDeleteRegistrationGroup(regGroup, context);

                rgChanges.add(bulkStatus);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("", e);
        }
        return rgChanges;
    }

    private BulkStatusInfo bulkDeleteRegistrationGroup(RegistrationGroupInfo regGroup, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo status = deleteRegistrationGroup(regGroup.getId(), context);

        BulkStatusInfo bulkStatus = new BulkStatusInfo();

        bulkStatus.setId(regGroup.getId());
        bulkStatus.setSuccess(status.getIsSuccess());
        bulkStatus.setMessage("Registration Group Deleted");

        return bulkStatus;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<BulkStatusInfo> deleteGeneratedRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        try {
            List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByFormatOffering(formatOfferingId, context);
            for (RegistrationGroupInfo regGroup : regGroups) {
                if (regGroup.getIsGenerated()) {
                    // Only delete reg groups that are generated
                    BulkStatusInfo bulkStatus = bulkDeleteRegistrationGroup(regGroup, context);

                    rgChanges.add(bulkStatus);
                }
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("deleteGeneratedRegistrationGroupsByFormatOffering(formatOfferingId="+formatOfferingId+") failed", e);
        }
        return rgChanges;
    }

    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        try {
            List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);
            for (RegistrationGroupInfo rgInfo : regGroups){

                BulkStatusInfo bulkStatus = bulkDeleteRegistrationGroup(rgInfo, contextInfo);

                rgChanges.add(bulkStatus);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("deleteRegistrationGroupsForCluster(AOCId="+activityOfferingClusterId+") failed: ", e);
        }

        return rgChanges;
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(String registrationGroupId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // Use the extender to implement
        CourseOfferingServiceExtender extender = getCourseOfferingServiceExtender();
        return extender.verifyRegistrationGroup(registrationGroupId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, String activityOfferingClusterId, String registrationGroupType,
                                                                RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {

        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityOfferingClusterInfo getActivityOfferingCluster(String activityOfferingClusterId,
                                                                  ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        ActivityOfferingClusterEntity activityOfferingClusterEntity = activityOfferingClusterDao.find(activityOfferingClusterId);
        if (null == activityOfferingClusterEntity) {
            throw new DoesNotExistException(activityOfferingClusterId);
        }
        return activityOfferingClusterEntity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByIds(List<String> activityOfferingClusterIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(activityOfferingClusterIds == null) {
            throw new MissingParameterException("activityOfferingClusterIds is required");
        }

        List<ActivityOfferingClusterInfo> results = new ArrayList<ActivityOfferingClusterInfo>();

        try {
            List<ActivityOfferingClusterEntity> entities = activityOfferingClusterDao.findByIds(activityOfferingClusterIds);
            if(entities != null && !entities.isEmpty()) {
                for(ActivityOfferingClusterEntity entity : entities) {
                    results.add(entity.toDto());
                }
            }
        } catch(Exception e) {
            //forced to catch a generic exception by activityOfferingClusterDao.findByIds
            throw new OperationFailedException("failed to load activity offering clusters by ids", e);
        }

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingClusterEntity> entities = activityOfferingClusterDao.getByFormatOffering(formatOfferingId);
        List<ActivityOfferingClusterInfo> list = new ArrayList<ActivityOfferingClusterInfo>(entities.size());
        for (ActivityOfferingClusterEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    private Set<String> verifyUniquenessOfAoTypes(ActivityOfferingClusterInfo clusterInfo) throws InvalidParameterException {
        Set<String> aoTypeSet = new HashSet<String>();
        if (clusterInfo.getActivityOfferingSets() == null) {
            return aoTypeSet;
        }

        // Check that each AO set has a non-null type and no two sets have the same AO type
        for (ActivityOfferingSetInfo setInfo: clusterInfo.getActivityOfferingSets()) {
            String aoType = setInfo.getActivityOfferingType();
            if (aoType == null) {
                throw new InvalidParameterException("Activity Offering Set has null AO type");
            }
            // Make sure you haven't seen this AO type before--if so, exception
            if (aoTypeSet.contains(aoType)) {
                throw new InvalidParameterException("AO type, " + aoType + ", appears more than once in AO set of AO cluster");
            }
            aoTypeSet.add(aoType);
        }
        return aoTypeSet;
    }

    private void verifyClusterAoTypesMatchFoAoTypes(Set<String> clusterAoTypes, Set<String> foAoTypes,
                                                    ActivityOfferingClusterInfo clusterInfo,
                                                    String foId) throws InvalidParameterException {
        if (!clusterAoTypes.equals(foAoTypes)) {
            Set<String> aoTypeSetCopy = new HashSet<String>(clusterAoTypes);
            aoTypeSetCopy.removeAll(foAoTypes);
            if (!aoTypeSetCopy.isEmpty()) {
                // There are aoTypes in the cluster, which do not appear in the fo's ao types
                StringBuilder error = new StringBuilder();
                for (String aoType: aoTypeSetCopy) {
                    error.append(aoType).append(" ");
                }
                error.append("not valid AO types for FO (").append(foId).append(")");
                throw new InvalidParameterException(error.toString());
            } else {
                // All cluster AO types exist in FO but some are missing, so fill in missing ones
                Set<String> missingAoTypes = new HashSet<String>(foAoTypes);
                missingAoTypes.removeAll(clusterAoTypes);
                for (String aoType: missingAoTypes) {
                    ActivityOfferingSetInfo setInfo = new ActivityOfferingSetInfo();
                    setInfo.setActivityOfferingType(aoType);
                    clusterInfo.getActivityOfferingSets().add(setInfo);
                }
            }
        }
    }

    private void verifyAoIdsInCorrectAoSet(ActivityOfferingClusterInfo clusterInfo, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        for (ActivityOfferingSetInfo setInfo: clusterInfo.getActivityOfferingSets()) {
            String aoType = setInfo.getActivityOfferingType();
            for (String aoId: setInfo.getActivityOfferingIds()) {
                LuiInfo lui = luiService.getLui(aoId, contextInfo);
                if (!lui.getTypeKey().equals(aoType)) {
                    throw new InvalidParameterException("AO (" + lui.getId() + ") does not match AOset's AoType, " + aoType);
                }
            }
        }
    }

    /**
     * Mostly throws InvalidParameterException if data validation fails.  If there are missing AOsets in the
     * cluster, this will fill them in as a side effect, provided nothing else is wrong.
     * @param foInfo Format offering info
     * @param clusterInfo AO cluster info
     * @param contextInfo Context
     */
    private void verifyAOSetsInCluster(FormatOfferingInfo foInfo, ActivityOfferingClusterInfo clusterInfo,
                                       ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            OperationFailedException, PermissionDeniedException {
        // Make sure types are unique
        Set<String> clusterAoTypes = verifyUniquenessOfAoTypes(clusterInfo);
        List<String> aoTypes = foInfo.getActivityOfferingTypeKeys();
        Set<String> foAoTypes = new HashSet<String>(aoTypes);
        if (foAoTypes.size() != aoTypes.size()) {
            // FOs should not have more than one AO type
            throw new InvalidParameterException("FO (" + foInfo.getId() + ") has AO types that appear more than once");
        }
        verifyClusterAoTypesMatchFoAoTypes(clusterAoTypes, foAoTypes, clusterInfo, foInfo.getId());
        verifyAoIdsInCorrectAoSet(clusterInfo, contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ActivityOfferingClusterInfo createActivityOfferingCluster(String formatOfferingId,
                                                                     String activityOfferingClusterTypeKey,
                                                                     ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                     ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {

        // validate params
        if (!formatOfferingId.equals(activityOfferingClusterInfo.getFormatOfferingId())) {
            throw new InvalidParameterException(formatOfferingId + " does not match the corresponding value in the object " + activityOfferingClusterInfo.getFormatOfferingId());
        }
        if (!activityOfferingClusterTypeKey.equals(activityOfferingClusterInfo.getTypeKey())) {
            throw new InvalidParameterException(activityOfferingClusterTypeKey + " does not match the corresponding value in the object " + activityOfferingClusterInfo.getTypeKey());
        }
        // Make sure that there are as many AOSets as AO types in the FO
        FormatOfferingInfo foInfo = getFormatOffering(formatOfferingId, contextInfo);
        if (activityOfferingClusterInfo.getActivityOfferingSets() == null ||
                activityOfferingClusterInfo.getActivityOfferingSets().isEmpty()) {
            // If it's empty
            createAOSets(foInfo, activityOfferingClusterInfo);
        } else {
            verifyAOSetsInCluster(foInfo, activityOfferingClusterInfo, contextInfo);  // Throws exception if it fails to verify
        }
        // persist
        ActivityOfferingClusterEntity activityOfferingClusterEntity =
                new ActivityOfferingClusterEntity(activityOfferingClusterInfo);

        activityOfferingClusterEntity.setEntityCreated(contextInfo);
        //activityOfferingClusterEntity.setEntityUpdated(contextInfo);
        activityOfferingClusterDao.persist(activityOfferingClusterEntity);
        activityOfferingClusterDao.getEm().flush();
        return activityOfferingClusterEntity.toDto();
    }

    private void createAOSets(FormatOfferingInfo foInfo, ActivityOfferingClusterInfo clusterInfo) {
        if (clusterInfo.getActivityOfferingSets() == null) {
            clusterInfo.setActivityOfferingSets(new ArrayList<ActivityOfferingSetInfo>());
        }
        List<ActivityOfferingSetInfo> setInfos = clusterInfo.getActivityOfferingSets();
        List<String> aoTypeKeys = foInfo.getActivityOfferingTypeKeys();
        if (aoTypeKeys != null) {
            for (String aoTypeKey: aoTypeKeys) {
                // Create an AOSetInfo
                ActivityOfferingSetInfo setInfo = new ActivityOfferingSetInfo();
                setInfo.setActivityOfferingType(aoTypeKey);
                setInfo.setActivityOfferingIds(new ArrayList<String>()); // leave it empty for now
                // Add it to the list
                setInfos.add(setInfo);
            }
        }
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(String validationTypeKey, String formatOfferingId,
                                                                      ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                      ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
                PermissionDeniedException {

        List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>();
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();

        Set<String> totals = new HashSet<String>();
        for(ActivityOfferingSetInfo activityOfferingSet : activityOfferingClusterInfo.getActivityOfferingSets()){

            //Perform a search that returns each AOSet and the total max enrollment for each
            if (activityOfferingSet.getActivityOfferingIds() != null && !activityOfferingSet.getActivityOfferingIds().isEmpty()) {
                SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_KEY);
                searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_IDS, activityOfferingSet.getActivityOfferingIds());
                SearchResultInfo searchResult = searchService.search(searchRequest, contextInfo);
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    for (SearchResultCellInfo cell : row.getCells()) {
                        if (ActivityOfferingSearchServiceImpl.SearchResultColumns.TOTAL_MAX_SEATS.equals(cell.getKey())) {
                            String totalMaxSeatsStr = cell.getValue();
                            if (totals.contains(totalMaxSeatsStr)) {
                                validationResultInfo.setLevel(ValidationResult.ErrorLevel.WARN);
                                validationResultInfo.setMessage("Sum of enrollment for each AO type is not equal");
                                validationResultInfos.add(validationResultInfo);

                                return validationResultInfos;
                            }
                            totals.add(totalMaxSeatsStr);
                        }
                    }
                }
            }
        }

        //The max enrollment numbers of all the aoSets in the given AOC are the same. The validation passes.
        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfo.setMessage("Sum of enrollment for each AO type is equal");
        validationResultInfos.add(validationResultInfo);
        return validationResultInfos;
    }

    @Override
    public AOClusterVerifyResultsInfo verifyActivityOfferingClusterForGeneration(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
                PermissionDeniedException {

        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = new AOClusterVerifyResultsInfo();
        List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>() ;
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();

        ActivityOfferingClusterInfo aoCInfo = getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        List<ActivityOfferingSetInfo> aoSetInfos = aoCInfo.getActivityOfferingSets();

        for (ActivityOfferingSetInfo aoSetInfo : aoSetInfos ){
            List<String> aoIdList = aoSetInfo.getActivityOfferingIds();
            if (aoIdList == null || aoIdList.isEmpty()) {
                //invalidValidationInfo.setError("");
                validationResultInfo.setLevel(ValidationResult.ErrorLevel.WARN);
                validationResultInfo.setMessage("AO type: " + aoSetInfo.getActivityOfferingType() + " doesn't have AOs attached to it");
                validationResultInfos.add(validationResultInfo);
                aoClusterVerifyResultsInfo.setValidationResults(validationResultInfos);

                return aoClusterVerifyResultsInfo;
            }
        }

        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfo.setMessage("Each AO type has AOs attached to it");
        validationResultInfos.add(validationResultInfo);
        aoClusterVerifyResultsInfo.setValidationResults(validationResultInfos);

        return aoClusterVerifyResultsInfo;

    }

    private void uAoCDeleteRegGroupsWithAosNotInCluster(ActivityOfferingClusterInfo clusterInfo, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException,
                OperationFailedException {

        // Find all AO IDs in this cluster
        List<ActivityOfferingSetInfo> aoSetInfos = clusterInfo.getActivityOfferingSets();
        Set<String> aoIdsInCluster = new HashSet<String>();
        for (ActivityOfferingSetInfo setInfo: aoSetInfos) {
            //  Loop through and add all AO IDs from each of the sets
            aoIdsInCluster.addAll(setInfo.getActivityOfferingIds());
        }
        // For each reg group, look at its list of AO Ids.  If all of them are in the cluster, good.
        // If not, add into regGroupIdsToDelete
        List<RegistrationGroupInfo> regGroups =
                getRegistrationGroupsByActivityOfferingCluster(clusterInfo.getId(), contextInfo);
        List<String> regGroupIdsToDelete = new ArrayList<String>();
        for (RegistrationGroupInfo regGroup: regGroups) {
            List<String> regGroupAoIds = regGroup.getActivityOfferingIds();
            if (!aoIdsInCluster.containsAll(regGroupAoIds)) {
                // Didn't find all AOs from the reg group AO IDs
                regGroupIdsToDelete.add(regGroup.getId());
            }
            // In case we have mismatch between the number of sets (AO types) in cluster and number of AOs in reg group
            if ((clusterInfo.getActivityOfferingSets().size() > regGroup.getActivityOfferingIds().size())
                    && !regGroupIdsToDelete.contains(regGroup.getId())) {
                regGroupIdsToDelete.add(regGroup.getId());
            }
        }
        // Delete the reg groups in the list
        for (String regGroupIdToDelete: regGroupIdsToDelete) {
            deleteRegistrationGroup(regGroupIdToDelete, contextInfo);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(String formatOfferingId,
                                                                     String activityOfferingClusterId,
                                                                     ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                     ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {

        ActivityOfferingClusterEntity activityOfferingClusterEntity = activityOfferingClusterDao.find(activityOfferingClusterId);
        if (null != activityOfferingClusterEntity) {
            if (!activityOfferingClusterEntity.getActivityOfferingClusterState().equals(activityOfferingClusterInfo.getStateKey())) {
                throw new ReadOnlyException("state key can only be changed by calling changeActivityOfferingClusterState");
            }
            FormatOfferingInfo foInfo = getFormatOffering(formatOfferingId, contextInfo);
            verifyAOSetsInCluster(foInfo, activityOfferingClusterInfo, contextInfo);

            List<Object> orphans = activityOfferingClusterEntity.fromDto(activityOfferingClusterInfo);
            // Delete any orphaned children
            for (Object orphan : orphans){
                activityOfferingClusterDao.getEm().remove(orphan);
            }
            activityOfferingClusterEntity.setEntityUpdated(contextInfo);

            ActivityOfferingClusterEntity mergedEntity = activityOfferingClusterDao.merge(activityOfferingClusterEntity);

            activityOfferingClusterDao.getEm().flush();

            ActivityOfferingClusterInfo merged = mergedEntity.toDto();
            // Delete reg groups with AOs no longer in AO cluster (put here, in case merge fails--then, this code won't
            // run.
            uAoCDeleteRegGroupsWithAosNotInCluster(merged, contextInfo);
            return merged;
        } else {
            throw new DoesNotExistException("No activityOfferingCluster has been found for activityOfferingClusterId=" + activityOfferingClusterId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteActivityOfferingCluster(String activityOfferingClusterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DependentObjectsExistException {

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        ActivityOfferingClusterEntity activityOfferingClusterEntity = activityOfferingClusterDao.find(activityOfferingClusterId);
        if (null != activityOfferingClusterEntity) {
            // Don't delete AOC if there are dependent RGs.
            List<RegistrationGroupInfo> rgInfos =
                    getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, context);
            if (rgInfos != null && !rgInfos.isEmpty()) {
                throw new DependentObjectsExistException("Activity offering cluster (id: " + activityOfferingClusterId + ") has attached reg groups");
            }
            // Delete attributes
            if (activityOfferingClusterEntity.getAttributes() != null) {
                for(ActivityOfferingClusterAttributeEntity attr:activityOfferingClusterEntity.getAttributes()) {
                    activityOfferingClusterDao.getEm().remove(attr);
                }
            }
            // Delete AOSets
            if (activityOfferingClusterEntity.getAoSets()!=null) {
                for (ActivityOfferingSetEntity aoSet:activityOfferingClusterEntity.getAoSets()) {
                    activityOfferingClusterDao.getEm().remove(aoSet);
                }
                activityOfferingClusterEntity.getAoSets().clear();
            }
            activityOfferingClusterDao.remove(activityOfferingClusterEntity);
        } else {
            throw new DoesNotExistException(activityOfferingClusterId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        SeatPoolDefinitionEntity poolEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId); // throws DoesNotExistException
        if (null == poolEntity) {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return poolEntity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<SeatPoolDefinitionInfo> seatPoolDefinitionInfos = new ArrayList<SeatPoolDefinitionInfo>();
        if (StringUtils.isNotBlank(activityOfferingId)) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(
                    PredicateFactory.equal("activityOfferingId", activityOfferingId));
            QueryByCriteria criteria = qbcBuilder.build();

            //Do search. In ideal case, returns one element, which is the desired SeatPool.
            seatPoolDefinitionInfos = searchForSeatpoolDefinitions(criteria, new ContextInfo());
            Collections.sort(seatPoolDefinitionInfos, new Comparator<SeatPoolDefinitionInfo>() {
                @Override
                public int compare(SeatPoolDefinitionInfo o1, SeatPoolDefinitionInfo o2) {
                    if (o1.getProcessingPriority() == null) {
                        return -1;
                    } else if (o2.getProcessingPriority() == null) {
                        return 1;
                    }
                    return o1.getProcessingPriority().compareTo(o2.getProcessingPriority());
                }
            });
        }
        return seatPoolDefinitionInfos;

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        SeatPoolDefinitionEntity poolEntity = new SeatPoolDefinitionEntity(seatPoolDefinitionInfo);
        poolEntity.setEntityCreated(context);
        poolEntity.setEntityUpdated(context);
        seatPoolDefinitionDao.persist(poolEntity);
        seatPoolDefinitionDao.getEm().flush();
        return poolEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId,
                                                           SeatPoolDefinitionInfo seatPoolDefinitionInfo,
                                                           ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        SeatPoolDefinitionEntity seatPoolDefinitionEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId);
        if (null != seatPoolDefinitionEntity) {
            seatPoolDefinitionEntity.fromDto(seatPoolDefinitionInfo);
            seatPoolDefinitionEntity.setEntityUpdated(context);

            SeatPoolDefinitionEntity mergedEntity = seatPoolDefinitionDao.merge(seatPoolDefinitionEntity);

            seatPoolDefinitionDao.getEm().flush();

            return mergedEntity.toDto();

        } else {
            throw new DoesNotExistException("No SeatPool found for seatPoolDefinitionId=" + seatPoolDefinitionId);
        }
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey,
                                                                 SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
                PermissionDeniedException {
        // TODO: KSENROLL-2658
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        SeatPoolDefinitionEntity popEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId);
        if (null != popEntity) {
            seatPoolDefinitionDao.remove(popEntity);
        } else {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return status;
    }


    /**
     * This method allows you to search for Course Offering Ids by Criteria. In order to make this search more usable it has been backed
     * by the "CriteriaLookupService". This service allows us to join across entities. For example, you are able to pass in
     * "courseOfferingCode" with a value of "CHEM199" even though the code does not live on the LuiEntity (which backs Course Offerings).
     *
     * The CourseOfferingCriteriaTransform is coded to wire in the additional database joins needed to complete the search.
     *
     * Please look in CourseOfferingCriteriaTransformer for a complete list of available mappings.
     *
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
         //Add luiType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);

        GenericQueryResults<String> results = coCriteriaLookupService.lookupIds(LuiEntity.class, newCriteria);
        
        if (results != null)
        	return results.getResults();
        else {
			return new ArrayList<String>(0);
		}
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
    	List<String> courseOfferingIds = searchForCourseOfferingIds(criteria,
				context);

		int results = courseOfferingIds.size();

		if (results > 1) {
			try {
				return getCourseOfferingsByIds(courseOfferingIds, context);
			} catch (DoesNotExistException e) {
				throw new OperationFailedException(
						"Failed to bulk load course offerings by ids: "
								+ StringUtils.join(courseOfferingIds, ", "), e);
			}
		} else if (results == 1) {
			String courseOfferingId = courseOfferingIds.get(0);
			CourseOfferingInfo co;
			try {
				co = getCourseOffering(courseOfferingId, context);
			} catch (DoesNotExistException e) {
				throw new OperationFailedException(
						"Failed to load course offerings with id: "
								+ courseOfferingId, e);
			}
			return Arrays.asList(new CourseOfferingInfo[] { co });
		} else {
			return new ArrayList<CourseOfferingInfo>(0);
		}

        
    }

    private void createLuiLuiRelationForRegGroups(String luiId, String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
            luiRel.setLuiId(luiId);
            luiRel.setRelatedLuiId(relatedLuiId);
            luiRel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
            luiRel.setEffectiveDate(new Date());
            try {
                luiService.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiRel, context);
            } catch (ReadOnlyException roe) {
                throw new OperationFailedException("setting read only fields", roe);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e);
        }
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate
        QueryByCriteria newCriteria = addLuiTypeLikePredicate(criteria, "kuali.lui.type.activity.offering.*");

        GenericQueryResults<LuiEntity> results = aoCriteriaLookupService.lookup(LuiEntity.class, newCriteria);

        List<ActivityOfferingInfo> activityOfferingInfos = new ArrayList<ActivityOfferingInfo>(results.getResults().size());
        for (LuiEntity lui : results.getResults()) {
            try {
                ActivityOfferingInfo ao = this.getActivityOffering(lui.getId(), context);
                activityOfferingInfos.add(ao);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException(lui.getId(), ex);
            }
        }
        return activityOfferingInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate
        QueryByCriteria newCriteria = addLuiTypeLikePredicate(criteria, "kuali.lui.type.activity.offering.*");

        GenericQueryResults<String> results =  aoCriteriaLookupService.lookupIds(LuiEntity.class, newCriteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                criteria.getPredicate(),
                PredicateFactory.equal(LuiServiceConstants.PREDICATE_PATH_FOR_RELATEDLUI_LUITYPE, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY),
                PredicateFactory.equal(LuiServiceConstants.PREDICATE_PATH_FOR_LUILUIRELATIONTYPE, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY)));

        GenericQueryResults<LuiLuiRelationEntity> results =  rgCriteriaLookupService.lookup(LuiLuiRelationEntity.class, qbcBuilder.build());

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        for (LuiLuiRelationEntity  luiLuiRelation : results.getResults()) {
            try {
                RegistrationGroupInfo rgInfo = registrationGroupTransformer.lui2Rg(luiLuiRelation.getRelatedLui().toDto(), context);
                rgInfo.setCourseOfferingId(this.getFormatOffering(rgInfo.getFormatOfferingId(), context).getCourseOfferingId());
                regGroups.add(rgInfo); // Add the reg group
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("searchForRegistrationGroups", ex);
            }
        }

        return regGroups;
    }

    private boolean _checkTypeForRegistrationGroupType(String typeKey) {
        return LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY.equals(typeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate
        List<String> relatedItems = new ArrayList<>();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                criteria.getPredicate(),
                PredicateFactory.equal(LuiServiceConstants.PREDICATE_PATH_FOR_RELATEDLUI_LUITYPE, LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY),
                PredicateFactory.equal(LuiServiceConstants.PREDICATE_PATH_FOR_LUILUIRELATIONTYPE, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY)));

        GenericQueryResults<LuiLuiRelationEntity> results =  rgCriteriaLookupService.lookup(LuiLuiRelationEntity.class, qbcBuilder.build());
        for (LuiLuiRelationEntity items :results.getResults()){
            relatedItems.add(items.getRelatedLui().getId());
        }
        return relatedItems;
    }

    private QueryByCriteria addLuiTypeLikePredicate(QueryByCriteria criteria, String luiType){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
               criteria.getPredicate(),
               PredicateFactory.like(PREDICATE_FACTORY_PATH_FOR_LUITYPE, luiType)));
        return qbcBuilder.build();
    }

    private QueryByCriteria addLuiTypeEqualPredicate(QueryByCriteria criteria, String luiType){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
               criteria.getPredicate(),
               PredicateFactory.equal(PREDICATE_FACTORY_PATH_FOR_LUITYPE, luiType)));
        return qbcBuilder.build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        GenericQueryResults<SeatPoolDefinitionEntity> results = spCriteriaLookupService.lookup(SeatPoolDefinitionEntity.class, criteria);
        List<SeatPoolDefinitionInfo> seatPoolDefinitions = new ArrayList<SeatPoolDefinitionInfo>(results.getResults().size());
        for (SeatPoolDefinitionEntity seatPoolEntity : results.getResults()) {
            SeatPoolDefinitionInfo sp = seatPoolEntity.toDto();
            seatPoolDefinitions.add(sp);
        }
        return seatPoolDefinitions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForSeatpoolDefinitionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    private boolean checkTypeForActivityOfferingType(String typeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> types = getActivityOfferingTypes(context);
        return checkTypeInTypes(typeKey, types);
    }

    private boolean checkTypeInTypes(String typeKey, List<TypeInfo> types) {
        if (types != null && !types.isEmpty()) {
            for (TypeInfo type : types) {
                if (type.getKey().equals(typeKey)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeInfo getCourseOfferingType(String courseOfferingTypeKey,
                                          ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getCourseOfferingTypes(ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(
            String courseOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(
            String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo addSeatPoolDefinitionToActivityOffering(String seatPoolDefinitionId, String activityOfferingId,
                                                              ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {


        if (!luiExists(activityOfferingId,contextInfo)) {
            throw new DoesNotExistException("Activity offering does not exist with ID: " + activityOfferingId);
        }
        // The seat pool definition is connected only via the entity.  The DTO does not store the
        // activity offering ID.
        SeatPoolDefinitionEntity seatPoolEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId);
        seatPoolEntity.setActivityOfferingId(activityOfferingId);
        try {
            seatPoolDefinitionDao.merge(seatPoolEntity);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("version mismatch exception when saving seatPoolEntity(id=" + seatPoolEntity.getId() + ")", e);
        }
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // should be supported in M4
        LuiInfo lui = luiService.getLui(activityOfferingId, contextInfo);
        if (lui == null) {
            throw new DoesNotExistException("Activity offering ID does not exist: " + activityOfferingId);
        }
        // The seat pool definition is connected only via the entity.  The DTO does not store the
        // activity offering ID.
        SeatPoolDefinitionEntity seatPoolEntity = seatPoolDefinitionDao.find(seatPoolDefinitionId);
        String fetchedId = seatPoolEntity.getActivityOfferingId();
        if (!fetchedId.equals(activityOfferingId)) {
            throw new InvalidParameterException("activityOfferingId does not match the one in seatpool: " + activityOfferingId);
        }
        seatPoolEntity.setActivityOfferingId(null); // Remove the activity offering ID.
        try {
            seatPoolDefinitionDao.merge(seatPoolEntity);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("version mismatch exception for seatPoolEntity.id=" + seatPoolEntity.getId(), e);
        }
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    public StatusInfo changeCourseOfferingState(String courseOfferingId, String nextStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LuiInfo lui = luiService.getLui(courseOfferingId, contextInfo);
        String thisStateKey = lui.getStateKey();

        if (StringUtils.isNotBlank(nextStateKey) && !StringUtils.equals(thisStateKey,nextStateKey)){
            StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(courseOfferingId,nextStateKey,contextInfo);
            if (statusInfo.getIsSuccess()){

                lui.setStateKey(nextStateKey);
                try{
                    luiService.updateLui(lui.getId(), lui, contextInfo);
                }catch(Exception e){
                    throw new OperationFailedException(FAILED_TO_UPDATE_LUI_STATE_ERROR_MESSAGE, e);
                }

                String propagationKey = thisStateKey + ":" + nextStateKey;
                Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(courseOfferingId,propagationKey,contextInfo);
                for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                    if (!statusInfo1.getIsSuccess()){
                        throw new OperationFailedException(statusInfo1.getMessage());
                    }
                }
                return new StatusInfo();
            }else{
                return statusInfo;
            }
        } else {
            if(StringUtils.isBlank(nextStateKey)) {
                throw new OperationFailedException("The next state key is empty");
            }
            return  new StatusInfo();
        }

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeFormatOfferingState(
            String formatOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LuiInfo lui = luiService.getLui(formatOfferingId, contextInfo);
        String thisStateKey = lui.getStateKey();

        StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(formatOfferingId,nextStateKey,contextInfo);
        if (statusInfo.getIsSuccess()){
            lui.setStateKey(nextStateKey);
            try{
                luiService.updateLui(lui.getId(), lui, contextInfo);
            }catch(Exception e){
                throw new OperationFailedException(FAILED_TO_UPDATE_LUI_STATE_ERROR_MESSAGE, e);
            }

            String propagationKey = thisStateKey + ":" + nextStateKey;
            Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(formatOfferingId,propagationKey,contextInfo);
            for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                if (!statusInfo1.getIsSuccess()){
                    throw new OperationFailedException(statusInfo1.getMessage());
                }
            }

            return new StatusInfo();
        }else{
            return statusInfo;
        }
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeActivityOfferingState(
            String activityOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(activityOfferingId, contextInfo);
        String thisStateKey = lui.getStateKey();

        if(!StringUtils.isEmpty(nextStateKey) && !thisStateKey.equals(nextStateKey)){
            StatusInfo scStatus = stateTransitionsHelper.processStateConstraints(activityOfferingId, nextStateKey, contextInfo);
            if(scStatus.getIsSuccess()) {
                //update entity
                lui.setStateKey(nextStateKey);
                try{
                    luiService.updateLui(lui.getId(), lui, contextInfo);
                }catch(Exception e){
                    throw new OperationFailedException(FAILED_TO_UPDATE_LUI_STATE_ERROR_MESSAGE, e);
                }

                //propagation
                Map<String, StatusInfo> spStatusMap = stateTransitionsHelper.processStatePropagations(activityOfferingId, thisStateKey + ":" + nextStateKey, contextInfo);
                for (StatusInfo statusInfo : spStatusMap.values()) {
                    if (!statusInfo.getIsSuccess()){
                        throw new OperationFailedException(statusInfo.getMessage());
                    }
                }
                // Check if colo
                List<ScheduleRequestSetInfo> scheduleRequestSets = getSchedulingService().searchForScheduleRequestSets(this.buildScheduleRequestSetQBC(lui.getId()), contextInfo);
                for(ScheduleRequestSetInfo scheduleRequestSet : scheduleRequestSets){
                    if (scheduleRequestSet != null && scheduleRequestSet.getRefObjectIds() != null
                        && scheduleRequestSet.getRefObjectIds().size() > 1) { // colo
                        throw new OperationFailedException("At this point, can't remove ADLs for colo");
                    }
                }
                // Delete ADLs, if need be
                String state = lui.getStateKey();
                if (state.equals(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY) ||
                state.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) ) {
                    ActivityOfferingInfo ao = getActivityOffering(lui.getId(), contextInfo);
                    if (ao.getScheduleIds() != null &&
                            !ao.getScheduleIds().isEmpty()) {
                        for (String schedId: ao.getScheduleIds()) {
                            // Delete ADLs (what about colo?)
                            getSchedulingService().deleteSchedule(schedId, contextInfo);
                        }
                        // Update so that this ActivityOffering no longer has ADLs
                        ao.setScheduleIds(new ArrayList<String>());
                        try {
                            updateActivityOffering(lui.getId(), ao, contextInfo);
                        } catch(Exception e) {
                            throw new OperationFailedException("Failed to update activityOffering", e);
                        }
                    }
                }
            } else{
                LOGGER.warn("State Constraints failed for AO id {} to state {}", activityOfferingId, nextStateKey);
                return scStatus;
            }
        }
        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeRegistrationGroupState(
            String registrationGroupId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LuiInfo lui = luiService.getLui(registrationGroupId, contextInfo);
        String thisStateKey = lui.getStateKey();
        StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(registrationGroupId,nextStateKey,contextInfo);
        if (statusInfo.getIsSuccess()){
            lui.setStateKey(nextStateKey);
            boolean exceptionOccurred = false;
            String exceptionMessage = "None";
            try{
                luiService.updateLui(lui.getId(), lui, contextInfo);
            } catch (DataValidationErrorException e) {
                exceptionOccurred = true;
                exceptionMessage = e.getMessage();
            } catch (ReadOnlyException e) {
                exceptionOccurred = true;
                exceptionMessage = e.getMessage();
            } catch (VersionMismatchException e) {
                exceptionOccurred = true;
                exceptionMessage = e.getMessage();
            }catch(Exception e){
                exceptionOccurred = true;
                exceptionMessage = FAILED_TO_UPDATE_LUI_STATE_ERROR_MESSAGE + " " + e.getMessage();
            }
            if (exceptionOccurred) {
                throw new OperationFailedException(exceptionMessage);
            }

            String propagationKey = thisStateKey + ":" + nextStateKey;
            Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(registrationGroupId,propagationKey,contextInfo);
            for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                if (!statusInfo1.getIsSuccess()){
                    throw new OperationFailedException(statusInfo1.getMessage());
                }
            }

            return new StatusInfo();
        }else{
            return statusInfo;
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeActivityOfferingClusterState(
            String activityOfferingClusterId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        ActivityOfferingClusterEntity entity = activityOfferingClusterDao.find(activityOfferingClusterId);
        if (entity == null) {
            throw new DoesNotExistException(activityOfferingClusterId);
        }
        // TODO: Is it OK if the state does not change?
        entity.setActivityOfferingClusterState(nextStateKey);
        this.logAOCStateChange(entity, contextInfo);
        entity.setEntityUpdated(contextInfo);
        try {
            activityOfferingClusterDao.merge(entity);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("version mismatch exception, aoc.id="  + entity.getId(), e);
        }
        StatusInfo status = new StatusInfo ();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private void logAOCStateChange(ActivityOfferingClusterEntity entity, ContextInfo contextInfo) {
        // add the state change to the log
        // TODO: consider changing this to a call to a real logging facility instead of stuffing it in the dynamic attributes
        Date date = contextInfo.getCurrentDate();
        AttributeInfo attr = new AttributeInfo(entity.getActivityOfferingClusterState(), DateFormatters.STATE_CHANGE_DATE_FORMATTER.format(date));
        entity.getAttributes().add(new ActivityOfferingClusterAttributeEntity(attr, entity));
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeSeatPoolDefinitionState(
            String seatPoolDefinitionId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("To be Implemented in M5");
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOfferingCluster(
            String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        ActivityOfferingClusterInfo aoCInfo = getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        List<RegistrationGroupInfo> regGroupsForAOC = new ArrayList<RegistrationGroupInfo>();
        List<RegistrationGroupInfo> regGroups = getRegistrationGroupsByFormatOffering(aoCInfo.getFormatOfferingId(),contextInfo);

        for (RegistrationGroupInfo regGroup : regGroups) {
            if (regGroup.getActivityOfferingClusterId().equals(activityOfferingClusterId)) {
                regGroupsForAOC.add(regGroup);
            }
        }

        return regGroupsForAOC;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingInfo> getActivityOfferingsByCluster(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> aoIds = activityOfferingClusterDao.getActivityOfferingIdsByClusterId(activityOfferingClusterId);
        return getActivityOfferingsByIds(aoIds, contextInfo);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteActivityOfferingClusterCascaded(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> rgInfos =
                getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        List<String> failedToDelete = new ArrayList<String>();
        for (RegistrationGroupInfo rgInfo: rgInfos) {
            String id = rgInfo.getId();
            try {
                // Delete as many as you can...
                StatusInfo statusInfo = deleteRegistrationGroup(id, contextInfo);
                if (!statusInfo.getIsSuccess()) {
                    failedToDelete.add(id);
                }
                // Hopefully, the only exceptions deleteRegGroup throws
            } catch (DoesNotExistException e) {
                failedToDelete.add(id);
            } catch (InvalidParameterException e) {
                failedToDelete.add(id);
            } catch (MissingParameterException e) {
                failedToDelete.add(id);
            } catch (OperationFailedException e) {
                failedToDelete.add(id);
            } catch (PermissionDeniedException e) {
                failedToDelete.add(id);
            }
        }
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);
        if (failedToDelete.isEmpty()) {
            try {
                // Call non-cascaded version
                deleteActivityOfferingCluster(activityOfferingClusterId, contextInfo);
            } catch (DependentObjectsExistException e) {
                statusInfo.setSuccess(Boolean.FALSE);
                statusInfo.setMessage("Dependent objects exist: " + e.getMessage());
            }
        } else {
            // Some reg groups still exist, so error.
            statusInfo.setSuccess(Boolean.FALSE);

            //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
            StringBuilder builder = new StringBuilder("Failed to delete:");
            for (String str: failedToDelete) {
                builder.append(" " + str);
            }
            statusInfo.setMessage(builder.toString());
        }
        if (!statusInfo.getIsSuccess()) {
            // Only doing this because the mock impl appears to do this too.
            throw new OperationFailedException(statusInfo.getMessage());
        }
        return statusInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getActivityOfferingClustersIdsByFormatOffering(
            String formatOfferingId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingClusterEntity> entities = activityOfferingClusterDao.getByFormatOffering(formatOfferingId);
        List<String> list = new ArrayList<String>(entities.size());
        for (ActivityOfferingClusterEntity entity : entities) {
            list.add(entity.getId());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForActivityOfferingClusterIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<String> results = aoCriteriaLookupService.lookupIds(ActivityOfferingClusterEntity.class, criteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityOfferingClusterInfo> searchForActivityOfferingClusters(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<ActivityOfferingClusterEntity> results = aoCriteriaLookupService.lookup(ActivityOfferingClusterEntity.class, criteria);
        List<ActivityOfferingClusterInfo> activityOfferingClusterInfos = new ArrayList<ActivityOfferingClusterInfo>(results.getResults().size());
        for (ActivityOfferingClusterEntity activityOfferingClusterEntity : results.getResults()) {
            ActivityOfferingClusterInfo aocInfo = activityOfferingClusterEntity.toDto();
            activityOfferingClusterInfos.add(aocInfo);
        }
        return activityOfferingClusterInfos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForFormatOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);

        GenericQueryResults<String> results = foCriteriaLookupService.lookupIds(LuiEntity.class, newCriteria);
        return results.getResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormatOfferingInfo> searchForFormatOfferings(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Add luiType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);

        GenericQueryResults<LuiEntity> results = foCriteriaLookupService.lookup(LuiEntity.class, newCriteria);
        List<FormatOfferingInfo> infos = new ArrayList<FormatOfferingInfo>(results.getResults().size());
        for (LuiEntity lui : results.getResults()) {
            try {
                FormatOfferingInfo co = this.getFormatOffering(lui.getId(), contextInfo);
                infos.add(co);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException(lui.getId(), ex);
            }
        }
        return infos;
    }

    protected boolean luiExists(String luiId, ContextInfo context){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", luiId));

        QueryByCriteria criteria = qbcBuilder.build();

        GenericQueryResults<String> results = coCriteriaLookupService.lookupIds(LuiEntity.class, criteria);
        List<String> ids = results.getResults();

        if( ids == null || ids.isEmpty() ) {
            return false;
        }

        return true;
    }

    /**
     * Because the ScheduleRequestService does not have an "exists" check
     * we need to get the schedule request sets via a queryByCriteria search
     * method. if we don't then the DoesNotExist exception will break
     * us out of a transaction.
     * @param luiId
     * @return
     */
    protected QueryByCriteria buildScheduleRequestSetQBC(String luiId){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", luiId));

        return qbcBuilder.build();
    }

    public void setActivityOfferingClusterDao(ActivityOfferingClusterDaoApi activityOfferingClusterDao) {
        this.activityOfferingClusterDao = activityOfferingClusterDao;
    }

    public void setActivityOfferingTransformer(ActivityOfferingTransformer activityOfferingTransformer) {
        this.activityOfferingTransformer = activityOfferingTransformer;
    }

    public void setBusinessLogic(CourseOfferingServiceBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setCourseOfferingTransformer(CourseOfferingTransformer courseOfferingTransformer) {
        this.courseOfferingTransformer = courseOfferingTransformer;
    }

    public void setOfferingCodeGenerator(CourseOfferingCodeGenerator offeringCodeGenerator) {
        this.offeringCodeGenerator = offeringCodeGenerator;
    }

    public void setRegistrationGroupTransformer(RegistrationGroupTransformer registrationGroupTransformer) {
        this.registrationGroupTransformer = registrationGroupTransformer;
    }

    public void setRgAssembler(RegistrationGroupAssembler rgAssembler) {
        this.registrationGroupAssembler = rgAssembler;
    }

    public void setSeatPoolDefinitionDao(SeatPoolDefinitionDaoApi seatPoolDefinitionDao) {
        this.seatPoolDefinitionDao = seatPoolDefinitionDao;
    }

    public StateTransitionsHelper getStateTransitionsHelper() {
        return stateTransitionsHelper;
    }

    public void setStateTransitionsHelper(StateTransitionsHelper stateTransitionsHelper) {
        this.stateTransitionsHelper = stateTransitionsHelper;
    }

    public AcademicCalendarService getAcalService() {
    return acalService;
}

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseWaitListService getCourseWaitListService() {
        return courseWaitListService;
    }

    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }

    public ExamOfferingService getExamOfferingService() {
        return examOfferingService;
    }

    public void setExamOfferingService(ExamOfferingService examOfferingService) {
        this.examOfferingService = examOfferingService;
    }

    public void setCoCriteriaLookupService(CriteriaLookupService coCriteriaLookupService) {
        this.coCriteriaLookupService = coCriteriaLookupService;
    }

    public void setAoCriteriaLookupService(CriteriaLookupService aoCriteriaLookupService) {
        this.aoCriteriaLookupService = aoCriteriaLookupService;
    }

    public void setFoCriteriaLookupService(CriteriaLookupService foCriteriaLookupService) {
        this.foCriteriaLookupService = foCriteriaLookupService;
    }

    public void setRgCriteriaLookupService(CriteriaLookupService rgCriteriaLookupService) {
        this.rgCriteriaLookupService = rgCriteriaLookupService;
    }

    public void setSpCriteriaLookupService(CriteriaLookupService spCriteriaLookupService) {
        this.spCriteriaLookupService = spCriteriaLookupService;
    }

    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public RoomService getRoomService() {
        if (roomService == null){
            roomService = (RoomService)GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    @SuppressWarnings("unused")
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public void setCourseOfferingServiceExtender(CourseOfferingServiceExtender extender) {
        this.courseOfferingServiceExtender = extender;
    }

    public CourseOfferingServiceExtender getCourseOfferingServiceExtender() {
        return this.courseOfferingServiceExtender;
    }

	@Override
	public List<CourseOfferingInfo> getFormatOfferingsByIds(
			List<String> formatOfferingIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<CourseOfferingInfo> getSeatPoolDefinitionsByIds(
			List<String> seatPoolDefinitionIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}
}
