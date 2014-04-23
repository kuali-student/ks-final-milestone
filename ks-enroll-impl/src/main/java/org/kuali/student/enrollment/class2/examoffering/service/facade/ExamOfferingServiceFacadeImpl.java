package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.class2.examoffering.krms.evaluator.ExamOfferingSlottingEvaluator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Application Service Layer to provide the functionally specified functionality
 * using several service calls.
 *
 * @author Kuali Student Team
 */
public class ExamOfferingServiceFacadeImpl implements ExamOfferingServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamOfferingServiceFacadeImpl.class);

    private AtpService atpService;
    private ExamService examService;
    private CourseOfferingService courseOfferingService;
    private ExamOfferingService examOfferingService;
    private CourseOfferingSetService socService;
    private SchedulingService schedulingService;
    private TypeService typeService;
    private boolean setLocation;

    private ExamOfferingSlottingEvaluator scheduleEvaluator;

    private enum Driver {PER_CO, PER_FO, PER_AO, NONE}

    @Override
    public ExamOfferingResult generateFinalExamOffering(CourseOfferingInfo courseOfferingInfo, String termId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return generateFinalExamOfferingOptimized(courseOfferingInfo, termId, optionKeys, null, context);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingOptimized(CourseOfferingInfo courseOfferingInfo, String termId, List<String> optionKeys,
                                                Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        String examPeriodId = null;
        try{
            examPeriodId = this.getExamPeriodId(termId, context);
        } catch (DoesNotExistException e) {
            return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND);
        }

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)) {
            boolean useFinalExamMatrix = useMatrix(courseOfferingInfo);
            return generateFinalExamOfferingsPerAOOptimized(courseOfferingInfo.getId(), termId, examPeriodId, optionKeys, foIdToListOfAOs, useFinalExamMatrix, context);
        } else if (driver.equals(Driver.PER_FO)) {
            return generateFinalExamOfferingsPerFOOptimized(courseOfferingInfo.getId(), termId, examPeriodId, optionKeys, foIdToListOfAOs, context);
        } else if (driver.equals(Driver.PER_CO)) {
            return generateFinalExamOfferingsPerCOOptimized(courseOfferingInfo, termId, examPeriodId, optionKeys, foIdToListOfAOs, context);
        } else if (driver.equals(Driver.NONE)) {
            if ((!optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) && (isSocPublished(termId, context))) {
                cancelFinalExamOfferings(courseOfferingInfo.getId(), context);
            } else {
                removeFinalExamOfferingsFromCO(courseOfferingInfo.getId(), context);
            }
        }

        return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_DRIVER_UNKNOWN);
    }

    /**
     * The Final_Exam_Use_Matrix attribute is set to TRUE when the user selected the toggle on the edit Course Offering screen.
     * If toggle is deselected, the system should not use the matrix to do any slotting on this course offering.
     */
    private boolean useMatrix(CourseOfferingInfo courseOfferingInfo) {
        return Boolean.parseBoolean(courseOfferingInfo.getAttributeValue(CourseOfferingServiceConstants.FINAL_EXAM_USE_MATRIX));
    }

    /**
     * The Matrix Override attribute is set to TRUE when the user manually enterred slotting information on the Manage Exam
     * Offering screen. When this attribute exist and is set to TRUE, the system should not re-slot this exam offering with
     * the matrix.
     */
    private boolean userOverride(ExamOfferingInfo examOfferingInfo) {
        return Boolean.parseBoolean(examOfferingInfo.getAttributeValue(ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_OVERRIDE_ATTR));
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingForAO(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo,
                                                     String termId, String finalExamLevelTypeKey, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        String examPeriodId = null;
        try{
            examPeriodId = this.getExamPeriodId(termId, context);
        } catch (DoesNotExistException e) {
            return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND);
        }

        //Build up the map of AO for the methods so it does not have to make a DB call.
        Map<String,List<ActivityOfferingInfo>> foIdToListOfAOs = new HashMap<String, List<ActivityOfferingInfo>>();
        List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>();
        aoInfos.add(activityOfferingInfo);
        foIdToListOfAOs.put(activityOfferingInfo.getFormatOfferingId(),aoInfos);

        Driver driver = calculateEODriver(courseOfferingInfo);
        boolean  useFinalExamMatrix  = Boolean.parseBoolean(courseOfferingInfo.getAttributeValue(CourseOfferingServiceConstants.FINAL_EXAM_USE_MATRIX));
        if (driver.equals(Driver.PER_AO)) {
            return generateFinalExamOfferingsPerAOOptimized(courseOfferingInfo.getId(), termId, examPeriodId, optionKeys, foIdToListOfAOs, useFinalExamMatrix, context);
        } else if (driver.equals(Driver.PER_CO)) {
            return generateFinalExamOfferingsPerCOOptimized(courseOfferingInfo, termId, examPeriodId, optionKeys, foIdToListOfAOs, context);
        } else if (driver.equals(Driver.NONE)) {
            // Final exam type is not STANDARD or no exam driver was selected. No exam offerings are generated
        }

        return new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_DRIVER_UNKNOWN);
    }

    @Override
    public String getExamPeriodId(String termID, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        //Get the Exam Period Id for the term.
        String epId = null;
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByTypeAndAtp(termID,
                AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY, context);
        for (AtpAtpRelationInfo atpRelation : results) {
            epId = atpRelation.getRelatedAtpId();
            break;
        }

        // Check that an exam period was created for the target term
        if (epId == null) {
            throw new DoesNotExistException("Generate final exam offerings skipped because an exam period does not exist for the target term.");
        }
        return epId;
    }

    private Driver calculateEODriver(CourseOfferingInfo co) {
        if (FinalExam.STANDARD.toString().equals(co.getFinalExamType())) {

            //Check driver.
            String examDriver = getAttrValueForKey(co, CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            if (LuServiceConstants.LU_EXAM_DRIVER_AO_KEY.equals(examDriver)) {
                return Driver.PER_AO;
            } else if (LuServiceConstants.LU_EXAM_DRIVER_CO_KEY.equals(examDriver)) {
                return Driver.PER_CO;
            }
            return Driver.NONE;
        } else {

            //If it is not a Standard exam, the course offering should not have any exams.
            return Driver.NONE;
        }
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerCO(CourseOfferingInfo courseOffering, String termId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        return generateFinalExamOfferingsPerCOOptimized(courseOffering, termId, examPeriodId, optionKeys, null, context);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerCOOptimized(CourseOfferingInfo courseOffering, String termId, String examPeriodId,
                                                         List<String> optionKeys, Map<String, List<ActivityOfferingInfo>> foIdToListOfAO,
                                                         ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        if (StringUtils.isEmpty(examPeriodId)) {
            throw new MissingParameterException("Exam Period id is not provided.");
        }

        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_CO);
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(courseOffering.getId(), context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        //Slotting parameters
        boolean useFinalExamMatrix = this.useMatrix(courseOffering);
        String termType = this.getAtpService().getAtp(termId, context).getTypeKey();

        ExamOfferingInfo eo = null;
        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per co driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), Driver.PER_CO.name());

            //Create new exam offerings per CO
            for (ExamOfferingRelationInfo eoRelation : eors) {
                eo = eos.get(eoRelation.getExamOfferingId());
                if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                    this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                }
            }

            ExamOfferingResult foResult;
            boolean userOverride = false;
            Map<String, String> contextParms = new HashMap<String, String>();
            contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE, courseOffering.getCourseOfferingCode());

            if (eo == null) {
                //Create a new Exam Offering
                eo = createExamOffering(examPeriodId, ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, Driver.PER_CO.name(),
                        context);
                foResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED);
            } else {
                userOverride = this.userOverride(eo);

                //Remove RDL for Exam Offering if use fe matrix toggle was deselected and user did not override timeslot.
                if(!userOverride && !useFinalExamMatrix){
                    removeExamOfferingRDL(eo, context);
                }
                foResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED);
            }

            //(re)perform slotting if use fe matrix toggle is selected and use did not override timeslot.
            if (!userOverride && useFinalExamMatrix) {
                foResult.getChildren().add(this.getScheduleEvaluator().executeRuleForCOSlotting(courseOffering, eo.getId(),
                        termType, new ArrayList<String>(), context));
            }

            //Create new Exam Offering Relationship
            List<ActivityOfferingInfo> aoInfos = getAOsForFoId(foEntry.getKey().getId(), foIdToListOfAO, context);
            createExamOfferingRelationPerFO(foEntry.getKey().getId(), eo.getId(), aoInfos, context);
            foResult.setContext(contextParms);
            result.getChildren().add(foResult);

        }

        if (isSocPublished(termId, context)) {
            cancelFinalExamOfferings(foToEoRelations, eos, context);
        } else {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        return result;

    }

    private List<ActivityOfferingInfo> getAOsForFoId(String foId, Map<String, List<ActivityOfferingInfo>> foIdToListOfAO, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (foIdToListOfAO != null && !foIdToListOfAO.isEmpty()) {
            LOGGER.info("(2) Using 'cached' AOs");
            return foIdToListOfAO.get(foId);
        } else {
            LOGGER.info("(2) Not using 'cached' AOs");
            return this.getCourseOfferingService().getActivityOfferingsByFormatOffering(
                    foId, context);
        }
    }

    /** Removes all SheduleRequestInfoSets and ScheduleRequestInfos for given Exam Offering
     *
     * @param examOfferingInfo
     */
    public void removeExamOfferingRDL(ExamOfferingInfo examOfferingInfo, ContextInfo context) {
        List<ScheduleRequestSetInfo> scheduleRequestSetInfoList = null;
        try {
            scheduleRequestSetInfoList = getSchedulingService().getScheduleRequestSetsByRefObject(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, examOfferingInfo.getId(), context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!scheduleRequestSetInfoList.isEmpty() || scheduleRequestSetInfoList != null) {
            try {
                for (ScheduleRequestSetInfo scheduleRequestSetInfo : scheduleRequestSetInfoList) {
                    List<ScheduleRequestInfo> scheduleRequestInfoList = getSchedulingService().getScheduleRequestsByScheduleRequestSet(scheduleRequestSetInfo.getId(), context);
                    for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfoList) {
                        getSchedulingService().deleteScheduleRequest(scheduleRequestInfo.getId(), context);
                    }
                    getSchedulingService().deleteScheduleRequestSet(scheduleRequestSetInfo.getId(), context);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByCourseOffering(String courseOfferingId,
                                                                                   ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException, PermissionDeniedException {

        List<String> foIds = new ArrayList<String>();
        List<FormatOfferingInfo> fos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                ContextUtils.createDefaultContextInfo());
        for(FormatOfferingInfo fo : fos){
            foIds.add(fo.getId());
        }

        //Retrieve ExamOfferingRelationInfos
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("lui.id", foIds.toArray()));

        QueryByCriteria criteria = qbcBuilder.build();
        return this.getExamOfferingService().searchForExamOfferingRelations(criteria, contextInfo);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerFO(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        return generateFinalExamOfferingsPerFOOptimized(courseOfferingId, termId, examPeriodId, optionKeys, null, context);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerFOOptimized(String courseOfferingId, String termId, String examPeriodId,
                                                         List<String> optionKeys, Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs,
                                                         ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        if (StringUtils.isEmpty(examPeriodId)) {
            throw new MissingParameterException("Exam Period id is not provided.");
        }

        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_FO);

        //Retrieve all format offerings linked to the course offering.
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(courseOfferingId, context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per fo driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), Driver.PER_FO.name());

            //Create new exam offerings per FO
            boolean hasEo = false;
            for (ExamOfferingRelationInfo eoRelation : eors) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                    this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                    break;
                }
            }

            if (!hasEo) {
                //Create a new Exam Offering
                ExamOfferingInfo eo = createExamOffering(examPeriodId, Driver.PER_FO.name(),
                        ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                result.getChildren().add(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED));

                //Create new Exam Offering Relationship
                List<ActivityOfferingInfo> aoInfos = getAOsForFoId(foEntry.getKey().getId(), foIdToListOfAOs, context);
                createExamOfferingRelationPerFO(foEntry.getKey().getId(), eo.getId(), aoInfos, context);
            } else {
                result.getChildren().add(new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED));
            }

        }

        if (isSocPublished(termId, context)) {
            cancelFinalExamOfferings(foToEoRelations, eos, context);
        } else {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        return result;
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerAO(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                                boolean useFinalExamMatrix, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        // Default to "optimized" with null parameter
        return generateFinalExamOfferingsPerAOOptimized(courseOfferingId, termId, examPeriodId, optionKeys, null, useFinalExamMatrix, context);
    }

    @Override
    public ExamOfferingResult generateFinalExamOfferingsPerAOOptimized(String courseOfferingId, String termId, String examPeriodId, List<String> optionKeys,
                                                         Map<String, List<ActivityOfferingInfo>> foIdToListOfAOs, boolean useFinalExamMatrix, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        if (StringUtils.isEmpty(examPeriodId)) {
            throw new MissingParameterException("Exam Period id is not provided.");
        }

        ExamOfferingResult result = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_AO);

        //Retrieve all format offerings linked to the course offering.
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = loadExamOfferingRelationships(courseOfferingId, context);
        if (optionKeys.contains(ExamOfferingServiceFacade.RECREATE_OPTION_KEY)) {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        boolean socPublished = isSocPublished(termId, context);

        // Slotting parameters.
        String termType = this.getAtpService().getAtp(termId, context).getTypeKey();
        List<String> evaluatorOptions = new ArrayList<String>();
        if(this.isSetLocation()){
            evaluatorOptions.add(ExamOfferingSlottingEvaluator.USE_AO_LOCATION_OPTION_KEY);
        }

        Map<String, ExamOfferingInfo> eos = loadExamOfferings(foToEoRelations, context);
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {

            //Get all existing eo as per ao driver, and remove them from the map.
            List<ExamOfferingRelationInfo> eors = getExistingExamOfferingsPerDriver(eos, foEntry.getValue(), Driver.PER_AO.name());

            TypeInfo finalExamLevelType = null;
            if (foEntry.getKey().getFinalExamLevelTypeKey() != null) {
                finalExamLevelType = this.getTypeService().getType(foEntry.getKey().getFinalExamLevelTypeKey(), context);
            }

            //Create new exam offerings per AO
            List<ActivityOfferingInfo> aoInfos = this.getAOsForFoId(foEntry.getKey().getId(), foIdToListOfAOs, context);
            for (ActivityOfferingInfo aoInfo : aoInfos) {
                //Do not create exam offerings for canceled activity offerings.
                if (LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())) continue;

                //Check and Update EO type according to allowed FO
                if (!checkDriverActivity(aoInfo, eors, finalExamLevelType, socPublished, context)) {
                    continue; //next aoInfo if driver activity does not match.
                }

                ExamOfferingInfo eo = null;
                for (ExamOfferingRelationInfo eoRelation : eors) {
                    if (eoRelation.getActivityOfferingIds().contains(aoInfo.getId())) {
                        eo = eos.get(eoRelation.getExamOfferingId());
                        if (eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                            this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                                    ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY, context);
                        }
                        break;
                    }
                }

                boolean userOverride = false;
                ExamOfferingResult aoResult = null;
                Map<String, String> contextParms = new HashMap<String, String>();
                contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE, aoInfo.getCourseOfferingCode());
                contextParms.put(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE, aoInfo.getActivityCode());

                if (eo == null) {
                    //Retrieve corresponding eo state for ao.
                    String eoState = this.getExamOfferingStateForActivityOffering(aoInfo);
                    eo = createFinalExamOfferingPerAO(foEntry.getKey().getId(), aoInfo, foEntry.getKey().getFinalExamLevelTypeKey(),
                            examPeriodId, eoState, termType, context);
                    aoResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_CREATED, contextParms);
                } else {
                    userOverride = this.userOverride(eo);

                    //Remove RDL for Exam Offering if use fe matrix toggle was deselected and user did not override timeslot.
                    if(!userOverride && !useFinalExamMatrix){
                        aoResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED, contextParms);
                        removeExamOfferingRDL(eo, context);
                    } else {
                        aoResult = new ExamOfferingResult(ExamOfferingServiceConstants.EXAM_OFFERING_UNCHANGED, contextParms);
                    }
                }

                //(re)perform slotting if use fe matrix toggle is selected and use did not override timeslot.
                if (!userOverride && useFinalExamMatrix) {
                    aoResult.getChildren().add(this.getScheduleEvaluator().executeRuleForAOSlotting(aoInfo, eo.getId(),
                            termType, evaluatorOptions, context));
                }
                result.getChildren().add(aoResult);

            }
        }

        if (socPublished) {
            cancelFinalExamOfferings(foToEoRelations, eos, context);
        } else {
            removeFinalExamOfferingsFromCO(foToEoRelations, context);
        }

        return result;

    }

    /**
     * Check if the ao type matches the fo driver activity. If not remove or cancel the exam offerings based on
     * the soc state, else return true.
     */
    private boolean checkDriverActivity(ActivityOfferingInfo aoInfo, List<ExamOfferingRelationInfo> eors, TypeInfo finalExamLevelType,
                                        boolean socPublished, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (finalExamLevelType == null) {
            return true;
        }

        TypeInfo activityType = this.getTypeService().getType(aoInfo.getTypeKey(), context);
        //cancel if eo exits but not in sync with allowed FO
        if (!activityType.getName().equals(finalExamLevelType.getName())) {
            ExamOfferingRelationInfo eor = null;
            for (ExamOfferingRelationInfo eoRelation : eors) {
                if (eoRelation.getActivityOfferingIds().contains(aoInfo.getId())) {
                    eor = eoRelation;
                    break;
                }
            }

            //Remove or cancel unwanted exam offering based on soc state.
            if (eor != null) {
                if (socPublished) {
                    this.getExamOfferingService().changeExamOfferingState(eor.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
                } else {
                    this.getExamOfferingService().deleteExamOfferingRelation(eor.getId(), context);
                    this.getExamOfferingService().deleteExamOffering(eor.getExamOfferingId(), context);
                }
            }
            return false;
        }
        return true;
    }

    private String getExamOfferingStateForActivityOffering(ActivityOfferingInfo aoInfo) {
        if (LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())){
            return ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY;
        }
        if (LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY.equals(aoInfo.getStateKey())){
            return ExamOfferingServiceConstants.EXAM_OFFERING_SUSPENDED_STATE_KEY;
        }
        return ExamOfferingServiceConstants.EXAM_OFFERING_DRAFT_STATE_KEY;
    }

    private boolean isSocPublished(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        boolean isSocPublished = false;
        String socStateKey = null;

        SocInfo soc = CourseOfferingSetUtil.getMainSocForTermId(termId, context);
        if (soc != null) {
            socStateKey = soc.getStateKey();
        }

        if (CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY.equals(socStateKey)
                || CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY.equals(socStateKey)) {
            isSocPublished = true;
        }
        return isSocPublished;
    }

    private List<ExamOfferingRelationInfo> getExistingExamOfferingsPerDriver(Map<String, ExamOfferingInfo> eos,
                                                                             List<ExamOfferingRelationInfo> eors, String driver)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ExamOfferingRelationInfo> eorsForDriver = new ArrayList<ExamOfferingRelationInfo>();
        for (ExamOfferingRelationInfo eoRelation : eors) {
            ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());

            if (isPerDriver(driver, eo)) {
                eorsForDriver.add(eoRelation);
            }
        }
        for (ExamOfferingRelationInfo eoRelation : eorsForDriver) {
            eors.remove(eoRelation);
        }
        return eorsForDriver;
    }

    private boolean isPerDriver(String driver, ExamOfferingInfo eo) {
        for (AttributeInfo attribute : eo.getAttributes()) {
            if (attribute.getKey().equals(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR)) {
                if (attribute.getValue().equals(driver)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> loadExamOfferingRelationships(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, context);
        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = new HashMap<FormatOfferingInfo, List<ExamOfferingRelationInfo>>();
        for (FormatOfferingInfo foInfo : foInfos) {
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            foToEoRelations.put(foInfo, eoRelations);
        }
        return foToEoRelations;
    }

    private Map<String, ExamOfferingInfo> loadExamOfferings(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Map<String, ExamOfferingInfo> eos = new HashMap<String, ExamOfferingInfo>();
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (eo == null) {
                    eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), context);
                    eos.put(eo.getId(), eo);
                }
            }
        }
        return eos;
    }

    private void createExamOfferingRelationPerFO(String foId, String eoId, List<ActivityOfferingInfo> aoInfos, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            if(!LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(aoInfo.getStateKey())){
                aoIds.add(aoInfo.getId());
            }
        }
        createExamOfferingRelation(foId, eoId, aoIds, context);
    }

    private ExamOfferingInfo createFinalExamOfferingPerAO(String foId, ActivityOfferingInfo activityOffering, String activityDriver, String examPeriodId,
                                              String stateKey, String termType, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //Create a new Exam Offering
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();

        AttributeInfo attribute = new AttributeInfo();
        attribute.setKey(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
        attribute.setValue(Driver.PER_AO.name());
        attributes.add(attribute);

        attribute = new AttributeInfo();
        attribute.setKey(ExamOfferingServiceConstants.FINAL_EXAM_ACTIVITY_DRIVER_ATTR);
        attribute.setValue(activityDriver);
        attributes.add(attribute);

        ExamOfferingInfo eo = this.createExamOffering(examPeriodId, stateKey, attributes, context);

        //Create new Exam Offering Relationship
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOffering.getId());
        createExamOfferingRelation(foId, eo.getId(), aoIds, context);

        return eo;
    }

    @Override
    public void removeFinalExamOfferingsFromCO(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations = this.loadExamOfferingRelationships(courseOfferingId, context);
        removeFinalExamOfferingsFromCO(foToEoRelations, context);

    }

    private void removeFinalExamOfferingsFromCO(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DoesNotExistException {

        Set<String> eoIds = new HashSet<String>();
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            //Remove the relationships.
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                this.getExamOfferingService().deleteExamOfferingRelation(eoRelation.getId(), context);
                eoIds.add(eoRelation.getExamOfferingId());
            }
            foToEoRelations.put(foEntry.getKey(), new ArrayList());
        }

        //Delete orphaned exam offerings.
        for (String eoId : eoIds) {
            this.getExamOfferingService().deleteExamOffering(eoId, context);
        }
    }

    private void cancelFinalExamOfferings(Map<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foToEoRelations, Map<String, ExamOfferingInfo> eos, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        for (Map.Entry<FormatOfferingInfo, List<ExamOfferingRelationInfo>> foEntry : foToEoRelations.entrySet()) {
            for (ExamOfferingRelationInfo eoRelation : foEntry.getValue()) {
                ExamOfferingInfo eo = eos.get(eoRelation.getExamOfferingId());
                if (!eo.getStateKey().equals(ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY)) {
                    this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(),
                            ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
                }
            }
        }
    }

    public void cancelFinalExamOfferings(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        changeFinalExamOfferingsState(courseOfferingId, ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY, context);
    }

    @Override
    public void changeFinalExamOfferingsState(String courseOfferingId, String stateKey, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        //Retrieve all format offerings linked to the course offering.
        List<FormatOfferingInfo> foInfos = this.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,
                context);

        for (FormatOfferingInfo foInfo : foInfos) {
            //Retrieve all exam offerings linked to the format offering.
            List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                    foInfo.getId(), context);
            for (ExamOfferingRelationInfo eoRelation : eoRelations) {
                this.getExamOfferingService().changeExamOfferingState(eoRelation.getExamOfferingId(), stateKey, context);
            }
        }
    }

    /**
     * Create a new Exam Offering.
     *
     * @param examPeriodId
     * @param stateKey
     * @param driver
     * @param context
     * @return
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     */
    private ExamOfferingInfo createExamOffering(String examPeriodId, String stateKey, String driver, ContextInfo context) throws MissingParameterException,
            InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            DataValidationErrorException, ReadOnlyException {

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();

        if (driver != null) {
            AttributeInfo attribute = new AttributeInfo();
            attribute.setKey(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
            attribute.setValue(driver);
            attributes.add(attribute);
        }

        return this.createExamOffering(examPeriodId, stateKey, attributes, context);
    }

    /**
     * Create a new Exam Offering.
     *
     * @param examPeriodId
     * @param stateKey
     * @param attributes
     * @param context
     * @return
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     */
    private ExamOfferingInfo createExamOffering(String examPeriodId, String stateKey, List<AttributeInfo> attributes, ContextInfo context) throws MissingParameterException,
            InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            DataValidationErrorException, ReadOnlyException {

        ExamOfferingInfo eo = new ExamOfferingInfo();
        eo.setTypeKey(ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);
        eo.setStateKey(stateKey);
        eo.setExamId(this.getCanonicalExam(context));
        eo.setExamPeriodId(examPeriodId);
        eo.getAttributes().addAll(attributes);

        return this.getExamOfferingService().createExamOffering(eo.getExamPeriodId(),
                eo.getExamId(), eo.getTypeKey(), eo, context);
    }

    /**
     * Create a new Exam Offering Relationship.
     *
     * @param formatOfferingId
     * @param examOfferingId
     * @param aoIds
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    private ExamOfferingRelationInfo createExamOfferingRelation(String formatOfferingId, String examOfferingId,
                                                                List<String> aoIds, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        ExamOfferingRelationInfo eoRelation = new ExamOfferingRelationInfo();
        eoRelation.setFormatOfferingId(formatOfferingId);
        eoRelation.setExamOfferingId(examOfferingId);
        eoRelation.setActivityOfferingIds(aoIds);
        eoRelation.setPopulationIds(new ArrayList<String>());
        eoRelation.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY);

        return this.getExamOfferingService().createExamOfferingRelation(formatOfferingId,
                examOfferingId, eoRelation.getTypeKey(), eoRelation, context);
    }

    private String getCanonicalExam(ContextInfo context) throws MissingParameterException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> examIds = this.getExamService().getExamIdsByType(ExamServiceConstants.EXAM_FINAL_TYPE_KEY, context);
        for (String examId : examIds) {
            return examId; //Return the first one as there should only be one canonical final exam.
        }
        return null;
    }

    private String getAttrValueForKey(CourseOfferingInfo courseOfferingInfo, String key) {
        for (AttributeInfo attr : courseOfferingInfo.getAttributes()) {
            if (key.equals(attr.getKey())) {
                return attr.getValue();
            }
        }
        return null;
    }

    @Override
    public ExamOfferingResult reslotExamOffering(CourseOfferingInfo courseOfferingInfo, ActivityOfferingInfo activityOfferingInfo,
                                                 ExamOfferingInfo examOfferingInfo, String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        String termType = this.getAtpService().getAtp(termId, context).getTypeKey();

        Driver driver = calculateEODriver(courseOfferingInfo);
        if (driver.equals(Driver.PER_AO)) {
            if(activityOfferingInfo==null){
                throw new MissingParameterException("Activity Offering is null.");
            }

            List<String> evaluatorOptions = new ArrayList<String>();
            if(this.isSetLocation()){
                evaluatorOptions.add(ExamOfferingSlottingEvaluator.USE_AO_LOCATION_OPTION_KEY);
            }

            return this.getScheduleEvaluator().executeRuleForAOSlotting(activityOfferingInfo, examOfferingInfo.getId(), termType, evaluatorOptions, context);
        } else if (driver.equals(Driver.PER_CO)) {
            return this.getScheduleEvaluator().executeRuleForCOSlotting(courseOfferingInfo, examOfferingInfo.getId(), termType, new ArrayList<String>(), context);
        } else if (driver.equals(Driver.NONE)) {
            // Final exam type is not STANDARD or no exam driver was selected. No exam offerings are generated
        }
        return null;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public ExamService getExamService() {
        return examService;
    }

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public ExamOfferingService getExamOfferingService() {
        return examOfferingService;
    }

    public void setExamOfferingService(ExamOfferingService examOfferingService) {
        this.examOfferingService = examOfferingService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public ExamOfferingSlottingEvaluator getScheduleEvaluator() {
        return scheduleEvaluator;
    }

    public void setScheduleEvaluator(ExamOfferingSlottingEvaluator scheduleEvaluator) {
        this.scheduleEvaluator = scheduleEvaluator;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public boolean isSetLocation() {
        return setLocation;
    }

    public void setSetLocation(boolean setLocation) {
        this.setLocation = setLocation;
    }
}
